DROP DATABASE IF EXISTS lab1_jdbc;
CREATE DATABASE lab1_jdbc;
USE lab1_jdbc;

CREATE TABLE kaf_records (
id int PRIMARY KEY AUTO_INCREMENT,
naz VARCHAR(20) NOT NULL,
tel VARCHAR(11) NOT NULL
);

INSERT INTO kaf_records(naz,tel) VALUES("Инженерное дело","72001002525");