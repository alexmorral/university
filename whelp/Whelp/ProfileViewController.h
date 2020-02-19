//
//  ProfileViewController.h
//  Whelp
//
//  Created by Alex Morral on 8/10/15.
//  Copyright © 2015 Alex Morral. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <FBSDKShareKit/FBSDKShareKit.h>
#import <FBSDKLoginKit/FBSDKLoginKit.h>

@interface ProfileViewController : UIViewController <FBSDKLoginButtonDelegate> {
    
    
    IBOutlet UIView *shareContainer;
    IBOutlet UIView *loginBtnContainer;
    
}

@property (strong, nonatomic) IBOutlet UIButton *completadosButton;
@property (strong, nonatomic) IBOutlet UIButton *comentariosButton;
@property (strong, nonatomic) IBOutlet UILabel *textoPuntuacionLabel;
@property (strong, nonatomic) IBOutlet UILabel *puntuacionLabel;

@end
