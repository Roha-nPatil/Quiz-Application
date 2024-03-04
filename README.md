# QUIZ APPLICATION


### CREATING A DATABASE

CREATE DATABASE IF NOT EXISTS quiz_application_db;                                               
USE quiz_application_db;

_____________________________________________________________
### CREATING A TABLE quiz_app

CREATE TABLE IF NOT EXISTS quiz_app (                                             
question_id INT NOT NULL AUTO_INCREMENT,                                               
question TEXT NOT NULL,                                               
option1 VARCHAR(50) DEFAULT NULL,                                               
option2 VARCHAR(50) DEFAULT NULL,                                               
option3 VARCHAR(50) DEFAULT NULL,                                               
option4 VARCHAR(50) DEFAULT NULL,                                               
answer VARCHAR(50) NOT NULL,                                               
PRIMARY KEY (question_id)                                               
);                                               

______________________________________________________________
### CREATING A TABLE user_login

CREATE TABLE IF NOT EXISTS user_login (                                               
email varchar(50) NOT NULL,                                               
password varchar(16) NOT NULL CHECK (LENGTH(password) >= 8),                                               
PRIMARY KEY (email)                                               
);                                               
