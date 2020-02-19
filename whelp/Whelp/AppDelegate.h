//
//  AppDelegate.h
//  Whelp
//
//  Created by Alex Morral on 21/9/15.
//  Copyright © 2015 Alex Morral. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Usuari.h"

@interface AppDelegate : UIResponder <UIApplicationDelegate>

@property (strong, nonatomic) UIWindow *window;

@property (nonatomic, strong) Usuari *currentUser;

@property(nonatomic, strong) NSDictionary *allUsers;




@end

