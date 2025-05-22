SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[InsertClientLoan_FullRollback]
    @first_name NVARCHAR(100),
    @last_name NVARCHAR(100),
    @email NVARCHAR(100),
    @date_of_birth DATE,
    @address NVARCHAR(200),
    @loan_amount DECIMAL(18,2),
    @loan_type NVARCHAR(50),
    @interest_rate DECIMAL(5,2),
    @issue_date DATE,
    @due_date DATE,
    @role NVARCHAR(50)
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        BEGIN TRANSACTION;

        INSERT INTO Clients (first_name, last_name, email, date_of_birth, address)
        VALUES (@first_name, @last_name, @email, @date_of_birth, @address);

        DECLARE @client_id BIGINT = SCOPE_IDENTITY();

        INSERT INTO Loans (loan_amount, loan_type, interest_rate, issue_date, due_date, remaining_balance)
        VALUES (@loan_amount, @loan_type, @interest_rate, @issue_date, @due_date, @loan_amount);

        DECLARE @loan_id BIGINT = SCOPE_IDENTITY();

        INSERT INTO Client_Loans (client_id, loan_id, role)
        VALUES (@client_id, @loan_id, @role);

        INSERT INTO ActionLog (action_type, details)
        VALUES ('InsertClientLoan_FullRollback', CONCAT('ClientID: ', @client_id, ', LoanID: ', @loan_id));

        COMMIT TRANSACTION;
    END TRY
    BEGIN CATCH
        ROLLBACK TRANSACTION;
        INSERT INTO ActionLog (action_type, details, error_message)
        VALUES ('InsertClientLoan_FullRollback', 'Insert failed', ERROR_MESSAGE());
        THROW;
    END CATCH
END
GO
