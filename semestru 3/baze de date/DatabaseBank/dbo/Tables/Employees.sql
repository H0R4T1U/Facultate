CREATE TABLE [dbo].[Employees] (
    [employee_id] INT          IDENTITY (1, 1) NOT NULL,
    [branch_id]   INT          NULL,
    [first_name]  VARCHAR (20) NOT NULL,
    [last_name]   VARCHAR (20) NOT NULL,
    [position]    VARCHAR (20) NOT NULL,
    [hire_date]   DATE         NULL,
    [salary]      INT          NOT NULL,
    PRIMARY KEY CLUSTERED ([employee_id] ASC),
    FOREIGN KEY ([branch_id]) REFERENCES [dbo].[Branches] ([branch_id])
);


GO

