package com.example.test_weather;

public class BookModel {

    private int status;
    private String message;
    private int id;
    private String title;
    private String author;
    private int Year;
    private String cover_url;
    private String description;
    private String genre;

    public BookModel(int status, String message, int id, String title, String author, int year) {
        this.status = status;
        this.message = message;
        this.id = id;
        this.title = title;
        this.author = author;
        this.Year = year;
        this.cover_url = cover_url;
        this.description = description;
        this.genre = genre;
    }

    public BookModel(int id, String title, String author, int year) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.Year = year;
        this.cover_url = cover_url;
        this.description = description;
        this.genre = genre;
    }

    public BookModel() {
    }

    @Override
    public String toString() {
        return title + "\n" +
                "Author: '" + author + "\n" +
                "Year: " + Year + "\n" +
                "ID: " + id + "\n" +
                "Genre: " + genre + "\n" +
                "Description: " + description + "\n"+
                "Cover_url: " + cover_url + "\n"
                ;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return Year;
    }

    public String getCover_url() {
        return cover_url;
    }

    public String getDescription() {
        return description;
    }

    public String getGenre() {
        return genre;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setYear(int year) {
        Year = year;
    }

    public void setCover_url(String cover_url) {
        this.cover_url = cover_url;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}


