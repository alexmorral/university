//
//  ActualesViewController.h
//  Whelp
//
//  Created by Alex Morral on 20/10/15.
//  Copyright © 2015 Alex Morral. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ActualesViewController : UIViewController <UITableViewDelegate, UITableViewDataSource>


@property (strong, nonatomic) IBOutlet UITableView *actualesTableView;

@property (strong,nonatomic) NSMutableArray *dictDatosProceso;

@property (strong,nonatomic) NSMutableArray *dictDatosPendientes;

@property (strong,nonatomic) NSIndexPath *selectedindexPath;

@end
