USE BANK;
GO

-- Create View_Transactions without the ORDER BY
CREATE OR ALTER VIEW View_Transactions AS
SELECT TOP 10000 transaction_id, 
               account_id, 
               transaction_type, 
               amount, 
               transaction_date, 
               description
FROM Transactions;

SELECT * FROM View_Transactions
-- Ensure Index Support for Transactions
CREATE NONCLUSTERED INDEX index_account_id_transactions 
ON Transactions (account_id) INCLUDE (transaction_type, amount, transaction_date, description);

CREATE NONCLUSTERED INDEX index_transaction_date 
ON Transactions (transaction_date);
GO

CREATE OR ALTER VIEW View_ClientAccounts AS
SELECT 
    a.account_id, 
    a.account_type, 
    a.status, 
    a.balance, 
    c.first_name, 
    c.last_name
FROM 
    Accounts a
WITH (NOLOCK)
INNER JOIN 
    ClientsAccounts ca
WITH (NOLOCK)
    ON a.account_id = ca.account_id
INNER JOIN 
    Clients c
WITH (NOLOCK)
    ON ca.client_id = c.client_id;
GO


-- Optimized Query for View_ClientAccounts
SELECT * FROM View_ClientAccounts

-- Ensure Index Support for Accounts, ClientsAccounts, and Clients
CREATE NONCLUSTERED INDEX index_account_details 
ON Accounts (account_id) INCLUDE (account_type, status, balance);

CREATE NONCLUSTERED INDEX index_client_account 
ON ClientsAccounts (client_id, account_id) INCLUDE (role);

CREATE NONCLUSTERED INDEX index_branch_id_clients 
ON Clients (branch_id);

CREATE NONCLUSTERED INDEX index_client_id 
ON Clients (client_id) INCLUDE (first_name, last_name);
GO

-- Drop indexes on Transactions
DROP INDEX IF EXISTS index_account_id_transactions ON Transactions;
DROP INDEX IF EXISTS index_transaction_date ON Transactions;

-- Drop indexes on ClientsAccounts
DROP INDEX IF EXISTS index_client_account ON ClientsAccounts;

-- Drop indexes on Clients
DROP INDEX IF EXISTS index_branch_id_clients ON Clients;
DROP INDEX IF EXISTS index_client_id ON Clients;

-- Drop indexes on Accounts
DROP INDEX IF EXISTS index_account_details ON Accounts;
