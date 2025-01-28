USE Bank
GO
CREATE OR ALTER PROCEDURE CRUD_Branches
	@Nume varchar(50),
	@City varchar(50)
AS
BEGIN
	SET NOCOUNT ON
	IF (dbo.isBranchValid(@Nume,@City) = 1)
	BEGIN
		INSERT INTO Branches(name,city) VALUES(@Nume, @City)
		
		SELECT * FROM Branches

		UPDATE Branches SET Branches.name = @Nume + '_CRUD' WHERE Branches.name = @Nume

		SELECT * FROM Branches

		DELETE FROM Branches WHERE Branches.name LIKE @Nume + '_CRUD'

		SELECT * FROM Branches

		PRINT 'S-au realizat cu succes operatiile CRUD pentru tabela Branches'
	END

	ELSE
	BEGIN
		RAISERROR('Eroare la validare', 16, 1)
	END
END

EXEC CRUD_Branches '', 'adf'; -- eroare la validare, nr clienti prea mic
EXEC CRUD_Branches 'asdf', ''; -- eroare la validare, nr clienti prea mic

EXEC CRUD_Branches 'Nume', 'Oras';