CREATE TABLE USER (
  id NOT NULL PRIMARY KEY,
  first_name VARCHAR(50),
  last_name VARCHAR(50),
  customer_number VARCHAR(20),
  home_address VARCHAR(500),
  work_address VARCHAR(500),
  phone_number VARCHAR(15),
  national_code VARCHAR(10),
  family_count INT
);


CREATE TABLE ACCOUNT_BALANCE (
  id NOT NULL PRIMARY KEY,
  createDate DATE,
  debit INT,
  credit INT,
  account_balance INT,
  description VARCHAR(1000),
  customer_number VARCHAR(20)
);



CREATE TABLE BILL (


);