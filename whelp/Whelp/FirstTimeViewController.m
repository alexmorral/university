//
//  FirstTimeViewController.m
//  Whelp
//
//  Created by Alex Morral on 15/11/15.
//  Copyright © 2015 Alex Morral. All rights reserved.
//

#import "FirstTimeViewController.h"

@implementation FirstTimeViewController

-(void)viewDidLoad {
    
}

-(void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    [self.view layoutIfNeeded];
    
    [discapContainer.layer setCornerRadius:discapContainer.frame.size.height/2];
    [discapContainer setAlpha:0.7f];
    [discapContainer.layer setBorderColor:UIColorFromHex(purpleColor).CGColor];
    [discapContainer.layer setBorderWidth:2.0f];
    
    [volunteerContainer.layer setCornerRadius:volunteerContainer.frame.size.height/2];
    [volunteerContainer setAlpha:0.7f];
    [volunteerContainer.layer setBorderColor:UIColorFromHex(purpleColor).CGColor];
    [volunteerContainer.layer setBorderWidth:2.0f];
    
    
}

-(void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}


- (IBAction)volunteerClicked:(id)sender {
    //POST update usuario con voluntario YES
    
    [voluntBtn setEnabled:NO];
    appDelegate.currentUser.discapacitat = [NSNumber numberWithBool:NO];
    
    [Utils updateUser:appDelegate.currentUser andSuccessBlock:^(NSDictionary *result) {
        NSLog(@"success");
       [self performSegueWithIdentifier:@"showTabbar" sender:self];
    } error:^(NSString *err) {
        NSLog(@"%@", err);
        [voluntBtn setEnabled:YES];
        [SVProgressHUD showErrorWithStatus:err];
    }];
}

- (IBAction)discapClicked:(id)sender {
    //POST update usuario con dicap YES
    appDelegate.currentUser.discapacitat = [NSNumber numberWithBool:YES];
    
    [discapBtn setEnabled:NO];
    
    [Utils updateUser:appDelegate.currentUser andSuccessBlock:^(NSDictionary *result) {
        NSLog(@"success");
        [self performSegueWithIdentifier:@"showTabbar" sender:self];
    } error:^(NSString *err) {
        NSLog(@"%@", err);
        [discapBtn setEnabled:YES];
        [SVProgressHUD showErrorWithStatus:err];
    }];
}

@end
