//
//  CompletadosDetailViewController.h
//  Whelp
//
//  Created by Alex Morral on 13/10/15.
//  Copyright © 2015 Alex Morral. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Trip.h"

@interface CompletadosDetailViewController : UIViewController
{
    
    IBOutlet UILabel *tituloLbl;
    IBOutlet UILabel *fechaLbl;
    IBOutlet UILabel *puntuacionLbl;
    IBOutlet UILabel *descLbl;
    IBOutlet UILabel *origenLbl;
    IBOutlet UILabel *destLbl;
    IBOutlet UITextView *commentsTextView;
    IBOutlet UILabel *autorLbl;
    IBOutlet UILabel *whelperLbl;
    IBOutlet UILabel *textCommentsLbl;
}
@property (strong, nonatomic) Trip *viaje;

@property (weak, nonatomic) IBOutlet UILabel *titulo;

@end
