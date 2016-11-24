from beerbackend.user.models import User, Rating
from beerbackend.beer.models import Beer
from flask_restful import Resource, reqparse
from datetime import datetime

def grab_matching_ratings(user, fn):
    if user:
        trimmed_ratings = [x for x in user.ratings if fn(x.rating)]
        sorted_beers = sorted(trimmed_ratings, key=lambda rating: rating.created_at, reverse=True)

        beers = [{"beer": rating.beer.to_data(),"rating": rating.rating} for rating in sorted_beers]
        return beers
    else:
        return None, 401

def make_beers_arr(ratings):
    ratings_arr=[]
    for rating in ratings:
        data = rating.beer.to_data()
        beerdict = {"beer": data,
                    "rating": rating.rating}
        ratings_arr.append(beerdict)

    return {"beers": ratings_arr,
            "total": len(ratings_arr)}

auth_parse = reqparse.RequestParser()
auth_parse.add_argument('username', dest='username',
                        type=str, required=True,
                        help='username of user to authenticate and gather a token for')

auth_parse.add_argument('password', dest='password',
                        type=str, required=True,
                        help='password of user to authenticate and gather a token for')
class AuthApi(Resource):
    def get(self):
        args = auth_parse.parse_args()
        username = args.username
        password = args.password
        user = User.query.filter(User.username == username).first()
        if user and user.check_password(password):
            return {'access_token': user.generate_auth_token()}

        else:
            return None, 401

recommend_get_parse = reqparse.RequestParser()
recommend_get_parse.add_argument('access_token', dest='access_token',
                                 type=str, required=True,
                                 help='The access_token of the user you want a recommend')


class UserApi(Resource):
    def get(self):
        args = recommend_get_parse.parse_args()
        user = User.verify_auth_token(args.access_token)
        if user:
            return {"username": user.username,
                    "taste_profile": user.get_profile(),
                    "initial_profile": user.get_taste_profile(),
                    "ratings": make_beers_arr(user.ratings)}
    def post(self):
        create_user_parse = reqparse.RequestParser()
        create_user_parse.add_argument('username', dest='username',
                                       required=True,
                                       help="Username of user to be created")
        create_user_parse.add_argument('password', dest='password',
                                       required=True,
                                       help="Password of user to be created")
        create_user_parse.add_argument('password_confirm', dest='password_confirm',
                                       required=True,
                                       help="Password confirmation of user to be created")
        create_user_parse.add_argument('email', dest='email',
                                       required=True,
                                       help="Email address of user to be created")
        args = create_user_parse.parse_args()

        user = User.query.filter(User.username == args.username).first()
        emailuser = User.query.filter(User.email == args.email).first()
        if user:
            return {"message": "username already exists"}, 400
        elif emailuser:
            return {"message": "email already registered"}, 400
        elif args.password != args.password_confirm:
            return {"message": "passwords do not match"}, 400
        elif len(args.password) < 6 or len(args.password) > 128:
            return {"message": "invalid password length"}, 400
        elif not args.username or len(args.username) > 80:
            return {"message": "invalid username length"}, 400
        else:
            valid_user = User.create(username=args.username, password=args.password,
                                     email=args.email, active=True)
            return {'access_token': valid_user.generate_auth_token()}, 201

PBR = {
    "sour": 1,
    "malty": 1,
    "family": "pale-lager",
    "hoppy": 1,
    "name": "PBR",
    "abv": 1,
    "wood": 1,
    "bitter": 1,
    "color": 1,
    "roasty": 1,
    "spice": 1,
    "smoke": 1,
    "sweet": 1,
    "fruit": 1,
    "id": 1
}

class UserBeers(Resource):
    def get(self):
        args = recommend_get_parse.parse_args()
        user = User.verify_auth_token(args.access_token)
        if user:
            return make_beers_arr(user.ratings), 200
        else:
            return None, 401

class LikedBeers(Resource):
    def get(self):
        args = recommend_get_parse.parse_args()
        user = User.verify_auth_token(args.access_token)
        x = grab_matching_ratings(user, lambda x: x >= 3)
        return {'beers': x, 'total': len(x)}

class DislikedBeers(Resource):
    def get(self):
        args = recommend_get_parse.parse_args()
        user = User.verify_auth_token(args.access_token)
        x = grab_matching_ratings(user, lambda x: x < 3)
        return {'beers': x, 'total': len(x)}


class Recommend(Resource):
    def get(self):
        args = recommend_get_parse.parse_args()
        user = User.verify_auth_token(args.access_token)
        if user:
            #lol for now
            beer = user.reccommend()
            return beer.to_data()
        else:
            return None, 401


profile_set_parser = reqparse.RequestParser()
profile_set_parser.add_argument('access_token', dest='access_token',
                              type=str, required=True,
                              help='Access token of user profile')
profile_set_parser.add_argument('malty', dest='malty',
                                type=float, required=True,
                                help='maltiness preference')
profile_set_parser.add_argument('sour', dest='sour',
                                type=float, required=True,
                                help='sourness preference')
profile_set_parser.add_argument('hoppy', dest='hoppy',
                                type=float, required=True,
                                help='hoppiness preference')
profile_set_parser.add_argument('wood', dest='wood',
                                type=float, required=True,
                                help='woodiness preference')
profile_set_parser.add_argument('bitter', dest='bitter',
                                type=float, required=True,
                                help='bitterness preference')
profile_set_parser.add_argument('roasty', dest='roasty',
                                type=float, required=True,
                                help='roastiness preference')
profile_set_parser.add_argument('spice', dest='spice',
                                type=float, required=True,
                                help='spice preference')
profile_set_parser.add_argument('fruit', dest='fruit',
                                type=float, required=True,
                                help='fruit preference')
profile_set_parser.add_argument('sweet', dest='sweet',
                                type=float, required=True,
                                help='sweet preference')
profile_set_parser.add_argument('smoke', dest='smoke',
                                type=float, required=True,
                                help='smoke preference')


class TasteProfile(Resource):
    def post(self):
        args = profile_set_parser.parse_args()
        user = User.verify_auth_token(args.access_token)
        if user:
            print(args)
            print(user.taste_profile)
            user.update_taste_profile(malty=args.malty, sour=args.sour, wood=args.wood,
                                      spice=args.spice, fruit=args.fruit, sweet=args.sweet,
                                      roasty=args.roasty, bitter=args.bitter, smoke=args.smoke,
                                      hoppy=args.hoppy)
            print(user.taste_profile)
            return user.get_taste_profile(), 201
        else:
            return None, 400
    def get(self):
        profile_get_parser = reqparse.RequestParser()
        profile_get_parser.add_argument('access_token', dest='access_token',
                                type=str, required=True,
                                help='Access token of user profile')
        args = profile_get_parser.parse_args()
        user = User.verify_auth_token(args.access_token)
        if user:
            return user.get_taste_profile(), 201
        else:
            return None, 400



rate_get_parser = reqparse.RequestParser()
rate_get_parser.add_argument('id', dest='id',
                             type=str, required=True,
                             help='The ID of the rating')

rate_post_parser = reqparse.RequestParser()
rate_post_parser.add_argument('beer_id', dest='beer_id',
                              type=str, required=True,
                              help='The ID of the beer you are rating')

rate_post_parser.add_argument('access_token', dest='access_token',
                              type=str, required=True,
                              help='Access token of user rating the beer')
rate_post_parser.add_argument('rating', dest='rating',
                              type=int, required=True,
                              help='Rating the user gave the beer')
class RateApi(Resource):
    def get(self):
        args = rate_get_parser.parse_args()
        id = args.id
        rating = Rating.query.filter(Rating.id == id).first()
        if rating:
            user = User.query.filter(User.id == rating.user_id).first()
            beer = Beer.query.filter(Beer.id == rating.beer_id).first()
            return {
                "rating": rating.rating,
                "user": user.username,
                "beer": beer.to_data()
            }
        else:
            return None, 404
    def post(self):
        args = rate_post_parser.parse_args()
        user = User.verify_auth_token(args.access_token)
        rating_val = args.rating
        beer_id = args.beer_id
        if user:
            rating = Rating.query.filter(Rating.user_id == user.id, Rating.beer_id == beer_id).first()
            if rating:
                rating = rating.update_time(beer_id=beer_id, user_id=user.id, rating=rating_val)
            else:
                rating = Rating.create(beer_id=beer_id, user_id=user.id, rating=rating_val)
            return rating.id, 201
        else:
            return None, 401



recommend_get_parse = reqparse.RequestParser()
recommend_get_parse.add_argument('access_token', dest='access_token',
                                 type=str, required=True,
                                 help='The access_token of the user you want a recommend')
