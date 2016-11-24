# -*- coding: utf-8 -*-
"""Model unit tests."""
import datetime as dt

import pytest

from beerbackend.user.models import Role, User, Rating
from beerbackend.beer.models import Beer

from .factories import UserFactory


@pytest.mark.usefixtures('db')
class TestUser:
    """User tests."""

    def test_get_by_id(self):
        """Get user by ID."""
        user = User('foo', 'foo@bar.com')
        user.save()

        retrieved = User.get_by_id(user.id)
        assert retrieved == user

    def test_created_at_defaults_to_datetime(self):
        """Test creation date."""
        user = User(username='foo', email='foo@bar.com')
        user.save()
        assert bool(user.created_at)
        assert isinstance(user.created_at, dt.datetime)

    def test_password_is_nullable(self):
        """Test null password."""
        user = User(username='foo', email='foo@bar.com')
        user.save()
        assert user.password is None

    def test_factory(self, db):
        """Test user factory."""
        user = UserFactory(password='myprecious')
        db.session.commit()
        assert bool(user.username)
        assert bool(user.email)
        assert bool(user.created_at)
        assert user.is_admin is False
        assert user.active is True
        assert user.check_password('myprecious')

    def test_check_password(self):
        """Check password."""
        user = User.create(username='foo', email='foo@bar.com',
                           password='foobarbaz123')
        assert user.check_password('foobarbaz123') is True
        assert user.check_password('barfoobaz') is False

    def test_full_name(self):
        """User full name."""
        user = UserFactory(first_name='Foo', last_name='Bar')
        assert user.full_name == 'Foo Bar'

    def test_roles(self):
        """Add a role to a user."""
        role = Role(name='admin')
        role.save()
        user = UserFactory()
        user.roles.append(role)
        user.save()
        assert role in user.roles

    def test_user_token_auth(self):
        user = User.create(username='test', email='foo@bar.com',
                           password='bingbangboom')
        auth_token = user.generate_auth_token()
        invalid_token = ""
        assert User.verify_auth_token(invalid_token) is None
        assert User.verify_auth_token(auth_token) is user

    def test_recommend_returns(self):
        user = User.create(username='test', email='lol@lol.com',
                password='wedon\'tcare')
        beer = Beer.create(beer_name='testbeer', abv=2.3,bitter=2.3, color=4, fruit=5, hoppy=4, malty=3, roasty=2,
                           smoke=1, sour=3, spice=4.5, family=1, sweet=2, wood=3)
        beer_rec = user.reccommend()
        assert beer_rec == beer

    def test_recommend_returns_closest(self):
        user = User.create(username='test', email='lol@lol.com',
                           password='wedon\'tcare')
        beer = Beer.create(beer_name='testbeer', abv=2.3,bitter=2.3, color=4, fruit=3, hoppy=4, malty=3, roasty=2,
                           smoke=1, sour=3, spice=4.5, family=1, sweet=2, wood=3)

        beer1 = Beer.create(beer_name='testbeer1', abv=5.3, bitter=6, color=3, fruit=7, hoppy=8, malty=3, roasty=2,
                           smoke=1, sour=3, spice=4.5, family=1, sweet=2, wood=3)

        user.update_taste_profile(malty=0, bitter=7, fruit=6.5, hoppy=7, wood=2, sweet=3,
                                  spice=1.4, sour=3.4, smoke=4, roasty=3)
        assert user.reccommend() is beer1
        assert user.reccommend() is not beer

    def test_rating_beer(self):
        user = User.create(username='test', email='lol@lol.com',
                           password='wedon\'tcare')
        beer = Beer.create(beer_name='testbeer', abv=2.3,bitter=2.3, color=4, fruit=3, hoppy=4, malty=3, roasty=2,
                           smoke=1, sour=3, spice=4.5, family=1, sweet=2, wood=3)
        Rating.create(user_id = user.id, beer_id = beer.id, rating = 4)
        assert [x for x in user.ratings] is not []
        assert [x for x in user.ratings][0].rating is 4






