import datetime as dt

from flask_login import UserMixin

from beerbackend.database import Column, Model, SurrogatePK, db, reference_col, relationship
from beerbackend.extensions import bcrypt


families = {
    1: "ipa",
    2: "brown-ale",
    3: "pale-ale",
    4: "pale-lager",
    5: "pilsner",
    6: "amber-ale",
    7: "amber-lager",
    8: "dark-lager",
    9: "porter",
    10: "stout",
    11: "bock",
    12: "strong-ale",
    13: "wheat",
    14: "specialty"
}

class Beer(SurrogatePK, Model):
    """It's a beer!"""

    __tablename__ = 'beers'
    beer_name = Column(db.String(80), unique=True, nullable=False)
    abv = Column(db.Numeric, nullable=False, default=1)
    bitter = Column(db.Numeric, nullable=False, default=1)
    color = Column(db.Numeric, nullable=False, default=1)
    fruit = Column(db.Numeric, nullable=False, default=1)
    hoppy = Column(db.Numeric, nullable=False, default=1)
    malty = Column(db.Numeric, nullable=False, default=1)
    roasty = Column(db.Numeric, nullable=False, default=1)
    smoke = Column(db.Numeric, nullable=False, default=1)
    sour = Column(db.Numeric, nullable=False, default=1)
    spice = Column(db.Numeric, nullable=False, default=1)
    sweet = Column(db.Numeric, nullable=False, default=1)
    wood = Column(db.Numeric, nullable=False, default=1)
    family = Column(db.Integer, nullable=False, default=1)
    created_at = Column(db.DateTime, nullable=False, default=dt.datetime.utcnow)
    ratings = db.relationship('Rating', backref='beer',
                              lazy='dynamic')

    def __init__(self, beer_name, abv, bitter, color, fruit,
                 hoppy, malty, roasty, smoke, sour,
                 spice, sweet, wood, family, **kwargs):
        db.Model.__init__(self, beer_name=beer_name, abv=abv, bitter=bitter, color=color,
                          fruit=fruit, hoppy=hoppy, malty=malty, roasty=roasty,
                          smoke=smoke, sour=sour, spice=spice, sweet=sweet,
                          wood=wood, family=family, **kwargs)

    def __repr__(self):
        """What makes a beer a beer, I say?"""
        return '<Beer({beer_name!r})>'.format(beer_name=self.beer_name)

    def get_rating(self):
        count = 0
        sum = 0
        for rating in self.ratings:
            sum += rating.rating
            count += 1
        if count != 0:
            return sum/count
        else:
            return 3
    def total_ratings(self):
        return len(['' for _ in self.ratings])


    def to_data(self):
        return {
            "name": self.beer_name,
            "abv": self.abv.normalize(),
            "bitter": self.bitter.normalize(),
            "color": self.color.normalize(),
            "fruit": self.fruit.normalize(),
            "hoppy": self.hoppy.normalize(),
            "malty": self.malty.normalize(),
            "roasty": self.roasty.normalize(),
            "sour": self.sour.normalize(),
            "smoke": self.smoke.normalize(),
            "spice": self.spice.normalize(),
            "sweet": self.sweet.normalize(),
            "wood": self.wood.normalize(),
            "id": self.id,
            "average": self.get_rating(),
            "total_ratings": self.total_ratings(),
            "family": families.get(self.family, None)
        }

    def get_family(self):
        return families.get(self.family, None)
