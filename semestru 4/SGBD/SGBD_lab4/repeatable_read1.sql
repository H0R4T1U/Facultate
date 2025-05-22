INSERT INTO Accounts( account_type, balance,opening_date,status) VALUES
('TEST', 150, GETDATE(),'TEST!@#');

BEGIN TRANSACTION
	WAITFOR DELAY '00:00:07'
	UPDATE Accounts SET status = 'TEST123'
	WHERE  account_type = 'TEST'
COMMIT TRAN;

select * from Accounts where account_type='TEST';
WAITFOR DELAY '00:00:05'
delete from Accounts where account_type = 'TEST';
