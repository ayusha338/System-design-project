CREATE DATABASE IF NOT EXISTS bookstore_db;
USE bookstore_db;

CREATE TABLE IF NOT EXISTS books(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    title          VARCHAR(255)  NOT NULL,
    author         VARCHAR(255)  NOT NULL,
    price          DECIMAL(10,2) NOT NULL,
    genre          VARCHAR(100),
    published_year INT,
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);