CREATE TABLE [dbo].[Accounts] (
    [account_id]   INT          IDENTITY (1, 1) NOT NULL,
    [account_type] VARCHAR (50) NULL,
    [balance]      FLOAT (53)   DEFAULT ((0)) NULL,
    [opening_date] DATE         NULL,
    [status]       VARCHAR (20) NOT NULL,
    PRIMARY KEY CLUSTERED ([account_id] ASC)
);


GO

