//
//  Comentari.m
//  Whelp
//
//  Created by 1137952 on 17/12/15.
//  Copyright (c) 2015 Alex Morral. All rights reserved.
//

#import "Comentari.h"

@implementation Comentari

-(id)init {
    self = [super init];
    
    return self;
}

//Override
-(NSMutableDictionary *)getModelDictionary {
    NSMutableDictionary *dict = [super getModelDictionary];
    
    [dict setValue:self.idActivity forKey:@"id_activitat"];
    [dict setValue:self.voluntari forKey:@"voluntari"];
    [dict setValue:self.discapacitat forKey:@"discapacitat"];
    [dict setValue:self.puntuacio forKey:@"puntuacio"];
    [dict setValue:self.textComentari forKey:@"comentari"];
    
    return dict;
    
}

@end
