# -*- coding: utf-8 -*-
"""The app module, containing the app factory function."""
from flask import Flask, render_template, make_response
from flask_restful import Api
from simplejson import dumps

from beerbackend import commands
from beerbackend.public.views import blueprint as public_bp
from beerbackend.beer.views import blueprint as beer_bp
from beerbackend.user.api import *
from beerbackend.beer.api import *
from beerbackend.user.views import blueprint as user_bp
from beerbackend.assets import assets
from beerbackend.extensions import bcrypt, cache, db, debug_toolbar, login_manager, migrate
from beerbackend.settings import ProdConfig
import flask_wtf.csrf

def truncatenum(number):
    return '{:02.1f}'.format(number)

def create_app(config_object=ProdConfig):
    """An application factory, as explained here: http://flask.pocoo.org/docs/patterns/appfactories/.

    :param config_object: The configuration object to use.
    """
    app = Flask(__name__)
    prot = flask_wtf.csrf.CsrfProtect(app)
    api = Api(app, decorators=[prot.exempt])

    @api.representation('application/json')
    def output_json(data, code, headers=None):
        resp = make_response(dumps(data), code)
        resp.headers.extend(headers or {})
        return resp

    app.config.from_object(config_object)
    app.jinja_env.globals.update(truncatenum=truncatenum)
    register_extensions(app)
    register_blueprints(app)
    register_api(api)
    register_errorhandlers(app)
    # register_shellcontext(app)
    register_commands(app)
    return app


def register_extensions(app):
    """Register Flask extensions."""
    assets.init_app(app)
    bcrypt.init_app(app)
    cache.init_app(app)
    db.init_app(app)
    login_manager.init_app(app)
    debug_toolbar.init_app(app)
    migrate.init_app(app, db)
    return None


def register_blueprints(app):
    """Register Flask blueprints."""
    app.register_blueprint(public_bp)
    app.register_blueprint(user_bp)
    app.register_blueprint(beer_bp)
    return None

def register_api(api):
    api.add_resource(BeerApi, '/api/beer')
    api.add_resource(BeersApi, '/api/beer/all')
    api.add_resource(Recommend, '/api/recommend')
    api.add_resource(AuthApi, '/api/auth')
    api.add_resource(UserApi, '/api/user')
    api.add_resource(TasteProfile, '/api/user/taste')
    api.add_resource(UserBeers, '/api/user/beers')
    api.add_resource(LikedBeers, '/api/user/likedbeers')
    api.add_resource(DislikedBeers, '/api/user/dislikedbeers')
    api.add_resource(RateApi, '/api/rate')


def register_errorhandlers(app):
    """Register error handlers."""
    def render_error(error):
        """Render error template."""
        # If a HTTPException, pull the `code` attribute; default to 500
        error_code = getattr(error, 'code', 500)
        return render_template('{0}.html'.format(error_code)), error_code
    for errcode in [401, 404, 500]:
        app.errorhandler(errcode)(render_error)
    return None




def register_commands(app):
    """Register Click commands."""
    app.cli.add_command(commands.test)
    app.cli.add_command(commands.lint)
    app.cli.add_command(commands.clean)
    app.cli.add_command(commands.urls)
