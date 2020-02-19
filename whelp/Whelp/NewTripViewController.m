//
//  NewTripViewController.m
//  Whelp
//
//  Created by 1137952 on 27/10/15.
//  Copyright (c) 2015 Alex Morral. All rights reserved.
//

#import "NewTripViewController.h"
#import "Trip.h"
#import "Activitat.h"

@interface NewTripViewController ()

@end

@implementation NewTripViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self.navigationItem setTitle:@"Nuevo viaje"];
    
    
    [descriptionTextView.layer setCornerRadius:5.0];
    [descriptionTextView.layer setBorderWidth:0.5];
    [descriptionTextView.layer setBorderColor:[UIColor grayColor].CGColor];
    

    [origenLatField setText:[NSString stringWithFormat:@"%@",[_tripCoordinates objectForKey:@"origen_lat"]]];
    [origenLonField setText:[NSString stringWithFormat:@"%@",[_tripCoordinates objectForKey:@"origen_lon"]]];
    [destinoLatField setText:[NSString stringWithFormat:@"%@",[_tripCoordinates objectForKey:@"destino_lat"]]];
    [destinoLonField setText:[NSString stringWithFormat:@"%@",[_tripCoordinates objectForKey:@"destino_lon"]]];
                             
    UIBarButtonItem *creaBtn = [[UIBarButtonItem alloc] initWithTitle:@"Crear" style:UIBarButtonItemStylePlain target:self action:@selector(createTrip:)];
    self.navigationItem.rightBarButtonItem = creaBtn;
    
    [self initDatePicker];
    
}


-(void)initDatePicker {
    datePickerContainer = [[UIView alloc] initWithFrame:CGRectMake(0, self.view.frame.size.height, self.view.frame.size.width, 200)];
    [datePickerContainer setBackgroundColor:[UIColor colorWithWhite:0.90 alpha:1.0]];
    
    datePicker = [[UIDatePicker alloc] initWithFrame:CGRectMake(0, 30, datePickerContainer.frame.size.width, 170)];
    
    UIButton *cerrarBtn = [[UIButton alloc] initWithFrame:CGRectMake(self.view.frame.size.width-100, 0, 100, 30)];
    [cerrarBtn setTitle:@"Cerrar" forState:UIControlStateNormal];
    [cerrarBtn addTarget:self action:@selector(datechosen:) forControlEvents:UIControlEventTouchUpInside];
    [cerrarBtn setTitleColor:[UIColor blueColor] forState:UIControlStateNormal];
    
    [datePickerContainer addSubview:datePicker];
    [datePickerContainer addSubview:cerrarBtn];
    [self.view addSubview:datePickerContainer];
    
    [datePickerContainer setHidden:YES];
    
}


- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)createTrip:(id)sender {
    
    
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"¿Estás seguro que quieres crear el viaje?"
                                                    message:@"Se enviará una notificación a todos los voluntarios cercanos"
                                                   delegate:self
                                          cancelButtonTitle:@"No"
                                          otherButtonTitles:@"Sí", nil];
    [alert show];
    
}


- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
    if (buttonIndex == 1){
        NSString *tituloString = tituloField.text;
        NSString *fechaString = fechaField.text;
        NSString *origenTextString = origenTextField.text;
        NSString *destinoTextString = destinoTextField.text;
        NSString *descriptionString = descriptionTextView.text;
        
        //revisar los campos
        if (![tituloString  isEqual:@""] && ![fechaString  isEqual:@""] && ![origenTextString isEqual:@""] && ![destinoTextString  isEqual:@""] && ![descriptionString  isEqual:@""]) {
            
            // post en API
            Activitat *newActivity = [[Activitat alloc] init];
            newActivity.discapacitat = appDelegate.currentUser.id_fb;
            newActivity.titol = tituloString;
            newActivity.data = fechaString;
            newActivity.origen = origenTextString;
            newActivity.origen_latitud = [_tripCoordinates objectForKey:@"origen_lat"];
            newActivity.origen_longitud = [_tripCoordinates objectForKey:@"origen_lon"];
            newActivity.desti = destinoTextString;
            newActivity.desti_latitud = [_tripCoordinates objectForKey:@"destino_lat"];
            newActivity.desti_longitud = [_tripCoordinates objectForKey:@"destino_lon"];
            newActivity.descripcio = descriptionString;
            newActivity.completada = [NSNumber numberWithBool:NO];
            newActivity.en_proces = [NSNumber numberWithBool:NO];
            
            [SVProgressHUD showWithStatus:@"Enviando"];
            [Utils postData:newActivity withType:typeActivity andSuccessBlock:^(NSDictionary *result) {
                [SVProgressHUD dismiss];
                [self.navigationController popViewControllerAnimated:YES];
            } error:^(NSString *err) {
                NSLog(@"%@", err);
                [SVProgressHUD showErrorWithStatus:@"Hubo algún error"];
            }];
            
        } else {
            [SVProgressHUD showInfoWithStatus:@"Por favor, rellene todos los campos"];
        }
    } else {
        
        
    }
}


- (void)datechosen:(id)sender {
    [self dismissDatePicker];
    
    NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init];
    [dateFormatter setDateFormat:@"dd-MM-yyyy HH:mm"];
    NSString *formatedDate = [dateFormatter stringFromDate:datePicker.date];
    [fechaField setText:formatedDate];
}

-(void)dismissDatePicker {
    
    [UIView animateWithDuration:0.4 animations:^{
        [datePickerContainer setFrame:CGRectMake(datePickerContainer.frame.origin.x, self.view.frame.size.height, datePickerContainer.frame.size.width, datePickerContainer.frame.size.height)];
    } completion:^(BOOL finished) {
        [datePickerContainer setHidden:YES];
    }];
}


-(void)showDatePicker {
    [self.view endEditing:NO];
    CGSize datepickSize = datePickerContainer.frame.size;
    [datePickerContainer setFrame:CGRectMake(self.view.frame.size.width/2 - datepickSize.width/2, self.view.frame.size.height, datepickSize.width, datepickSize.height)];
    [datePickerContainer setHidden:NO];
    
    [UIView animateWithDuration:0.4 animations:^{
        [datePickerContainer setFrame:CGRectMake(datePickerContainer.frame.origin.x, self.view.frame.size.height - datePickerContainer.frame.size.height, datePickerContainer.frame.size.width, datePickerContainer.frame.size.height)];
    }];
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event{
    [self.view endEditing:YES];
    [super touchesBegan:touches withEvent:event];

}

- (IBAction)showDatePicker:(id)sender {
    
    [self showDatePicker];
}
@end
