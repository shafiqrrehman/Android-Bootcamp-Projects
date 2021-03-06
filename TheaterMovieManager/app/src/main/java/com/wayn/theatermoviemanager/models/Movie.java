package com.wayn.theatermoviemanager.models;

import java.io.Serializable;

public class Movie implements Serializable {

    String id;
    String title;
    String overview;
    float voteAverage;
    float voteCount;
    String fosterPath;
    String backdropPath;

    public Movie(String id, String title, String overview, float voteAverage, float voteCount, String fosterPath, String backdropPath) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
        this.fosterPath = fosterPath;
        this.backdropPath = backdropPath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public float getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(float voteCount) {
        this.voteCount = voteCount;
    }

    public String getFosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342%s", fosterPath);
    }

    public void setFosterPath(String fosterPath) {
        this.fosterPath = fosterPath;
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w780%s", backdropPath);
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }
}
