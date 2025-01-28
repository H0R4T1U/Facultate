USE BANK;

INSERT INTO Transactions ( account_id, transaction_type, amount, transaction_date, description)
VALUES 
  ( 1, 'Deposit', 2000, '2021-06-01', 'Salary deposit'),
  ( 2, 'Withdrawal', 500, '2022-04-05', 'ATM withdrawal'),
  ( 1, 'Transfer', 1000, '2021-07-15', 'Transfer to savings'),
  ( 3, 'Deposit', 3000, '2020-08-12', 'Bonus payment');
