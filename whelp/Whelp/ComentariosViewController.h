//
//  ViewController.h
//  Whelp
//
//  Created by Alex Morral on 21/9/15.
//  Copyright © 2015 Alex Morral. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ComentariosViewController : UIViewController <UITableViewDelegate, UITableViewDataSource>


@property (strong, nonatomic) IBOutlet UITableView *comentariosTableView;

@property (strong,nonatomic) NSMutableArray *dictDatos;

@property (strong,nonatomic) NSIndexPath *selectedindexPath;

@end

