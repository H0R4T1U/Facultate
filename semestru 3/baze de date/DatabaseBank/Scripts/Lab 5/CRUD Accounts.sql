USE Bank
GO
CREATE OR ALTER PROCEDURE CRUD_Accounts
	@Balance FLOAT,
	@Status VARCHAR(20)
AS
BEGIN
	SET NOCOUNT ON
	IF (dbo.isStatusValid(@Status) = 1)
	BEGIN
		INSERT INTO Accounts(balance,status) VALUES(@Balance, @Status)
		
		SELECT * FROM Accounts

		UPDATE Accounts SET Accounts.status = @Status + '_CRUD' WHERE Accounts.status = @Status

		SELECT * FROM Accounts

		DELETE FROM Accounts WHERE Accounts.status LIKE @Status + '_CRUD'

		SELECT * FROM Accounts

		PRINT 'S-au realizat cu succes operatiile CRUD pentru tabela Accounts'
	END

	ELSE
	BEGIN
		RAISERROR('Eroare la validare', 16, 1)
	END
END

EXEC CRUD_Accounts 50, ''; -- eroare la validare, statusul este null
EXEC CRUD_Accounts 503.53, 'Economii';