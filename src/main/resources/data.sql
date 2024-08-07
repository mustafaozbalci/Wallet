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
) NOT NULL,
    email VARCHAR
(
    255
) NOT NULL,
    password VARCHAR
(
    255
) NOT NULL
    );

INSERT INTO users (id, username, email, password)
VALUES (99999, 'u1', 'u1@e.com', 'p1'),
       (100000, 'u2', 'u2@e.com', 'p2'),
       (100001, 'u3', 'u3@e.com', 'p3'),
       (100002, 'u4', 'u4@e.com', 'p4'),
       (100003, 'u5', 'u5@e.com', 'p5'),
       (100004, 'john_doe', 'u6@e.com', 'password123'),
       (100005, 'u7', 'u7@e.com', 'p7'),
       (100006, 'company1', 'c1@e.com', 'p6'),
       (100007, 'company2', 'c2@e.com', 'p7'),
       (100008, 'company3', 'c3@e.com', 'p8');

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
)
    );

INSERT INTO wallet (id, user_id, balance)
VALUES (99999, 99999, 1000.0),
       (100000, 100000, 1000.0),
       (100001, 100001, 1000.0),
       (100002, 100002, 1000.0),
       (100003, 100003, 1000.0),
       (100004, 100004, 1000.0),
       (100005, 100005, 1000.0),
       (100006, 100006, 1000.0),
       (100007, 100007, 10000),
       (100008, 100008, 1000.0);

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
VALUES (99999, 99999, 100000, 50.0, '2024-07-01 12:00:00'),
       (100000, 100000, 100001, 30.0, '2024-07-02 14:00:00'),
       (100001, 100001, 100002, 20.0, '2024-07-03 16:00:00');
