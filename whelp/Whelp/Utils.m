//
//  Utils.m
//  Whelp
//
//  Created by Alex Morral on 3/11/15.
//  Copyright © 2015 Alex Morral. All rights reserved.
//

#import "Utils.h"

@implementation Utils


#pragma mark - Posts

+(void)postUser:(NSDictionary *)user andSuccessBlock:(void (^)(NSDictionary *result))succesBlock error:(void (^)(NSString* err))errorBlock {
    NSString *baseUrl = [urlApp stringByAppendingString:typeUser];
    
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:[NSURL URLWithString:baseUrl]
                                                           cachePolicy:NSURLRequestReloadIgnoringCacheData  timeoutInterval:60];
    
    [request setHTTPMethod:@"POST"];
    [request setValue: @"application/json" forHTTPHeaderField:@"Content-Type"];
    
    NSError *err = nil;
    NSData * jsonData = [NSJSONSerialization  dataWithJSONObject:user options:0 error:&err];
    NSString * body = [[NSString alloc] initWithData:jsonData   encoding:NSUTF8StringEncoding];
    [request setHTTPBody: [body dataUsingEncoding:NSUTF8StringEncoding]];
    
    AFHTTPRequestOperation *op = [[AFHTTPRequestOperation alloc] initWithRequest:request];
    op.responseSerializer = [AFJSONResponseSerializer serializer];
    
    [op setCompletionBlockWithSuccess:^(AFHTTPRequestOperation *operation, id responseObject) {
        NSDictionary *dataReceived  = (NSDictionary*)responseObject;
        succesBlock(dataReceived);
        [self getAllUsers];
    } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
        NSLog(@"%@", error.description);
        errorBlock(@"Ha habido un error, intentalo de nuevo más tarde");
    }];
    [op start];
}

+(void)updateUser:(Model *)user andSuccessBlock:(void (^)(NSDictionary *result))succesBlock error:(void (^)(NSString* err))errorBlock {
    
    NSDictionary *postData = [user getModelDictionary];
//    NSString *baseUrl = [urlApp stringByAppendingString:typeUser];
    NSString *baseUrl = [urlApp stringByAppendingString:[NSString stringWithFormat:@"%@/%@/%@", typeUser, appDelegate.currentUser.id_fb, appDelegate.currentUser.id_fb]];
    
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:[NSURL URLWithString:baseUrl]
                                                           cachePolicy:NSURLRequestReloadIgnoringCacheData  timeoutInterval:60];
    
    [request setHTTPMethod:@"PUT"];
    [request setValue: @"application/json" forHTTPHeaderField:@"Content-Type"];
    
    NSError *err = nil;
    NSData * jsonData = [NSJSONSerialization  dataWithJSONObject:postData options:0 error:&err];
    NSString * body = [[NSString alloc] initWithData:jsonData   encoding:NSUTF8StringEncoding];
    [request setHTTPBody: [body dataUsingEncoding:NSUTF8StringEncoding]];
    
    AFHTTPRequestOperation *op = [[AFHTTPRequestOperation alloc] initWithRequest:request];
    op.responseSerializer = [AFJSONResponseSerializer serializer];
    
    [op setCompletionBlockWithSuccess:^(AFHTTPRequestOperation *operation, id responseObject) {
        NSDictionary *dataReceived  = (NSDictionary*)responseObject;
        succesBlock(dataReceived);
    } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
        NSLog(@"%@", error.description);
        errorBlock(@"Ha habido un error, intentalo de nuevo más tarde");
    }];
    [op start];
}

+(void)postEnProces:(NSDictionary *)activitat andSuccessBlock:(void (^)(NSDictionary *result))succesBlock error:(void (^)(NSString* err))errorBlock {
    NSString *baseUrl = [urlApp stringByAppendingString:marcarEnProces];
    
    baseUrl = [baseUrl stringByAppendingString:[NSString stringWithFormat:@"%@",[activitat objectForKey:@"id"]]];
    
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:[NSURL URLWithString:baseUrl]
                                                           cachePolicy:NSURLRequestReloadIgnoringCacheData  timeoutInterval:60];
    
    [request setHTTPMethod:@"POST"];
    [request setValue: @"application/json" forHTTPHeaderField:@"Content-Type"];

    AFHTTPRequestOperation *op = [[AFHTTPRequestOperation alloc] initWithRequest:request];
    op.responseSerializer = [AFJSONResponseSerializer serializer];
    
    [op setCompletionBlockWithSuccess:^(AFHTTPRequestOperation *operation, id responseObject) {
        NSDictionary *dataReceived  = (NSDictionary*)responseObject;
        succesBlock(dataReceived);
        [self getAllUsers];
    } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
        NSLog(@"%@", error.description);
        errorBlock(@"Ha habido un error, intentalo de nuevo más tarde");
    }];
    [op start];
}

+(void)postCancelarActivitat:(NSDictionary *)activitat andSuccessBlock:(void (^)(NSDictionary *result))succesBlock error:(void (^)(NSString* err))errorBlock {
    NSString *baseUrl = [urlApp stringByAppendingString:cancelarActivitat];
    
    baseUrl = [baseUrl stringByAppendingString:[NSString stringWithFormat:@"%@",[activitat objectForKey:@"id"]]];
    
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:[NSURL URLWithString:baseUrl]
                                                           cachePolicy:NSURLRequestReloadIgnoringCacheData  timeoutInterval:60];
    
    [request setHTTPMethod:@"POST"];
    [request setValue: @"application/json" forHTTPHeaderField:@"Content-Type"];
    
    AFHTTPRequestOperation *op = [[AFHTTPRequestOperation alloc] initWithRequest:request];
    op.responseSerializer = [AFJSONResponseSerializer serializer];
    
    [op setCompletionBlockWithSuccess:^(AFHTTPRequestOperation *operation, id responseObject) {
        NSDictionary *dataReceived  = (NSDictionary*)responseObject;
        succesBlock(dataReceived);
        [self getAllUsers];
    } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
        NSLog(@"%@", error.description);
        errorBlock(@"Ha habido un error, intentalo de nuevo más tarde");
    }];
    [op start];
}


+(void)postEliminarActivitat:(NSDictionary *)activitat andSuccessBlock:(void (^)(NSDictionary *result))succesBlock error:(void (^)(NSString* err))errorBlock {
    NSString *baseUrl = [urlApp stringByAppendingString:typeActivity];
    
    baseUrl = [baseUrl stringByAppendingString:[NSString stringWithFormat:@"/%@",[activitat objectForKey:@"id"]]];
    
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:[NSURL URLWithString:baseUrl]
                                                           cachePolicy:NSURLRequestReloadIgnoringCacheData  timeoutInterval:60];
    
    [request setHTTPMethod:@"DELETE"];
    [request setValue: @"application/json" forHTTPHeaderField:@"Content-Type"];
    
    AFHTTPRequestOperation *op = [[AFHTTPRequestOperation alloc] initWithRequest:request];
    op.responseSerializer = [AFJSONResponseSerializer serializer];
    
    [op setCompletionBlockWithSuccess:^(AFHTTPRequestOperation *operation, id responseObject) {
        NSDictionary *dataReceived  = (NSDictionary*)responseObject;
        succesBlock(dataReceived);
        [self getAllUsers];
    } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
        NSLog(@"%@", error.description);
        errorBlock(@"Ha habido un error, intentalo de nuevo más tarde");
    }];
    [op start];
}

+(void)postFinalitzarActivitat:(NSDictionary *)activitat andSuccessBlock:(void (^)(NSDictionary *result))succesBlock error:(void (^)(NSString* err))errorBlock {
    NSString *baseUrl = [urlApp stringByAppendingString:marcarAcabada];
    
    baseUrl = [baseUrl stringByAppendingString:[NSString stringWithFormat:@"/%@",[activitat objectForKey:@"id"]]];
    
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:[NSURL URLWithString:baseUrl]
                                                           cachePolicy:NSURLRequestReloadIgnoringCacheData  timeoutInterval:60];
    
    [request setHTTPMethod:@"POST"];
    [request setValue: @"application/json" forHTTPHeaderField:@"Content-Type"];
    
    
    
    AFHTTPRequestOperation *op = [[AFHTTPRequestOperation alloc] initWithRequest:request];
    op.responseSerializer = [AFJSONResponseSerializer serializer];
    
    [op setCompletionBlockWithSuccess:^(AFHTTPRequestOperation *operation, id responseObject) {
        NSDictionary *dataReceived  = (NSDictionary*)responseObject;
        succesBlock(dataReceived);
        [self getAllUsers];
    } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
        NSLog(@"%@", error.description);
        errorBlock(@"Ha habido un error, intentalo de nuevo más tarde");
    }];
    [op start];
}

+(void)postUnirActivitat:(NSDictionary *)unio andSuccessBlock:(void (^)(NSDictionary *result))succesBlock error:(void (^)(NSString* err))errorBlock {
    NSString *baseUrl = [urlApp stringByAppendingString:unirActivitat];
    
//    baseUrl = [baseUrl stringByAppendingString:[NSString stringWithFormat:@"/%@",[activitat objectForKey:@"id"]]];
    
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:[NSURL URLWithString:baseUrl]
                                                           cachePolicy:NSURLRequestReloadIgnoringCacheData  timeoutInterval:60];
    
    [request setHTTPMethod:@"POST"];
    [request setValue: @"application/json" forHTTPHeaderField:@"Content-Type"];
    
    NSError *err = nil;
    NSData * jsonData = [NSJSONSerialization  dataWithJSONObject:unio options:0 error:&err];
    NSString * body = [[NSString alloc] initWithData:jsonData   encoding:NSUTF8StringEncoding];
    [request setHTTPBody: [body dataUsingEncoding:NSUTF8StringEncoding]];
    
    AFHTTPRequestOperation *op = [[AFHTTPRequestOperation alloc] initWithRequest:request];
    op.responseSerializer = [AFJSONResponseSerializer serializer];
    
    [op setCompletionBlockWithSuccess:^(AFHTTPRequestOperation *operation, id responseObject) {
        NSDictionary *dataReceived  = (NSDictionary*)responseObject;
        succesBlock(dataReceived);
        [self getAllUsers];
    } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
        NSLog(@"%@", error.description);
        errorBlock(@"Ha habido un error, intentalo de nuevo más tarde");
    }];
    [op start];
}

+(void)postReportarUser:(NSDictionary *)report andSuccessBlock:(void (^)(NSDictionary *result))succesBlock error:(void (^)(NSString* err))errorBlock {
    NSString *baseUrl = [urlApp stringByAppendingString:reportarUsuari];
    
    //    baseUrl = [baseUrl stringByAppendingString:[NSString stringWithFormat:@"/%@",[activitat objectForKey:@"id"]]];
    
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:[NSURL URLWithString:baseUrl]
                                                           cachePolicy:NSURLRequestReloadIgnoringCacheData  timeoutInterval:60];
    
    [request setHTTPMethod:@"POST"];
    [request setValue: @"application/json" forHTTPHeaderField:@"Content-Type"];
    
    NSError *err = nil;
    NSData * jsonData = [NSJSONSerialization  dataWithJSONObject:report options:0 error:&err];
    NSString * body = [[NSString alloc] initWithData:jsonData   encoding:NSUTF8StringEncoding];
    [request setHTTPBody: [body dataUsingEncoding:NSUTF8StringEncoding]];
    
    AFHTTPRequestOperation *op = [[AFHTTPRequestOperation alloc] initWithRequest:request];
    op.responseSerializer = [AFJSONResponseSerializer serializer];
    
    [op setCompletionBlockWithSuccess:^(AFHTTPRequestOperation *operation, id responseObject) {
        NSDictionary *dataReceived  = (NSDictionary*)responseObject;
        succesBlock(dataReceived);
        [self getAllUsers];
    } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
        NSLog(@"%@", error.description);
        errorBlock(@"Ha habido un error, intentalo de nuevo más tarde");
    }];
    [op start];
}




#pragma mark - Get Activitats

+(void)getAllActivitatsWithSuccessBlock:(void (^)(NSDictionary *result))succesBlock error:(void (^)(NSString* err))errorBlock {
    NSString *baseUrl = [urlApp stringByAppendingString:typeActivity];
    
    AFHTTPRequestOperationManager *manager = [AFHTTPRequestOperationManager manager];
    [manager GET:baseUrl parameters:nil success:^(AFHTTPRequestOperation *operation, id responseObject) {
        
        NSDictionary *returnObj = (NSDictionary *)responseObject;
        succesBlock(returnObj);
        
    } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
        NSLog(@"%@", error.description);
        errorBlock(@"Ha habido un error, intentalo de nuevo más tarde");
    }];
    
}

+(void)getPuntuacionsMillorsWithSuccessBlock:(void (^)(NSDictionary *result))succesBlock error:(void (^)(NSString* err))errorBlock {
    NSString *baseUrl = [urlApp stringByAppendingString:puntuacioMillor];
    
    AFHTTPRequestOperationManager *manager = [AFHTTPRequestOperationManager manager];
    [manager GET:baseUrl parameters:nil success:^(AFHTTPRequestOperation *operation, id responseObject) {
        
        NSDictionary *returnObj = (NSDictionary *)responseObject;
        succesBlock(returnObj);
        
    } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
        NSLog(@"%@", error.description);
        errorBlock(@"Ha habido un error, intentalo de nuevo más tarde");
    }];
    
}

+(void)getCommentsWithSuccessBlock:(void (^)(NSDictionary *result))succesBlock error:(void (^)(NSString* err))errorBlock {
    NSNumber *idUser = appDelegate.currentUser.id_fb;
    NSString *baseUrl = [urlApp stringByAppendingString:[NSString stringWithFormat:@"%@%@",comentarisWhelper, idUser]];
    NSLog(@"%@",baseUrl);
    
    AFHTTPRequestOperationManager *manager = [AFHTTPRequestOperationManager manager];
    [manager GET:baseUrl parameters:nil success:^(AFHTTPRequestOperation *operation, id responseObject) {
        
        NSDictionary *returnObj = (NSDictionary *)responseObject;
        succesBlock(returnObj);
        
    } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
        NSLog(@"%@", error.description);
        errorBlock(@"Ha habido un error, intentalo de nuevo más tarde");
    }];
    
}


+(void)getCompletedActivitatsWithSuccessBlock:(void (^)(NSDictionary *result))succesBlock error:(void (^)(NSString* err))errorBlock {
    NSNumber *idUser = appDelegate.currentUser.id_fb;
    NSString *baseUrl = nil;
    if (appDelegate.currentUser.discapacitat.boolValue == YES) {
        baseUrl = [urlApp stringByAppendingString:[NSString stringWithFormat:@"%@%@%@", typeActivity, completadasdiscapacitat,idUser]];
    }
    else{
        baseUrl = [urlApp stringByAppendingString:[NSString stringWithFormat:@"%@%@%@", typeActivity, completadasvoluntari,idUser]];
    }
    
    AFHTTPRequestOperationManager *manager = [AFHTTPRequestOperationManager manager];
    [manager GET:baseUrl parameters:nil success:^(AFHTTPRequestOperation *operation, id responseObject) {
        
        NSDictionary *returnObj = (NSDictionary *)responseObject;
        succesBlock(returnObj);
        
    } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
        NSLog(@"%@", error.description);
        errorBlock(@"Ha habido un error, intentalo de nuevo más tarde");
    }];
    
}

+(void)getInProcesActivitatsWithSuccessBlock:(void (^)(NSDictionary *result))succesBlock error:(void (^)(NSString* err))errorBlock {
    NSNumber *idUser = appDelegate.currentUser.id_fb;
    NSString *baseUrl = nil;
    if (appDelegate.currentUser.discapacitat.boolValue == YES) {
        baseUrl = [urlApp stringByAppendingString:[NSString stringWithFormat:@"%@%@%@", typeActivity, enprocesodiscapacitat,idUser]];
    }
    else{
        baseUrl = [urlApp stringByAppendingString:[NSString stringWithFormat:@"%@%@%@", typeActivity, enprocesovoluntari,idUser]];
    }
    
    AFHTTPRequestOperationManager *manager = [AFHTTPRequestOperationManager manager];
    [manager GET:baseUrl parameters:nil success:^(AFHTTPRequestOperation *operation, id responseObject) {
        
        NSDictionary *returnObj = (NSDictionary *)responseObject;
        succesBlock(returnObj);
        
    } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
        NSLog(@"%@", error.description);
        errorBlock(@"Ha habido un error, intentalo de nuevo más tarde");
    }];
    
}

+(void)getNoCompletedActivitatsWithSuccessBlock:(void (^)(NSDictionary *result))succesBlock error:(void (^)(NSString* err))errorBlock {
    NSNumber *idUser = appDelegate.currentUser.id_fb;
    NSString *baseUrl = nil;
    if (appDelegate.currentUser.discapacitat.boolValue == YES) {
        baseUrl = [urlApp stringByAppendingString:[NSString stringWithFormat:@"%@%@%@", typeActivity, nocompletadasdiscapacitat,idUser]];
    }
    else{
        baseUrl = [urlApp stringByAppendingString:[NSString stringWithFormat:@"%@%@%@", typeActivity, nocompletadasvoluntari,idUser]];
    }
    
    AFHTTPRequestOperationManager *manager = [AFHTTPRequestOperationManager manager];
    [manager GET:baseUrl parameters:nil success:^(AFHTTPRequestOperation *operation, id responseObject) {
        
        NSDictionary *returnObj = (NSDictionary *)responseObject;
        succesBlock(returnObj);
        
    } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
        NSLog(@"%@", error.description);
        errorBlock(@"Ha habido un error, intentalo de nuevo más tarde");
    }];
}

#pragma mark - Custom Functions



+(void)postData:(Model *)model withType:(NSString *)type andSuccessBlock:(void (^)(NSDictionary *result))succesBlock error:(void (^)(NSString* err))errorBlock {
    NSDictionary *postData = [model getModelDictionary];
    
    NSString *baseUrl = [urlApp stringByAppendingString:type];
    
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:[NSURL URLWithString:baseUrl]
                                                           cachePolicy:NSURLRequestReloadIgnoringCacheData  timeoutInterval:60];
    
    [request setHTTPMethod:@"POST"];
    [request setValue: @"application/json" forHTTPHeaderField:@"Content-Type"];
    
    NSError *err = nil;
    NSData * jsonData = [NSJSONSerialization  dataWithJSONObject:postData options:0 error:&err];
    NSString * body = [[NSString alloc] initWithData:jsonData   encoding:NSUTF8StringEncoding];
    [request setHTTPBody: [body dataUsingEncoding:NSUTF8StringEncoding]];
    
    AFHTTPRequestOperation *op = [[AFHTTPRequestOperation alloc] initWithRequest:request];
    op.responseSerializer = [AFJSONResponseSerializer serializer];
    
    [op setCompletionBlockWithSuccess:^(AFHTTPRequestOperation *operation, id responseObject) {
        NSDictionary *dataReceived  = (NSDictionary*)responseObject;
        succesBlock(dataReceived);
    } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
        NSLog(@"%@", error.description);
        errorBlock(@"Ha habido un error, intentalo de nuevo más tarde");
    }];
    [op start];
    
}



#pragma mark - Base Functions
+(void) postConnectionTo:(NSString *)type withData:(NSDictionary *)dict {
    
    [SVProgressHUD showWithStatus:@"Cargando"];
    
    NSString *baseUrl = [urlApp stringByAppendingString:type];
    
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:[NSURL URLWithString:baseUrl]
                                                           cachePolicy:NSURLRequestReloadIgnoringCacheData  timeoutInterval:60];
    
    [request setHTTPMethod:@"POST"];
    [request setValue: @"application/json" forHTTPHeaderField:@"Content-Type"];
    
    NSError *err = nil;
    NSData * jsonData = [NSJSONSerialization  dataWithJSONObject:dict options:0 error:&err];
    NSString * body = [[NSString alloc] initWithData:jsonData   encoding:NSUTF8StringEncoding];
    [request setHTTPBody: [body dataUsingEncoding:NSUTF8StringEncoding]];
    
    AFHTTPRequestOperation *op = [[AFHTTPRequestOperation alloc] initWithRequest:request];
    op.responseSerializer = [AFJSONResponseSerializer serializer];
    
    [op setCompletionBlockWithSuccess:^(AFHTTPRequestOperation *operation, id responseObject) {
        NSDictionary *dataReceived  = (NSDictionary*)responseObject;
//        NSLog(@"Data received: %@", dataReceived);
        [SVProgressHUD showSuccessWithStatus:@"Completado"];
    } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
        NSLog(@"Error: %@", error);
        NSLog(@"%@", error.description);
        [SVProgressHUD showErrorWithStatus:@"Ha habido un error"];
    }];
    [op start];
    
}

+(void)getallUsersWithSuccessBlock:(void (^)(NSDictionary *result))succesBlock error:(void (^)(NSString* err))errorBlock {
    NSString *baseUrl = [urlApp stringByAppendingString:typeUser];
    baseUrl = [baseUrl stringByAppendingString:[NSString stringWithFormat:@"/%@", appDelegate.currentUser.id_fb]];
    AFHTTPRequestOperationManager *manager = [AFHTTPRequestOperationManager manager];
    [manager GET:baseUrl parameters:nil success:^(AFHTTPRequestOperation *operation, id responseObject) {
        //        NSLog(@"JSON: %@", responseObject);
        
        NSMutableDictionary *allUsers = [[NSMutableDictionary alloc] init];
        for (NSDictionary *user in [responseObject objectForKey:@"data"]) {
            [allUsers setValue:user forKey:[user objectForKey:@"id_fb"]];
        }
        appDelegate.allUsers = allUsers;
    } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
        NSLog(@"Error: %@", error);
    }];
}

+(void)getAllUsers {
    NSString *baseUrl = [urlApp stringByAppendingString:typeUser];
    baseUrl = [baseUrl stringByAppendingString:[NSString stringWithFormat:@"/%@", appDelegate.currentUser.id_fb]];
    AFHTTPRequestOperationManager *manager = [AFHTTPRequestOperationManager manager];
    [manager GET:baseUrl parameters:nil success:^(AFHTTPRequestOperation *operation, id responseObject) {
//        NSLog(@"JSON: %@", responseObject);
        
        NSMutableDictionary *allUsers = [[NSMutableDictionary alloc] init];
        for (NSDictionary *user in [responseObject objectForKey:@"data"]) {
            [allUsers setValue:user forKey:[user objectForKey:@"id_fb"]];
        }
        appDelegate.allUsers = allUsers;
    } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
        NSLog(@"Error: %@", error);
    }];
}


@end