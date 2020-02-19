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
	db.drop_collection("query3")

def insertsQ3():
	delete()
	insertsQuery3 = [
		{
		"_id" : 1,
		"orderKey": 1,
		"orderDate": datetime(2015,4,1,1,1,1),
		"mktSegment": 4,
		"shippriority": 1,
		"lineItems": [{
		  "discount": 0.5,
		  "extendedPrice": 20,
		  "shipDate": datetime(2015,4,19,1,1,1),
		  "returnFlag": 'a',
		  "quantity": 1,
		  "lineStatus": 2,
		  "tax": 0.1
		},{
		  "discount": 0.3,
		  "extendedPrice": 20,
		  "shipDate"      : datetime(2015,4,23,1,1,1),
		  "returnFlag"    : 'a',
		  "quantity"      : 1,
		  "lineStatus"    : 2,
		  "tax"           : 2
		}
		],
	"regionName" : 'Catalunya',
	"nationName" : 'Francia'
	},
	{
		"_id" : 2,
		"orderKey"   : 2,
		"orderDate"  : datetime(2015,4,3,1,1,1),
		"mktSegment" : 4,
		"shippriority": 1,
		"lineItems"  : [{
		  "discount"      : 0.6,
		  "extendedPrice" : 2,
		  "shipDate"      : datetime(2015,4,7,1,1,1),
		  "returnFlag"    : 'a',
		  "quantity"      : 2,
		  "lineStatus"    : 2,
		  "tax"           : 0.9
		},{
		  "discount"      : 0.1,
		  "extendedPrice" : 9,
		  "shipDate"      : datetime(2015,4,16,1,1,1),
		  "returnFlag"    : 'a',
		  "quantity"      : 1,
		  "lineStatus"    : 2,
		  "tax"           : 0.3
		}
		],
	"regionName" : 'Aragon',
	"nationName" : 'Portugal'
	},
	{
		"_id" : 3,
		"orderKey"   : 3,
		"orderDate"  : datetime(2015,4,7,1,1,1),
		"mktSegment" : 4,
		"shippriority": 1,
		"lineItems"  : [{
		  "discount"      : 0.4,
		  "extendedPrice" : 35,
		  "shipDate"      : datetime(2015,4,7,1,1,1),
		  "returnFlag"    : 'a',
		  "quantity"      : 2,
		  "lineStatus"    : 2,
		  "tax"           : 0.9
		},{
		  "discount"      : 0.3,
		  "extendedPrice" : 2,
		  "shipDate"      : datetime(2015,4,16,1,1,1),
		  "returnFlag"    : 'a',
		  "quantity"      : 1,
		  "lineStatus"    : 2,
		  "tax"           : 0.3
		}
		],
	"regionName" : 'Aragon',
	"nationName" : 'Portugal'
	},
	{
		"_id" : 4,
		"orderKey"   : 4,
		"orderDate"  : datetime(2015,4,3,1,1,1),
		"mktSegment" : 4,
		"shippriority": 1,
		"lineItems"  : [{
		  "discount"      : 0.4,
		  "extendedPrice" : 35,
		  "shipDate"      : datetime(2015,4,7,1,1,1),
		  "returnFlag"    : 'a',
		  "quantity"      : 2,
		  "lineStatus"    : 2,
		  "tax"           : 0.9
		},{
		  "discount"      : 0.3,
		  "extendedPrice" : 2,
		  "shipDate"      : datetime(2015,4,4,1,1,1),
		  "returnFlag"    : 'a',
		  "quantity"      : 1,
		  "lineStatus"    : 2,
		  "tax"           : 0.3
		}
		],
	"regionName" : 'Aragon',
	"nationName" : 'Portugal'
	},
	{
		"_id" : 5,
		"orderKey"   : 5,
		"orderDate"  : datetime(2015,4,2,1,1,1),
		"mktSegment" : 6,
		"shippriority": 1,
		"lineItems"  : [{
		  "discount"      : 0.4,
		  "extendedPrice" : 35,
		  "shipDate"      : datetime(2015,4,17,1,1,1),
		  "returnFlag"    : 'a',
		  "quantity"      : 2,
		  "lineStatus"    : 2,
		  "tax"           : 0.9
		},{
		  "discount"      : 0.3,
		  "extendedPrice" : 2,
		  "shipDate"      : datetime(2015,4,26,1,1,1),
		  "returnFlag"    : 'a',
		  "quantity"      : 1,
		  "lineStatus"    : 2,
		  "tax"           : 0.3
		}
		],
	"regionName" : 'Aragon',
	"nationName" : 'Portugal'
	},
	]
	db.query3.insert(insertsQuery3);



print()
insertsQ3()


segment = 4
date1 = datetime(2015,4,5,1,1,1)
date2 = datetime(2015,4,15,1,1,1)
l = db.query3.aggregate([
	#{"$unwind": "$orders"},
	{"$unwind": "$lineItems"},
	#{"$unwind": "$orders.lineitems.quantity"},
	{"$match": {
		"mktSegment": segment,
		"orderDate": {"$lt": date1},
		"lineItems.shipDate": {"$gt": date2}
	}}
	,
	{"$group": {
		"_id": {"orderKey": "$orderKey","orderDate": "$orderDate", "shippriority": "$shippriority"},
		"revenue": {"$sum": {"$multiply": [{"$subtract": [1,"$lineItems.discount"]}, "$lineItems.extendedPrice"]}}
	}},
	{"$sort": {
		"revenue": -1, "orderdate": 1
	}}

])

print(list(l))
