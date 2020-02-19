//
//  Trip.h
//  Whelp
//
//  Created by 1137952 on 21/10/15.
//  Copyright (c) 2015 Alex Morral. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Trip : NSObject


@property (strong, nonatomic) NSString *title;
@property (strong, nonatomic) NSString *descript;
@property (strong, nonatomic) NSString *beginning;
@property (nonatomic, strong) NSDictionary *tripCoord;
@property (strong, nonatomic) NSString *destination;
@property (strong, nonatomic) NSString *comment;
@property (strong, nonatomic) NSNumber *score;
@property (strong, nonatomic) NSString *date;
@property (strong,nonatomic) NSString *author;
@property (strong,nonatomic) NSString *whelper;
@property BOOL inProcess;
@property BOOL completed;

-(NSString*) getTitle;

-(NSString*) getDescript;

-(NSString*) getBeginning;

-(NSString*) getDestination;

-(NSString*) getComment;

-(NSNumber*) getScore;

-(NSString*) getDate;

-(NSString*) getAuthor;

-(NSString*) getWhelper;

-(BOOL) isInProcess;

-(BOOL) isCompleted;

@end
