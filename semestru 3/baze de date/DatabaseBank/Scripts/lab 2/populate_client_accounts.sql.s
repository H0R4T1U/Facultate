Use Bank;

INSERT INTO ClientsAccounts (client_id, account_id, role)
VALUES 
  (1, 1, 'Primary'),
  (2, 2, 'Primary'),
  (3, 3, 'Primary'),
  (1, 3, 'Secondary');