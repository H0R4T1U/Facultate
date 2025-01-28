CREATE TABLE [dbo].[ClientsAccounts] (
    [client_id]  INT          NOT NULL,
    [account_id] INT          NOT NULL,
    [role]       VARCHAR (20) NOT NULL,
    CONSTRAINT [pk_ClientsAccounts] PRIMARY KEY CLUSTERED ([client_id] ASC, [account_id] ASC),
    FOREIGN KEY ([account_id]) REFERENCES [dbo].[Accounts] ([account_id]),
    FOREIGN KEY ([client_id]) REFERENCES [dbo].[Clients] ([client_id])
);


GO

