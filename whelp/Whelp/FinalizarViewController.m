//
//  FinalizarViewController.m
//  Whelp
//
//  Created by 1137952 on 20/11/15.
//  Copyright (c) 2015 Alex Morral. All rights reserved.
//

#import "FinalizarViewController.h"
#import "Comentari.h"

@interface FinalizarViewController ()

@end

@implementation FinalizarViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    [self.puntuacionLbl setText:[NSString stringWithFormat:@"%0.f",self.puntuacionStp.value]];
    
    [self.comentarioTxtView.layer setBorderColor:[UIColor darkGrayColor].CGColor];
    [self.comentarioTxtView.layer setBorderWidth:1];
    [self.comentarioTxtView.layer setCornerRadius:7];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)stepperChanged:(id)sender {
    [self.puntuacionLbl setText:[NSString stringWithFormat:@"%0.f",self.puntuacionStp.value]];
}


- (IBAction)aceptarCicked:(id)sender {
    

    NSString *comentarioString = self.comentarioTxtView.text;
    NSNumber *puntuacionNumber = [NSNumber numberWithDouble:self.puntuacionStp.value];
    
    //POST añadir puntuacion y coment
    
    Comentari *newComment = [[Comentari alloc] init];
    newComment.discapacitat = self.discapacitat;
    newComment.voluntari = self.voluntari;
    newComment.puntuacio = puntuacionNumber;
    newComment.textComentari = comentarioString;
    newComment.idActivity = self.id_activitat;
    
    [SVProgressHUD showWithStatus:@"Enviando"];
    [Utils postData:newComment withType:typeComment andSuccessBlock:^(NSDictionary *result) {
        [SVProgressHUD dismiss];
        [self.navigationController dismissViewControllerAnimated:YES completion:nil];
    } error:^(NSString *err) {
        NSLog(@"%@", err);
       [SVProgressHUD showErrorWithStatus:@"Hubo algún error"];
    }];
    
    
}
- (IBAction)reportU:(id)sender {
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"¿Estas seguro que quieres reportar al whelper que te ha acompañado?"
                                                    message:@""
                                                   delegate:self
                                          cancelButtonTitle:@"No"
                                          otherButtonTitles:@"Sí", nil];
    [alert show];
}

- (IBAction)cancelarClicked:(id)sender {
    [self dismissViewControllerAnimated:YES completion:nil];
    [self.navigationController popViewControllerAnimated:YES];
}

- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex {
    if (buttonIndex == 1){
        NSLog(@"button 1");
        NSMutableDictionary *dict = [NSMutableDictionary new];
        [dict setObject:appDelegate.currentUser.id_fb forKey:@"usuari"];
        [dict setObject:_id_activitat forKey:@"id"];
        
        
        [Utils postReportarUser:dict andSuccessBlock:^(NSDictionary *result) {
            [Utils postEliminarActivitat:dict andSuccessBlock:^(NSDictionary *result) {
                [SVProgressHUD showSuccessWithStatus:@"Usuario reportado con éxito"];
                [self.navigationController popToRootViewControllerAnimated:YES];
            } error:^(NSString *err) {
                NSLog(@"%@", err);
                [SVProgressHUD showErrorWithStatus:@"Ha habido un error al reportar"];
            }];
        } error:^(NSString *err) {
            NSLog(@"%@", err);
            [SVProgressHUD showErrorWithStatus:@"Ha habido un error al reportar"];
        }];
        
        [self.reportBtn setEnabled:NO];
    } else {
        NSLog(@"button 0");
        
    }
}


@end
