DELETE TABLE IF EXISTS accounts;

ROLLBACK;

COMMIT;

CREAT TABLE accounts {
	account_id SERIAL PRIMARY KEY, -- 2 primary key = composit id
	client_id INTEGER PRIMARY KEY,
	account_type VARCHAR)(100) NOT NULL,
	amount MONEY NOT NULL,
	interest FLOAT NOT NULL,
	
	CONSTRAINT fk_cilent FOREIGN KEY(client_id)
		REFERENCES client(client_id)
);

-- ========== INSERTING

-- Insering rows
INSERT INTO accounts (client_id, account_type, amount, interest)
VALUES
(1, 'checking', 2000, 0.01),
(2, 'checking', 6000, 0.05),
(2, 'saving', 80000, 0.30),
(3, 'checking', 7000, 0.05),
(3, 'saving', 75000, 0.30)

-- Inserting new rows
INSERT INTO accounts (client_id, account_type, amount, interest)
VALUES
	(1, 'saving', 65000, 0.05);

-- ========== UPDATE 
UPDATE accounts
SET
	

-- ========== QUERYING
	
-- Querying all columns
SELECT account_id, client_id, account_type, amount, interest
FROM accounts;

-- Querying by account id
SELECT *
FROM accounts
WHERE client_id = 2;

-- Querying sepcific columns
SELECT account_id, accoount_type, amount
FROM grades
WHERE clien_id = 3;

