//
//  MapInfoViewController.m
//  Whelp
//
//  Created by Alex Morral on 15/10/15.
//  Copyright © 2015 Alex Morral. All rights reserved.
//

#import "MapInfoViewController.h"

@interface MapInfoViewController () {
    BOOL unido;
}

@end

@implementation MapInfoViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    
    [self.view setFrame:CGRectMake(0, 0, [UIScreen mainScreen].bounds.size.width, 300)];
    
    if ([appDelegate.currentUser.discapacitat boolValue]){
        [self hideControls:YES];
    } else {
        [self hideControls:NO];
    }
    
    unido = NO;
    
}

-(void) hideControls:(BOOL)isHidden{
    [aceptarBtn setHidden:isHidden];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


-(void)reloadInfo {
    NSDictionary *user = [appDelegate.allUsers objectForKey:[_activity objectForKey:@"discapacitat"]];
    [titleLbl setText:[_activity objectForKey:@"titol"]];
    [fechaLbl setText:[_activity objectForKey:@"data"]];
    [usuarioLbl setText:[user objectForKey:@"nom_complet"]];
    [origenLbl setText:[_activity objectForKey:@"origen"]];
    [destinoLbl setText:[_activity objectForKey:@"desti"]];
    [descriptLbl setText:[_activity objectForKey:@"descripcio"]];

    [self hideControls:NO];
    NSNumber *voluntari = [_activity objectForKey:@"voluntari"];
    if (![voluntari isKindOfClass:[NSNull class]] || [appDelegate.currentUser.discapacitat boolValue]) {
        [self hideControls:YES];
    } else {
        [self hideControls:NO];
    }
    unido = NO;
}


- (IBAction)dismissView:(id)sender {
    NSDictionary *userInfo = [NSDictionary dictionaryWithObject:[NSNumber numberWithBool:unido] forKey:@"unido"];
    
    [[NSNotificationCenter defaultCenter] postNotificationName:@"mapInfoDismissed" object:nil userInfo:userInfo];
}

-(IBAction)aceptarActivitat:(id)sender {
    
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"¿Estas seguro que quieres aceptar el viaje?"
                                                    message:@"Se te asignará este viaje a tu cuenta"
                                                   delegate:self
                                          cancelButtonTitle:@"No"
                                          otherButtonTitles:@"Sí", nil];
    [alert show];
    
    
    
}



- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex {
    if (buttonIndex == 1){ //YES
        [SVProgressHUD showWithStatus:@"Asignando..."];
        
        NSMutableDictionary *unio = [[NSMutableDictionary alloc] init];
        [unio setValue:appDelegate.currentUser.id_fb forKey:@"voluntari"];
        [unio setValue:[_activity valueForKey:@"id"] forKey:@"id"];
        
        [Utils postUnirActivitat:unio andSuccessBlock:^(NSDictionary *result) {
            [SVProgressHUD showSuccessWithStatus:@"Asignado correctamente"];
            unido = YES;
            [self dismissView:self];
        } error:^(NSString *err) {
            [SVProgressHUD showErrorWithStatus:err];
        }];
        
    }
}

@end
