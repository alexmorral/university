//
//  Utils.h
//  Whelp
//
//  Created by Alex Morral on 3/11/15.
//  Copyright © 2015 Alex Morral. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "AFNetworking.h"
#import "Model.h"
#import "SVProgressHUD.h"

@interface Utils : NSObject



//POST
+(void)postUser:(NSDictionary *)user andSuccessBlock:(void (^)(NSDictionary *result))succesBlock error:(void (^)(NSString* err))errorBlock;

+(void)updateUser:(Model *)user andSuccessBlock:(void (^)(NSDictionary *result))succesBlock error:(void (^)(NSString* err))errorBlock;

+(void)postData:(Model *)model withType:(NSString *)type andSuccessBlock:(void (^)(NSDictionary *result))succesBlock error:(void (^)(NSString* err))errorBlock;

+(void)postEnProces:(NSDictionary *)activitat andSuccessBlock:(void (^)(NSDictionary *result))succesBlock error:(void (^)(NSString* err))errorBlock;

+(void)postCancelarActivitat:(NSDictionary *)activitat andSuccessBlock:(void (^)(NSDictionary *result))succesBlock error:(void (^)(NSString* err))errorBlock;

+(void)postEliminarActivitat:(NSDictionary *)activitat andSuccessBlock:(void (^)(NSDictionary *result))succesBlock error:(void (^)(NSString* err))errorBlock;

+(void)postFinalitzarActivitat:(NSDictionary *)activitat andSuccessBlock:(void (^)(NSDictionary *result))succesBlock error:(void (^)(NSString* err))errorBlock;

+(void)postUnirActivitat:(NSDictionary *)unio andSuccessBlock:(void (^)(NSDictionary *result))succesBlock error:(void (^)(NSString* err))errorBlock;

+(void)postReportarUser:(NSDictionary *)report andSuccessBlock:(void (^)(NSDictionary *result))succesBlock error:(void (^)(NSString* err))errorBlock;
//GET
+(void)getAllActivitatsWithSuccessBlock:(void (^)(NSDictionary *result))succesBlock error:(void (^)(NSString* err))errorBlock;
+(void)getCompletedActivitatsWithSuccessBlock:(void (^)(NSDictionary *result))succesBlock error:(void (^)(NSString* err))errorBlock;
+(void)getInProcesActivitatsWithSuccessBlock:(void (^)(NSDictionary *result))succesBlock error:(void (^)(NSString* err))errorBlock;
+(void)getNoCompletedActivitatsWithSuccessBlock:(void (^)(NSDictionary *result))succesBlock error:(void (^)(NSString* err))errorBlock;

+(void)getPuntuacionsMillorsWithSuccessBlock:(void (^)(NSDictionary *result))succesBlock error:(void (^)(NSString* err))errorBlock;
+(void)getallUsersWithSuccessBlock:(void (^)(NSDictionary *result))succesBlock error:(void (^)(NSString* err))errorBlock;

+(void)getCommentsWithSuccessBlock:(void (^)(NSDictionary *result))succesBlock error:(void (^)(NSString* err))errorBlock;

@end

