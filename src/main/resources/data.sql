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

INSERT INTO users (id, username, email, password)
VALUES (1, 'u1', 'u1@e.com', 'p1');
INSERT INTO users (id, username, email, password)
VALUES (2, 'u2', 'u2@e.com', 'p2');
INSERT INTO users (id, username, email, password)
VALUES (3, 'u3', 'u3@e.com', 'p3');
INSERT INTO users (id, username, email, password)
VALUES (4, 'u4', 'u4@e.com', 'p4');
INSERT INTO users (id, username, email, password)
VALUES (5, 'u5', 'u5@e.com', 'p5');
INSERT INTO users (id, username, email, password)
VALUES (6, 'u6', 'u6@e.com', 'p6');
INSERT INTO users (id, username, email, password)
VALUES (7, 'u7', 'u7@e.com', 'p7');
INSERT INTO users (id, username, email, password)
VALUES (8, 'u8', 'u8@e.com', 'p8');
INSERT INTO users (id, username, email, password)
VALUES (9, 'u9', 'u9@e.com', 'p9');
INSERT INTO users (id, username, email, password)
VALUES (10, 'u10', 'u10@e.com', 'p10');


INSERT INTO wallet (id, user_id, balance)
VALUES (1, 1, 100.0);
INSERT INTO wallet (id, user_id, balance)
VALUES (2, 2, 200.0);
INSERT INTO wallet (id, user_id, balance)
VALUES (3, 3, 300.0);
INSERT INTO wallet (id, user_id, balance)
VALUES (4, 4, 400.0);
INSERT INTO wallet (id, user_id, balance)
VALUES (5, 5, 500.0);
INSERT INTO wallet (id, user_id, balance)
VALUES (6, 6, 600.0);
INSERT INTO wallet (id, user_id, balance)
VALUES (7, 7, 700.0);
INSERT INTO wallet (id, user_id, balance)
VALUES (8, 8, 800.0);
INSERT INTO wallet (id, user_id, balance)
VALUES (9, 9, 900.0);
INSERT INTO wallet (id, user_id, balance)
VALUES (10, 10, 1000.0);
