package com.example.test_weather;

public class BookModel {

    private int status;
    private String message;
    private int id;
    private String title;
    private String author;
    private int Year;

    public BookModel(int status, String message, int id, String title, String author, int year) {
        this.status = status;
        this.message = message;
        this.id = id;
        this.title = title;
        this.author = author;
        Year = year;
    }

    public BookModel(int id, String title, String author, int year) {
        this.id = id;
        this.title = title;
        this.author = author;
        Year = year;
    }

    public BookModel() {
    }

    @Override
    public String toString() {
        return title +
                ", author: '" + author + '\'' +
                ", Year: " + Year +
                ", id: " + id +
                '}';
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
}


