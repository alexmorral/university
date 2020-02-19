//
//  CompletadosDetailViewController.h
//  Whelp
//
//  Created by Alex Morral on 13/10/15.
//  Copyright © 2015 Alex Morral. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Trip.h"

@interface ComentariosDetailViewController : UIViewController
{
    
    IBOutlet UILabel *tituloLbl;
    IBOutlet UITextView *commentsTextView;
    IBOutlet UILabel *autorLbl;
}

@property (strong, nonatomic) Trip *viaje;


@end
