CREATE TABLE [dbo].[Deposits] (
    [deposit_id]    INT        IDENTITY (1, 1) NOT NULL,
    [client_id]     INT        NULL,
    [amount]        INT        NOT NULL,
    [deposit_date]  DATE       NULL,
    [interest_rate] FLOAT (53) NOT NULL,
    [maturity_date] DATE       NOT NULL,
    PRIMARY KEY CLUSTERED ([deposit_id] ASC),
    FOREIGN KEY ([client_id]) REFERENCES [dbo].[Clients] ([client_id])
);


GO

