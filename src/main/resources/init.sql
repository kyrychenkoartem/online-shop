CREATE DATABASE shop_repository;

CREATE SCHEMA shop_storage;

CREATE TABLE IF NOT EXISTS users
(
    id         BIGSERIAL PRIMARY KEY,
    username   VARCHAR(64) NOT NULL UNIQUE,
    email      VARCHAR(64) NOT NULL UNIQUE,
    password   VARCHAR(64) NOT NULL,
    birth_date DATE        NOT NULL,
    first_name VARCHAR(64) NOT NULL,
    last_name  VARCHAR(64) NOT NULL,
    role       VARCHAR(32) NOT NULL,
    gender     VARCHAR(32),
    created_at TIMESTAMP   NOT NULL,
    created_by VARCHAR(64) NOT NULL,
    updated_at TIMESTAMP,
    updated_by VARCHAR(64)
);

CREATE TABLE IF NOT EXISTS product
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(128) NOT NULL,
    description VARCHAR(256) NOT NULL,
    price       NUMERIC      NOT NULL,
    quantities  INT          NOT NULL,
    category    VARCHAR(64)  NOT NULL,
    material    VARCHAR(64)  NOT NULL
);

CREATE TABLE IF NOT EXISTS promo_code
(
    id    BIGSERIAL PRIMARY KEY,
    key   VARCHAR(128) NOT NULL UNIQUE,
    value INT          NOT NULL
);

CREATE TABLE IF NOT EXISTS cart
(
    id            BIGSERIAL PRIMARY KEY,
    price         NUMERIC NOT NULL,
    promo_code_id BIGSERIAL REFERENCES promo_code (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS product_item
(
    id         BIGSERIAL PRIMARY KEY,
    product_id BIGSERIAL REFERENCES product (id) ON DELETE CASCADE,
    count      INT NOT NULL,
    cart_id    BIGSERIAL REFERENCES cart (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS orders
(
    id         BIGSERIAL PRIMARY KEY,
    cart_id    BIGSERIAL UNIQUE REFERENCES cart (id) ON DELETE CASCADE,
    users_id   BIGSERIAL REFERENCES users (id) ON DELETE CASCADE,
    created_at TIMESTAMP NOT NULL,
    closed_at  TIMESTAMP,
    status     VARCHAR(64)
);

CREATE TABLE IF NOT EXISTS bank_card
(
    id          BIGSERIAL PRIMARY KEY,
    users_id    BIGSERIAL REFERENCES users (id) ON DELETE CASCADE,
    card_number VARCHAR(25) NOT NULL,
    expiry_date VARCHAR(25) NOT NULL,
    bank        VARCHAR(32) NOT NULL,
    cvv         CHAR(3)     NOT NULL,
    card_type   VARCHAR(32) NOT NULL
);

CREATE TABLE IF NOT EXISTS delivery_address
(
    id          BIGSERIAL PRIMARY KEY,
    orders_id   BIGSERIAL REFERENCES orders (id) ON DELETE CASCADE,
    address     VARCHAR(64) NOT NULL,
    city        VARCHAR(32) NOT NULL,
    province    VARCHAR(32) NOT NULL,
    postal_code varchar(32) NOT NULL
);

CREATE TABLE IF NOT EXISTS payment
(
    id             BIGSERIAL PRIMARY KEY,
    orders_id      BIGSERIAL REFERENCES orders (id) ON DELETE CASCADE,
    bank_card_id   BIGSERIAL REFERENCES bank_card (id) ON DELETE CASCADE,
    payment_time   TIMESTAMP   NOT NULL,
    payment_status VARCHAR(64) NOT NULL
);

DROP TABLE users CASCADE;
DROP TABLE product CASCADE;
DROP TABLE product_item CASCADE;
DROP TABLE promo_code CASCADE;
DROP TABLE cart CASCADE;
DROP TABLE orders CASCADE;
DROP TABLE bank_card CASCADE;
DROP TABLE delivery_address CASCADE;
DROP TABLE payment CASCADE;

