CREATE TABLE IF NOT EXISTS tickets (
id int NOT NULL,
name varchar(250),
creation_time date,
PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS users (
id int NOT NULL,
first_name varchar(255),
last_name varchar(255),
username varchar(255),
email varchar(255) UNIQUE,
PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS user_roles (
user_id int NOT NULL,
role varchar(50) NOT NULL,
PRIMARY KEY(user_id, role),
FOREIGN KEY(user_id) REFERENCES users(id)
);

CREATE SEQUENCE IF NOT EXISTS users_seq;
CREATE SEQUENCE IF NOT EXISTS tickets_seq;