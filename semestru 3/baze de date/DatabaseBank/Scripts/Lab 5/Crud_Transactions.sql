USE Bank
GO
CREATE OR ALTER PROCEDURE Crud_Transactions
	@AccountId INT, 
	@transactionType varchar(20),
	@Amount INT
AS
BEGIN
	SET NOCOUNT ON
	IF (dbo.isTransactionValid(@AccountId,@transactionType) = 1)
	BEGIN
		INSERT INTO Transactions(account_id,transaction_type,amount) VALUES(@AccountId, @transactionType,@Amount)
		
		SELECT * FROM Transactions

		UPDATE Transactions SET Transactions.transaction_type = @transactionType + '_CRUD' WHERE Transactions.transaction_type = @transactionType

		SELECT * FROM Transactions

		DELETE FROM Transactions WHERE Transactions.transaction_type LIKE @transactionType + '_CRUD'

		SELECT * FROM Transactions

		PRINT 'S-au realizat cu succes operatiile CRUD pentru tabela Transactions'
	END

	ELSE
	BEGIN
		RAISERROR('Eroare la validare', 16, 1)
	END
END

EXEC Crud_Transactions 100000, 'tio',200; -- eroare la validare, nu exista un teren cu id-ul 1
EXEC Crud_Transactions 1, '',200; -- eroare la validare, string null

EXEC Crud_Transactions 1, 'tip',200;

