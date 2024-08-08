DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS wallet;
DROP TABLE IF EXISTS transaction_history;
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
VALUES (10001, 'company', 'company1@example.com', 'pc'),
       (10002, 'user', 'user1@example.com', 'pu');


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
VALUES (10001, 10001, 1000.0),
       (10002, 10002, 10000.0);


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

