//
//  MapInfoViewController.h
//  Whelp
//
//  Created by Alex Morral on 15/10/15.
//  Copyright © 2015 Alex Morral. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface MapInfoViewController : UIViewController <UIAlertViewDelegate> {
    IBOutlet UIButton *dismissBtn;
    IBOutlet UIButton *aceptarBtn;
    
    IBOutlet UILabel *titleLbl;
    IBOutlet UILabel *fechaLbl;
    IBOutlet UILabel *usuarioLbl;
    IBOutlet UILabel *origenLbl;
    IBOutlet UILabel *destinoLbl;
    IBOutlet UITextView *descriptLbl;
    
}

@property(nonatomic, strong) NSDictionary *activity;

- (IBAction)dismissView:(id)sender;
-(IBAction)aceptarActivitat:(id)sender;

-(void)reloadInfo;

@end
