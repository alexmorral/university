//
//  MapAnotationHelper.m
//  Whelp
//
//  Created by Alex Morral on 21/9/15.
//  Copyright © 2015 Alex Morral. All rights reserved.
//

#import "MapAnotationHelper.h"

@interface MapAnotationHelper ()

@end

@implementation MapAnotationHelper

- (id)initWithName:(NSString*)name address:(NSString*)address info:(NSDictionary *)info coordinate:(CLLocationCoordinate2D)coordinate {
    if ((self = [super init])) {
        if ([name isKindOfClass:[NSString class]]) {
            self.name = name;
        } else {
            self.name = @"Unknown charge";
        }
        self.address = address;
        self.theCoordinate = coordinate;
        self.info = info;
    }
    return self;
}

- (NSString *)title {
    return _name;
}

- (NSString *)subtitle {
    return _address;
}

-(NSDictionary *)info {
    return _info;
}

- (CLLocationCoordinate2D)coordinate {
    return _theCoordinate;
}

- (MKMapItem*)mapItem {
    NSDictionary *addressDict = @{@"address" : _address};
    
    MKPlacemark *placemark = [[MKPlacemark alloc]
                              initWithCoordinate:self.coordinate
                              addressDictionary:addressDict];
    
    MKMapItem *mapItem = [[MKMapItem alloc] initWithPlacemark:placemark];
    mapItem.name = self.title;
    
    return mapItem;
}

@end
