CREATE TABLE IF NOT EXISTS projects (
id int NOT NULL,
name varchar(255),
description varchar(255),
PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS sprints (
id int NOT NULL,
name varchar(255),
description varchar(255),
start_date timestamp NOT NULL,
end_date timestamp NOT NULL,
PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS users (
id int NOT NULL,
first_name varchar(255),
last_name varchar(255),
username varchar(255),
email varchar(255) UNIQUE,
assigned_project_id int,
PRIMARY KEY(id),
FOREIGN KEY(assigned_project_id) REFERENCES projects(id)
);

CREATE TABLE IF NOT EXISTS user_roles (
user_id int NOT NULL,
role varchar(50) NOT NULL,
PRIMARY KEY(user_id, role),
FOREIGN KEY(user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS tickets (
id int NOT NULL,
title varchar(250) NOT NULL,
author_id int NOT NULL,
assignee_id int,
creation_time timestamp NOT NULL,
project_id int NOT NULL,
sprint_id int,
status varchar(50) NOT NULL,
size varchar(50) NOT NULL,
content TEXT,
errata TEXT,
PRIMARY KEY(id),
FOREIGN KEY(author_id) REFERENCES users(id),
FOREIGN KEY(assignee_id) REFERENCES users(id),
FOREIGN KEY(project_id) REFERENCES projects(id),
FOREIGN KEY(sprint_id) REFERENCES sprints(id)
);

CREATE TABLE IF NOT EXISTS ticket_events (
id int NOT NULL,
author_id int NOT NULL,
ticket_id int NOT NULL,
creation_time timestamp NOT NULL,
type varchar(50) NOT NULL,
message TEXT,
size_vote varchar(50),
PRIMARY KEY(id),
FOREIGN KEY(author_id) REFERENCES users(id),
FOREIGN KEY(ticket_id) REFERENCES tickets(id)
);

CREATE TABLE IF NOT EXISTS ticket_comments (
id int NOT NULL,
author_id int NOT NULL,
ticket_id int NOT NULL,
creation_time timestamp NOT NULL,
content TEXT,
PRIMARY KEY(id),
FOREIGN KEY(author_id) REFERENCES users(id),
FOREIGN KEY(ticket_id) REFERENCES tickets(id)
);

CREATE SEQUENCE IF NOT EXISTS users_seq;
CREATE SEQUENCE IF NOT EXISTS projects_seq;
CREATE SEQUENCE IF NOT EXISTS sprints_seq;
CREATE SEQUENCE IF NOT EXISTS tickets_seq;
CREATE SEQUENCE IF NOT EXISTS ticket_events_seq;
CREATE SEQUENCE IF NOT EXISTS ticket_comments_seq;