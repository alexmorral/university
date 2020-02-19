//
//  Usuari.m
//  Whelp
//
//  Created by Alex Morral on 6/11/15.
//  Copyright © 2015 Alex Morral. All rights reserved.
//

#import "Usuari.h"

@implementation Usuari

-(id)init {
    self = [super init];
    
    return self;
}

//curl -H "Content-Type: application/json" -X POST -d b":1,"nom_usuari":"xyz","nom_complet":"xxx","email":"blabla@blabla","discapacitat":1,"descripcio":"descr"}' http://localhost:8000/api/v1/usuaris

//Override
-(NSMutableDictionary *)getModelDictionary {
    NSMutableDictionary *dict = [super getModelDictionary];
    
    [dict setValue:_id_fb forKey:@"id_fb"];
    [dict setValue:_nom_usuari forKey:@"nom_usuari"];
    [dict setValue:_nom_complet forKey:@"nom_complet"];
    [dict setValue:_email forKey:@"email"];
    [dict setValue:_discapacitat forKey:@"discapacitat"];
    [dict setValue:_descripcio forKey:@"descripcio"];
    return dict;
}

@end
