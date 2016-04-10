CREATE TABLE USER (
  user_id INT NOT NULL PRIMARY KEY,
  username varchar(50),
  password varchar(300),
  first_name VARCHAR(50),
  last_name VARCHAR(50),
  customer_number VARCHAR(20)NOT NULL UNIQUE,
  home_address VARCHAR(500),
  work_address VARCHAR(500),
  phone_number VARCHAR(15),
  national_code VARCHAR(10),
  family_count INT NOT NULL 
);



CREATE TABLE ACCOUNTBALANCE (

  acc_id INT NOT NULL PRIMARY KEY,
  create_date DATE,
  debit INT,
  credit INT,
  account_balance INT,
  description VARCHAR(1000),
  user_id INT,
  FOREIGN KEY (user_id) REFERENCES USER(user_id)
);



CREATE TABLE BILL (
  bill_id INT NOT NULL PRIMARY KEY,
  previous_Date DATE,
  new_date DATE,
  previous_figure INT,
  current_figure INT,
  cunsumption INT,
  abonman DOUBLE ,
  reduction INT,
  services INT,
  cost_water DOUBLE,
  cost_balance INT,
  final_amount INT,
  last_debit INT,
  user_id INT,
  FOREIGN KEY (user_id) REFERENCES USER(user_id)
);
