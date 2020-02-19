//
//  ActualesViewController.m
//  Whelp
//
//  Created by Alex Morral on 20/10/15.
//  Copyright © 2015 Alex Morral. All rights reserved.
//

#import "ActualesViewController.h"
#import "Trip.h"
#import "Activitat.h"
#import "ActualesDetailViewController.h"


@interface ActualesViewController ()

@end

@implementation ActualesViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.

//    self.dictDatosProceso = [[NSMutableDictionary alloc] init];
//    self.dictDatosPendientes = [[NSMutableDictionary alloc] init];
    
    
}

-(void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    
    
    //GET Viajes pendientes
    
    
    [self.actualesTableView reloadData];
    
    [self getPendientesFromBackoffice];
}


-(void)getPendientesFromBackoffice {
    
    //faltan campos de nombrediscapacitado, nombrevoluntario, tituloactividad y descripcionactividad
    //si puede ser que devuelva solo las actividades pendientes que no esten en proceso
    
    [SVProgressHUD showWithStatus:@"Cargando"];
    
    [Utils getNoCompletedActivitatsWithSuccessBlock:^(NSDictionary *result) {
        
        [SVProgressHUD dismiss];
        
//        NSLog(@"Pendientes data:%@",result);
        
        self.dictDatosProceso = [NSMutableArray new];
        self.dictDatosPendientes = [NSMutableArray new];
        
        NSArray *noCompletadosArray = [[result objectForKey:@"data"] objectForKey:@"activitats"];
        
        for (NSDictionary *dic in noCompletadosArray) {
            NSNumber *enProceso = [dic objectForKey:@"en_proces"];
            if ([enProceso isEqual:[NSNumber numberWithBool:YES]]) {
                [self.dictDatosProceso addObject:dic];
            } else {
                [self.dictDatosPendientes addObject:dic];
            }
        }
        [self.actualesTableView reloadData];
        
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
    UIView *footerView = (UIView *)[cell viewWithTag:3];
    UIView *nameContainer = (UIView *)[cell viewWithTag:4];
    UILabel *tipoLabel = (UILabel *) [cell viewWithTag:5];
    UILabel *nombreLabel = (UILabel *) [cell viewWithTag:6];

    
    [cellBg.layer setCornerRadius:5.0];
    [footerView.layer setCornerRadius:footerView.frame.size.height/2];
    [nameContainer.layer setCornerRadius:5.0];
    [nameContainer setBackgroundColor:[UIColor clearColor]];
    [nameContainer.layer setBorderWidth:1];
    
    BOOL discapacitado = [appDelegate.currentUser.discapacitat boolValue];
    NSString *tipo;
    
    if (discapacitado) { //mostrar con quien esta
        [tipoLabel setText:@"Whelper"];
        tipo = @"voluntari";
    } else {
        [tipoLabel setText:@"Discapacitado"];
        tipo = @"discapacitat";
    }
    
    if (indexPath.row % 2) {
        [footerView setBackgroundColor:UIColorFromHex(0x3f2f8f)];
        [nameContainer.layer setBorderColor:UIColorFromHex(0x3f2f8f).CGColor];
    }
    else {
        [footerView setBackgroundColor:UIColorFromHex(0x8bc53f)];
        [nameContainer.layer setBorderColor:UIColorFromHex(0x8bc53f).CGColor];
    }
    
    
    if (indexPath.section == 0) {
        if(self.dictDatosProceso != nil){
            NSDictionary *item = [self.dictDatosProceso objectAtIndex:indexPath.row];
            [titleLabel setText:[item objectForKey:@"titol"]];
            NSNumber *userTo = [item objectForKey:tipo];
            if (![userTo isKindOfClass:[NSNull class]]) {
                NSDictionary *user = [appDelegate.allUsers objectForKey:[item objectForKey:tipo]];
                [nombreLabel setText:[ user objectForKey:@"nom_complet"]];
            }
        }
    }
    else{
        if(self.dictDatosPendientes != nil){
            NSDictionary *item = [self.dictDatosPendientes objectAtIndex:indexPath.row];
            [titleLabel setText:[item objectForKey:@"titol"]];
            NSNumber *userTo = [item objectForKey:tipo];
            if (![userTo isKindOfClass:[NSNull class]]) {
                NSDictionary *user = [appDelegate.allUsers objectForKey:[item objectForKey:tipo]];
                [nombreLabel setText:[ user objectForKey:@"nom_complet"]];
            }
        }
     //   [titleLabel setText:[(Trip *)[self.dictDatosPendientes objectForKey:key] getTitle]];
    }
    
    [cell setSelectionStyle:UITableViewCellSelectionStyleNone];
    
    return cell;
}

-(NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 2;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    
    if(section==0){
        return [self.dictDatosProceso count];
    }
    return [self.dictDatosPendientes count];
}

-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    return 100;
}

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
//    NSLog(@"Selected");
    self.selectedindexPath = indexPath;
    [self performSegueWithIdentifier:@"verDetalle" sender:self];
    
}

-(void) prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    if ([[segue identifier] isEqualToString:@"verDetalle"]) {

        ActualesDetailViewController *segundoView = [segue destinationViewController];
        
        NSDictionary *dicViaje = nil;
        
        if (self.selectedindexPath.section == 0) {
//            segundoView.viaje = [self.dictDatosProceso objectAtIndex:self.selectedindexPath.row];
            dicViaje = [self.dictDatosProceso objectAtIndex:self.selectedindexPath.row];
        }
        else{
//            segundoView.viaje = [self.dictDatosPendientes objectAtIndex:self.selectedindexPath.row];
            dicViaje = [self.dictDatosPendientes objectAtIndex:self.selectedindexPath.row];
        }
        
//        Trip *viaje = [[Trip alloc] init];
//        
//        [viaje setTitle:[dicViaje objectForKey:@"titol"]];
//        [viaje setAuthor:[dicViaje objectForKey:@"nom_discapacitat"]];
//        [viaje setWhelper:[dicViaje objectForKey:@"nom_voluntari"]];
//        [viaje setDate:[dicViaje objectForKey:@"data"]];
//        [viaje setBeginning:[dicViaje objectForKey:@"origen"]];
//        [viaje setDestination:[dicViaje objectForKey:@"desti"]];
//        [viaje setDescript:[dicViaje objectForKey:@"descripcio"]];
//        [viaje setScore:[dicViaje objectForKey:@"puntuacio"]];
//        [viaje setComment:[dicViaje objectForKey:@"comentari"]];
        Activitat *activitat = [[Activitat alloc] init];
        [activitat setTitol:[dicViaje objectForKey:@"titol"]];
        [activitat setDiscapacitat:[dicViaje objectForKey:@"discapacitat"]];
        [activitat setVoluntari:[dicViaje objectForKey:@"voluntari"]];
        [activitat setData:[dicViaje objectForKey:@"data"]];
        [activitat setDesti:[dicViaje objectForKey:@"desti"]];
        [activitat setOrigen:[dicViaje objectForKey:@"origen"]];
        [activitat setDescripcio:[dicViaje objectForKey:@"descripcio"]];
        [activitat setEn_proces:[dicViaje objectForKey:@"en_proces"]];
        [activitat setIdActivity:[dicViaje objectForKey:@"id"]];
        
        

        
        segundoView.viaje = activitat;
    }
}

- (NSString *)tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section
{
    NSString *sectionName;
    if(section == 0){
        sectionName = @"En Proceso";
    }
    else{
        sectionName = @"Pendientes";
    }
    return sectionName;
}

@end
