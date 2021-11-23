DROP TABLE IF EXISTS client;

DROP TABLE IF EXISTS repair;

CREATE TABLE client
(
    client_id int NOT NULL auto_increment,
    client_name varchar(50) NOT NULL UNIQUE,
    CONSTRAINT client_pk PRIMARY KEY (client_id)
);

CREATE TABLE repair
(
     repair_id int NOT NULL auto_increment,
     repair_type ENUM('ELECTRIC','PLUMBER','FINISHING','ANOTHER') NOT NULL,
     address varchar(250) NOT NULL,
     difficulty_level ENUM('EASY','MEDIUM','HARD') NOT NULL,
     preference_date DATE NOT NULL,
     client_id int NOT NULL,
         CONSTRAINT repair_pk PRIMARY KEY (repair_id),
         CONSTRAINT repair_client_fk FOREIGN KEY (client_id) REFERENCES client(client_id)
);
