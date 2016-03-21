CREATE TABLE USER (
  user_id INT NOT NULL PRIMARY KEY,
  first_name VARCHAR(50),
  last_name VARCHAR(50),
  customer_number VARCHAR(20)NOT NULL UNIQUE,
  home_address VARCHAR(500),
  work_address VARCHAR(500),
  phone_number VARCHAR(15),
  national_code VARCHAR(10),
  family_count INT NOT NULL 
);


CREATE TABLE ACCOUNT_BALANCE (
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
  create_date DATE,
  previous_figure INT,
  current_figure INT,
  cunsumption INT,
  abonman FLOAT ,
  reduction INT,
  services INT,
  cost_water INT,
  cost_balance INT,
  final_amount INT,
  user_id INT,
  FOREIGN KEY (user_id) REFERENCES USER(user_id)
);
