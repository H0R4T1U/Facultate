CREATE TABLE [dbo].[Transactions] (
    [transaction_id]   INT           IDENTITY (1, 1) NOT NULL,
    [account_id]       INT           NULL,
    [transaction_type] VARCHAR (20)  NOT NULL,
    [amount]           INT           NOT NULL,
    [transaction_date] DATE          NULL,
    [description]      VARCHAR (100) NULL,
    PRIMARY KEY CLUSTERED ([transaction_id] ASC),
    FOREIGN KEY ([account_id]) REFERENCES [dbo].[Accounts] ([account_id])
);


GO

