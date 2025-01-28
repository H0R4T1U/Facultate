USE Bank
GO

CREATE TABLE ClientsTest
(client_id INT PRIMARY KEY,
first_name VARCHAR(20),
last_name VARCHAR(20),
email VARCHAR(20),
address VARCHAR(20),
branch_id INT FOREIGN KEY REFERENCES Branches(branch_id)
)

CREATE TABLE AccountsTest
(Id INT PRIMARY KEY,
type VARCHAR(50) NOT NULL,
status VARCHAR(20) NOT NULL,
balance FLOAT
)


CREATE TABLE ClientsAccountsTest
(ClientId INT FOREIGN KEY REFERENCES ClientsTest(client_id),
AccountId INT FOREIGN KEY REFERENCES AccountsTest(Id),
CONSTRAINT pk_ClientsAccountsTest PRIMARY KEY (ClientId, AccountId)
)





DROP TABLE ClientsAccountsTest
DROP TABLE ClientsTest
DROP TABLE AccountsTest