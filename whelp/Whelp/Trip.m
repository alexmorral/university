//
//  Trip.m
//  Whelp
//
//  Created by 1137952 on 21/10/15.
//  Copyright (c) 2015 Alex Morral. All rights reserved.
//

#import "Trip.h"

@implementation Trip

//-(void) setTitle:(NSString *)title{
//    self.title = title;
//}
//
//-(void) setDescript:(NSString *)descript{
//    self.descript = descript;
//}
//
//-(void) setBeginning:(NSString *)beginning{
//    self.beginning = beginning;
//}
//
//-(void) setDestination:(NSString *)destination{
//    self.destination = destination;
//}
//
//-(void) setComment:(NSString *)comment{
//    self.comment = comment;
//}
//
//-(void) setScore:(NSNumber *)score{
//    self.score = score;
//}
//
//-(void) setDate:(NSString *)date{
//    self.date = date;
//}
//
//-(void) setAuthor:(NSString *)author{
//    self.author = author;
//}
//
//-(void) setWhelper:(NSString *)whelper{
//    self.whelper = whelper;
//}

-(NSString*) getTitle{
    return self.title;
}

-(NSString*) getDescript{
    return self.descript;
}

-(NSString*) getBeginning{
    return self.beginning;
}

-(NSString*) getDestination{
    return self.destination;
}

-(NSString*) getComment{
    return self.comment;
}

-(NSNumber*) getScore{
    return self.score;
}

-(NSString*) getDate{
    return self.date;
}

-(NSString*) getAuthor{
    return self.author;
}

-(NSString*) getWhelper{
    return self.whelper;
}

-(BOOL) isInProcess{
    return self.inProcess;
}

-(BOOL) isCompleted{
    return self.completed;
}


@end
