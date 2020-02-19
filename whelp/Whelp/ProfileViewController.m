//
//  ProfileViewController.m
//  Whelp
//
//  Created by Alex Morral on 8/10/15.
//  Copyright © 2015 Alex Morral. All rights reserved.
//

#import "ProfileViewController.h"

@implementation ProfileViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
    
    //self.dictDatos = [[NSMutableDictionary alloc] init];
    
    //cargar dictDatos de la api
    
    if (appDelegate.currentUser.discapacitat.boolValue == YES) {
        [self hideControls];
    }
    else{
        [self getPuntuacionFromBackoffice];
    }
    
    [self.view layoutIfNeeded];
    
    FBSDKShareLinkContent *content = [[FBSDKShareLinkContent alloc] init];
    NSURL *contentURL = [[NSURL alloc] initWithString:
                         @"https://gentle-shelf-9505.herokuapp.com"];
    NSURL *imageURL =
    [NSURL URLWithString:@"https://gentle-shelf-9505.herokuapp.com/logoapp.png"];
    content.contentURL = contentURL;
    content.imageURL = imageURL;
    content.contentTitle = @"Whelp";
    content.contentDescription = @"Estoy usando Whelp. Apúntate tu también y ayuda a personas con mobilidad reducida a moverse por la ciudad";
    
    FBSDKShareButton *shareButton = [[FBSDKShareButton alloc] initWithFrame:CGRectMake(shareContainer.frame.size.width/4, 0, shareContainer.frame.size.width/2, shareContainer.frame.size.height)];
    
    shareButton.shareContent = content;

    [shareContainer addSubview:shareButton];
    
    
    
    FBSDKLoginButton *myLoginButton = [[FBSDKLoginButton alloc] initWithFrame:CGRectMake(shareContainer.frame.size.width/4, 0, shareContainer.frame.size.width/2, shareContainer.frame.size.height)];
    
    myLoginButton.readPermissions = @[@"email",@"public_profile",@"user_hometown",@"user_birthday",@"user_about_me",@"user_friends",@"user_photos"];
    myLoginButton.delegate = self;
    // Add the button to the view
    [loginBtnContainer addSubview:myLoginButton];

}

-(void) hideControls{
    [self.comentariosButton setHidden:YES];
    [self.textoPuntuacionLabel setHidden:NO];
    [self.textoPuntuacionLabel setText:[NSString stringWithFormat:@"Hola %@", appDelegate.currentUser.nom_complet]];
    [self.puntuacionLabel setHidden:YES];
}

-(void)getPuntuacionFromBackoffice {
    [SVProgressHUD showWithStatus:@"Cargando"];
    
    [Utils getCommentsWithSuccessBlock:^(NSDictionary *result) {
        
        [SVProgressHUD dismiss];
        
//        NSLog(@"coments data:%@",result);
        
        NSMutableArray *puntuaciones = [result objectForKey:@"data"];
        float puntTotal = 0;
        if ([puntuaciones count]) {
            for (NSDictionary *puntcom in puntuaciones){
                puntTotal = puntTotal + [[puntcom objectForKey:@"puntuacio"] intValue];
            }
            puntTotal = puntTotal / [puntuaciones count];
        }
        [self.puntuacionLabel setText:[NSString stringWithFormat:@"%.f",puntTotal]];
        
    } error:^(NSString *err) {
        [SVProgressHUD showErrorWithStatus:err];
        NSLog(@"Error:%@", err);
        
    }];
}



-(void)loginButtonDidLogOut:(FBSDKLoginButton *)loginButton {
    [[NSNotificationCenter defaultCenter] postNotificationName:@"logout" object:nil];
}

@end
