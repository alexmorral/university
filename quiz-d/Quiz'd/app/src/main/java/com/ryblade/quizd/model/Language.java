package com.ryblade.quizd.model;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by alexmorral on 21/2/15.
 */
public class Language {

    /** Attributes **/
    private int id;
    private String name;
    private ArrayList<Word> words;
    private ArrayList<String> wordsString;


    /** Methods **/
    public Language() {
        words = new ArrayList<>();
        wordsString = new ArrayList<>();
    }

    public Language(String name) {
        this.name = name;
        words = new ArrayList<>();
        wordsString = new ArrayList<>();
        if (name.equals("English")) {
            Word w = new Word(name, "House");
            Word w2 = new Word(name, "Tree");
            Word w3 = new Word(name, "Father");
            Word w4 = new Word(name, "Computer");
            words.add(w);
            words.add(w2);
            words.add(w3);
            words.add(w4);
            wordsString.add("House");
            wordsString.add("Tree");
            wordsString.add("Father");
            wordsString.add("Computer");
        } else if (name.equals("Español")){
            Word w = new Word(name, "Ordenador");
            words.add(w);
            wordsString.add("Ordenador");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getNumberOfWords() {
        return words.size();
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<String> getWords() {
        return wordsString;
    }

    public ArrayList<Word> getAllWords() {
        return words;
    }

    public Word getWord(String name) {
        for (Word w : words) {
            if (w.getName().equals(name)) return w;
        }
        return new Word();
    }

    /*public void addWord(String wordName) {
        Word w = new Word();
        w.setName(wordName);
        w.setLanguage(name);
        words.add(w);
        wordsString.add(wordName);
    }*/

    public void addWord(Word word) {
        words.add(word);
        wordsString.add(word.getName());
    }

    public void removeWord(String wordName) {
        wordsString.remove(wordName);
        boolean finish = false;
        for (int i = 0; i < words.size() && !finish; i++) {
            if (words.get(i).getName().equals(wordName)) {
                words.remove(i);
                finish = true;
            }
        }
    }

    public void removeAllTranslations(String lang) {
        for (Word w : words) {
            w.removeAllTranslations(lang);
        }
    }

    public boolean hasAtLeastTranslations(String lang, int num) {
        int count = 0;
        for (Word w : words) {
            count += w.getNumTranslations(lang);
        }
        //Log.v("Number of translations", "Count:" + count);
        return (count >= num);
    }

}
