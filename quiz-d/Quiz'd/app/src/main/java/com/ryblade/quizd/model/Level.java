package com.ryblade.quizd.model;

/**
 * Created by alexmorral on 21/2/15.
 */
public class Level {

    /** Attributes **/
    private String name;
    private int qttyWords;


    /** Methods **/
    public Level() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQttyWords() {
        return qttyWords;
    }

    public void setQttyWords(int qttyWords) {
        this.qttyWords = qttyWords;
    }
}
