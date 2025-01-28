CREATE TABLE [dbo].[Clients] (
    [client_id]     INT          IDENTITY (1, 1) NOT NULL,
    [branch_id]     INT          NULL,
    [first_name]    VARCHAR (20) NOT NULL,
    [last_name]     VARCHAR (20) NOT NULL,
    [email]         VARCHAR (50) NULL,
    [date_of_birth] DATE         NULL,
    [date_of_join]  DATE         NULL,
    [address]       VARCHAR (50) NOT NULL,
    PRIMARY KEY CLUSTERED ([client_id] ASC),
    FOREIGN KEY ([branch_id]) REFERENCES [dbo].[Branches] ([branch_id])
);


GO

