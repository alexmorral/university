//
//  LocationHelper.h
//  Whelp
//
//  Created by Alex Morral on 21/9/15.
//  Copyright © 2015 Alex Morral. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <Foundation/Foundation.h>
#import "INTULocationManager.h"

@interface LocationHelper : NSObject <UIAlertViewDelegate>

+(void)getLocation:(void (^)(CLLocation *currentLocation))succesBlock error:(void (^)(NSString* err))errorBlock;

@end
