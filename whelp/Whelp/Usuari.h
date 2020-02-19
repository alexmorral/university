//
//  Usuari.h
//  Whelp
//
//  Created by Alex Morral on 6/11/15.
//  Copyright © 2015 Alex Morral. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Model.h"


/*
 $table->increments('id');
 $table->bigInteger('id_fb');
 $table->string('nom_usuari');
 $table->string('nom_complet');
 $table->string('email');
 $table->boolean('discapacitat');
 $table->longText('descripcio');
 $table->timestamps();
 */
@interface Usuari : Model

@property(nonatomic, strong) NSNumber *id_fb;
@property(nonatomic, strong) NSString *nom_usuari;
@property(nonatomic, strong) NSString *nom_complet;
@property(nonatomic, strong) NSString *email;
@property(nonatomic, strong) NSNumber *discapacitat;
@property(nonatomic, strong) NSString *descripcio;
@property(nonatomic, strong) NSString *urlToPicture;

@end
