CREATE DATABASE Preparing
go

CREATE TABLE Magazine(
idMagazine INT PRIMARY KEY,
Denumire varchar(50),
AnDeschidere Date
)

CREATE TABLE Locatii(
idLocatii INT PRIMARY KEY,
idMagazine INT FOREIGN KEY REFERENCES Magazine(idMagazine),
Localitate varchar(50),
Strada varchar(50),
Numar INT,
CodPostal INT
)

CREATE TABLE Clienti(
idClienti INT PRIMARY KEY,
Nume varchar(50),
Prenume varchar(50),
Gen varchar(50),
DataNastere DATE
)

CREATE TABLE ProduseFavorite(
idProduse INT PRIMARY KEY,
idClienti INT FOREIGN KEY REFERENCES Clienti(idClienti),
Denumire varchar(50),
Pret INT,
Reducere INT)

CREATE TABLE Cumparaturi(
idClienti INT FOREIGN KEY REFERENCES Clienti(idClienti),
idMagazine INT FOREIGN KEY REFERENCES Magazine(idMagazine),
CONSTRAINT pk_Cumparaturi PRIMARY KEY(idClienti,idMagazine),
DataCumparaturilor DATE,
Pret INT
)


INSERT INTO Magazine (idMagazine, Denumire, AnDeschidere)
VALUES
(1, 'Magazinul Unu', '2005-01-15'),
(2, 'Magazinul Doi', '2010-06-20'),
(3, 'Magazinul Trei', '2015-11-30');

INSERT INTO Clienti (idClienti, Nume, Prenume, Gen, DataNastere)
VALUES
(1, 'Popescu', 'Ion', 'Masculin', '1990-05-25'),
(2, 'Ionescu', 'Maria', 'Feminin', '1985-08-12'),
(3, 'Vasilescu', 'Ana', 'Feminin', '1995-03-17');

INSERT INTO Locatii (idLocatii, idMagazine, Localitate, Strada, Numar, CodPostal)
VALUES
(1, 1, 'București', 'Victoriei', 10, 12345),
(2, 2, 'Cluj-Napoca', 'Libertății', 5, 54321),
(3, 3, 'Timișoara', 'Pacii', 20, 67890);


INSERT INTO ProduseFavorite (idProduse, idClienti, Denumire, Pret, Reducere)
VALUES
(1, 1, 'Laptop', 3000, 10),
(2, 2, 'Telefon', 2000, 5),
(3, 3, 'Tabletă', 1500, 15);


INSERT INTO Cumparaturi (idClienti, idMagazine, DataCumparaturilor, Pret)
VALUES
(1, 1, '2023-10-01', 2500),
(2, 2, '2023-10-02', 1800),
(3, 3, '2023-10-03', 1200);

GO
CREATE OR ALTER FUNCTION VerificareConditie(
	@idClienti INT,
	@idMagazine INT,
	@DataCumparaturi DATE,
	@pret INT
)
RETURNS INT AS BEGIN
	DECLARE @validator INT
	SELECT @validator = COUNT(*) FROM Cumparaturi where @idClienti=idClienti and @idMagazine=idMagazine;
	if @validator > 0
		RETURN 0
	RETURN 1
END
GO

GO
CREATE OR ALTER PROCEDURE Adaugare
	@idClient INT,
	@numeClient varchar(50),
	@prenumeClient varchar(50),
	@gen varchar(50),
	@dataNastere DATE,

	@idMagazine INT,
	@denumireMagazin varchar(50),
	@anDeschidere DATE,

	@dataCumparaturi DATE,
	@pretAchitat INT
AS BEGIN
	DECLARE @validator INT;
	SELECT @validator = dbo.VerificareConditie(@idClient,@idMagazine,@dataCumparaturi,@pretAchitat)
	IF (@validator = 0)
	BEGIN
		UPDATE Cumparaturi
		SET DataCumparaturilor = @dataCumparaturi , Pret = @pretAchitat
		SELECT * FROM Cumparaturi
		PRINT('Am facut update in tabela Cumparaturi')
	END

	IF (@validator = 1)
	BEGIN
		INSERT INTO Clienti (idClienti, Nume, Prenume, Gen, DataNastere) VALUES (@idClient,@numeClient,@prenumeClient,@gen,@dataNastere)
		INSERT INTO Magazine (idMagazine, Denumire, AnDeschidere) VALUES (@idMagazine,@denumireMagazin,@anDeschidere)
		INSERT INTO Cumparaturi (idClienti, idMagazine, DataCumparaturilor, Pret) VALUES (@idClient,@idMagazine,@dataCumparaturi,@pretAchitat)

		SELECT * FROM Clienti
		SELECT * FROM Magazine
		SELECT * FROM Cumparaturi
		PRINT('Am facut adaugarea cu succes!')

	END
END
GO

GO
CREATE OR ALTER VIEW viewClienti AS
	SELECT (c.Nume+' '+c.Prenume) AS Nume 
	FROM Clienti c INNER JOIN ProduseFavorite p ON
	c.idClienti=p.idClienti 
	GROUP BY p.idClienti,c.idClienti,c.Nume,c.Prenume 
	HAVING COUNT(c.idClienti) <=3
GO

SELECT * FROM viewClienti