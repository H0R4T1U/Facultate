-- tabelele pe care vom lucra: Cluburi, Terenuri, ManageriTerenuri, RezervariTerenuri, Rezervari
-- validari:
-- pentru Branch : validam nume si oras sa nu fie null
-- pentru Accounts : validam sa nu fie tipul null
-- pentru Transactions : validam sa existe cheia straina in Accouints si type sa fie ne null
-- pentru ClientAccounts : validam sa existe ambele chei straine
-- pentru Clienti : cheia branch ului sa fie existenta


-- functie de validare pentru Branch

GO
CREATE OR ALTER FUNCTION isBranchValid(@Nume VARCHAR(50),@City VARCHAR(50)) RETURNS INT
AS 
BEGIN
	IF @Nume IS NOT NULL AND LEN(@Nume) > 1 AND @City IS NOT NULL AND LEN(@City) > 1
	BEGIN
		RETURN 1
	END

	RETURN 0
END


-- functie de validare pentru Accounts
GO
CREATE OR ALTER FUNCTION isStatusValid(@Status VARCHAR(20)) RETURNS INT
AS
BEGIN
	IF @Status IS NOT NULL AND LEN(@Status) > 1
	BEGIN
		RETURN 1 
	END
	
	RETURN 0 
END




-- functie de validare pentru Transactions
GO
CREATE OR ALTER FUNCTION isTransactionValid(@ForeignKey INT,@transactionType varchar(20)) RETURNS INT
AS
BEGIN
	IF EXISTS (SELECT * FROM Accounts WHERE @ForeignKey = Accounts.account_id)
	 AND @transactionType IS NOT NULL AND LEN(@transactionType) > 1
	BEGIN
		RETURN 1
	END

	RETURN 0
END


-- functie de validare pentru ClientAccounts
GO
CREATE OR ALTER FUNCTION isRezervariTerenuriForeignKeyValid(@ClientKey INT, @AccountKey INT) RETURNS INT
AS
BEGIN
	IF EXISTS (SELECT * FROM Accounts WHERE Accounts.account_id = @AccountKey) AND
	   EXISTS (SELECT * FROM Clients WHERE Clients.client_id = @ClientKey)
	   BEGIN
		RETURN 1
	END

	RETURN 0
END


-- functii de validare pentru Clienti
GO
CREATE OR ALTER FUNCTION isBranchForeignKeyValid(@ForeignKey INT) RETURNS INT
AS
BEGIN
	IF EXISTS (SELECT * FROM Branches WHERE @ForeignKey = Branches.branch_id)
	BEGIN
		RETURN 1
	END

	RETURN 0
END