"""
To start MongoDB:
mongod
To Insert Data from a file:
mongoimport --db databaseName --collection collectionName --drop --file file.json
Example:
mongoimport --db test --collection restaurants --drop --file primer-dataset.json

To Delete DB:
mongo databaseName --eval "db.dropDatabase()"
"""
#-*- encoding: utf-8 -*-
from pymongo import MongoClient
from pymongo import ASCENDING, DESCENDING
from random import randint
from datetime import date
from bson.son import SON
from datetime import datetime, timedelta
from pprint import pprint
import sys
import random

client = MongoClient('mongodb://localhost:27017/')
db = client['test']

def delete():
	db.drop_collection("query4")

def insertsQ4():
	delete()
	insertsQuery4 = [
		{
		"_id" : 1,
		"orderKey": 1,
		"orderDate": datetime(2015,1,23,1,1,1),
		"mktSegment": 4,
		"shippriority": 1,
		"lineItems": [{
		  "discount": 0.1,
		  "extendedPrice": 20,
		  "shipDate": datetime(2015,1,4,1,1,1),
		  "returnFlag": 'a',
		  "quantity": 1,
		  "lineStatus": 2,
		  "tax": 0.9
		},{
		  "discount": 0.2,
		  "extendedPrice": 2,
		  "shipDate"      : datetime(2015,1,23,1,1,1),
		  "returnFlag"    : 'a',
		  "quantity"      : 2,
		  "lineStatus"    : 2,
		  "tax"           : 0.5
		}
		],
	"regionName" : 'Catalunya',
	"nationName" : 'Espanya'
	},
	{
		"_id" : 2,
		"orderKey"   : 2,
		"orderDate"  : datetime(2015,4,2,1,1,1),
		"mktSegment" : 4,
		"shippriority": 1,
		"lineItems"  : [{
		  "discount"      : 0.2,
		  "extendedPrice" : 5,
		  "shipDate"      : datetime(2015,3,10,1,1,1),
		  "returnFlag"    : 'a',
		  "quantity"      : 1,
		  "lineStatus"    : 2,
		  "tax"           : 0.2
		},{
		  "discount"      : 0.8,
		  "extendedPrice" : 6,
		  "shipDate"      : datetime(2015,3,13,1,1,1),
		  "returnFlag"    : 'a',
		  "quantity"      : 1,
		  "lineStatus"    : 2,
		  "tax"           : 0.3
		}
		],
	"regionName" : 'Catalunya',
	"nationName" : 'Francia'
	},
	{
		"_id" : 3,
		"orderKey"   : 3,
		"orderDate"  : datetime(2015,9,15,1,1,1),
		"mktSegment" : 4,
		"shippriority": 1,
		"lineItems"  : [{
		  "discount"      : 0.2,
		  "extendedPrice" : 5,
		  "shipDate"      : datetime(2015,1,23,1,1,1),
		  "returnFlag"    : 'a',
		  "quantity"      : 1,
		  "lineStatus"    : 2,
		  "tax"           : 0.2
		},{
		  "discount"      : 0.8,
		  "extendedPrice" : 6,
		  "shipDate"      : datetime(2015,4,3,1,1,1),
		  "returnFlag"    : 'a',
		  "quantity"      : 1,
		  "lineStatus"    : 2,
		  "tax"           : 0.3
		}
		],
	"regionName" : 'Aragon',
	"nationName" : 'Belgica'
	},
	{
		"_id" : 4,
		"orderKey"   : 4,
		"orderDate"  : datetime(2015,1,12,1,1,1),
		"mktSegment" : 4,
		"shippriority": 1,
		"lineItems"  : [{
		  "discount"      : 0.2,
		  "extendedPrice" : 5,
		  "shipDate"      : datetime(2015,1,7,1,1,1),
		  "returnFlag"    : 'a',
		  "quantity"      : 1,
		  "lineStatus"    : 2,
		  "tax"           : 0.2
		},{
		  "discount"      : 0.8,
		  "extendedPrice" : 6,
		  "shipDate"      : datetime(2015,1,7,1,1,1),
		  "returnFlag"    : 'a',
		  "quantity"      : 1,
		  "lineStatus"    : 2,
		  "tax"           : 0.3
		}
		],
	"regionName" : 'Catalunya',
	"nationName" : 'Italia'
	},
	{
		"_id" : 5,
		"orderKey"   : 5,
		"orderDate"  : datetime(2017,1,23,1,1,1),
		"mktSegment" : 4,
		"shippriority": 1,
		"lineItems"  : [{
		  "discount"      : 0.2,
		  "extendedPrice" : 5,
		  "shipDate"      : datetime(2016,12,23,1,1,1),
		  "returnFlag"    : 'a',
		  "quantity"      : 1,
		  "lineStatus"    : 2,
		  "tax"           : 0.2
		},{
		  "discount"      : 0.8,
		  "extendedPrice" : 6,
		  "shipDate"      : datetime(2016,12,23,1,1,1),
		  "returnFlag"    : 'a',
		  "quantity"      : 1,
		  "lineStatus"    : 2,
		  "tax"           : 0.3
		}
		],
	"regionName" : 'Catalunya',
	"nationName" : 'Portugal'
	}
	]
	db.query4.insert(insertsQuery4);



print()
insertsQ4()


region = 'Catalunya'
date1 = datetime(2015,1,19,1,1,1)
date2 = datetime(2016,1,19,1,1,1)
l = db.query4.aggregate([

	{"$unwind": "$lineItems"},

	{"$match": {
    "orderDate" : {
      "$gte" : date1,
      "$lt" : date2
    },
		"regionName": region

	}}
  ,
	{"$group": {
		"_id": {"nationName": "$nationName"},
		"revenue": {"$sum": {"$multiply": [{"$subtract": [1,"$lineItems.discount"]}, "$lineItems.extendedPrice"]}}
	}},
	{"$sort": {
		"revenue": -1
	}}

])

print(list(l))
