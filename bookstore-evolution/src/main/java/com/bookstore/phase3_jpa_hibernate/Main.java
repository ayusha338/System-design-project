package com.bookstore.phase3_jpa_hibernate;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // EntityManagerFactory - created ONCE
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("bookstore-pu");
        BookDao bookDao = new BookDao(emf);

        // ========================
        // CREATE
        // ========================
        System.out.println("===== INSERT =====");
        Book book1 = new Book("Clean Code", "Robert C. Martin", 499.99, "Technology", 2008);
        Book book2 = new Book("The Pragmatic Programmer", "David Thomas", 599.99, "Technology", 1999);
        Book book3 = new Book("Atomic Habits", "James Clear", 399.99, "Self Help", 2018);

        bookDao.insertBook(book1);
        bookDao.insertBook(book2);
        bookDao.insertBook(book3);

        System.out.println("Inserted: " + book1);
        System.out.println("Inserted: " + book2);
        System.out.println("Inserted: " + book3);

        // ========================
        // READ by id
        // ========================
        System.out.println("\n===== READ by ID =====");
        Book fetched = bookDao.findById(book1.getId());
        System.out.println("Fetched: " + fetched);

        // ========================
        // READ all - JPQL
        // ========================
        System.out.println("\n===== READ ALL =====");
        List<Book> allBooks = bookDao.findAll();
        allBooks.forEach(b -> System.out.println(b));

        // ========================
        // UPDATE
        // ========================
        System.out.println("\n===== UPDATE =====");
        book1.setPrice(299.99);
        book1.setGenre("Software Engineering");
        bookDao.updateBook(book1);
        System.out.println("Updated: " + bookDao.findById(book1.getId()));

        // ========================
        // DELETE
        // ========================
        System.out.println("\n===== DELETE =====");
        bookDao.deleteBook(book3.getId());
        System.out.println("Deleted book with id: " + book3.getId());

        // ========================
        // JPQL - find by genre
        // ========================
        System.out.println("\n===== JPQL - find by genre =====");
        List<Book> techBooks = bookDao.findByGenre("Technology");
        techBooks.forEach(b -> System.out.println(b));

        // ========================
        // JPQL - find by price range
        // ========================
        System.out.println("\n===== JPQL - price range =====");
        List<Book> priceRange = bookDao.findByPriceRange(200.00, 600.00);
        priceRange.forEach(b -> System.out.println(b));

        // ========================
        // Native Query
        // ========================
        System.out.println("\n===== Native Query =====");
        List<Book> byAuthor = bookDao.findByAuthorNative("Robert C. Martin");
        byAuthor.forEach(b -> System.out.println(b));

        // ========================
        // TypedQuery
        // ========================
        System.out.println("\n===== TypedQuery - title containing =====");
        List<Book> byKeyword = bookDao.findByTitleContaining("Clean");
        byKeyword.forEach(b -> System.out.println(b));

        // close EMF when app shuts down
        emf.close();
    }
}