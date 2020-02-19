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
	db.drop_collection("query2")

def insertsQ2():
	delete()
	insertsQuery2 = [
	{
	"_id":1,
	"suppKey"    : 1,
	"name"       : 'Distribuidor2',
	"address"    : 'direccion2',
	"phone"      : '936587456',
	"acctbal"    : 1.9,
	"comment"    : 'Comentario2',
	"nationName" : 'Portugal',
	"regionName" : 'Aragon',
	"parts"      : [{
	   "partKey"     : 2,
       "mfgr"        : 'mfgr2',
       "type"        : 'type',
       "size"        : 1,
       "supplyCost"  : 10
	}]},
	{
	"_id": 2,
	"suppKey"    : 2,
	"name"       : 'Distribuidor1',
	"address"    : 'direccion1',
	"phone"      : '934568987',
	"acctbal"    : 2.5,
	"comment"    : 'Comentario1',
	"nationName" : 'Francia',
	"regionName" : 'Catalunya',
	"parts"      : [{
	   "partKey"     : 1,
       "mfgr"        : 'mfgr1',
       "type"        : 'type',
       "size"        : 1,
       "supplyCost"  : 3
	}]},
	{
	"_id": 3,
	"suppKey"    : 3,
	"name"       : 'Distribuidor3',
	"address"    : 'direccion3',
	"phone"      : '934568987',
	"acctbal"    : 4.5,
	"comment"    : 'Comentario3',
	"nationName" : 'Italia',
	"regionName" : 'Catalunya',
	"parts"      : [{
	   "partKey"     : 1,
       "mfgr"        : 'mfgr3',
       "type"        : 'type',
       "size"        : 2,
       "supplyCost"  : 10
	}]
	},
	{
	"_id": 4,
	"suppKey"    : 4,
	"name"       : 'Distribuidor4',
	"address"    : 'direccion4',
	"phone"      : '934598987',
	"acctbal"    : 4.7,
	"comment"    : 'Comentario4',
	"nationName" : 'Italia',
	"regionName" : 'Catalunya',
	"parts"      : [{
	   "partKey"     : 1,
       "mfgr"        : 'mfgr4',
       "type"        : 'type',
       "size"        : 1,
       "supplyCost"  : 12
	}]
	},
	{
	"_id": 5,
	"suppKey"    : 5,
	"name"       : 'Distribuidor5',
	"address"    : 'direccion5',
	"phone"      : '934325987',
	"acctbal"    : 4.7,
	"comment"    : 'Comentario5',
	"nationName" : 'Italia',
	"regionName" : 'Catalunya',
	"parts"      : [{
	   "partKey"     : 1,
       "mfgr"        : 'mfgr5',
       "type"        : 'type',
       "size"        : 5,
       "supplyCost"  : 3
	}]
	},
	{
	"_id": 6,
	"suppKey"    : 6,
	"name"       : 'Distribuidor6',
	"address"    : 'direccion6',
	"phone"      : '934325967',
	"acctbal"    : 2.1,
	"comment"    : 'Comentario6',
	"nationName" : 'Italia',
	"regionName" : 'Catalunya',
	"parts"      : [{
	   "partKey"     : 1,
       "mfgr"        : 'mfgr6',
       "type"        : 'type2',
       "size"        : 5,
       "supplyCost"  : 3
	}]
	},
	{
	"_id": 7,
	"suppKey"    : 7,
	"name"       : 'Distribuidor7',
	"address"    : 'direccion7',
	"phone"      : '934325677',
	"acctbal"    : 1.5,
	"comment"    : 'Comentario7',
	"nationName" : 'Italia',
	"regionName" : 'Aragon',
	"parts"      : [{
	   "partKey"     : 1,
       "mfgr"        : 'mfgr7',
       "type"        : 'type',
       "size"        : 5,
       "supplyCost"  : 3
	}]
	},
	{
	"_id": 8,
	"suppKey"    : 8,
	"name"       : 'Distribuidor8',
	"address"    : 'direccion8',
	"phone"      : '934325987',
	"acctbal"    : 7.8,
	"comment"    : 'Comentario8',
	"nationName" : 'Italia',
	"regionName" : 'Catalunya',
	"parts"      : [{
	   "partKey"     : 1,
       "mfgr"        : 'mfgr8',
       "type"        : 'type',
       "size"        : 1,
       "supplyCost"  : 3
	}]
	}


	]
	db.query2.insert(insertsQuery2);



print()
insertsQ2()


region = 'Catalunya'

l = db.query2.aggregate([

	{"$unwind": "$parts"},

	{"$match": {
		"regionName": region
	}}
	,
	{"$group": { "_id": "null", "minsupcost": {"$min": "$parts.supplyCost"}}}
])

mincost = 0
for x in l:
	mincost = x['minsupcost']
size = 1
type = 'type'

l = db.query2.aggregate([

	{"$unwind": "$parts"},

	{"$match": {
		"parts.size": size,
		"regionName": region,
		"parts.supplyCost": mincost,
		"parts.type": type
	}},
	{"$project": {
		"_id": 0,
		"acctbal": 1 ,
		"name": 1,
		"nationName": 1,
		"address": 1,
		"phone": 1,
		"comment": 1,
		"parts.partKey": 1,
		"parts.mfgr": 1} },
	{"$sort": {
		"acctbal": -1, "nationName": 1, "name": 1, "parts.partKey": 1
	}}
])

print(list(l))
