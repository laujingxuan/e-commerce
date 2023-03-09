from flask import Flask
from flask_restful import Resource, Api
import constants

#https://flask-restful.readthedocs.io/en/latest/
app = Flask(__name__)
api = Api(app)


if __name__ == '__main__':
    app.run(port=constants.PORT)  # run our Flask app