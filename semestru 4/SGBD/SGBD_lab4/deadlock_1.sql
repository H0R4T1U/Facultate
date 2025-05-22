SET DEADLOCK_PRIORITY HIGH
-- SET DEADLOCK_PRIORITY LOW

BEGIN TRANSACTION
	update Branches set name = 'asdf'
	where branch_id = 1;
	
	WAITFOR DELAY '00:00:05';

	update Accounts set balance = 200 
    where account_id = 1;
COMMIT TRAN;


update Accounts set balance = 100
	where account_id = 1;
update Branches set name = 'xxxz'
	where branch_id = 1;