package com.bookstore.phase2_spring_jdbc;

import com.bookstore.phase1_jdbc.Book;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        // Setup
        JdbcTemplate jdbcTemplate = AppConfig.jdbcTemplate();
        BookDaoSpringJdbc bookDao = new BookDaoSpringJdbc(jdbcTemplate);

        // ========================
        // CREATE - positional ?
        // ========================
        System.out.println("===== INSERT (JdbcTemplate) =====");
        Book book1 = new Book("Clean Code", "Robert C. Martin", 499.99, "Technology", 2008);
        bookDao.insertBook(book1);
        System.out.println("Inserted: " + book1);

        // ========================
        // CREATE - named parameters
        // ========================
        System.out.println("\n===== INSERT (NamedParameter) =====");
        Book book2 = new Book("The Pragmatic Programmer", "David Thomas", 599.99, "Technology", 1999);
        bookDao.insertBookNamed(book2);
        System.out.println("Inserted: " + book2);

        // ========================
        // READ by id
        // ========================
        System.out.println("\n===== READ by ID =====");
        Book fetched = bookDao.findById(book1.getId());
        System.out.println("Fetched: " + fetched);

        // ========================
        // READ all
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
        bookDao.deleteBook(book2.getId());
        System.out.println("Deleted book with id: " + book2.getId());

        System.out.println("\n===== FINAL LIST =====");
        bookDao.findAll().forEach(b -> System.out.println(b));
    }
}