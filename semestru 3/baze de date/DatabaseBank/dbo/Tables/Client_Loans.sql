CREATE TABLE [dbo].[Client_Loans] (
    [loan_id]   INT          NOT NULL,
    [client_id] INT          NOT NULL,
    [role]      VARCHAR (20) NOT NULL,
    CONSTRAINT [pk_ClientsLoans] PRIMARY KEY CLUSTERED ([client_id] ASC, [loan_id] ASC),
    FOREIGN KEY ([client_id]) REFERENCES [dbo].[Clients] ([client_id]),
    FOREIGN KEY ([loan_id]) REFERENCES [dbo].[Loans] ([loan_id])
);


GO

