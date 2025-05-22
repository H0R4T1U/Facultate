SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[InsertClientLoan_PartialRollback]
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
    DECLARE @client_id BIGINT = NULL;
    DECLARE @loan_id BIGINT = NULL;

    BEGIN TRY
        INSERT INTO Clients (first_name, last_name, email, date_of_birth, address)
        VALUES (@first_name, @last_name, @email, @date_of_birth, @address);
        SET @client_id = SCOPE_IDENTITY();

        INSERT INTO ActionLog (action_type, details)
        VALUES ('InsertClient_PartialRollback', CONCAT('ClientID: ', @client_id));

        BEGIN TRY
            INSERT INTO Loans (loan_amount, loan_type, interest_rate, issue_date, due_date, remaining_balance)
            VALUES (@loan_amount, @loan_type, @interest_rate, @issue_date, @due_date, @loan_amount);
            SET @loan_id = SCOPE_IDENTITY();

            INSERT INTO ActionLog (action_type, details)
            VALUES ('InsertLoan_PartialRollback', CONCAT('LoanID: ', @loan_id));

            BEGIN TRY
                INSERT INTO Client_Loans (client_id, loan_id, role)
                VALUES (@client_id, @loan_id, @role);

                INSERT INTO ActionLog (action_type, details)
                VALUES ('InsertClientLoanLink_PartialRollback', CONCAT('ClientID: ', @client_id, ', LoanID: ', @loan_id));
            END TRY
            BEGIN CATCH
                INSERT INTO ActionLog (action_type, details, error_message)
                VALUES ('InsertClientLoanLink_PartialRollback', CONCAT('ClientID: ', @client_id, ', LoanID: ', @loan_id), ERROR_MESSAGE());
                -- fail pe link intre client si loan 
            END CATCH
        END TRY
        BEGIN CATCH
            INSERT INTO ActionLog (action_type, details, error_message)
            VALUES ('InsertLoan_PartialRollback', CONCAT('ClientID: ', @client_id), ERROR_MESSAGE());
            -- fail pe loan
        END CATCH
    END TRY
    BEGIN CATCH
        INSERT INTO ActionLog (action_type, details, error_message)
        VALUES ('InsertClient_PartialRollback', 'Insert client failed', ERROR_MESSAGE());
        -- nimic
    END CATCH
END
GO
