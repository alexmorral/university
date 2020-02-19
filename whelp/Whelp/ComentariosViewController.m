//
//  ViewController.m
//  Whelp
//
//  Created by Alex Morral on 21/9/15.
//  Copyright © 2015 Alex Morral. All rights reserved.
//

#import "ComentariosViewController.h"
#import "ComentariosDetailViewController.h"
#import "Trip.h"

@interface ComentariosViewController ()

@end

@implementation ComentariosViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
    

    
}

-(void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    
    //GET comentarios
    
    [self getComentariosFromBackoffice];
}


-(void)getComentariosFromBackoffice {
    [SVProgressHUD showWithStatus:@"Cargando"];
    
    [Utils getCommentsWithSuccessBlock:^(NSDictionary *result) {
        
        [SVProgressHUD dismiss];
        
//        NSLog(@"coments data:%@",result);
        
        self.dictDatos = [result objectForKey:@"data"];
        
        [self.comentariosTableView reloadData];
        
    } error:^(NSString *err) {
        [SVProgressHUD showErrorWithStatus:err];
        NSLog(@"Error:%@", err);
        
    }];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


#pragma mark - UITableViewDataSource

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"cell"];
    if (cell == nil) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:@"cell"];
    }
    
    [cell setBackgroundColor:[UIColor clearColor]];
    
    UIView *cellBg = (UIView *) [cell viewWithTag:1];
    UILabel *titleLabel = (UILabel *) [cell viewWithTag:2];
    UILabel *autorLabel = (UILabel *) [cell viewWithTag:3];
    
    [cell.textLabel setText:@""];
    
    [cellBg.layer setCornerRadius:5.0];
    
    if (![self.dictDatos count]) {
        [cell.textLabel setText:@"No hay datos"];
    } else {
        if(self.dictDatos != nil){
            [titleLabel setText:[[self.dictDatos objectAtIndex:indexPath.row] objectForKey:@"comentari"]];
            
            NSDictionary *autor = [appDelegate.allUsers objectForKey:[[self.dictDatos objectAtIndex:indexPath.row] objectForKey:@"discapacitat"]];
            [autorLabel setText:[autor objectForKey:@"nom_complet"]];
        }
        
    }
    
    [cell setSelectionStyle:UITableViewCellSelectionStyleNone];
    
    return cell;
}

-(NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}

-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    //return [[self.dictDatos allKeys] count];
    if ([self.dictDatos count]) {
        return [self.dictDatos count];
    }
    return 1;
}


-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    return 100;
}

/*-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    NSLog(@"Selected");
    self.selectedindexPath = indexPath;
    [self performSegueWithIdentifier:@"verDetalle" sender:self];
 
}*/

/*-(void) prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    if ([[segue identifier] isEqualToString:@"verDetalle"]) {
//        CompletadosDetailViewController *segundoView = (CompletadosDetailViewController *)[segue destinationViewController];
//        segundoView.viaje = [self.dictDatos objectForKey:@"viaje":[self.selectedindexPath description]];
        
        ComentariosDetailViewController *segundoView = [segue destinationViewController];
//        segundoView.viaje = [self.dictDatos objectForKey:@"viaje"];
        NSString *key = [NSString stringWithFormat:@"viaje%ld", (long)self.selectedindexPath.row+1];
        segundoView.viaje = [self.dictDatos objectForKey:key];
        
    }
}*/

@end
