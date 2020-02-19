#!/usr/bin/python
from __future__ import division
from collections import namedtuple

import time
import sys
import string
import pdb

class Airport:
    def __init__ (self, iden=None, name=None):
        self.code = iden
        self.name = name
        self.routes = []        # Lista de aeropuertos hacia los que viaja
        self.routeHash = dict() # Diccionario que contiene por key aeropuertoDestino y por valor el weight
        self.outweight = 0      # Suma de todos los weight a los que va este aeropuerto
        self.pointedBy = []     # Lista de los aeropuertos que viajan a este aeropuerto
        self.pagerank = 0

    def __repr__(self):
        return "{0}\t{2}\t{1}".format(self.code, self.name, self.pageIndex)

airportList = []        # list of Airports
airportHash = dict()    # hash key IATA code -> Airport

def readAirports(fd):
    print "Reading Airport file from {0}".format(fd)
    airportsTxt = open(fd, "r");
    cont = 0
    for line in airportsTxt.readlines():
        a = Airport()
        try:
            temp = line.split(',')
            if len(temp[4]) != 5 :
                raise Exception('not an IATA code')
            a.name=(temp[1] + ", " + temp[3]).replace("\"", "")
            a.code=temp[4].replace("\"", "")
        except Exception as inst:
            pass
        else:
            cont += 1
            airportList.append(a)
            airportHash[a.code] = a
    airportsTxt.close()
    print "There were {0} Airports with IATA code".format(cont)


def readRoutes(fd):
    print "Reading Routes file from {0}".format(fd)
    routesTxt = open(fd, 'r');
    cont = 0
    for line in routesTxt.readlines():
      temp = line.split(',')
      if temp[2] in airportHash :
	  a = airportHash[temp[2]] #origin airport
	  if temp[4] not in a.routes :
	      a.routeHash[temp[4]] = 1
	      a.routes.append(temp[4])
	  else:
           a.routeHash[temp[4]] += 1
	  cont += 1
    routesTxt.close()
    print "There were {0} Routes".format(cont)

def updateAirportsOutweight():
    for a in airportList:
        a.outweight = 0
        for dest in a.routes:
            a.outweight += a.routeHash[dest]
            if dest in airportHash:
                destAp = airportHash[dest]
                destAp.pointedBy.append(a.code)
	#a.outweight = outweight

def printAirportRouteInformation():
    for a in airportList :
        print "Airport {0} outweight:{1} routes: ".format(a.code, a.outweight)
        for r in a.routes :
            print "Route to {0} has weight {1}".format(r, a.routeHash[r])


def sumRouteHashWeights(ap):
    p = 0
    for point in ap.pointedBy:
        originAp = airportHash[point]
        if ap.code in originAp.routeHash:
            p = p + ((originAp.pagerank * originAp.routeHash[ap.code]) / originAp.outweight)
    return p

def computePageRanks():
    n = len(airportList)
    P = [1/n] * n;
    L = 0.9
    nIts = 15
    for ap in airportList:
        ap.pagerank = 1/n
    k = 0
    while k < nIts :
        Q = [0] * n;
        for i in range(0,n) :
            sumRoutes = sumRouteHashWeights(airportList[i])
            Q[i] = ((1-L)/n) + (L * sumRoutes)
            airportList[i].pagerank = Q[i]
        P = Q;
        k += 1
    return k

def outputPageRanks():
    orderedApList = sorted(airportList, key=lambda Airport: Airport.pagerank)
    for ap in reversed(orderedApList) :
        print "Airport {0} - Pagerank {1}".format(ap.code, ap.pagerank)


def main(argv=None):
    readAirports("airports.txt")
    readRoutes("routes.txt")
    updateAirportsOutweight()
    # printAirportRouteInformation()
    time1 = time.time()
    iterations = computePageRanks()
    time2 = time.time()
    outputPageRanks()
    print "#Iterations:", iterations
    print "Time of computePageRanks():", time2-time1


if __name__ == "__main__":
    sys.exit(main())
