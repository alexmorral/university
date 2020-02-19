package com.ryblade.quizd.domain;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by alexmorral on 4/5/15.
 */
public class DAODatabase extends SQLiteOpenHelper {

    public static final String TABLE_LANGUAGES = "languages";
    public static final String TABLE_WORDS = "words";
    public static final String TABLE_TRANSLATIONS = "translations";
    public static final String TABLE_RANKINGS = "rankings";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_LANG_NAME = "name";
    public static final String COLUMN_WORD_NAME = "name";
    public static final String COLUMN_WORD_LANG = "lang";
    public static final String COLUMN_TRANS_LANG1 = "lang1";
    public static final String COLUMN_TRANS_WORD1 = "word1";
    public static final String COLUMN_TRANS_LANG2 = "lang2";
    public static final String COLUMN_TRANS_WORD2 = "word2";

    public static final String COLUMN_RANK_NAME = "name";
    public static final String COLUMN_RANK_SCORE = "score";


    private static final String DATABASE_NAME = "quizd.db";
    private static final int DATABASE_VERSION = 1;

    public DAODatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createLangsTable = "create table "
                + TABLE_LANGUAGES + "("
                + COLUMN_ID + " integer primary key autoincrement, "
                + COLUMN_LANG_NAME + " varchar(255) not null);";
        String createWordsTable = "create table "
                + TABLE_WORDS + "("
                + COLUMN_ID + " integer primary key autoincrement, "
                + COLUMN_WORD_NAME + " varchar(255) not null,"
                + COLUMN_WORD_LANG + " varchar(255) not null);";
        String createTransTable = "create table "
                + TABLE_TRANSLATIONS + "("
                + COLUMN_ID + " integer primary key autoincrement, "
                + COLUMN_TRANS_LANG1 + " varchar(255) not null,"
                + COLUMN_TRANS_WORD1 + " varchar(255) not null,"
                + COLUMN_TRANS_LANG2 + " varchar(255) not null,"
                + COLUMN_TRANS_WORD2 + " varchar(255) not null);";
        String createRanksTable = "create table "
                + TABLE_RANKINGS + "("
                + COLUMN_ID + " integer primary key autoincrement, "
                + COLUMN_RANK_NAME + " varchar(255) not null, "
                + COLUMN_RANK_SCORE + " int(4) not null);";

        db.execSQL(createLangsTable);
        db.execSQL(createWordsTable);
        db.execSQL(createTransTable);
        db.execSQL(createRanksTable);

        insertOnDb(db);

    }

    public void insertOnDb(SQLiteDatabase db) {
        String insertOnBd1 = "Insert into " + TABLE_LANGUAGES + "(" + COLUMN_LANG_NAME + ") VALUES ('English');";
        String insertOnBd2 = "Insert into " + TABLE_LANGUAGES + "(" + COLUMN_LANG_NAME + ") VALUES ('Spanish');";
        String insertOnBd3 = "Insert into " + TABLE_WORDS + "(" + COLUMN_WORD_LANG + "," + COLUMN_WORD_NAME + ") VALUES('English','House');";
        String insertOnBd4 = "Insert into " + TABLE_WORDS + "(" + COLUMN_WORD_LANG + "," + COLUMN_WORD_NAME + ") VALUES('English','Computer');";
        String insertOnBd5 = "Insert into " + TABLE_WORDS + "(" + COLUMN_WORD_LANG + "," + COLUMN_WORD_NAME + ") VALUES('English','Screen');";
        String insertOnBd6 = "Insert into " + TABLE_WORDS + "(" + COLUMN_WORD_LANG + "," + COLUMN_WORD_NAME + ") VALUES('English','Mouse');";
        String insertOnBd7 = "Insert into " + TABLE_WORDS + "(" + COLUMN_WORD_LANG + "," + COLUMN_WORD_NAME + ") VALUES('English','Keyboard');";
        String insertOnBd8 = "Insert into " + TABLE_WORDS + "(" + COLUMN_WORD_LANG + "," + COLUMN_WORD_NAME + ") VALUES('Spanish','Casa');";
        String insertOnBd9 = "Insert into " + TABLE_WORDS + "(" + COLUMN_WORD_LANG + "," + COLUMN_WORD_NAME + ") VALUES('Spanish','Ordenador');";
        String insertOnBd10 = "Insert into " + TABLE_WORDS + "(" + COLUMN_WORD_LANG + "," + COLUMN_WORD_NAME + ") VALUES('Spanish','Pantalla');";
        String insertOnBd11 = "Insert into " + TABLE_WORDS + "(" + COLUMN_WORD_LANG + "," + COLUMN_WORD_NAME + ") VALUES('Spanish','Raton');";
        String insertOnBd12 = "Insert into " + TABLE_WORDS + "(" + COLUMN_WORD_LANG + "," + COLUMN_WORD_NAME + ") VALUES('Spanish','Teclado');";

        String insertOnBd13 = "Insert into " + TABLE_TRANSLATIONS + "(" + COLUMN_TRANS_LANG1 + "," + COLUMN_TRANS_WORD1 + "," + COLUMN_TRANS_LANG2 + "," + COLUMN_TRANS_WORD2 + ") VALUES('English','House','Spanish','Casa');";
        String insertOnBd14 = "Insert into " + TABLE_TRANSLATIONS + "(" + COLUMN_TRANS_LANG1 + "," + COLUMN_TRANS_WORD1 + "," + COLUMN_TRANS_LANG2 + "," + COLUMN_TRANS_WORD2 + ") VALUES('Spanish','Casa','English','House');";
        String insertOnBd15 = "Insert into " + TABLE_TRANSLATIONS + "(" + COLUMN_TRANS_LANG1 + "," + COLUMN_TRANS_WORD1 + "," + COLUMN_TRANS_LANG2 + "," + COLUMN_TRANS_WORD2 + ") VALUES('English','Computer','Spanish', 'Ordenador');";
        String insertOnBd16 = "Insert into " + TABLE_TRANSLATIONS + "(" + COLUMN_TRANS_LANG1 + "," + COLUMN_TRANS_WORD1 + "," + COLUMN_TRANS_LANG2 + "," + COLUMN_TRANS_WORD2 + ") VALUES('Spanish','Ordenador','English', 'Computer');";
        String insertOnBd17 = "Insert into " + TABLE_TRANSLATIONS + "(" + COLUMN_TRANS_LANG1 + "," + COLUMN_TRANS_WORD1 + "," + COLUMN_TRANS_LANG2 + "," + COLUMN_TRANS_WORD2 + ") VALUES('English','Screen','Spanish','Pantalla');";
        String insertOnBd18 = "Insert into " + TABLE_TRANSLATIONS + "(" + COLUMN_TRANS_LANG1 + "," + COLUMN_TRANS_WORD1 + "," + COLUMN_TRANS_LANG2 + "," + COLUMN_TRANS_WORD2 + ") VALUES('Spanish','Pantalla','English', 'Screen');";
        String insertOnBd19 = "Insert into " + TABLE_TRANSLATIONS + "(" + COLUMN_TRANS_LANG1 + "," + COLUMN_TRANS_WORD1 + "," + COLUMN_TRANS_LANG2 + "," + COLUMN_TRANS_WORD2 + ") VALUES('English','Mouse','Spanish','Raton');";
        String insertOnBd20 = "Insert into " + TABLE_TRANSLATIONS + "(" + COLUMN_TRANS_LANG1 + "," + COLUMN_TRANS_WORD1 + "," + COLUMN_TRANS_LANG2 + "," + COLUMN_TRANS_WORD2 + ") VALUES('Spanish','Raton','English','Mouse');";
        String insertOnBd21 = "Insert into " + TABLE_TRANSLATIONS + "(" + COLUMN_TRANS_LANG1 + "," + COLUMN_TRANS_WORD1 + "," + COLUMN_TRANS_LANG2 + "," + COLUMN_TRANS_WORD2 + ") VALUES('English','Keyboard','Spanish', 'Teclado');";
        String insertOnBd22 = "Insert into " + TABLE_TRANSLATIONS + "(" + COLUMN_TRANS_LANG1 + "," + COLUMN_TRANS_WORD1 + "," + COLUMN_TRANS_LANG2 + "," + COLUMN_TRANS_WORD2 + ") VALUES('Spanish','Teclado','English', 'Keyboard');";

        db.execSQL(insertOnBd1);
        db.execSQL(insertOnBd2);
        db.execSQL(insertOnBd3);
        db.execSQL(insertOnBd4);
        db.execSQL(insertOnBd5);
        db.execSQL(insertOnBd6);
        db.execSQL(insertOnBd7);
        db.execSQL(insertOnBd8);
        db.execSQL(insertOnBd9);
        db.execSQL(insertOnBd10);
        db.execSQL(insertOnBd11);
        db.execSQL(insertOnBd12);
        db.execSQL(insertOnBd13);
        db.execSQL(insertOnBd14);
        db.execSQL(insertOnBd15);
        db.execSQL(insertOnBd16);
        db.execSQL(insertOnBd17);
        db.execSQL(insertOnBd18);
        db.execSQL(insertOnBd19);
        db.execSQL(insertOnBd20);
        db.execSQL(insertOnBd21);
        db.execSQL(insertOnBd22);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DAODatabase.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LANGUAGES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORDS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSLATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RANKINGS);
        onCreate(db);
    }
}
