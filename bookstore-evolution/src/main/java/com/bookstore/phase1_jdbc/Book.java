package com.bookstore.phase1_jdbc;
public class Book {

    private Long id;
    private String title;
    private String author;
    private double price;
    private String genre;
    private int publishedYear;

    public Book(){

    }
    // Parameterized constructor (without id - for INSERT)
    public Book(String title, String author, double price, String genre, int publishedYear) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.genre = genre;
        this.publishedYear = publishedYear;
    }
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public int getPublishedYear() { return publishedYear; }
    public void setPublishedYear(int publishedYear) { this.publishedYear = publishedYear; }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", genre='" + genre + '\'' +
                ", publishedYear=" + publishedYear +
                '}';
    }

}