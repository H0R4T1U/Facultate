use Bank


CREATE TABLE Branches(
branch_id int PRIMARY KEY IDENTITY,
name varchar(50) NOT NULL, 
adress varchar(50),
city varchar(20) NOT NULL,
phone varchar(20),
email varchar(50)
)

CREATE TABLE Managers(
manager_id int FOREIGN KEY REFERENCES Branches(branch_id),
first_name varchar(20) NOT NULL,
last_name varchar(20) NOT NULL,
hire_date DATE,
salary int,
CONSTRAint pk_BranchesManagers PRIMARY KEY(manager_id)
)

CREATE TABLE Employees(
employee_id int PRIMARY KEY IDENTITY,
branch_id int FOREIGN KEY REFERENCES Branches(branch_id),
first_name varchar(20) NOT NULL,
last_name varchar(20) NOT NULL,
position varchar(20) NOT NULL,
hire_date DATE,
salary int NOT NULL
)

CREATE TABLE Clients(
client_id int PRIMARY KEY IDENTITY,
branch_id int FOREIGN KEY REFERENCES Branches(branch_id),
first_name varchar(20) NOT NULL,
last_name varchar(20) NOT NULL,
email varchar(50),
date_of_birth DATE,
date_of_join DATE,
address varchar(50) NOT NULL
)

CREATE TABLE Deposits(
deposit_id int PRIMARY KEY IDENTITY,
client_id int FOREIGN KEY REFERENCES Clients(client_id),
amount int NOT NULL,
deposit_date DATE,
interest_rate FLOAT NOT NULL,
maturity_date DATE NOT NULL
)

CREATE TABLE Accounts(
account_id int PRIMARY KEY IDENTITY,
account_type varchar(50),
balance FLOAT DEFAULT 0,
opening_date DATE,
status varchar(20) NOT NULL
)

CREATE TABLE ClientsAccounts(
client_id int FOREIGN KEY REFERENCES Clients(client_id),
account_id int FOREIGN KEY REFERENCES Accounts(account_id),
role varchar(20) NOT NULL,
constraint pk_ClientsAccounts PRIMARY KEY (client_id,account_id)
)

CREATE TABLE Transactions(
transaction_id int PRIMARY KEY IDENTITY,
account_id int FOREIGN KEY REFERENCES Accounts(account_id),
transaction_type varchar(20) NOT NULL,
amount int NOT NULL,
transaction_date DATE,
description VARCHAR(100)
)

CREATE TABLE Loans(
loan_id int PRIMARY KEY IDENTITY,
loan_amount int NOT NULL,
loan_type VARCHAR(20) ,
interest_rate FLOAT NOT NULL,
issue_date DATE,
due_date DATE NOT NULL,
remaining_balance FLOAT NOT NULL
)

CREATE TABLE Client_Loans(
loan_id int FOREIGN KEY REFERENCES Loans(loan_id),
client_id int FOREIGN KEY REFERENCES Clients(client_id),
role VARCHAR(20) NOT NULL,
constraint pk_ClientsLoans PRIMARY KEY (client_id,loan_id)
)

CREATE TABLE Payments(
payment_id int PRIMARY KEY IDENTITY,
loan_id int FOREIGN KEY REFERENCES Loans(loan_id),
amount int NOT NULL,
payment_date DATE,
type varchar(20)
)
