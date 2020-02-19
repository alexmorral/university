//
//  ActualesDetailViewController.m
//  Whelp
//
//  Created by 1137952 on 17/11/15.
//  Copyright (c) 2015 Alex Morral. All rights reserved.
//

#import "ActualesDetailViewController.h"
#import <Foundation/Foundation.h>
#import "FinalizarViewController.h"

@interface ActualesDetailViewController ()

@end

@implementation ActualesDetailViewController 

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    NSDictionary *user = [appDelegate.allUsers objectForKey:_viaje.discapacitat];
    [tituloLbl setText:self.viaje.titol];
    [fechaLbl setText:self.viaje.data];
    [descrTextView setText:self.viaje.descripcio];
    [origenLbl setText:self.viaje.origen];
    [destLbl setText:self.viaje.desti];
    [autorLbl setText:[user objectForKey:@"nom_complet"]];
    
    BOOL viajeEnProceso = [self.viaje.en_proces boolValue];
    if (!viajeEnProceso || appDelegate.currentUser.discapacitat.boolValue == NO) {
        [finBtn setHidden:YES];
    }
    if (viajeEnProceso || [self.viaje.voluntari isKindOfClass:[NSNull class]]){
        [iendoBtn setEnabled:NO];
    }
    
    [descrTextView.layer setCornerRadius:5];
    [descrTextView.layer setBorderColor:[UIColor lightGrayColor].CGColor];
    [descrTextView.layer setBorderWidth:1];

}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}



- (IBAction)cancelarViaje:(id)sender { 
    if (appDelegate.currentUser.discapacitat.boolValue == YES) {
        //USER
        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"¿Estas seguro que quieres cancelar el viaje?"
                                                        message:@"Se borrara la alerta del viaje"
                                                       delegate:self
                                              cancelButtonTitle:@"No"
                                              otherButtonTitles:@"Sí", nil];
        [alert show];
    }
    else{
        //WHELPER
        if ([self.viaje.en_proces isEqual:[NSNumber numberWithBool:YES]]) {
            UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"¿Estas seguro que quieres cancelar el viaje?"
                                                            message:@"Serás desasignado del viaje"
                                                           delegate:self
                                                  cancelButtonTitle:@"No"
                                                  otherButtonTitles:@"Sí", nil];
            [alert show];
        }
        else{
            UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"¿Estas seguro que quieres cancelar el viaje?"
                                                            message:@"Serás desasignado del viaje"
                                                           delegate:self
                                                  cancelButtonTitle:@"No"
                                                  otherButtonTitles:@"Sí", nil];
            [alert show];
        }
    }
    
    
    
    
}

- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex {
    if ([alertView.title isEqualToString:@"¿Estas seguro que quieres cancelar el viaje?"]) {
        if (buttonIndex == 1){ //YES
//            NSLog(@"button 1");
            if (appDelegate.currentUser.discapacitat.boolValue == YES) {
                //USER
                
                [Utils postEliminarActivitat:self.viaje.getModelDictionary andSuccessBlock:^(NSDictionary *result) {
                    [SVProgressHUD showSuccessWithStatus:@"Viaje eliminado"];
                    [self.navigationController popViewControllerAnimated:YES];
                } error:^(NSString *err) {
                    [SVProgressHUD showErrorWithStatus:err];
                }];
                
            }
            else{
                //WHELPER
                if ([self.viaje.en_proces isEqual:[NSNumber numberWithBool:YES]]) {
                    //enviar notificacion user -- no se va a poder presentar whelper
                    
                    [Utils postCancelarActivitat:self.viaje.getModelDictionary andSuccessBlock:^(NSDictionary *result) {
                        [SVProgressHUD showSuccessWithStatus:@"Viaje eliminado"];
                        [self.navigationController popViewControllerAnimated:YES];
                    } error:^(NSString *err) {
                        [SVProgressHUD showErrorWithStatus:err];
                    }];
                    
                }
                else{
                    //enviar notificacion user -- whelper desasignado al viaje
                    
                    [Utils postCancelarActivitat:self.viaje.getModelDictionary andSuccessBlock:^(NSDictionary *result) {
                        [SVProgressHUD showSuccessWithStatus:@"Viaje eliminado"];
                        [self.navigationController popViewControllerAnimated:YES];
                    } error:^(NSString *err) {
                        [SVProgressHUD showErrorWithStatus:err];
                    }];
                    
                    //POST Desasignar whelper al viaje
                }
            }
//            [self.navigationController popViewControllerAnimated:YES];
        }
    } else if([alertView.title isEqualToString:@"¿Estas seguro que quieres finalizar el viaje?"]) {
        if (buttonIndex == 1){
            NSLog(@"button 1");
            
            //POST Finalizar viaje
            
            [self performSegueWithIdentifier:@"finalizarViaje" sender:self];
            
            
//            [self.navigationController popViewControllerAnimated:YES];
        } else if (buttonIndex == 2){
            NSLog(@"button 2");
            
            //Reportar user
            
            [self performSegueWithIdentifier:@"finalizarViaje" sender:self];
            
        }
    }
}

- (IBAction)iendoViaje:(id)sender {
    //Se debe deshabilitar una vez avisado?
    [iendoBtn setEnabled:NO];
    //enviar notificación user/whelper
    
    [Utils postEnProces:self.viaje.getModelDictionary andSuccessBlock:^(NSDictionary *result) {
        [SVProgressHUD showSuccessWithStatus:@"La actividad a cambiado de estado a \"En Proceso\""];
    } error:^(NSString *err) {
        NSLog(@"%@", err);
        [SVProgressHUD showErrorWithStatus:@"Ha habido algún problema"];
    }];
    [self.navigationController popViewControllerAnimated:YES];
}

- (IBAction)finalizarViaje:(id)sender {
    if (appDelegate.currentUser.discapacitat.boolValue == YES) {
        [self performSegueWithIdentifier:@"finalizarViaje" sender:self];
    }
    else{
        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"¿Estas seguro que quieres finalizar el viaje?"
                                                        message:@"El viaje se dará por finalizado"
                                                       delegate:self
                                              cancelButtonTitle:@"No"
                                              otherButtonTitles:@"Sí", nil];
        [alert show];
    }
//    [finBtn setEnabled:NO];
    
}

-(void) prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    if ([[segue identifier] isEqualToString:@"finalizarViaje"]) {
        
        FinalizarViewController *segundoView = [segue destinationViewController];
        segundoView.id_activitat = self.viaje.idActivity;
        segundoView.voluntari = self.viaje.voluntari;
        segundoView.discapacitat = self.viaje.discapacitat;
        
    }
}



@end
