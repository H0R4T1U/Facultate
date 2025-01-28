CREATE TABLE [dbo].[Loans] (
    [loan_id]           INT          IDENTITY (1, 1) NOT NULL,
    [loan_amount]       INT          NOT NULL,
    [loan_type]         VARCHAR (20) NULL,
    [interest_rate]     FLOAT (53)   NOT NULL,
    [issue_date]        DATE         NULL,
    [due_date]          DATE         NOT NULL,
    [remaining_balance] FLOAT (53)   NOT NULL,
    PRIMARY KEY CLUSTERED ([loan_id] ASC)
);


GO

