package com.bookstore.phase1_jdbc;

import java.sql.*;

public class BookDao {

    // Step 1: Define connection details
    private static final String URL = "jdbc:mysql://localhost:3308/bookstore_db";
    private static final String USER = "bookstore_user";
    private static final String PASSWORD = "bookstore_pass";

    // Step 2: Get a connection
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // ========================
    // CREATE
    // ========================
    public void insertBook(Book book) {
        String sql = "INSERT INTO books (title, author, price, genre, published_year) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setDouble(3, book.getPrice());
            ps.setString(4, book.getGenre());
            ps.setInt(5, book.getPublishedYear());

            int rowsAffected = ps.executeUpdate();
            System.out.println("Rows inserted: " + rowsAffected);

            // Get auto generated id back
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    book.setId(generatedKeys.getLong(1));
                    System.out.println("Generated ID: " + book.getId());
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ========================
    // UPDATE
    // ========================
    public void updateBook(Book book) {
        String sql = "UPDATE books SET title=?, author=?, price=?, genre=?, published_year=? WHERE id=?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setDouble(3, book.getPrice());
            ps.setString(4, book.getGenre());
            ps.setInt(5, book.getPublishedYear());
            ps.setLong(6, book.getId());

            int rowsAffected = ps.executeUpdate();
            System.out.println("Rows updated: " + rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ========================
    // DELETE
    // ========================
    public void deleteBook(Long id) {
        String sql = "DELETE FROM books WHERE id=?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            int rowsAffected = ps.executeUpdate();
            System.out.println("Rows deleted: " + rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ========================
    // READ (by id)
    // ========================
    public Book findById(Long id) {
        String sql = "SELECT * FROM books WHERE id=?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToBook(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ========================
    // Helper - Map ResultSet to Book
    // ========================
    private Book mapResultSetToBook(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setId(rs.getLong("id"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));
        book.setPrice(rs.getDouble("price"));
        book.setGenre(rs.getString("genre"));
        book.setPublishedYear(rs.getInt("published_year"));
        return book;
    }
}