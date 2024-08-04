-- Kullanıcı tablosu ve verileri
CREATE TABLE IF NOT EXISTS users
(
    id
    BIGINT
    AUTO_INCREMENT
    PRIMARY
    KEY,
    username
    VARCHAR
(
    255
) NOT NULL, email VARCHAR
(
    255
) NOT NULL, password VARCHAR
(
    255
) NOT NULL );

INSERT INTO users (id, username, email, password)
VALUES (1, 'u1', 'u1@e.com', 'p1'),
       (2, 'u2', 'u2@e.com', 'p2'),
       (3, 'u3', 'u3@e.com', 'p3'),
       (4, 'u4', 'u4@e.com', 'p4'),
       (5, 'u5', 'u5@e.com', 'p5'),
       (6, 'u6', 'u6@e.com', 'p6'),
       (7, 'u7', 'u7@e.com', 'p7'),
       (8, 'u8', 'u8@e.com', 'p8'),
       (9, 'u9', 'u9@e.com', 'p9'),
       (10, 'u10', 'u10@e.com', 'p10');

-- Cüzdan tablosu ve verileri
CREATE TABLE IF NOT EXISTS wallet
(
    id
    BIGINT
    AUTO_INCREMENT
    PRIMARY
    KEY,
    user_id
    BIGINT,
    balance
    DOUBLE,
    FOREIGN
    KEY
(
    user_id
) REFERENCES users
(
    id
) );

INSERT INTO wallet (id, user_id, balance)
VALUES (1, 1, 1000.0),
       (2, 2, 1000.0),
       (3, 3, 1000.0),
       (4, 4, 1000.0),
       (5, 5, 1000.0),
       (6, 6, 1000.0),
       (7, 7, 1000.0),
       (8, 8, 1000.0),
       (9, 9, 10000),
       (10, 10, 1000.0);

-- İşlem geçmişi tablosu ve verileri
CREATE TABLE IF NOT EXISTS transaction_history
(
    id
    BIGINT
    AUTO_INCREMENT
    PRIMARY
    KEY,
    wallet_id_from
    BIGINT,
    wallet_id_to
    BIGINT,
    amount
    DOUBLE,
    transaction_time
    TIMESTAMP
);

-- Örnek işlemler
INSERT INTO transaction_history (id, wallet_id_from, wallet_id_to, amount, transaction_time)
VALUES (1, 1, 2, 50.0, '2024-07-01 12:00:00'),
       (2, 2, 3, 30.0, '2024-07-02 14:00:00'),
       (3, 3, 4, 20.0, '2024-07-03 16:00:00');
