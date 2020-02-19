//
//  MillorsWhelpersViewController.m
//  Whelp
//
//  Created by 1137952 on 01/12/15.
//  Copyright © 2015 Alex Morral. All rights reserved.
//

#import "MillorsWhelpersViewController.h"

@interface MillorsWhelpersViewController ()

@end

@implementation MillorsWhelpersViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(void)getBestWhelpersFromBackoffice {
    
    [SVProgressHUD showWithStatus:@"Cargando"];
    
    [Utils getPuntuacionsMillorsWithSuccessBlock:^(NSDictionary *result) {
        
        [SVProgressHUD dismiss];
        
//        NSLog(@"puntuacions data:%@",result);
        
        self.arrayPunts = [result objectForKey:@"data"];
        
        [myTableView reloadData];
    } error:^(NSString *err) {
        [SVProgressHUD showErrorWithStatus:err];
        NSLog(@"Error:%@", err);
        
    }];
}

-(void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    
    [self getBestWhelpersFromBackoffice];
}


-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"UITableViewCell"];
    
    if (cell == nil) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:@"UITableViewCell"];
    }
    
    NSDictionary *item = [self.arrayPunts objectAtIndex:indexPath.row];
    
    UILabel *pos = (UILabel *)[cell viewWithTag:1];
    UILabel *name = (UILabel *)[cell viewWithTag:2];
    UILabel *punt = (UILabel *)[cell viewWithTag:3];
    
    NSDictionary *user = [appDelegate.allUsers objectForKey:[item objectForKey:@"voluntari"]];
    
    [pos setText:[NSString stringWithFormat:@"%ld", (long)indexPath.row+1]];
    [name setText:[user objectForKey:@"nom_complet"]];
    float puntFloat = [[item objectForKey:@"puntuacio"] floatValue];
    [punt setText:[NSString stringWithFormat:@"%.f", puntFloat]];
    
    return cell;
}


-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return [self.arrayPunts count];
}

-(NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}

@end
