# -*- coding: utf-8 -*-
"""User models."""
import datetime as dt
import random

from flask_login import UserMixin
from beerbackend.database import Column, Model, SurrogatePK, db, reference_col, relationship
from beerbackend.extensions import bcrypt
from itsdangerous import(TimedJSONWebSignatureSerializer as srl,
                         BadSignature, SignatureExpired)
from beerbackend.beer.models import Beer, families
import os
import json
from decimal import Decimal


class Role(SurrogatePK, Model):
    """A role for a user."""

    __tablename__ = 'roles'
    name = Column(db.String(80), unique=True, nullable=False)
    user_id = reference_col('users', nullable=True)
    user = relationship('User', backref='roles')

    def __init__(self, name, **kwargs):
        """Create instance."""
        db.Model.__init__(self, name=name, **kwargs)

    def __repr__(self):
        """Represent instance as a unique string."""
        return '<Role({name})>'.format(name=self.name)


class User(UserMixin, SurrogatePK, Model):
    """A user of the app."""

    __tablename__ = 'users'
    username = Column(db.String(80), unique=True, nullable=False)
    email = Column(db.String(80), unique=True, nullable=False)
    #: The hashed password
    password = Column(db.String(128), nullable=True)
    created_at = Column(db.DateTime, nullable=False, default=dt.datetime.utcnow)
    first_name = Column(db.String(30), nullable=True)
    last_name = Column(db.String(30), nullable=True)
    active = Column(db.Boolean(), default=False)
    is_admin = Column(db.Boolean(), default=False)
    taste_profile=Column(db.Text(), nullable=True)
    ratings = db.relationship('Rating', backref='user',
                             lazy='dynamic')

    def __init__(self, username, email, password=None, **kwargs):
        """Create instance."""
        db.Model.__init__(self, username=username, email=email, **kwargs)
        if password:
            self.set_password(password)
        else:
            self.password = None

    def set_password(self, password):
        """Set password."""

        self.password = bcrypt.generate_password_hash(password).decode('utf-8')

    def check_password(self, value):
        """Check password."""
        print(value)
        return bcrypt.check_password_hash(self.password, value)

    def generate_auth_token(self, expire_time=None):
        s = srl(os.environ.get('BEERBACKEND_SECRET',default=None), expires_in=expire_time)
        return s.dumps({'id': self.id})

    def update_taste_profile(self, malty=5, sour=5, wood=5, hoppy=5, spice=5,
                             fruit=5, sweet=5, roasty=5, bitter=5, smoke=5):
        taste={
            "malty": float(malty),
            "sour": float(sour),
            "wood": float(wood),
            "hoppy": float(hoppy),
            "bitter": float(bitter),
            "spice": float(spice),
            "fruit": float(fruit),
            "smoke": float(smoke),
            "sweet": float(sweet),
            "roasty": float(roasty),
        }
        print(taste)
        self.update(taste_profile=json.dumps(taste))

    @staticmethod
    def verify_auth_token(token):
        s = srl(os.environ.get('BEERBACKEND_SECRET',default=None))
        try:
            data=s.loads(token)
        except SignatureExpired:
            return None
        except BadSignature:
            return None
        user = User.query.get(data['id'])
        return user

    def get_profile(self):
        taste_profile=None
        if self.taste_profile:
            taste_profile=json.loads(self.taste_profile)

        PBR = {
            "sour": 0,
            "malty": 0,
            "hoppy": 0,
            "wood": 0,
            "bitter": 0,
            "roasty": 0,
            "spice": 0,
            "sweet": 0,
            "fruit": 0,
            "smoke": 0,
        }
        final_map=PBR
        beers = [rating for rating in self.ratings if rating.rating != 3]
        if len(beers) > 0:
            print(beers)
            vals_arr = list(PBR.keys())
            total_weight = 0
            for rating in beers:
                rating_offset = 3 - rating.rating
                total_weight += abs(rating_offset)
                for key in vals_arr:
                    taste_weight = rating_offset*(rating.beer.to_data()[key]-5)
                    print(key)
                    print(taste_weight)
                    PBR[key] += taste_weight

            if taste_profile:
                print(PBR)
                print(taste_profile)
                final_map = {key: Decimal(Decimal((5-(val/total_weight))+Decimal(taste_profile[key]))/2) for key,
                                                                                                 val in PBR.items()}
            else:
                print(PBR)
                final_map = {key: Decimal(5-(val/total_weight)) for key, val in PBR.items()}
        else:
            if taste_profile:
                final_map = taste_profile

        return final_map

    def get_taste_profile(self):
        if self.taste_profile:
            return json.loads(self.taste_profile)
        else:
            return {
            "sour": 5,
            "malty": 5,
            "hoppy": 5,
            "wood": 5,
            "bitter": 5,
            "roasty": 5,
            "spice": 5,
            "sweet": 5,
            "fruit": 5,
            "smoke": 5,
        }

    def reccommend(self):
        #userbeers = set() get a list of all rated beers and then filter them out
        tastes = self.get_taste_profile()
        tastes = [(value, key) for key, value in tastes.items()]
        tastes.sort()
        tastes = tastes[len(tastes)-3: len(tastes)]
        
        ratedbeers = [rating.beer.beer_name for rating in self.ratings]
        beer = Beer.query.all() #gives me all the beers
        beers = [ x for x in beer if x.beer_name not in ratedbeers]
        
        counter = 0
        selected_beers = []
        while counter < 3 :
            if counter == 0 :
                distance = 0.5
            elif counter == 1 :
                distance = 2
            else:
                distance = 3

            for beer in beers:
                print(tastes)
                if abs(float(getattr(beer, tastes[0][1])) - tastes[0][0]) <= distance \
                        and abs(float(getattr(beer, tastes[1][1])) - tastes[1][0]) <= distance \
                        and abs(float(getattr(beer, tastes[2][1])) - tastes[2][0]) <= distance:
                    selected_beers.append(beer)
    
            if len(selected_beers) >= 5:
                counter = 3
            else:    
                counter += 1

        print(selected_beers)
        try:
            recommendation = random.choice(selected_beers)
        except IndexError:
            #means we couldn't find a nicely matching beer
            recommendation = random.choice(beers)

        return recommendation



    @property
    def full_name(self):
        """Full user name."""
        return '{0} {1}'.format(self.first_name, self.last_name)

    def __repr__(self):
        """Represent instance as a unique string."""
        return '<User({username!r})>'.format(username=self.username)


class Rating(SurrogatePK, Model):
    """It's a beer!"""

    rating = Column(db.Integer, nullable=False, default=3)
    user_id = Column(db.Integer, db.ForeignKey('users.id'))
    beer_id = Column(db.Integer, db.ForeignKey('beers.id'))
    created_at = Column(db.DateTime, nullable=False, default=dt.datetime.utcnow)
    __table_args__ = (db.UniqueConstraint("user_id", "beer_id"),)

    def __init__(self, rating, user_id, beer_id ):
        db.Model.__init__(self, rating=rating, user_id=user_id, beer_id=beer_id)

    def __repr__(self):
        """What makes a beer a beer, I say?"""
        return '<Rating({rating!r}, {beer!r} {user!r})>'.format(rating=self.rating,
                                                                beer=self.beer_id,
                                                                user=self.user_id)
    def update_time(self, **kwargs):
        return self.update(created_at=dt.datetime.utcnow(), **kwargs)
