package com.example.demodb;

public class TotalScore {

    private String id;
    private String score;

    public TotalScore() {

    }

    public TotalScore(String id, String score){
        this.id = id;
        this.score = score;
    }

    public String getId() {
        return id;
    }
    public String getScore() {
        return score;
    }
}
