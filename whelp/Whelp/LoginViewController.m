//
//  LoginViewController.m
//  Whelp
//
//  Created by Alex Morral on 13/10/15.
//  Copyright © 2015 Alex Morral. All rights reserved.
//

#import "LoginViewController.h"


@interface LoginViewController ()

@end

@implementation LoginViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
}

-(void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    
    [self.view layoutIfNeeded];
    
    if ([FBSDKAccessToken currentAccessToken]) {
        
        [self getFacebookInformation];
    }
    FBSDKLoginButton *myLoginButton = [[FBSDKLoginButton alloc] initWithFrame:CGRectMake(0, 0, loginBtnContainer.frame.size.width, loginBtnContainer.frame.size.height)];
    
    myLoginButton.readPermissions = @[@"email",@"public_profile",@"user_hometown",@"user_birthday",@"user_about_me",@"user_friends",@"user_photos"];
    myLoginButton.delegate = self;
    // Add the button to the view
    [loginBtnContainer addSubview:myLoginButton];
    
}


-(void) loginButton:(FBSDKLoginButton *)loginButton didCompleteWithResult:(FBSDKLoginManagerLoginResult *)result error:(NSError *)error {
    
    if (error) {
        NSLog(@"Process error");
    } else if (result.isCancelled) {
        NSLog(@"Cancelled");
    } else {
        NSLog(@"Logged in");
        if ([FBSDKAccessToken currentAccessToken]) {
            [self getFacebookInformation];
        }
    }

}


-(void) getFacebookInformation {
    NSString *info = @"id, name, email, picture";
    [[[FBSDKGraphRequest alloc] initWithGraphPath:@"me?fields=id,name,email,picture" parameters:nil]
     startWithCompletionHandler:^(FBSDKGraphRequestConnection *connection, id result, NSError *error) {
         if (!error) {
             NSDictionary *res = result;
             [self sendPostToBackOffice:res];
//             NSLog(@"Facebook fetched user:%@", res);
         } else {
             NSLog(@"Error %@", error.description);
             [SVProgressHUD showErrorWithStatus:@"There was an error"];
         }
     }];
}

-(void)sendPostToBackOffice:(NSDictionary *)user {
    [SVProgressHUD showWithStatus:@"Iniciando sesión"];
    
    NSMutableDictionary *newUser = [[NSMutableDictionary alloc] init];
    [newUser setValue:[user objectForKey:@"id"] forKey:@"id_fb"];
    [newUser setValue:[user objectForKey:@"email"] forKey:@"email"];
    [newUser setValue:[user objectForKey:@"name"] forKey:@"nom_complet"];
    
    [Utils postUser:newUser andSuccessBlock:^(NSDictionary *result) {
        [SVProgressHUD dismiss];
        
        int success = [[result objectForKey:@"success"] intValue];
        
        if (success) {
            NSDictionary *data = [result objectForKey:@"data"];
//            NSLog(@"API data: %@", data);
            
            Usuari *current = [[Usuari alloc] init];
            current.id_fb = [data objectForKey:@"id_fb"];
            current.nom_complet = [data objectForKey:@"nom_complet"];
            current.email = [data objectForKey:@"email"];
            current.urlToPicture = [[[user objectForKey:@"picture"] objectForKey:@"data"] objectForKey:@"url"];
            
            appDelegate.currentUser = current;
            NSNumber *discap = [data objectForKey:@"discapacitat"];

            
            if (discap == nil || [discap isKindOfClass:[NSNull class]]) {
                [self performSegueWithIdentifier:@"showFirstTime" sender:self];
            } else {
                current.discapacitat = discap;
                [self performSegueWithIdentifier:@"showTabbar" sender:self];
            }
            

        } else {
            [SVProgressHUD showErrorWithStatus:[result objectForKey:@"message"]];
        }
        
    } error:^(NSString *err) {
        NSLog(@"%@", err);
        [SVProgressHUD showErrorWithStatus:err];
    }];
}

-(void)loginButtonDidLogOut:(FBSDKLoginButton *)loginButton {
    
}


-(void)getUserData {
    if ([FBSDKAccessToken currentAccessToken]) {
        NSString *info = @"id, name, email";
        NSDictionary *params = @{@"fields" : info};
        [[[FBSDKGraphRequest alloc] initWithGraphPath:@"me" parameters:params]
         startWithCompletionHandler:^(FBSDKGraphRequestConnection *connection, id result, NSError *error) {
             if (!error) {
//                 NSLog(@"fetched user:%@", result);
             }
         }];
    }
}

@end
