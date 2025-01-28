USE Bank
GO
CREATE OR ALTER PROCEDURE CRUD_ClientAccounts
	@ClientId INT,
	@AccountId INT,
	@Role varchar(20)
AS
BEGIN
	SET NOCOUNT ON
	IF (dbo.isRezervariTerenuriForeignKeyValid(@ClientId, @AccountId) = 1)
	BEGIN
		INSERT INTO ClientsAccounts(client_id,account_id,role) VALUES(@ClientId, @AccountId,@Role)
		
		SELECT * FROM ClientsAccounts

		UPDATE ClientsAccounts SET ClientsAccounts.role = @Role + '_CRUD' WHERE ClientsAccounts.role = @Role
		SELECT * FROM ClientsAccounts

		DELETE FROM ClientsAccounts WHERE ClientsAccounts.client_id = @ClientId AND ClientsAccounts.account_id = @AccountId

		SELECT * FROM ClientsAccounts

		PRINT 'S-au realizat cu succes operatiile CRUD pentru tabela ClientAccounts'
	END

	ELSE
	BEGIN
		RAISERROR('Eroare la validare', 16, 1)
	END
END

EXEC CRUD_ClientAccounts 100000, 1,'girant' -- eroare la validare, cheia nu exista in tabela Clients
EXEC CRUD_ClientAccounts 1, 10000,'girant' -- eroare la validare, cheia nu exista in tabela ACCounts
EXEC CRUD_ClientAccounts 10000, 10179,'girant' -- eroare la validare, cheia nu exista in tabele
EXEC CRUD_ClientAccounts 3, 1,'girant'