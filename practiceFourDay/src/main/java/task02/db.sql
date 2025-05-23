CREATE DATABASE space;
USE space;

CREATE TABLE milepost (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(40),
    launchtime DATE,
    depict VARCHAR(255),
    state INT
);
