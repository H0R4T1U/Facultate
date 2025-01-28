

-- Procedura 1
CREATE OR ALTER PROCEDURE do_proc_1 AS
ALTER TABLE dbo.Clients
ALTER COLUMN first_name VARCHAR(200)
PRINT 'S-a modificat coloana [first_name] din tabelul [Clients]'
GO

-- Procedura 2
CREATE OR ALTER  PROCEDURE do_proc_2 AS
ALTER TABLE dbo.Accounts
ADD CONSTRAINT min_balance DEFAULT 5.00 FOR balance
PRINT 'S-a adaugat constrangere pentru coloana [balance] din tabelul [Accounts]'
GO

-- Procedura 3
CREATE OR ALTER  PROCEDURE do_proc_3 AS
DROP TABLE IF EXISTS Complaints
CREATE TABLE Complaints(
complaint_id INT NOT NULL PRIMARY KEY,
title VARCHAR(50) NOT NULL Unique,
description VARCHAR(200) NOT NULL,
)   
PRINT 'S-a creat un nou tabel [Complaints]'
GO

-- DROP PROCEDURE do_proc_4
CREATE OR ALTER PROCEDURE do_proc_4 AS
ALTER TABLE dbo.Complaints
ADD is_solved INT
PRINT 'S-a adaugat un camp nou in tabela [Complaints]'
GO

-- DROP PROCEDURE do_proc_5
CREATE OR ALTER PROCEDURE do_proc_5 AS
ALTER TABLE dbo.Complaints
ADD CONSTRAINT foreign_key_id FOREIGN KEY(complaint_id) REFERENCES Accounts(account_id)
PRINT 'S-a adaugat cheie straina'
GO

-- UNDO

-- DROP PROCEDURE undo_proc_1
CREATE OR ALTER PROCEDURE undo_proc_1 AS
ALTER TABLE dbo.Clients
ALTER COLUMN first_name VARCHAR(20)
PRINT 'S-a remodificat coloana [first_name] din tabelul [Clients]'
GO

-- DROP PROCEDURE undo_proc_2
CREATE OR ALTER PROCEDURE undo_proc_2 AS
ALTER TABLE dbo.Accounts
DROP CONSTRAINT IF EXISTS min_balance
PRINT 'S-a eliminat constrangerea pentru coloana [balance] din tabelul [Accounts]'
GO

-- DROP PROCEDURE undo_proc_3
CREATE OR ALTER PROCEDURE undo_proc_3 AS
DROP TABLE dbo.Complaints
PRINT 'S-a sters tabelul [Complaints]'
GO

-- DROP PROCEDURE undo_proc_4
CREATE OR ALTER PROCEDURE undo_proc_4 AS
ALTER TABLE dbo.Complaints
DROP COLUMN is_solved
PRINT 'S-a eliminat coloana [is_solved]  din [Complaints]'
GO

-- DROP PROCEDURE undo_proc_5
CREATE OR ALTER PROCEDURE undo_proc_5 AS
ALTER TABLE dbo.Complaints
DROP CONSTRAINT IF EXISTS foreign_key_id
PRINT 'S-a eliminat cheia straina'
GO

DROP TABLE IF EXISTS VersiuneDB
CREATE TABLE VersiuneDB (
	versionNo int
);

INSERT INTO VersiuneDB VALUES(0)


SELECT * FROM VersiuneDB

GO
CREATE OR ALTER PROCEDURE main @versiune INT
AS
BEGIN
		
		IF @versiune < 0 OR @versiune > 5
		BEGIN
			PRINT 'Versiunea este invalida!'
			RETURN
		END

		DECLARE @versiune_curenta AS INT
		SET @versiune_curenta = (SELECT versionNo FROM VersiuneDB)

		IF @versiune = @versiune_curenta
		BEGIN
			PRINT 'Versiunea actuala este deja cea curenta!'
			RETURN
		END

		DECLARE @proc VARCHAR(20)
		DECLARE @proc_undo VARCHAR(20)

		DECLARE @ok AS INT
		SET @ok = 0

		DELETE FROM VersiuneDB
		INSERT INTO VersiuneDB(versionNo) VALUES (@versiune)

		WHILE(@versiune_curenta < @versiune)
			BEGIN
				SET @ok = @ok + 1
				SET @versiune_curenta = @versiune_curenta + 1
				SET @proc = 'do_proc_' + CAST(@versiune_curenta AS VARCHAR(10))
				PRINT 'Se executa ' + @proc
				EXEC @proc
			END

		IF(@ok > 0)
		BEGIN
			RETURN
		END

		WHILE(@versiune_curenta > @versiune)
			BEGIN
				SET @proc_undo = 'undo_proc_' + CAST(@versiune_curenta AS VARCHAR(10))
				PRINT 'Se executa ' + @proc_undo
				EXEC @proc_undo
				SET @versiune_curenta = @versiune_curenta - 1
			END

END
GO



-- exec do_proc_1     
-- exec undo_proc_1   

-- exec do_proc_2
-- exec undo_proc_2

-- exec do_proc_3


-- exec do_proc_4
-- exec undo_proc_4

-- exec do_proc_5
-- exec undo_proc_5
-- exec undo_proc_3


exec main 0
exec main 1
exec main 2
exec main 3
exec main 4
exec main 5
exec main 10