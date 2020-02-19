//
//  MapViewController.h
//  Whelp
//
//  Created by Alex Morral on 21/9/15.
//  Copyright © 2015 Alex Morral. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <MapKit/MapKit.h>
#import "MapAnotationHelper.h"
#import "MapInfoViewController.h"
#import "Utils.h"
#import "TSMessageView.h"

@interface MapViewController : UIViewController <MKMapViewDelegate, TSMessageViewProtocol>
{
    
}

@property(nonatomic, strong) IBOutlet MKMapView *mapView;
@property(nonatomic, strong) MapInfoViewController *mapInfoVc;

@end
