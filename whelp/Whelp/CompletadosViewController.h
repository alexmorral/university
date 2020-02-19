//
//  ViewController.h
//  Whelp
//
//  Created by Alex Morral on 21/9/15.
//  Copyright © 2015 Alex Morral. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface CompletadosViewController : UIViewController <UITableViewDelegate, UITableViewDataSource>


@property (strong, nonatomic) IBOutlet UITableView *completadosTableView;

@property (strong,nonatomic) NSArray *dictDatos;

@property (strong,nonatomic) NSIndexPath *selectedindexPath;

@end

