DROP TABLE IF EXISTS ord;

CREATE TABLE ord
(
    ord_id int NOT NULL auto_increment,
    ord_address varchar(50) NOT NULL UNIQUE,
    CONSTRAINT ord_pk PRIMARY KEY (ord_id)
);