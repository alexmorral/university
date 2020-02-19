//
//  MapAnotationHelper.h
//  Whelp
//
//  Created by Alex Morral on 21/9/15.
//  Copyright © 2015 Alex Morral. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <MapKit/MapKit.h>

@interface MapAnotationHelper : NSObject <MKAnnotation>


@property (nonatomic, copy) NSString *name;
@property (nonatomic, copy) NSString *address;
@property (nonatomic, copy) NSDictionary *info;
@property (nonatomic, assign) CLLocationCoordinate2D theCoordinate;



- (id)initWithName:(NSString*)name address:(NSString*)address info:(NSDictionary *)info coordinate:(CLLocationCoordinate2D)coordinate;

- (MKMapItem*)mapItem;

@end