package com.ryblade.quizd.domain;

import android.content.Context;

import com.ryblade.quizd.model.Language;
import com.ryblade.quizd.model.Word;

import java.util.ArrayList;

/**
 * Created by alexmorral on 22/2/15.
 */
public class DomainController {
    /** Attributes **/
    private static DomainController instance = null;
    private ArrayList<Language> languages;
    private ArrayList<String> languagesString;
    private ArrayList<Word> playableWords;
    private ArrayList<String> playableWordsString;
    private DBDataSource datasource;


    /** Methods **/

    public static DomainController getInstance() {
        if (instance == null) instance = new DomainController();
        return instance;
    }



    private DomainController() {

        /*Language l = new Language("English");
        Language l2 = new Language("Español");
        languages.add(l);
        languages.add(l2);
        languagesString.add("English");
        languagesString.add("Español");*/

    }
    public void startDatabase(Context context) {
        try {
            datasource = new DBDataSource(context);
            datasource.open();
            languages = datasource.getAllLanguages();
            datasource.setAllTranslations(languages);
            languagesToArray();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void createLanguage(String newLanguageName) throws Exception {
        for(Language l : languages) {
            if (l.getName().equals(newLanguageName)) {
                throw new Exception("This language already exists");
            }
        }

        Language newLanguage = datasource.createLanguage(newLanguageName);
        languages.add(newLanguage);
        languagesString.add(newLanguageName);
        System.out.println("New Language added");
    }

    public void removeLanguage(String lang) {
        boolean found = false;
        for (int i = 0; i < languages.size() && !found; ++i) {
            if (languages.get(i).getName().equals(lang)) {
                datasource.deleteLanguage(languages.get(i));
                languages.remove(i);
                languagesString.remove(lang);

                removeAllTranslations(lang);
                found = true;
            }
        }
    }

    public void addWordToLanguage(String lang, String word) throws Exception{
        for (Language l : languages) {
            if (l.getName().equals(lang)) {
                for (String s : l.getWords()) {
                    if (s.equals(word)) throw new Exception("This word already exists");
                }
                Word w = datasource.createWord(lang, word);
                l.addWord(w);
            }
        }
    }

    public void setTranslation(String lang1, String word1, String lang2, String word2) throws Exception{
        Word w1 = new Word(), w2 = new Word();
        for (Language l : languages) {
            if (l.getName().equals(lang1)) {
                w1 = l.getWord(word1);
            }
            if (l.getName().equals(lang2)) {
                w2 = l.getWord(word2);
            }
        }
        w1.addTranslation(w2);
        w2.addTranslation(w1);
        datasource.createTranslation(w1, w2);
    }


    public void removeTranslation(String lang1, String word1, String lang2, String word2){
        Word w1 = new Word(), w2 = new Word();
        for (Language l : languages) {
            if (l.getName().equals(lang1)) {
                w1 = l.getWord(word1);
            }
            if (l.getName().equals(lang2)) {
                w2 = l.getWord(word2);
            }
        }
        datasource.removeTranslation(w1, w2);
        w1.removeTranslation(w2);
        w2.removeTranslation(w1);

    }

    public void removeAllTranslations(String lang) {
        for (Language l : languages) {
            l.removeAllTranslations(lang);
        }
    }

    public ArrayList<String> getTranslations(String langString, String wordString) {
        ArrayList<String> translations = new ArrayList<>();
        for (Language l : languages) {
            if (l.getName().equals(langString)) {
                Word w = l.getWord(wordString);
                translations = w.getTranslations();
            }
        }
        return translations;
    }

    public void deleteWordFromLanguage(String lang, String word) {
        for (Language l : languages) {
            if (l.getName().equals(lang)) {
                datasource.deleteWord(l.getWord(word));
                l.removeWord(word);
            }
        }
    }

    public ArrayList<String> getLanguages() {
        return languagesString;
    }

    public ArrayList<String> getWords(String lang) {
        for (Language l : languages) {
            if (l.getName().equals(lang)) {
                return l.getWords();
            }
        }
        return new ArrayList<String>();
    }

    private void languagesToArray() {
        languagesString = new ArrayList<>();
        for (Language l : languages) {
            languagesString.add(l.getName());
        }
    }

    public ArrayList<String> getPlayingLanguages(String lang, int num) {
        ArrayList<String> langs = new ArrayList<>();
        for (Language l : languages) {
            if (!l.getName().equals(lang)) {
                if (l.hasAtLeastTranslations(lang, num)) langs.add(l.getName());
            }
        }
        return langs;
    }

    public void generatePlayableWords(String lang1, String lang2, int num) {
        playableWords = new ArrayList<>();
        playableWordsString = new ArrayList<>();
        int count = 0;
        Language l1 = new Language(), l2 = new Language();
        for (Language l : languages) {
            if (l.getName().equals(lang1)) l1 = l;
            if (l.getName().equals(lang2)) l2 = l;
        }
        while (count < num) {
            for (Word w : l1.getAllWords()) {
                if (w.hasTranslationOn(lang2)) {
                    playableWords.add(w);
                    playableWordsString.add(w.getName());
                }
            }
            ++count;
        }
    }

    public ArrayList<String> getPlayableWords() {
        return playableWordsString;
    }

    public boolean checkWordCorrect(String lang1, String word1, String lang2, String word2) {
        Boolean correct = false;
        Language l1 = new Language(), l2 = new Language();
        for (Language l : languages) {
            if (l.getName().equals(lang1)) l1 = l;
            if (l.getName().equals(lang2)) l2 = l;
        }
        Word w1 = new Word();
        for (Word w : l1.getAllWords()) {
            if (w.getName().equals(word1)) w1 = w;
        }

        return w1.isTranslation(lang2, word2);
    }

    public void saveRank(String nameString, int score) {
        datasource.createRank(nameString, score);
    }

    public ArrayList<String> getAllRanks() {
        return datasource.getAllRanks();
    }
}
