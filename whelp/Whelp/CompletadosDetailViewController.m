//
//  CompletadosDetailViewController.m
//  Whelp
//
//  Created by Alex Morral on 13/10/15.
//  Copyright © 2015 Alex Morral. All rights reserved.
//

#import "CompletadosDetailViewController.h"

@interface CompletadosDetailViewController ()

@end

@implementation CompletadosDetailViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    [tituloLbl setText:self.viaje.title];
    
    [fechaLbl setText:[self.viaje getDate]];
    [puntuacionLbl setText:[NSString stringWithFormat:@"%@",[self.viaje getScore]]];
    [descLbl setText:[self.viaje getDescript]];
    [origenLbl setText:[self.viaje getBeginning]];
    [destLbl setText:[self.viaje getDestination]];
    [commentsTextView setText:[self.viaje getComment]];
    [autorLbl setText:[self.viaje getAuthor]];
    [whelperLbl setText:[self.viaje getWhelper]];
    
    /*if (appDelegate.currentUser.discapacitat.boolValue == YES) {
        [self hideControls];
    }*/
}

- (void) hideControls{
    [commentsTextView setHidden:YES];
    [textCommentsLbl setHidden:YES];
}

//declarar diccionaio en esta clase
//perform segue
//prepare for segue (pasarle el diccionario a esta clase)


- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/*
 #pragma mark - Navigation
 
 // In a storyboard-based application, you will often want to do a little preparation before navigation
 - (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
 // Get the new view controller using [segue destinationViewController].
 // Pass the selected object to the new view controller.
 }
 */

@end