package com.bookstore.phase2_spring_jdbc;

import com.bookstore.phase1_jdbc.Book;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BookDaoSpringJdbc {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public BookDaoSpringJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    // ========================
    // RowMapper
    // ========================
    private final RowMapper<Book> bookRowMapper = (rs, rowNum) -> {
        Book book = new Book();
        book.setId(rs.getLong("id"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));
        book.setPrice(rs.getDouble("price"));
        book.setGenre(rs.getString("genre"));
        book.setPublishedYear(rs.getInt("published_year"));
        return book;
    };

    // ========================
    // CREATE - using JdbcTemplate
    // ========================
    public void insertBook(Book book) {
        String sql = "INSERT INTO books (title, author, price, genre, published_year) " +
                "VALUES (?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            var ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setDouble(3, book.getPrice());
            ps.setString(4, book.getGenre());
            ps.setInt(5, book.getPublishedYear());
            return ps;
        }, keyHolder);

        book.setId(keyHolder.getKey().longValue());
        System.out.println("Inserted book with ID: " + book.getId());
    }

    // ========================
    // CREATE - using NamedParameterJdbcTemplate
    // ========================
    public void insertBookNamed(Book book) {
        String sql = "INSERT INTO books (title, author, price, genre, published_year) " +
                "VALUES (:title, :author, :price, :genre, :publishedYear)";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("title", book.getTitle())
                .addValue("author", book.getAuthor())
                .addValue("price", book.getPrice())
                .addValue("genre", book.getGenre())
                .addValue("publishedYear", book.getPublishedYear());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, params, keyHolder, new String[]{"id"});

        book.setId(keyHolder.getKey().longValue());
        System.out.println("Inserted book with ID (named): " + book.getId());
    }

    // ========================
    // UPDATE
    // ========================
    public void updateBook(Book book) {
        String sql = "UPDATE books SET title=:title, author=:author, " +
                "price=:price, genre=:genre, published_year=:publishedYear " +
                "WHERE id=:id";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("title", book.getTitle())
                .addValue("author", book.getAuthor())
                .addValue("price", book.getPrice())
                .addValue("genre", book.getGenre())
                .addValue("publishedYear", book.getPublishedYear())
                .addValue("id", book.getId());

        int rowsAffected = namedParameterJdbcTemplate.update(sql, params);
        System.out.println("Rows updated: " + rowsAffected);
    }

    // ========================
    // DELETE
    // ========================
    public void deleteBook(Long id) {
        String sql = "DELETE FROM books WHERE id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id);

        int rowsAffected = namedParameterJdbcTemplate.update(sql, params);
        System.out.println("Rows deleted: " + rowsAffected);
    }

    // ========================
    // READ by id
    // ========================
    public Book findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id);

        return namedParameterJdbcTemplate.queryForObject(sql, params, bookRowMapper);
    }

    // ========================
    // READ all
    // ========================
    public List<Book> findAll() {
        String sql = "SELECT * FROM books";
        return jdbcTemplate.query(sql, bookRowMapper);
    }
}