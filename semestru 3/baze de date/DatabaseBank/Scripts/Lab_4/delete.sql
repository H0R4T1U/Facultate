GO
USE Bank
GO

GO
CREATE OR ALTER PROCEDURE deleteTableRows
	@tableName VARCHAR(50),
	@noOfRows INT
AS
BEGIN
	DECLARE @contor INT
	SET @contor = 1

	WHILE @contor <= @noOfRows
	BEGIN
 		IF @tableName = 'Accounts'
			DELETE FROM AccountsTest WHERE AccountsTest.Id = @contor

		ELSE IF @tableName = 'ClientAccounts'
			DELETE FROM ClientsAccountsTest WHERE ClientsAccountsTest.ClientId = @contor AND
													ClientsAccountsTest.AccountId = @contor

		ELSE IF @tableName = 'Clients'
			DELETE FROM ClientsTest WHERE ClientsTest.client_id = @contor

		SET @contor = @contor + 1
	END
END;
GO

SELECT * FROM AccountsTest;
SELECT * FROM ClientsAccountsTest;
SELECT * FROM ClientsTest;


EXEC deleteTableRows ClientAccounts, 30;
EXEC deleteTableRows Clients, 30;
EXEC deleteTableRows Accounts, 30;
