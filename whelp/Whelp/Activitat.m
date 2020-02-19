//
//  Activitat.m
//  Whelp
//
//  Created by Alex Morral on 6/11/15.
//  Copyright © 2015 Alex Morral. All rights reserved.
//

#import "Activitat.h"

@implementation Activitat

-(id)init {
    self = [super init];
    
    return self;
}

//Override
-(NSMutableDictionary *)getModelDictionary {
    NSMutableDictionary *dict = [super getModelDictionary];
    
    [dict setValue:self.idActivity forKey:@"id"];
    [dict setValue:self.voluntari forKey:@"voluntari"];
    [dict setValue:self.discapacitat forKey:@"discapacitat"];
    [dict setValue:self.titol forKey:@"titol"];
    [dict setValue:self.data forKey:@"data"];
    [dict setValue:self.origen forKey:@"origen"];
    [dict setValue:self.origen_latitud forKey:@"origen_latitud"];
    [dict setValue:self.origen_longitud forKey:@"origen_longitud"];
    [dict setValue:self.desti forKey:@"desti"];
    [dict setValue:self.desti_latitud forKey:@"desti_latitud"];
    [dict setValue:self.desti_longitud forKey:@"desti_longitud"];
    [dict setValue:self.descripcio forKey:@"descripcio"];
    [dict setValue:self.completada forKey:@"completada"];
    [dict setValue:self.en_proces forKey:@"en_proces"];
    
    return dict;
    
}

@end
