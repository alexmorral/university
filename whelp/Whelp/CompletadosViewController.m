//
//  ViewController.m
//  Whelp
//
//  Created by Alex Morral on 21/9/15.
//  Copyright © 2015 Alex Morral. All rights reserved.
//

#import "CompletadosViewController.h"
#import "CompletadosDetailViewController.h"
#import "Trip.h"

@interface CompletadosViewController ()

@end

@implementation CompletadosViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
    
    //self.dictDatos = [[NSMutableDictionary alloc] init];
    
    //cargar dictDatos de la api
    
    //[self initFakeData];

    [self getCompletadasFromBackoffice];
    
}

-(void)initFakeData {
    Trip *viaje1 = [[Trip alloc] init];
    [viaje1 setTitle:@"Ir de compras"];
    [viaje1 setDescript:@"Me gustaría ir de compras al mercadona"];
    [viaje1 setBeginning:@"c/ Falsa 123, Barcelona"];
    [viaje1 setDestination:@"Mercadona, c/ Falsa 124, Barcelona"];
    [viaje1 setScore: [[NSNumber alloc] initWithDouble:8.5]];
    [viaje1 setComment:@"Muy simpático. Ha llegado muy puntual."];
    [viaje1 setAuthor:@"Ruedines McRueda"];
    [viaje1 setDate:@"22/10/2015 12:00"];
    
    [self.dictDatos setValue:viaje1 forKey:@"viaje1"];
    
    Trip *viaje2 = [[Trip alloc] init];
    [viaje2 setTitle:@"Ir de compras"];
    [viaje2 setDescript:@"Me gustaría ir de compras al mercadona"];
    [viaje2 setBeginning:@"c/ Falsa 123, Barcelona"];
    [viaje2 setDestination:@"Mercadona, c/ Falsa 124, Barcelona"];
    [viaje2 setScore: [[NSNumber alloc] initWithDouble:8.5]];
    [viaje2 setComment:@"Muy simpático. Ha llegado muy puntual."];
    [viaje2 setAuthor:@"Ruedines McRueda"];
    [viaje2 setDate:@"22/10/2015 12:00"];
    
    [self.dictDatos setValue:viaje2 forKey:@"viaje2"];
    
    Trip *viaje3 = [[Trip alloc] init];
    [viaje3 setTitle:@"Ir de compras"];
    [viaje3 setDescript:@"Me gustaría ir de compras al mercadona"];
    [viaje3 setBeginning:@"c/ Falsa 123, Barcelona"];
    [viaje3 setDestination:@"Mercadona, c/ Falsa 124, Barcelona"];
    [viaje3 setScore: [[NSNumber alloc] initWithDouble:8.5]];
    [viaje3 setComment:@"Muy simpático. Ha llegado muy puntual."];
    [viaje3 setAuthor:@"Ruedines McRueda"];
    [viaje3 setDate:@"22/10/2015 12:00"];
    
    [self.dictDatos setValue:viaje3 forKey:@"viaje3"];
    
    Trip *viaje4 = [[Trip alloc] init];
    [viaje4 setTitle:@"Ir de compras"];
    [viaje4 setDescript:@"Me gustaría ir de compras al mercadona"];
    [viaje4 setBeginning:@"c/ Falsa 123, Barcelona"];
    [viaje4 setDestination:@"Mercadona, c/ Falsa 124, Barcelona"];
    [viaje4 setScore: [[NSNumber alloc] initWithDouble:8.5]];
    [viaje4 setComment:@"Muy simpático. Ha llegado muy puntual."];
    [viaje4 setAuthor:@"Ruedines McRueda"];
    [viaje4 setDate:@"22/10/2015 12:00"];
    
    [self.dictDatos setValue:viaje4 forKey:@"viaje4"];
    
    Trip *viaje5 = [[Trip alloc] init];
    [viaje5 setTitle:@"Ir de compras"];
    [viaje5 setDescript:@"Me gustaría ir de compras al mercadona"];
    [viaje5 setBeginning:@"c/ Falsa 123, Barcelona"];
    [viaje5 setDestination:@"Mercadona, c/ Falsa 124, Barcelona"];
    [viaje5 setScore: [[NSNumber alloc] initWithDouble:8.5]];
    [viaje5 setComment:@"Muy simpático. Ha llegado muy puntual."];
    [viaje5 setAuthor:@"Ruedines McRueda"];
    [viaje5 setDate:@"22/10/2015 12:00"];
    
    [self.dictDatos setValue:viaje5 forKey:@"viaje5"];
    
    Trip *viaje6 = [[Trip alloc] init];
    [viaje6 setTitle:@"Ir de compras"];
    [viaje6 setDescript:@"Me gustaría ir de compras al mercadona"];
    [viaje6 setBeginning:@"c/ Falsa 123, Barcelona"];
    [viaje6 setDestination:@"Mercadona, c/ Falsa 124, Barcelona"];
    [viaje6 setScore: [[NSNumber alloc] initWithDouble:8.5]];
    [viaje6 setComment:@"Muy simpático. Ha llegado muy puntual."];
    [viaje6 setAuthor:@"Ruedines McRueda"];
    [viaje6 setDate:@"22/10/2015 12:00"];
    
    [self.dictDatos setValue:viaje6 forKey:@"viaje6"];
    
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(void)getCompletadasFromBackoffice {
    
    //faltan campos de nombrediscapacitado, nombrevoluntario, tituloactividad, descripcionactividad, puntuacionactividad y comentarioactividad
    
    [SVProgressHUD showWithStatus:@"Cargando"];
    
    [Utils getCompletedActivitatsWithSuccessBlock:^(NSDictionary *result) {
        
        [SVProgressHUD dismiss];
        
        
        self.dictDatos = [[result objectForKey:@"data"] objectForKey:@"activitats"];
        
        
//        NSLog(@"Completed array:%@", self.dictDatos);
        
        [self.completadosTableView reloadData];
        
    } error:^(NSString *err) {
        [SVProgressHUD showErrorWithStatus:err];
        NSLog(@"Error:%@", err);
        
    }];
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
    
    [cellBg.layer setCornerRadius:5.0];
    
    //NSString *key = [NSString stringWithFormat:@"viaje%ld", (long)indexPath.row+1];
    [cell.textLabel setText:@""];
    
    //[titleLabel setText:[(Trip *)[self.dictDatos objectForKey:key] getTitle]];
    if (![self.dictDatos count]) {
        [cell.textLabel setText:@"No hay datos"];
    } else {
        if(self.dictDatos != nil){
            [titleLabel setText:[[self.dictDatos objectAtIndex:indexPath.row] objectForKey:@"titol"]];
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
/*
-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    NSLog(@"Selected");
    if ([self.dictDatos count]) {
        self.selectedindexPath = indexPath;
//        [self performSegueWithIdentifier:@"verDetalle" sender:self];
    }
    
}

-(void) prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    if ([[segue identifier] isEqualToString:@"verDetalle"]) {
        CompletadosDetailViewController *segundoView = [segue destinationViewController];
        
        //NSString *key = [NSString stringWithFormat:@"viaje%ld", (long)self.selectedindexPath.row+1];
        //segundoView.viaje = [self.dictDatos objectForKey:key];
        
        
        //si nos pasan todos los campos
        
        NSDictionary *dicViaje = [self.dictDatos objectAtIndex:self.selectedindexPath.row];
        
        Trip *viaje = [[Trip alloc] init];
        
        [viaje setTitle:[dicViaje objectForKey:@"titol"]];
        [viaje setAuthor:[dicViaje objectForKey:@"nom_discapacitat"]];
        [viaje setWhelper:[dicViaje objectForKey:@"nom_voluntari"]];
        [viaje setDate:[dicViaje objectForKey:@"data"]];
        [viaje setBeginning:[dicViaje objectForKey:@"origen"]];
        [viaje setDestination:[dicViaje objectForKey:@"desti"]];
        [viaje setDescript:[dicViaje objectForKey:@"descripcio"]];
        [viaje setScore:[dicViaje objectForKey:@"puntuacio"]];
        [viaje setComment:[dicViaje objectForKey:@"comentari"]];
        
        
//        segundoView.viaje = [self.dictDatos objectAtIndex:self.selectedindexPath.row];
        segundoView.viaje = viaje;
        
        
    }
}*/

@end
