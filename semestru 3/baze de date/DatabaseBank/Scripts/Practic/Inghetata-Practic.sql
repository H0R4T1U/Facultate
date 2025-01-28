go
CREATE DATABASE Pregatire1
go

create table Inghetate(
idInghetata INT PRIMARY KEY,
Denumire varchar(50),
Descriere varchar(50),
Pret INT,
DataExpirare DATE)


CREATE TABLE Tipuri(
idTip INT PRIMARY KEY,
idInghetata INT FOREIGN KEY REFERENCES Inghetate(idInghetata),
Denumire varchar(50),
Descriere varchar(50)
)

CREATE TABLE Copiii(
idCopil INT PRIMARY KEY,
idInghetata INT FOREIGN KEY REFERENCES Inghetate(idInghetata),
Nume varchar(50),
Prenume varchar(50),
Gen varchar(50),
Varsta INT)


CREATE TABLE Comenzi(
idComanda INT PRIMARY KEY,
Denumire varchar(50),
Cantitate INT,
Pret INT)

CREATE TABLE Livrarii(
idComanda INT FOREIGN KEY REFERENCES Comenzi(idComanda),
idInghetata INT FOREIGN KEY REFERENCES Inghetate(idInghetata),
DataLivrarii DATE,
Discount INT)

INSERT INTO Inghetate(idInghetata,Denumire,Descriere,Pret,DataExpirare) VALUES 
	(1,'Corso','Aromata',100,'2020-01-19'),
	(2,'Joe','Cu ciocolata',200,'2024-02-10'),
	(3,'Snikers','Cu caramel',50,'2029-10-12');


INSERT INTO Tipuri(idTip,idInghetata,Denumire,Descriere) VALUES
	(1,1,'DenumireTip1','DescriereTip1'),
	(2,2,'DenumireTip1','DescriereTip1'),
	(3,3,'DenumireTip3','DescriereTip3');

INSERT INTO Copiii(idCopil,idInghetata,Nume,Prenume,Gen,Varsta) VALUES 
	(1,1,'N1','N1','M',10),
	(2,2,'N2','N2','M',12),
	(3,3,'N3','N3','F',2);

INSERT INTO Comenzi(idComanda,Denumire,Cantitate,Pret) VALUES
		(1,'C1',1000,203),
		(2,'C2',13040,213442),
		(3,'C3',345,9233);

INSERT INTO Livrarii(idComanda,idInghetata,DataLivrarii,Discount) VALUES
	(1,1,'2024-01-23',20),
	(2,2,'2024-02-25',10),
	(3,3,'2024-01-21',40);

GO
CREATE OR ALTER FUNCTION ValidareDate(
	@idComanda INT,
	@idInghetata INT,
	@data DATE,
	@discount INT
)RETURNS INT AS BEGIN 
	declare @count int
	SELECT @count = COUNT(*) From Livrarii where idInghetata =@idInghetata and idComanda=@idComanda;

	IF @count > 0
		return 0
	RETURN 1
END
GO


GO
CREATE OR ALTER PROCEDURE AddLivrari
	@idComanda INT,
	@denumireComanda varchar(50),
	@cantitate INT,
	@pretComanda INT,

	@idInghetata INT,
	@denumireInghetata varchar(50),
	@descriereInghetata varchar(50),
	@pretInghetata INT,
	@dataExpirare DATE,

	@dataLivrare DATE,
	@discount INT
AS BEGIN 
	DECLARE @validationStatus INT
	SELECT @validationStatus = dbo.ValidareDate(@idComanda,@idInghetata,@dataLivrare,@discount );

	IF (@validationStatus = 0)
	BEGIN
		UPDATE Livrarii
			SET DataLivrarii=@dataLivrare,Discount=@discount
			where idComanda=@idComanda and idInghetata=@idInghetata;
	SELECT * FROM Livrarii
	PRINT('Livrarea a fost modificata cu succes!');
	END
	
	IF(@validationStatus = 1)
	BEGIN
		INSERT INTO Inghetate(idInghetata,Denumire,Descriere,Pret,DataExpirare) VALUES 
		(@idInghetata,@denumireInghetata,@descriereInghetata,@pretInghetata,@dataExpirare);
		INSERT INTO Comenzi(idComanda,Cantitate,Denumire) VALUES 
		(@idComanda,@cantitate,@denumireComanda)
		INSERT INTO Livrarii(idComanda,idInghetata,DataLivrarii,Discount) VALUES
		(@idComanda,@idInghetata,@dataLivrare,@discount)

		SELECT * FROM Inghetate	
		SELECT * FROM Comenzi
		SELECT * FROM Livrarii
		PRINT('S-a adaugat livrarea')
	END
END
GO

GO
CREATE OR ALTER FUNCTION Afisare(
	@numar INT
)
RETURNS TABLE AS RETURN 
	SELECT i.Denumire AS Denumire
	FROM Inghetate i INNER JOIN Livrarii l ON
	i.idInghetata=l.idInghetata
	Group by i.Denumire
	HAVING COUNT(l.idInghetata) >= @numar;
GO

SELECT * FROM Afisare(2)