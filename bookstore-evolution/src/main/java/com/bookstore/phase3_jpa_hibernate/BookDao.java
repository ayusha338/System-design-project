package com.bookstore.phase3_jpa_hibernate;

import jakarta.persistence.*;
import java.util.List;

public class BookDao {

    private final EntityManagerFactory emf;

    public BookDao(EntityManagerFactory emf) {
        this.emf = emf;
    }

    // ========================
    // CREATE
    // ========================
    public void insertBook(Book book) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(book);
            em.createQuery("Select b from book b ", Book.class);
            tx.commit();
            System.out.println("Inserted book with ID: " + book.getId());
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    // ========================
    // UPDATE
    // ========================
    public void updateBook(Book book) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Book managed = em.find(Book.class, book.getId());
            if (managed != null) {
                managed.setTitle(book.getTitle());
                managed.setAuthor(book.getAuthor());
                managed.setPrice(book.getPrice());
                managed.setGenre(book.getGenre());
                managed.setPublishedYear(book.getPublishedYear());
            }
            tx.commit();
            System.out.println("Updated book with ID: " + book.getId());
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    // ========================
    // DELETE
    // ========================
    public void deleteBook(Long id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Book book = em.find(Book.class, id);
            if (book != null) {
                em.remove(book);
                System.out.println("Deleted book with ID: " + id);
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    // ========================
    // READ by id
    // ========================
    public Book findById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Book.class, id);
        } finally {
            em.close();
        }
    }

    // ========================
    // READ all - JPQL
    // ========================
    public List<Book> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT b FROM Book b", Book.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // ========================
    // JPQL - find by genre
    // ========================
    public List<Book> findByGenre(String genre) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT b FROM Book b WHERE b.genre = :genre", Book.class)
                    .setParameter("genre", genre)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // ========================
    // JPQL - find by price range
    // ========================
    public List<Book> findByPriceRange(double minPrice, double maxPrice) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT b FROM Book b WHERE b.price BETWEEN :minPrice AND :maxPrice ORDER BY b.price ASC",
                            Book.class)
                    .setParameter("minPrice", minPrice)
                    .setParameter("maxPrice", maxPrice)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // ========================
    // Native SQL query
    // ========================
    public List<Book> findByAuthorNative(String author) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createNativeQuery(
                            "SELECT * FROM books WHERE author = ?1", Book.class)
                    .setParameter(1, author)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // ========================
    // TypedQuery
    // ========================
    public List<Book> findByTitleContaining(String keyword) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Book> query = em.createQuery(
                    "SELECT b FROM Book b WHERE b.title LIKE :keyword", Book.class);
            query.setParameter("keyword", "%" + keyword + "%");
            query.setMaxResults(10);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}