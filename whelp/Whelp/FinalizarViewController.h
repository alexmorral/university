//
//  FinalizarViewController.h
//  Whelp
//
//  Created by 1137952 on 20/11/15.
//  Copyright (c) 2015 Alex Morral. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface FinalizarViewController : UIViewController <UIAlertViewDelegate>

@property (strong, nonatomic) IBOutlet UILabel *puntuacionLbl;
@property (strong, nonatomic) IBOutlet UIStepper *puntuacionStp;
@property (strong, nonatomic) IBOutlet UITextView *comentarioTxtView;

@property (strong, nonatomic) IBOutlet UIButton *aceptarBtn;
@property (strong, nonatomic) IBOutlet UIButton *cancelarBtn;

@property (strong, nonatomic) IBOutlet UIButton *reportBtn;

@property (strong, nonatomic) NSNumber *id_activitat;
@property (strong, nonatomic) NSNumber *voluntari;
@property (strong, nonatomic) NSNumber *discapacitat;

@end
