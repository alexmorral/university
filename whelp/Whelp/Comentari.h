//
//  Comentari.h
//  Whelp
//
//  Created by 1137952 on 17/12/15.
//  Copyright (c) 2015 Alex Morral. All rights reserved.
//

#import "Model.h"

@interface Comentari : Model

@property(nonatomic, strong) NSNumber *idActivity;
@property(nonatomic, strong) NSNumber *voluntari;
@property(nonatomic, strong) NSNumber *discapacitat;
@property(nonatomic, strong) NSString *textComentari;
@property(nonatomic, strong) NSNumber *puntuacio;

@end
