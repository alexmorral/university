package com.ryblade.quizd.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by alexmorral on 21/2/15.
 */
public class Word {

    /** Attributes **/
    private String name;
    private String language;
    private ArrayList<Word> translations;
    private ArrayList<String> translationsString;

    /** Methods **/
    public Word() {
        translations = new ArrayList<>();
        translationsString = new ArrayList<>();
    }
    public Word(String lang, String nameWord) {
        language = lang;
        name = nameWord;
        translations = new ArrayList<>();
        translationsString = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String lang) {
        this.language = lang;
    }

    public void addTranslation(Word w) throws Exception{
        if (translations.contains(w)) throw new Exception("This word already contains this translation");
        translations.add(w);
        translationsString.add(w.getName() + " (" + w.getLanguage() + ")");
    }
    public void removeTranslation(Word w) {
        translations.remove(w);
        translationsString.remove(w.getName() + " (" + w.getLanguage() + ")");
    }

    public void removeAllTranslations(String lang) {
        Iterator<Word> i = translations.iterator();
        while (i.hasNext()) {
            Word w = i.next();
            if(w.getLanguage().equals(lang)) {
                i.remove();
            }
        }
        Iterator<String> j = translationsString.iterator();
        while (j.hasNext()) {
            String w = j.next();
            String[] parts = w.split(" "); //parts[0] = word
            String[] parts2 = parts[1].split("[()]"); //parts2[1] = lang
            if(parts2[1].equals(lang)) {
                j.remove();
            }
        }
    }

    public ArrayList<String> getTranslations() {
        return translationsString;
    }

    public int getNumTranslations(String lang) {
        int count = 0;
        for (Word w : translations){
            if (w.getLanguage().equals(lang)) ++count;
        }
        return count;
    }

    public boolean hasTranslationOn(String lang2) {
        boolean hasTranslation = false;
        for (Word w : translations) {
            if (w.getLanguage().equals(lang2)) hasTranslation = true;
        }
        return hasTranslation;
    }

    public boolean isTranslation(String lang2, String word2) {
        boolean exists = false;
        for (Word w : translations) {
            if (w.getLanguage().equals(lang2) && w.getName().toLowerCase().equals(word2.toLowerCase())) exists = true;
        }
        return exists;
    }
}
