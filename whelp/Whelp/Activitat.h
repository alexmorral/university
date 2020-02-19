//
//  Activitat.h
//  Whelp
//
//  Created by Alex Morral on 6/11/15.
//  Copyright © 2015 Alex Morral. All rights reserved.
//

#import "Model.h"

/*
 
 $table->increments('id')->unsigned();
 $table->bigInteger('voluntari')->nullable();
 $table->foreign('voluntari')->references('id_fb')->on('usuaris');
 $table->bigInteger('discapacitat');
 $table->foreign('discapacitat')->references('id_fb')->on('usuaris');
 $table->string('titol');
 $table->string('data');
 $table->string('origen');
 $table->double('origen_latitud');
 $table->double('origen_longitud');
 $table->string('desti');
 $table->double('desti_latitud');
 $table->double('desti_longitud');
 $table->string('descripcio');
 $table->boolean('completada')->default(false);
 $table->boolean('en_proces')->default(false);
 $table->timestamps();
 */

@interface Activitat : Model

@property(nonatomic, strong) NSNumber *idActivity;
@property(nonatomic, strong) NSNumber *voluntari;
@property(nonatomic, strong) NSNumber *discapacitat;
@property(nonatomic, strong) NSString *titol;
@property(nonatomic, strong) NSString *data;
@property(nonatomic, strong) NSString *origen;
@property(nonatomic, strong) NSNumber *origen_latitud;
@property(nonatomic, strong) NSNumber *origen_longitud;
@property(nonatomic, strong) NSString *desti;
@property(nonatomic, strong) NSNumber *desti_latitud;
@property(nonatomic, strong) NSNumber *desti_longitud;
@property(nonatomic, strong) NSString *descripcio;
@property(nonatomic, strong) NSNumber *completada;
@property(nonatomic, strong) NSNumber *en_proces;

@end
