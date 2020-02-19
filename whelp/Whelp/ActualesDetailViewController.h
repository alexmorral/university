//
//  ActualesDetailViewController.h
//  Whelp
//
//  Created by 1137952 on 17/11/15.
//  Copyright (c) 2015 Alex Morral. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Activitat.h"
#import "AppDelegate.h"

@interface ActualesDetailViewController : UIViewController <UIAlertViewDelegate>
{
    
    IBOutlet UILabel *tituloLbl;
    IBOutlet UILabel *fechaLbl;
    IBOutlet UILabel *origenLbl;
    IBOutlet UILabel *destLbl;
    IBOutlet UITextView *descrTextView;
    IBOutlet UILabel *autorLbl;
    IBOutlet UIButton *finBtn;
    IBOutlet UIButton *cancelBtn;
    IBOutlet UIButton *iendoBtn;
}

@property (strong, nonatomic) Activitat *viaje;


@end
