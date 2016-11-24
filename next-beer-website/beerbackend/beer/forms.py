from flask_wtf import Form
from wtforms import PasswordField, StringField
from wtforms.validators import DataRequired, Email, EqualTo, Length
from wtforms import DecimalField, IntegerField, SelectField
from wtforms.validators import NumberRange, ValidationError
from .models import families

choices = list(families.items())
print(choices)

class BeerForm(Form):
    beer_name = StringField('Beer Name', validators=[DataRequired()])
    abv = DecimalField('Alcohol %', validators=[DataRequired()])
    bitter = DecimalField('Bitter', validators=[DataRequired(), NumberRange(0, 10)])
    color = DecimalField('Color', validators=[DataRequired(), NumberRange(0, 40)])
    fruit = DecimalField('Fruitiness', validators=[DataRequired(), NumberRange(0, 10)])
    hoppy = DecimalField('Hoppiness', validators=[DataRequired(), NumberRange(0, 10)])
    malty = DecimalField('Malty', validators=[DataRequired(), NumberRange(0, 10)])
    roasty = DecimalField('Roasty', validators=[DataRequired(), NumberRange(0, 10)])
    smoke = DecimalField('Smoke', validators=[DataRequired(), NumberRange(0, 10)])
    sour = DecimalField('Sour', validators=[DataRequired(), NumberRange(0, 10)])
    spice = DecimalField('Spice', validators=[DataRequired(), NumberRange(0, 10)])
    sweet = DecimalField('Sweet', validators=[DataRequired(), NumberRange(0, 10)])
    wood = DecimalField('Wood', validators=[DataRequired(), NumberRange(0, 10)])
    family = SelectField('Family', coerce=lambda x: int(x), choices=choices, validators=[DataRequired()])

    def check_abv(form, field):
        if field.data <= 0:
            raise ValidationError('Field Must be Positive')
        if field.data >= 100:
            raise ValidationError('Field must be Less than 100')

