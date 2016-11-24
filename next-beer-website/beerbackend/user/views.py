# -*- coding: utf-8 -*-
"""User views."""
from flask import Blueprint, render_template
from flask_login import login_required, current_user
from flask import Blueprint, flash, redirect, render_template, request, url_for
from beerbackend.utils import flash_errors
from beerbackend.user.forms import QuestionForm


blueprint = Blueprint('user', __name__, url_prefix='/users', static_folder='../static')


@blueprint.route('/')
@login_required
def members():
    """List members."""
    return render_template('users/members.html', user=current_user, profile=current_user.get_profile(),
                           ratings=sorted(current_user.ratings, key=lambda rating: rating.created_at, reverse=True),
                           str=str)


@blueprint.route('/questionnaire', methods=['GET','POST'])
@login_required
def questions():
    form = QuestionForm(request.form)
    if current_user.get_taste_profile():
        profile = current_user.get_taste_profile()
        # fill in default values using python kwargs
        form = QuestionForm(request.form, **profile)
    if request.method == 'POST':
        if form.validate_on_submit():
            current_user.update_taste_profile(malty=form.malty.data, sour=form.sour.data, wood=form.wood.data,
                                      spice=form.spice.data, fruit=form.fruit.data, sweet=form.sweet.data,
                                      roasty=form.roasty.data, bitter=form.bitter.data, smoke=form.smoke.data)
            redirect_url = request.args.get('next') or url_for('user.members')
            return redirect(redirect_url)
        else:
            flash_errors(form)
    return render_template('users/questionnaire.html', taste_profile=current_user.get_taste_profile(), form=form)

@blueprint.route('/recommend', methods=['GET','POST'])
@login_required
def recommend():
    #profile = current_user.get_profile()
    #profile_values = [(value, key) for key, value in profile.items()]
    #profile_values.sort()
    #top = profile_values[len(profile_values)-3: len(profile_values)]
    beer = current_user.reccommend()
    #redirect or some other crap look up above return redirect(url_for('beer.all')+str(current_user.recommend()))
    return redirect(url_for('beer.all')+str(beer.id))
    #return "O hai"
