
from flask_login import login_required, current_user
from beerbackend.beer.forms import BeerForm
from flask import Blueprint, flash, redirect, render_template, request, url_for
from beerbackend.utils import flash_errors
from beerbackend.user.models import User, Beer, Rating
from beerbackend.user.forms import RateForm
from flask_restful import Resource, Api, reqparse, fields, marshal_with


def get_color(color):
    if color < 2.5:
        return '#ffff45'
    elif color < 3.5:
        return '#ffe93e'
    elif color < 5.5:
        return '#fed849'
    elif color < 8.5:
        return '#ffa846'
    elif color < 11.5:
        return '#f49f44'
    elif color < 14.5:
        return '#d77f59'
    elif color < 17.5:
        return '#94523a'
    elif color < 19.5:
        return '#804541'
    elif color < 23.5:
        return '#5b342f'
    elif color < 28.5:
        return '#4c3b2b'
    elif color < 35:
        return '#38302e'
    else:
        return '#31302c'


blueprint = Blueprint('beer', __name__, url_prefix='/beers', static_folder='../static')


@blueprint.route('/add', methods=['GET', 'POST'])
@login_required
def add():
    form = BeerForm(request.form)
    if request.method == 'POST':
        if form.validate_on_submit():
            Beer.create(beer_name=form.beer_name.data, abv=form.abv.data, bitter=form.bitter.data,
                        color=form.color.data, fruit=form.fruit.data, hoppy=form.hoppy.data, malty=form.malty.data,
                        roasty=form.roasty.data, smoke=form.smoke.data, sour=form.sour.data,
                        spice=form.spice.data,
                        sweet=form.sweet.data, wood=form.wood.data, family=form.family.data)

            flash('You added a beer: success')
            redirect_url = request.args.get('next') or url_for('beer.all')
            return redirect(redirect_url)
        else:
            flash_errors(form)
    return render_template('beers/addbeer.html', form=form)


@blueprint.route('/', methods=['GET'])
def all():
    beers = Beer.query.all()
    return render_template('beers/all.html', beers=beers, str=str)

@blueprint.route('/<int:beer_id>', methods=['GET', 'POST'])
def getbeer(beer_id):
    form = RateForm(request.form)
    beer = Beer.query.filter(Beer.id == beer_id).first()
    if current_user.is_authenticated:
        rating = None
        if beer_id in [rating.beer_id for rating in current_user.ratings]:
            rating = next((rating for rating in current_user.ratings if\
                           rating.beer_id == beer_id), None)

        if request.method == 'POST':
            if form.validate_on_submit():
                if rating:
                    rating.update_time(rating=form.rating.data)
                else:
                    rating = Rating.create(rating=form.rating.data, beer_id=beer_id,
                                  user_id=current_user.id)
            else:
                flash_errors(form)

        return render_template('beers/onebeer.html', beer=beer, get_color=get_color,
                              user_profile=current_user.get_profile(), rating=rating, form=form)
    else:
        return render_template('beers/onebeer.html', beer=beer, get_color=get_color,
                               user_profile=None, rating=None, form=None)
