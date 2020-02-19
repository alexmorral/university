//
//  MapViewController.m
//  Whelp
//
//  Created by Alex Morral on 21/9/15.
//  Copyright © 2015 Alex Morral. All rights reserved.
//

#import "MapViewController.h"
#import "LocationHelper.h"
#import "Usuari.h"
#import "Trip.h"
#import "NewTripViewController.h"

@interface MapViewController() {
    BOOL editingState;
    BOOL origenSelected;
    BOOL destinoSelected;
}
@property(nonatomic, strong) MapAnotationHelper *origenAnnotation;
@property(nonatomic, strong) MapAnotationHelper *destinoAnnotation;

@property(nonatomic, strong) NSMutableArray *allAnnotations;
@property(nonatomic, strong) CLLocation *myLocation;

@end

@implementation MapViewController

@synthesize mapInfoVc;

-(void)viewDidLoad {
    [super viewDidLoad];
    
    [self initTabbar];
    
    if (appDelegate.currentUser.discapacitat.boolValue == YES){
        [self initAsDiscapacitado];
    }
    
    [self getAllActivitiesFromBackOffice];
    
    
//    [self initFakeData];
    
    
    
    [TSMessage setDefaultViewController:self];
    [TSMessage setDelegate:self];
    
    
    [LocationHelper getLocation:^(CLLocation *currentLocation) {
        _myLocation = currentLocation;
    } error:^(NSString *err) {
        [SVProgressHUD showWithStatus:err];
    }];
    
    [self initBooleans];
    
    
    
//    [self prueba];
//    [self prueba2];
//    [self getAnnotationMarkFromInformation:nil];
}

-(void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(receiveNotification:) name:@"mapInfoDismissed" object:nil];
    
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(receiveNotification:) name:@"createTrip" object:nil];
    
    [self.view layoutSubviews];
    mapInfoVc = [[MapInfoViewController alloc] initWithNibName:@"MapInfoViewController" bundle:nil];
    CGSize mapInfoSize = mapInfoVc.view.frame.size;
    [mapInfoVc.view setFrame:CGRectMake(self.view.frame.size.width/2 - mapInfoSize.width/2, self.view.frame.size.height, self.view.frame.size.width, mapInfoSize.height)];
    [mapInfoVc.view setHidden:NO];
    [self.view addSubview:mapInfoVc.view];
}

-(void)viewWillDisappear:(BOOL)animated {
    [self dismissInfoView:nil];
    [[NSNotificationCenter defaultCenter] removeObserver:self name:@"mapInfoDismissed" object:nil];
}

-(void)viewDidAppear:(BOOL)animated {
    [super viewDidAppear:animated];
//    [self zoomMapViewToFitAnnotations];
    
    [self getAllActivitiesFromBackOffice];
    
}


-(void)dismissInfoView:(NSNotification *)notification {
    NSDictionary *userInfo = notification.userInfo;
    
    
    
    [UIView animateWithDuration:0.4 animations:^{
        [mapInfoVc.view setFrame:CGRectMake(mapInfoVc.view.frame.origin.x, self.view.frame.size.height, mapInfoVc.view.frame.size.width, mapInfoVc.view.frame.size.height)];
    } completion:^(BOOL finished) {
        [mapInfoVc.view setHidden:YES];
        for (MapAnotationHelper *annotationView in self.mapView.selectedAnnotations) {
            [self.mapView deselectAnnotation:annotationView animated:YES];
        }
        if ([[userInfo valueForKey:@"unido"] isEqualToNumber:[NSNumber numberWithBool:YES]]) {
            [self getAllActivitiesFromBackOffice];
        }
    }];
    
    [self zoomMapViewToFitAnnotations];
}

- (void) receiveNotification:(NSNotification *) notification {
    if ([[notification name] isEqualToString:@"mapInfoDismissed"]) {
//        NSDictionary *userInfo = notification.userInfo;
//        NSString *something = [userInfo objectForKey:@"hello"];
//        [mapInfoVc.view setFrame:CGRectMake(mapInfoVc.view.frame.origin.x, self.view.frame.size.height - mapInfoVc.view.frame.size.height, mapInfoVc.view.frame.size.width, mapInfoVc.view.frame.size.height)];
        [self dismissInfoView:notification];
        
    }
    else if ([[notification name] isEqualToString:@"createTrip"]) {
        
        NSDictionary *userInfo = notification.userInfo;
        
        NSMutableDictionary *datos = [[NSMutableDictionary alloc] init];
        
        [datos setValue:@"Ir de compra" forKey:@"title"];
        [datos setValue:@"Me gustaría ir de compras al mercadona" forKey:@"description"];
        [datos setValue:@"c/ Falsa 123, Barcelona" forKey:@"origen"];
        [datos setValue:@"Mercadona, c/ Falsa 124, Barcelona" forKey:@"destino"];
        [datos setValue:@"22/10/2015 12:00" forKey:@"date"];
        [datos setValue:@"Ruedines" forKey:@"author"];
        
        Trip *trip = [userInfo objectForKey:@"newTrip"];
        
        CLLocationCoordinate2D coord = CLLocationCoordinate2DMake([[trip.tripCoord objectForKey:@"origen_lat"] floatValue], [[trip.tripCoord objectForKey:@"origen_lon"] floatValue]);
        
        MapAnotationHelper *origenTrip = [[MapAnotationHelper alloc] initWithName:@"Nombre1"
                                                                           address:@"address1"
                                                                              info:datos
                                                                        coordinate:coord];
        [_mapView addAnnotation:origenTrip];
    }
}

#pragma mark - Init


-(void)getAllActivitiesFromBackOffice {
    [SVProgressHUD showWithStatus:@"Cargando"];
    [Utils getAllActivitatsWithSuccessBlock:^(NSDictionary *result) {
        [SVProgressHUD dismiss];
//        NSLog(@"%@", result);
        
        NSArray *activities = [result objectForKey:@"activitats"];
        
        if ([activities count] > 0) {
            [self createAnnotationsFromActivities:activities];
            [self showAnnotationsInMapview];
            [self zoomMapViewToFitAnnotations];
        }
        else {
            [self performSelector:@selector(zoomToFitUser) withObject:nil afterDelay:1.0f];
        }
        
    } error:^(NSString *err) {
        [SVProgressHUD showErrorWithStatus:err];
    }];
}



-(void)createAnnotationsFromActivities:(NSArray *)activities {
    self.allAnnotations = [[NSMutableArray alloc] init];
    for (NSDictionary *activity in activities) {
        
        //Si soy voluntario no quiero ver las annotations que ya están asignadas
        if (([activity valueForKey:@"voluntari"] == nil || [[activity valueForKey:@"voluntari"] isKindOfClass:[NSNull class]]) || appDelegate.currentUser.discapacitat) {
            MapAnotationHelper *ann = [[MapAnotationHelper alloc] initWithName:@"Activity" address:@"Address" info:activity coordinate:CLLocationCoordinate2DMake([[activity objectForKey:@"origen_latitud"] floatValue], [[activity objectForKey:@"origen_longitud"] floatValue])];
            
            [self.allAnnotations addObject:ann];
        }
    }
    
    
    
}

-(void) initTabbar {
    NSArray *tabArray = self.tabBarController.tabBar.items;
    UIImage *mapIcon = [UIImage imageNamed:@"tabIconMap"];
    UIImage *mapIconSel = [UIImage imageNamed:@"tabIconMapSel"];
    
    UIImage *completadosIcon = [UIImage imageNamed:@"tabIconList"];
    UIImage *completadosIconSel = [UIImage imageNamed:@"tabIconListSel"];
    
    UIImage *profileIcon = [UIImage imageNamed:@"tabIconProfile"];
    UIImage *profileIconSel = [UIImage imageNamed:@"tabIconProfileSel"];
    
    UITabBarItem *map = [tabArray objectAtIndex:0];
    UITabBarItem *completados = [tabArray objectAtIndex:1];
    UITabBarItem *profile = [tabArray objectAtIndex:2];
    
    int offset = 5;
    UIEdgeInsets imageInset = UIEdgeInsetsMake(offset, 0, -offset, 0);
    
    [map setImage:[mapIcon imageWithRenderingMode:UIImageRenderingModeAlwaysOriginal]];
    [map setSelectedImage:[mapIconSel imageWithRenderingMode:UIImageRenderingModeAlwaysOriginal]];
    [map setImageInsets:imageInset];
    [map setTitle:@""];
    
    [completados setImage:[completadosIcon imageWithRenderingMode:UIImageRenderingModeAlwaysOriginal]];
    [completados setSelectedImage:[completadosIconSel imageWithRenderingMode:UIImageRenderingModeAlwaysOriginal]];
    [completados setImageInsets:imageInset];
    [completados setTitle:@""];
    
    [profile setImage:[profileIcon imageWithRenderingMode:UIImageRenderingModeAlwaysOriginal]];
    [profile setSelectedImage:[profileIconSel imageWithRenderingMode:UIImageRenderingModeAlwaysOriginal]];
    [profile setImageInsets:imageInset];
    [profile setTitle:@""];

    
    [self.tabBarController.tabBar setBackgroundColor:[UIColor whiteColor]];
    
}

-(void)initAsDiscapacitado {
//    UIBarButtonItem *plusBtn = [[UIBarButtonItem alloc] initWithTitle:@"+" style:UIBarButtonSystemItemAdd target:self action:@selector(newTrip)];
    UIBarButtonItem *plusBtn = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemAdd target:self action:@selector(newTrip)];
    self.navigationItem.rightBarButtonItem = plusBtn;
    
    UILongPressGestureRecognizer *addAnnotation = [[UILongPressGestureRecognizer alloc]
                                          initWithTarget:self action:@selector(createAnnotation:)];
    addAnnotation.minimumPressDuration = 2.0;
    [self.mapView addGestureRecognizer:addAnnotation];
}


-(void)removeAnnotationsFromMapView {
    for (MapAnotationHelper *ann in [_mapView annotations]) {
        [_mapView removeAnnotation:ann];
    }
}

-(void)showAnnotationsInMapview {
    for (MapAnotationHelper *ann in [_mapView annotations]) {
        [_mapView removeAnnotation:ann];
    }
    for (MapAnotationHelper *ann in _allAnnotations) {
        [_mapView addAnnotation:ann];
    }
}

-(void)initBooleans {
    editingState = NO;
    origenSelected = NO;
    destinoSelected = NO;
}

-(void)newTrip {
    //[self performSegueWithIdentifier:@"newtrip" sender:self];
    editingState = YES;
    
    [self removeAnnotationsFromMapView];
    
    [self showMessageNotificationWithTitle:@"Notificación" andSubtitle:@"Mantenga seleccionado en el origen"];
}

-(void)hideAllAnnotations:(BOOL)val {
    NSArray *annotations = [_mapView annotations];
    for (MapAnotationHelper *ann in annotations) {
        [[_mapView viewForAnnotation:ann] setHidden:val];
    }
}


- (void)createAnnotation:(UIGestureRecognizer *)gestureRecognizer
{
    if (gestureRecognizer.state != UIGestureRecognizerStateBegan)
        return;
    if (editingState) {
        if (origenSelected == NO) {
            origenSelected = YES;
            
            [self createAnnotationWithType:@"origen" andGesture:gestureRecognizer];
            
            [self showMessageNotificationWithTitle:@"Notificación" andSubtitle:@"Mantenga seleccionado en el final"];
        } else {
            if (destinoSelected == NO) {
                destinoSelected = YES;
                
                [self createAnnotationWithType:@"destino" andGesture:gestureRecognizer];
                NSLog(@"finish");
                [self finishCreateAnnotation];
                
            }
        }
    }
}

-(void)createAnnotationWithType:(NSString *)type andGesture:(UIGestureRecognizer *)gestureRecognizer {
    CGPoint touchPoint = [gestureRecognizer locationInView:self.mapView];
    CLLocationCoordinate2D touchMapCoordinate =
    [self.mapView convertPoint:touchPoint toCoordinateFromView:self.mapView];
    
    MapAnotationHelper *newAnnotation = [[MapAnotationHelper alloc] initWithName:type
                                                                         address:@"address"
                                                                            info:nil
                                                                      coordinate:touchMapCoordinate];
    if ([type isEqualToString:@"origen"]) _origenAnnotation = newAnnotation;
    else _destinoAnnotation = newAnnotation;
    
    [self.mapView addAnnotation:newAnnotation];
}

-(void)showMessageNotificationWithTitle:(NSString *)title andSubtitle:(NSString *)subtitle {
    [TSMessage showNotificationWithTitle:title
                                subtitle:subtitle
                                    type:TSMessageNotificationTypeMessage];
}

-(void) finishCreateAnnotation {
    
    //origenAnnotation + destinoAnnotation
    
    
    [self performSelector:@selector(newTripSegue) withObject:nil afterDelay:1.0];
}

-(void)newTripSegue {
    [self performSegueWithIdentifier:@"newtrip" sender:self];
    
    [self performSelector:@selector(resetView) withObject:nil afterDelay:1.0];
}

-(void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    if([segue.identifier isEqualToString:@"newtrip"]) {
        NewTripViewController *ntVC = segue.destinationViewController;
        NSMutableDictionary *tripCoordinates = [[NSMutableDictionary alloc] init];
        
        CLLocationCoordinate2D origen = _origenAnnotation.coordinate;
        CLLocationCoordinate2D destino = _destinoAnnotation.coordinate;
        
        [tripCoordinates setValue:[NSNumber numberWithFloat:origen.latitude] forKey:@"origen_lat"];
        [tripCoordinates setValue:[NSNumber numberWithFloat:origen.longitude] forKey:@"origen_lon"];
        [tripCoordinates setValue:[NSNumber numberWithFloat:destino.latitude] forKey:@"destino_lat"];
        [tripCoordinates setValue:[NSNumber numberWithFloat:destino.longitude] forKey:@"destino_lon"];
        
        ntVC.tripCoordinates = tripCoordinates;
    }
}

-(void)resetView {
    [self initBooleans];
    [self hideAllAnnotations:NO];
    
    [self showAnnotationsInMapview];
}



#pragma mark - MapKit

- (MKAnnotationView *)mapView:(MKMapView *)mapView viewForAnnotation:(id <MKAnnotation>)annotation {
    static NSString *identifier = @"Annotation";
    if ([annotation isKindOfClass:[MapAnotationHelper class]]) {
        MKAnnotationView *pinView = (MKAnnotationView *) [mapView dequeueReusableAnnotationViewWithIdentifier:identifier];
        if (!pinView) {
            pinView = [[MKAnnotationView alloc] initWithAnnotation:annotation reuseIdentifier:identifier] ;
            pinView.canShowCallout = NO;
            [pinView setImage:[UIImage imageNamed:@"annotation_user"]];
            UIButton *btnInfo = [UIButton buttonWithType:UIButtonTypeDetailDisclosure];
            pinView.rightCalloutAccessoryView = btnInfo;
            pinView.centerOffset = CGPointMake(0, -pinView.frame.size.height / 2);
        }
        else {
            pinView.annotation = annotation;
        }

        
        return pinView;
    }
    
    return nil;
}

-(void) zoomMapViewToFitUserAndAnnotation:(MKMapView *)mapView withUserLocation:(CLLocationCoordinate2D)userLocation andAnnotation:(id <MKAnnotation>)mkAnnotation{
    // You have coordinates
    
    if (mkAnnotation != nil) {
        CLLocationCoordinate2D annotation = CLLocationCoordinate2DMake([mkAnnotation coordinate].latitude, [mkAnnotation coordinate].longitude);
        
        
        CLLocationCoordinate2D user = userLocation;
        
        // Make map points
        MKMapPoint userPoint = MKMapPointForCoordinate(user);
        MKMapPoint annotationPoint = MKMapPointForCoordinate(annotation);
        // Make map rects with 0 size
        MKMapRect userRect = MKMapRectMake(userPoint.x, userPoint.y, 0, 0);
        MKMapRect annotationRect = MKMapRectMake(annotationPoint.x, annotationPoint.y, 0, 0);
        // Make union of those two rects
        MKMapRect unionRect = MKMapRectUnion(userRect, annotationRect);
        // You have the smallest possible rect containing both locations
        MKMapRect unionRectThatFits = [mapView mapRectThatFits:unionRect edgePadding:UIEdgeInsetsMake(30, 30, 30, 30)];
        [mapView setVisibleMapRect:unionRectThatFits animated:YES];
    } else {
        CLLocationCoordinate2D user = userLocation;
        MKMapPoint userPoint = MKMapPointForCoordinate(user);
        float margin = 5000;
        MKMapRect userRect = MKMapRectMake(userPoint.x - margin/2, userPoint.y - margin/2, margin, margin);
        MKMapRect unionRectThatFits = [mapView mapRectThatFits:userRect edgePadding:UIEdgeInsetsMake(30, 30, 30, 30)];
        [mapView setVisibleMapRect:unionRectThatFits animated:YES];
    }
}

-(void) zoomMapViewToAnnotation:(MKMapView *)mapView withAnnotation:(id <MKAnnotation>)annotation {
    MKMapRect zoomRect = MKMapRectNull;
    MKMapPoint annotationPoint = MKMapPointForCoordinate(annotation.coordinate);
    float margin = 5000;
    MKMapRect pointRect = MKMapRectMake(annotationPoint.x - margin/2, annotationPoint.y - margin/4, margin, margin);
    zoomRect = MKMapRectUnion(zoomRect, pointRect);
    [mapView setVisibleMapRect:zoomRect animated:YES];
}

-(void)zoomMapViewToFitAnnotations {
    MKMapRect zoomRect = MKMapRectNull;
    float margin = 5000;
    for (id <MKAnnotation> annotation in _mapView.annotations)
    {
        MKMapPoint annotationPoint = MKMapPointForCoordinate(annotation.coordinate);
        MKMapRect pointRect = MKMapRectMake(annotationPoint.x - margin/2, annotationPoint.y - margin/2, margin, margin);
        zoomRect = MKMapRectUnion(zoomRect, pointRect);
    }
    [_mapView setVisibleMapRect:zoomRect animated:YES];
}

-(void)zoomToFitUser {
    [self zoomMapViewToFitUserAndAnnotation:self.mapView withUserLocation:_myLocation.coordinate andAnnotation:nil];
}


- (void)mapView:(MKMapView *)mapView annotationView:(MKAnnotationView *)view calloutAccessoryControlTapped:(UIControl *)control {
    //NSLog(@"Accessory Info Tapped");
}


- (void)mapView:(MKMapView *)mapView didSelectAnnotationView:(MKAnnotationView *)view {
    //NSLog(@"Annotation Tapped");
    
    if (!editingState) {
        MapAnotationHelper *selectedAnnotation = [mapView.selectedAnnotations lastObject];
        
        mapInfoVc.activity = selectedAnnotation.info;
        //CGSize mapInfoSize = mapInfoVc.view.frame.size;
        CGSize mapInfoSize = CGSizeMake(self.view.frame.size.width, 300);
        [mapInfoVc.view setFrame:CGRectMake(self.view.frame.size.width/2 - mapInfoSize.width/2, self.view.frame.size.height, self.view.frame.size.width, mapInfoSize.height)];
        [mapInfoVc.view setHidden:NO];
        [mapInfoVc reloadInfo];
        
        [self zoomMapViewToAnnotation:_mapView withAnnotation:selectedAnnotation];
        
        [UIView animateWithDuration:0.4 animations:^{
            [mapInfoVc.view setFrame:CGRectMake(mapInfoVc.view.frame.origin.x, self.view.frame.size.height - mapInfoVc.view.frame.size.height, mapInfoVc.view.frame.size.width, mapInfoVc.view.frame.size.height)];
        }];
    }
    
}

- (void)mapView:(MKMapView *)mapView didAddAnnotationViews:(NSArray<MKAnnotation> *)views {
    //NSLog(@"Added");
}



-(void) getAnnotationInformationFromCoordinates:(CLLocationCoordinate2D)coord {
    CLGeocoder *ceo = [[CLGeocoder alloc]init];
    CLLocation *loc = [[CLLocation alloc]initWithLatitude:coord.latitude longitude:coord.longitude]; //insert your coordinates
    
    [ceo reverseGeocodeLocation:loc
              completionHandler:^(NSArray *placemarks, NSError *error) {
                  CLPlacemark *placemark = [placemarks objectAtIndex:0];
                  NSLog(@"placemark %@",placemark);
                  //String to hold address
                  NSString *locatedAt = [[placemark.addressDictionary valueForKey:@"FormattedAddressLines"] componentsJoinedByString:@", "];
                  NSLog(@"addressDictionary %@", placemark.addressDictionary);
                  
                  NSLog(@"placemark %@",placemark.region);
                  NSLog(@"placemark %@",placemark.country);  // Give Country Name
                  NSLog(@"placemark %@",placemark.locality); // Extract the city name
                  NSLog(@"location %@",placemark.name);
                  NSLog(@"location %@",placemark.ocean);
                  NSLog(@"location %@",placemark.postalCode);
                  NSLog(@"location %@",placemark.subLocality);
                  
                  NSLog(@"location %@",placemark.location);
                  //Print the location to console
                  NSLog(@"I am currently at %@",locatedAt);
              }
     
     ];
}

-(void)getAnnotationMarkFromInformation:(NSString *)address {
    NSString *location = @"Can Camp 39, 08480 L'ametlla del vallés, Barcelona, Spain";
    CLGeocoder *geocoder = [[CLGeocoder alloc] init];
    [geocoder geocodeAddressString:location
                 completionHandler:^(NSArray* placemarks, NSError* error){
                     if (placemarks && placemarks.count > 0) {
                         CLPlacemark *topResult = [placemarks objectAtIndex:0];
                         
                         
                         MKPlacemark *placemark = [[MKPlacemark alloc] initWithPlacemark:topResult];
                         

                         MKCoordinateRegion region = self.mapView.region;
                         region.center = placemark.region.center;
                         region.span.longitudeDelta /= 8.0;
                         region.span.latitudeDelta /= 8.0;
                         
                         MapAnotationHelper *ann = [[MapAnotationHelper alloc] initWithName:@"name" address:@"address" info:nil coordinate:CLLocationCoordinate2DMake(placemark.coordinate.latitude, placemark.coordinate.longitude)];
                         
//                         [self.mapView setRegion:region animated:YES];
                         [self.mapView addAnnotation:ann];
                     }
                 }
     ];
}
@end













