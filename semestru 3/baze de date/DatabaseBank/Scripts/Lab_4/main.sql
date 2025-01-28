GO
USE Bank
GO
dbcc checkident ('[Tables]', RESEED, 0)
dbcc checkident ('[Views]', RESEED, 0)
dbcc checkident ('[Tests]', RESEED, 0)
GO
INSERT INTO Tables(Name) VALUES ('Clients'), ('ClientAccounts'), ('Accounts');
SELECT * FROM Tables;
INSERT INTO Views(Name) VALUES ('View_Client'), ('View_ClientAccounts'), ('View_Accounts');
SELECT * FROM Views;
INSERT INTO Tests VALUES ('delete_table'), ('insert_table'), ('select_view');
SELECT * FROM Tests;
INSERT INTO TestViews VALUES (3,1), (3,2), (3,3);
SELECT * FROM TestViews;
INSERT INTO TestTables(TestID, TableID, NoOfRows, Position) 
VALUES (1, 2, 10000, 1), (1, 1, 10000, 2), (1, 3, 10000, 3), 
	   (2, 1, 10000, 4), (2, 3, 10000, 5), (2, 2, 10000, 6),
	   (3, 1, 10000, 7), (3, 2, 10000, 8), (3, 3, 10000, 9)
SELECT * FROM TestTables;


GO
CREATE OR ALTER PROCEDURE mainTest
AS
BEGIN
    DECLARE @TestId INT;
    DECLARE @TableId INT;
    DECLARE @NoOfRows INT;
    DECLARE @TableName NVARCHAR(255);
    DECLARE @Position INT;
    
    DECLARE @TestRunID INT;
    DECLARE @StartAt DATETIME;
    DECLARE @EndAt DATETIME;

    INSERT INTO TestRuns (Description, StartAt)
    VALUES ('Test', GETDATE());
    SET @TestRunID = SCOPE_IDENTITY();

    DECLARE TestCursor CURSOR FOR
    SELECT TestId, TableId, NoOfRows, Position
    FROM TestTables
    ORDER BY Position;

    OPEN TestCursor;
    FETCH NEXT FROM TestCursor INTO @TestId, @TableId, @NoOfRows, @Position;

    WHILE @@FETCH_STATUS = 0
    BEGIN
        SELECT @TableName = Name FROM Tables WHERE TableId = @TableId;
        SET @StartAt = GETDATE();

        IF @TestId = 1
        BEGIN
            EXEC deleteTableRows @TableName, @NoOfRows;
        END
        ELSE IF @TestId = 2
        BEGIN
            EXEC insertTableRows @TableName, @NoOfRows;
        END
        ELSE IF @TestId = 3
        BEGIN
            EXEC viewTableRows @TableName
        END
        SET @EndAt = GETDATE();
        IF @TestId = 1
        BEGIN
            INSERT INTO TestRunTables (TestRunID, TableID, StartAt, EndAt)
            VALUES (@TestRunId, @TableId, @StartAt, @EndAt);
        END
		ELSE IF @TestId = 2
		BEGIN
			INSERT INTO TestRunTables (TestRunID, TableID, StartAt, EndAt)
            VALUES (@TestRunId, @TableId, @StartAt, @EndAt)
		END
        ELSE IF @TestId = 3
        BEGIN
            INSERT INTO TestRunViews (TestRunID, ViewID, StartAt, EndAt)
            VALUES (@TestRunID, @TableId, @StartAt, @EndAt);
        END

        FETCH NEXT FROM TestCursor INTO @TestId, @TableId, @NoOfRows, @Position;
    END

    CLOSE TestCursor;
    DEALLOCATE TestCursor;

    UPDATE TestRuns SET EndAt = GETDATE() WHERE TestRunID = @TestRunID;
END;
GO

	GO
	DELETE FROM TestRuns
	DELETE FROM TestRunTables
	DELETE FROM TestRunViews
	DELETE FROM TestViews
	DELETE FROM Tests
	DELETE FROM Tables
	DELETE FROM Views
	DELETE FROM TestTables
	GO

	EXEC mainTest

	SELECT * FROM TestRuns
	SELECT * FROM TestRunTables
	SELECT * FROM TestRunViews

	SELECT * FROM TestViews
	SELECT * FROM TestTables

select * from ClientsAccountsTest
select * from AccountsTest
select * from ClientsTest
