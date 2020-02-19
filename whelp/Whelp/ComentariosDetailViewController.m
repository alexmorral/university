//
//  CompletadosDetailViewController.m
//  Whelp
//
//  Created by Alex Morral on 13/10/15.
//  Copyright © 2015 Alex Morral. All rights reserved.
//

#import "ComentariosDetailViewController.h"

@interface ComentariosDetailViewController ()

@end

@implementation ComentariosDetailViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    [tituloLbl setText:self.viaje.title];
    [commentsTextView setText:[self.viaje getComment]];
    [autorLbl setText:[self.viaje getAuthor]];
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