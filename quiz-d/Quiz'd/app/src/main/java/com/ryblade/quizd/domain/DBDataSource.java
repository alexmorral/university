package com.ryblade.quizd.domain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.ryblade.quizd.model.Language;
import com.ryblade.quizd.model.Word;

import org.apache.http.client.utils.CloneUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by alexmorral on 4/5/15.
 */
public class DBDataSource {

    // Database fields
    private SQLiteDatabase database;
    private DAODatabase dbHelper;
    private String[] allLangColumns = { DAODatabase.COLUMN_ID,
            DAODatabase.COLUMN_LANG_NAME };
    private String[] allWordColumns = {DAODatabase.COLUMN_WORD_NAME, DAODatabase.COLUMN_WORD_LANG};
    private String[] allTranslationsColumns = {DAODatabase.COLUMN_TRANS_LANG1, DAODatabase.COLUMN_TRANS_WORD1,
                                                DAODatabase.COLUMN_TRANS_LANG2, DAODatabase.COLUMN_TRANS_WORD2};
    private String[] allRankColumns = {DAODatabase.COLUMN_RANK_NAME, DAODatabase.COLUMN_RANK_SCORE};

    public DBDataSource(Context context) {
        dbHelper = new DAODatabase(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Language createLanguage(String lang) {
        ContentValues values = new ContentValues();
        values.put(DAODatabase.COLUMN_LANG_NAME, lang);
        long insertId = database.insert(DAODatabase.TABLE_LANGUAGES, null,
                values);


        Cursor cursor = database.query(DAODatabase.TABLE_LANGUAGES,
                allLangColumns, DAODatabase.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Language newLang = cursorToLanguage(cursor);
        cursor.close();
        return newLang;
    }

    public void deleteLanguage(Language lang) {
        int id = lang.getId();
        System.out.println("Language deleted with id: " + id);
        database.delete(DAODatabase.TABLE_LANGUAGES, DAODatabase.COLUMN_ID
                + " = " + id, null);
        database.delete(DAODatabase.TABLE_WORDS, DAODatabase.COLUMN_WORD_LANG
                + " = '" + lang.getName() + "'", null);
        database.delete(DAODatabase.TABLE_TRANSLATIONS, DAODatabase.COLUMN_TRANS_LANG1
                + " = '" + lang.getName() + "' OR " + DAODatabase.COLUMN_TRANS_LANG2 + " = '" + lang.getName() + "'", null);
    }


    public ArrayList<Language> getAllLanguages() {
        ArrayList<Language> langs = new ArrayList<>();

        Cursor langCursor = database.query(DAODatabase.TABLE_LANGUAGES,
                allLangColumns, null, null, null, null, null);

        langCursor.moveToFirst();
        while (!langCursor.isAfterLast()) {
            Language lang = cursorToLanguage(langCursor);
            Cursor wordCursor = database.query(DAODatabase.TABLE_WORDS, allWordColumns,
                    DAODatabase.COLUMN_WORD_LANG + " = '" + lang.getName() + "'", null, null, null, null);
            wordCursor.moveToFirst();
            while (!wordCursor.isAfterLast()) {
                Word word = cursorToWord(wordCursor);
                lang.addWord(word);
                wordCursor.moveToNext();
            }
            langs.add(lang);
            langCursor.moveToNext();
            wordCursor.close();
        }
        // make sure to close the cursor
        langCursor.close();
        return langs;
    }


    public void setAllTranslations(ArrayList<Language> langs) throws Exception{

        Iterator<Language> itLangs = langs.iterator();
        while (itLangs.hasNext()) {
            Language l = itLangs.next();
            Iterator<Word> itWords = l.getAllWords().iterator();
            while (itWords.hasNext()) {
                Word w = itWords.next();
                Cursor transCursor = database.query(DAODatabase.TABLE_TRANSLATIONS, allTranslationsColumns,
                        DAODatabase.COLUMN_TRANS_LANG1 + " = '" + l.getName() + "' AND "
                                + DAODatabase.COLUMN_TRANS_WORD1 + " = '" + w.getName() + "'", null, null, null, null);
                transCursor.moveToFirst();
                while (!transCursor.isAfterLast()) {
                    Word trans = cursorToTranslation(langs, transCursor);
                    w.addTranslation(trans);
                    transCursor.moveToNext();
                }
                transCursor.close();
            }
        }
    }


    private Language cursorToLanguage(Cursor cursor) {
        Language lang = new Language();
        lang.setId(cursor.getInt(0));
        lang.setName(cursor.getString(1));
        return lang;
    }

    private Word cursorToWord(Cursor cursor) {
        Word word = new Word();
        word.setName(cursor.getString(0));
        word.setLanguage(cursor.getString(1));
        return word;
    }

    private Word cursorToTranslation(ArrayList<Language> langs, Cursor cursor) {
        String wordName = cursor.getString(3);
        String langName = cursor.getString(2);
        Word word = new Word();
        Iterator<Language> itLangs = langs.iterator();
        while (itLangs.hasNext()) {
            Language l = itLangs.next();
            if(l.getName().equals(langName)) word = l.getWord(wordName);
        }
        return word;
    }

    public Word createWord(String lang, String word) {
        ContentValues values = new ContentValues();
        values.put(DAODatabase.COLUMN_WORD_LANG, lang);
        values.put(DAODatabase.COLUMN_WORD_NAME, word);

        long insertId = database.insert(DAODatabase.TABLE_WORDS, null,
                values);

        Cursor cursor = database.query(DAODatabase.TABLE_WORDS,
                allWordColumns, DAODatabase.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Word newWord = cursorToWord(cursor);
        cursor.close();
        System.out.println("Word '" + word + "' created in DB");
        return newWord;
    }


    public void deleteWord(Word word) {
        String wordName = word.getName();
        String wordLang = word.getLanguage();
        database.delete(DAODatabase.TABLE_WORDS, DAODatabase.COLUMN_WORD_NAME + " = '" + wordName + "' AND "
                + DAODatabase.COLUMN_WORD_LANG + " = '" + wordLang + "'", null);
        database.delete(DAODatabase.TABLE_TRANSLATIONS, "(" + DAODatabase.COLUMN_TRANS_LANG1 + " = '" + wordLang +"' AND "
                + DAODatabase.COLUMN_TRANS_WORD1 + " = '" + wordName + "') OR (" + DAODatabase.COLUMN_TRANS_LANG2 + " = '"
                + wordLang + "' AND " + DAODatabase.COLUMN_TRANS_WORD2 + " = '" + wordName + "')", null );
    }

    public void createTranslation(Word w1, Word w2) {
        ContentValues values = new ContentValues();
        values.put(DAODatabase.COLUMN_TRANS_LANG1, w1.getLanguage());
        values.put(DAODatabase.COLUMN_TRANS_WORD1, w1.getName());
        values.put(DAODatabase.COLUMN_TRANS_LANG2, w2.getLanguage());
        values.put(DAODatabase.COLUMN_TRANS_WORD2, w2.getName());

        ContentValues values2 = new ContentValues();
        values2.put(DAODatabase.COLUMN_TRANS_LANG1, w2.getLanguage());
        values2.put(DAODatabase.COLUMN_TRANS_WORD1, w2.getName());
        values2.put(DAODatabase.COLUMN_TRANS_LANG2, w1.getLanguage());
        values2.put(DAODatabase.COLUMN_TRANS_WORD2, w1.getName());

        long insertId = database.insert(DAODatabase.TABLE_TRANSLATIONS, null,
                values);
        long insertId2 = database.insert(DAODatabase.TABLE_TRANSLATIONS, null,
                values2);

        System.out.println("Translation '" + w1.getName() + "' -> '" +w2.getName() + "' created in DB");
        /*Cursor cursor = database.query(DAODatabase.TABLE_TRANSLATIONS,
                allTranslationsColumns, DAODatabase.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.close();*/
    }

    public void removeTranslation(Word w1, Word w2) {
        String wordName1 = w1.getName();
        String wordLang1 = w1.getLanguage();
        String wordName2 = w2.getName();
        String wordLang2 = w2.getLanguage();
        database.delete(DAODatabase.TABLE_TRANSLATIONS, "((" + DAODatabase.COLUMN_TRANS_LANG1 + " = '" + wordLang1 +"' AND "
                + DAODatabase.COLUMN_TRANS_WORD1 + " = '" + wordName1 + "') AND (" + DAODatabase.COLUMN_TRANS_LANG2 + " = '"
                + wordLang2 + "' AND " + DAODatabase.COLUMN_TRANS_WORD2 + " = '" + wordName2 + "')) OR (("
                + DAODatabase.COLUMN_TRANS_LANG1 + " = '" + wordLang2 +"' AND "
                + DAODatabase.COLUMN_TRANS_WORD1 + " = '" + wordName2 + "') AND (" + DAODatabase.COLUMN_TRANS_LANG2 + " = '"
                + wordLang1 + "' AND " + DAODatabase.COLUMN_TRANS_WORD2 + " = '" + wordName1 + "'))", null );
    }

    public void createRank(String name, int score) {
        ContentValues values = new ContentValues();
        values.put(DAODatabase.COLUMN_RANK_NAME, name);
        values.put(DAODatabase.COLUMN_RANK_SCORE, score);

        long insertId = database.insert(DAODatabase.TABLE_RANKINGS, null,
                values);
    }
    public ArrayList<String> getAllRanks() {
        ArrayList<String> ranks = new ArrayList<>();

        Cursor ranksCursor = database.query(DAODatabase.TABLE_RANKINGS,
                allRankColumns, null, null, null, null, DAODatabase.COLUMN_RANK_SCORE + " DESC", "5");

        ranksCursor.moveToFirst();
        while (!ranksCursor.isAfterLast()) {
            String rank = cursorToRank(ranksCursor);
            ranks.add(rank);
            ranksCursor.moveToNext();
        }
        // make sure to close the cursor
        ranksCursor.close();
        return ranks;
    }

    private String cursorToRank(Cursor cursor) {
        return cursor.getString(0) + " - " + String.valueOf(cursor.getInt(1));
    }


}
