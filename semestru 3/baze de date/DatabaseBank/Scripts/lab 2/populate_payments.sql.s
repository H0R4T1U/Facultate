USE Bank;

INSERT INTO Payments (loan_id, amount, payment_date, type)
VALUES 
  (1, 500, '2021-07-01', 'Monthly payment'),
  (2, 400, '2022-05-01', 'Monthly payment'),
  (1, 500, '2021-08-01', 'Monthly payment'),
  (3, 300, '2020-09-01', 'Monthly payment');
