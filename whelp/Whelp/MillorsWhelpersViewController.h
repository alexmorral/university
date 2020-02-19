//
//  MillorsWhelpersViewController.h
//  Whelp
//
//  Created by 1137952 on 01/12/15.
//  Copyright © 2015 Alex Morral. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface MillorsWhelpersViewController : UIViewController <UITableViewDelegate, UITableViewDataSource>{
    
    IBOutlet UILabel *primeroLbl;
    IBOutlet UILabel *segundoLbl;
    IBOutlet UILabel *terceroLbl;
    IBOutlet UITableView *myTableView;
}

@property (strong, nonatomic) NSArray *arrayPunts;

@end
