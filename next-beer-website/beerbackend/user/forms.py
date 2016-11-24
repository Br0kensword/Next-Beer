# -*- coding: utf-8 -*-
"""User forms."""
from flask_wtf import Form
from wtforms import PasswordField, StringField, IntegerField
from wtforms.validators import DataRequired, Email, EqualTo, Length
from wtforms import DecimalField
from wtforms.validators import NumberRange, ValidationError

from .models import User


class RegisterForm(Form):
    """Register form."""

    username = StringField('Username',
                           validators=[DataRequired(), Length(min=3, max=25)])
    email = StringField('Email',
                        validators=[DataRequired(), Email(), Length(min=6, max=40)])
    password = PasswordField('Password',
                             validators=[DataRequired(), Length(min=6, max=40)])
    confirm = PasswordField('Verify password',
                            [DataRequired(), EqualTo('password', message='Passwords must match')])

    def __init__(self, *args, **kwargs):
        """Create instance."""
        super(RegisterForm, self).__init__(*args, **kwargs)
        self.user = None

    def validate(self):
        """Validate the form."""
        initial_validation = super(RegisterForm, self).validate()
        if not initial_validation:
            return False
        user = User.query.filter_by(username=self.username.data).first()
        if user:
            self.username.errors.append('Username already registered')
            return False
        user = User.query.filter_by(email=self.email.data).first()
        if user:
            self.email.errors.append('Email already registered')
            return False
        return True

class RateForm(Form):
    rating = IntegerField('Rating', validators=[DataRequired(), NumberRange(1, 5)])

class QuestionForm(Form):
    bitter = DecimalField('I enjoy the bitterness of certain foods and drinks like coffee, dark chocolate, grapefruit or kale',
                          validators=[DataRequired(), NumberRange(0, 10)])
    fruit = DecimalField('I enjoy fruit aroma and flavors like bananas, apples and pears ',
                         validators=[DataRequired(), NumberRange(0, 10)])
    hoppy = DecimalField('I enjoy citrus fruit aromas and flavors like lemon, orange and grapefruit',
                         validators=[DataRequired(), NumberRange(0, 10)])
    malty = DecimalField('I enjoy buttery and bready flavors like biscuits or toast',
                         validators=[DataRequired(), NumberRange(0, 10)])
    roasty = DecimalField('I enjoy the roasty flavors of  coffee, barely or  genmai tea',
                          validators=[DataRequired(), NumberRange(0, 10)])
    smoke = DecimalField('I enjoy smokey flavors like smoked mozzarella or BBQ',
                         validators=[DataRequired(), NumberRange(0, 10)])
    sour = DecimalField('I enjoy sour or acidic tastes such as lemonade or kombucha',
                        validators=[DataRequired(), NumberRange(0, 10)])
    spice = DecimalField('I enjoy spices like cinnamon, nutmeg, and cloves',
                         validators=[DataRequired(), NumberRange(0, 10)])
    sweet = DecimalField('I enjoy sweet flavors like honey, milk, chocolate or fruit juice',
                         validators=[DataRequired(), NumberRange(0, 10)])
    wood = DecimalField('I enjoy woody flavors like you would get aged whiskey or wine',
                        validators=[DataRequired(), NumberRange(0, 10)])
