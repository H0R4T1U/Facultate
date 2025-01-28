CREATE TABLE [dbo].[Payments] (
    [payment_id]   INT          IDENTITY (1, 1) NOT NULL,
    [loan_id]      INT          NULL,
    [amount]       INT          NOT NULL,
    [payment_date] DATE         NULL,
    [type]         VARCHAR (20) NULL,
    PRIMARY KEY CLUSTERED ([payment_id] ASC),
    FOREIGN KEY ([loan_id]) REFERENCES [dbo].[Loans] ([loan_id])
);


GO

