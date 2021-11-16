DROP TABLE IF EXISTS client;

CREATE TABLE client
(
    client_id int NOT NULL auto_increment,
    client_name varchar(50) NOT NULL UNIQUE,
    CONSTRAINT client_pk PRIMARY KEY (client_id)
);