//
//  LoginViewController.h
//  Whelp
//
//  Created by Alex Morral on 13/10/15.
//  Copyright © 2015 Alex Morral. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <FBSDKCoreKit/FBSDKCoreKit.h>
#import <FBSDKLoginKit/FBSDKLoginKit.h>

@interface LoginViewController : UIViewController <FBSDKLoginButtonDelegate> {
    
    IBOutlet UIView *loginBtnContainer;
}

@end
