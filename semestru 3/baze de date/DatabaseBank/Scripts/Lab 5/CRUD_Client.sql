USE Bank
GO
CREATE OR ALTER PROCEDURE CRUD_Client
	@first_name VARCHAR(50),
	@last_name VARCHAR(50),
	@branch INT,
	@Adress VARCHAR(50)
AS
BEGIN
	SET NOCOUNT ON
	IF (dbo.isBranchForeignKeyValid(@branch) = 1)
	BEGIN
		INSERT INTO Clients(first_name,last_name,branch_id,address) VALUES(@first_name, @last_name,@branch,@Adress)
		
		SELECT * FROM Clients

		UPDATE Clients SET Clients.last_name = @last_name + '_CRUD' WHERE Clients.last_name = @last_name

		SELECT * FROM Clients

		DELETE FROM Clients WHERE Clients.last_name LIKE @last_name + '_CRUD'

		SELECT * FROM Clients

		PRINT 'S-au realizat cu succes operatiile CRUD pentru tabela Client'
	END

	ELSE
	BEGIN
		RAISERROR('Eroare la validare', 16, 1)
	END
END

EXEC CRUD_Client 'Nume', 'prenume',500,''; -- eroare la validare, branch ul nu exista
EXEC CRUD_Client 'Nume2', 'prenume2',1,'strada strada';