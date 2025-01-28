CREATE TABLE [dbo].[Branches] (
    [branch_id] INT          IDENTITY (1, 1) NOT NULL,
    [name]      VARCHAR (50) NOT NULL,
    [adress]    VARCHAR (50) NULL,
    [city]      VARCHAR (20) NOT NULL,
    [phone]     VARCHAR (20) NULL,
    [email]     VARCHAR (50) NULL,
    PRIMARY KEY CLUSTERED ([branch_id] ASC)
);


GO

