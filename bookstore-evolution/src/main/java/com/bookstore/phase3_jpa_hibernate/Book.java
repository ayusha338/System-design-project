package com.bookstore.phase3_jpa_hibernate;

import jakarta.persistence.*;
import java.time.LocalDateTime;

// This class Maps to the database table .
@Entity
//Specifies which table this entity maps to
// Specifies which table this entity maps to
@Table(name = "books")
public class Book {
    //Without @Id JPA throws AnnotationException at startup
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "author", nullable = false, length = 255)
    private String author;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "genre", length = 100)
    private String genre;

    @Column(name = "published_year")
    private int publishedYear;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Default constructor - MANDATORY for JPA
    // JPA uses reflection to create objects
    public Book() {}

    // Parameterized constructor
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

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

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