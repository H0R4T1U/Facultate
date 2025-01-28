CREATE TABLE [dbo].[Managers] (
    [manager_id] INT          NOT NULL,
    [first_name] VARCHAR (20) NOT NULL,
    [last_name]  VARCHAR (20) NOT NULL,
    [hire_date]  DATE         NULL,
    [salary]     INT          NULL,
    CONSTRAINT [pk_BranchesManagers] PRIMARY KEY CLUSTERED ([manager_id] ASC),
    FOREIGN KEY ([manager_id]) REFERENCES [dbo].[Branches] ([branch_id])
);


GO

