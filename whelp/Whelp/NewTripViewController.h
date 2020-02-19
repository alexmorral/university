//
//  NewTripViewController.h
//  Whelp
//
//  Created by 1137952 on 27/10/15.
//  Copyright (c) 2015 Alex Morral. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface NewTripViewController : UIViewController <UITextFieldDelegate, UIAlertViewDelegate>{
    
//    IBOutlet UITextView *descripttextview;
//    IBOutlet UITextField *destinationtextedit;
//    IBOutlet UITextField *beginningtextedit;
//    IBOutlet UITextField *datetextedit;
//    IBOutlet UITextField *titletextedit;
    
    
    IBOutlet UITextField *tituloField;
    IBOutlet UITextField *fechaField;
    IBOutlet UITextField *origenTextField;
    IBOutlet UITextField *origenLatField;
    IBOutlet UITextField *origenLonField;
    
    IBOutlet UITextField *destinoTextField;
    IBOutlet UITextField *destinoLatField;
    IBOutlet UITextField *destinoLonField;
    IBOutlet UITextView *descriptionTextView;
    
    UIView *datePickerContainer;
    UIDatePicker *datePicker;
}

@property(nonatomic, strong) NSDictionary *tripCoordinates;

- (IBAction)showDatePicker:(id)sender;

@end
