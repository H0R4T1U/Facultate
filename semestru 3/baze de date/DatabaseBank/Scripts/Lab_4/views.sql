USE Bank;

GO
CREATE OR ALTER VIEW View_Client AS
SELECT client_id, first_name, last_name,email,address
FROM ClientsTest;

GO
CREATE OR ALTER VIEW View_ClientAccounts AS
SELECT a.Id AS AccountId, 
       a.type, 
       a.status, 
       a.balance, 
       c.first_name, 
       c.last_name
FROM AccountsTest a
INNER JOIN ClientsAccountsTest ca ON a.Id = ca.AccountId
INNER JOIN ClientsTest c ON ca.ClientId = c.client_id;

GO
CREATE OR ALTER VIEW View_Accounts AS
SELECT c.branch_id, 
       COUNT(a.Id) AS TotalAccounts, 
       SUM(a.balance) AS TotalBalance
FROM AccountsTest a
INNER JOIN ClientsAccountsTest ca ON a.Id = ca.AccountId
INNER JOIN ClientsTest c ON ca.ClientId = c.client_id
GROUP BY c.branch_id;

GO
CREATE OR ALTER PROCEDURE viewTableRows
	@tableName VARCHAR(50)
AS
BEGIN
	IF @tableName = 'Clients'
	BEGIN
		SELECT * FROM View_Client
	END

	ELSE IF @tableName = 'Accounts'
	BEGIN
		SELECT * FROM View_Accounts
	END

	ELSE IF @tableName = 'ClientAccounts'
	BEGIN
		SELECT * FROM View_ClientAccounts
	END
END

		
	
GO
SELECT * FROM View_Client
SELECT * FROM View_ClientAccounts
SELECT * FROM View_Accounts

EXEC viewTableRows Clients;
EXEC viewTableRows Accounts;
EXEC viewTableRows ClientAccounts;

GO
DROP VIEW View_Client;
DROP VIEW View_ClientAccounts;
DROP VIEW View_Accounts;