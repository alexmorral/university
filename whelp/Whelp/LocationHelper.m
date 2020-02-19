//
//  LocationHelper.m
//  Whelp
//
//  Created by Alex Morral on 21/9/15.
//  Copyright © 2015 Alex Morral. All rights reserved.
//

#import "LocationHelper.h"
#import "SVProgressHUD.h"

@implementation LocationHelper

+(void)getLocation:(void (^)(CLLocation *currentLocation))succesBlock error:(void (^)(NSString* err))errorBlock {
    INTULocationManager *locMgr = [INTULocationManager sharedInstance];
    [locMgr requestLocationWithDesiredAccuracy:INTULocationAccuracyCity
                                       timeout:10.0
                          delayUntilAuthorized:YES
                                         block:^(CLLocation *currentLocation, INTULocationAccuracy achievedAccuracy, INTULocationStatus status) {
                                             if (status == INTULocationStatusSuccess) {
                                                 succesBlock(currentLocation);
                                             }
                                             else if (status == INTULocationStatusTimedOut) {
                                                 errorBlock(NSLocalizedString(@"Se ha agotado el tiempo de espera", nil));
                                             }
                                             else {
                                                 switch (status) {
                                                     case INTULocationStatusServicesNotDetermined: {
                                                         errorBlock(NSLocalizedString(@"No se han dado permisos de localizacion", nil));
                                                         break;
                                                     }
                                                     case INTULocationStatusServicesDenied: {
                                                         errorBlock(nil);
                                                         [self showAlertViewToSettings];
                                                         break;
                                                     }
                                                     case INTULocationStatusServicesRestricted: {
                                                         errorBlock(nil);
                                                         [self showAlertViewToSettings];
                                                         break;
                                                     }
                                                     case INTULocationStatusServicesDisabled: {
                                                         errorBlock(nil);
                                                         [self showAlertViewToSettings];
                                                         break;
                                                     }
                                                     case INTULocationStatusError: {
                                                         errorBlock(NSLocalizedString(@"Error indeterminado", nil));
                                                         break;
                                                     }
                                                     default: {
                                                         errorBlock(NSLocalizedString(@"Error indeterminado", nil));
                                                         break;
                                                     }
                                                 }
                                             }
                                         }];
}




+(void) showAlertViewToSettings {
    [SVProgressHUD dismiss];
    UIAlertView *alert;
    if (floor(NSFoundationVersionNumber) > NSFoundationVersionNumber_iOS_7_1) {
        alert = [[UIAlertView alloc] initWithTitle:NSLocalizedString(@"Aviso", nil)
                                           message:NSLocalizedString(@"Por favor, activa la localización en tu dispositivo para poder realizar una búsqueda por proximidad", nil)
                                          delegate:self
                                 cancelButtonTitle:NSLocalizedString(@"Cancelar", nil)
                                 otherButtonTitles:NSLocalizedString(@"Configuración", nil), nil];
    }
    else {
        alert = [[UIAlertView alloc] initWithTitle:NSLocalizedString(@"Aviso", nil)
                                           message:NSLocalizedString(@"Por favor, autoriza los servicios de localización para poder realizar una búsqueda por proximidad", nil)
                                          delegate:self
                                 cancelButtonTitle:NSLocalizedString(@"OK", nil)
                                 otherButtonTitles:nil];
    }
    [alert show];
}

#pragma mark - Alert view delegate
+ (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:
(NSInteger)buttonIndex{
    switch (buttonIndex) {
        case 0:
            break;
        case 1:
            [[UIApplication sharedApplication] openURL:[NSURL URLWithString:UIApplicationOpenSettingsURLString]];
            break;
        default:
            break;
    }
}

@end
