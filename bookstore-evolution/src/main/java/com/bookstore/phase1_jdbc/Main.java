package com.bookstore.phase1_jdbc;

public class Main {

    public static void main(String[] args) {

        BookDao bookDao = new BookDao();

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
        // READ
        // ========================
        System.out.println("\n===== READ =====");
        Book fetchedBook = bookDao.findById(book1.getId());
        System.out.println("Fetched: " + fetchedBook);

        // ========================
        // UPDATE
        // ========================
        System.out.println("\n===== UPDATE =====");
        book1.setPrice(299.99);
        book1.setGenre("Software Engineering");
        bookDao.updateBook(book1);

        Book updatedBook = bookDao.findById(book1.getId());
        System.out.println("Updated: " + updatedBook);

        // ========================
        // DELETE
        // ========================
        System.out.println("\n===== DELETE =====");
        bookDao.deleteBook(book3.getId());
        System.out.println("Deleted book with id: " + book3.getId());

        Book deletedBook = bookDao.findById(book3.getId());
        System.out.println("Find deleted book: " + deletedBook);
    }
}