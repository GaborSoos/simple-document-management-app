CREATE TABLE users (
                       id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       firstname VARCHAR(100) NOT NULL,
                       lastname VARCHAR(100) NOT NULL,
                       password VARCHAR(255),
                       enabled BOOLEAN
);

CREATE TABLE roles (
                       id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(50) NOT NULL UNIQUE,
                       enabled BOOLEAN
);

CREATE TABLE user_roles (
                       id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                       user_id BIGINT NOT NULL,
                       role_id BIGINT NOT NULL,
                       enabled BOOLEAN,
                       FOREIGN KEY (user_id) REFERENCES users(id),
                       FOREIGN KEY (role_id) REFERENCES roles(id)

);