DROP TABLE IF EXISTS accounts;

ROLLBACK;

COMMIT;

CREATE TABLE accounts (
	account_id SERIAL PRIMARY KEY, -- 2 primary key = composit id
	client_id INTEGER NOT NULL,
	account_type VARCHAR(100) NOT NULL,
	amount Numeric NOT NULL,
	
	CONSTRAINT fk_cilent FOREIGN KEY(client_id)
		REFERENCES clients(client_id)
);

-- on DELETE CASCADE TABLE
CREATE TABLE accounts (
	account_id SERIAL PRIMARY KEY, -- 2 primary key = composit id
	client_id INTEGER NOT NULL,
	account_type VARCHAR(100) NOT NULL,
	amount Numeric NOT NULL,
	
	CONSTRAINT fk_cilent FOREIGN KEY(client_id)
		REFERENCES clients(client_id) ON DELETE CASCADE
);

-- ========== INSERT

-- Insering rows
INSERT INTO accounts (client_id, account_type, amount)
VALUES
(1, 'checking', 2000),
(2, 'checking', 6000),
(2, 'saving', 80000),
(3, 'checking', 7000),
(3, 'saving', 75000)

-- Inserting new rows
INSERT INTO accounts (client_id, account_type, amount)
VALUES
	(1, 'saving', 65000);

-- ========== UPDATE 
UPDATE accounts
SET
	amount = 500
WHERE
	client_id = 2
AND	
	account_id = 2;

-- ========== QUERYING
	
-- Querying all columns
SELECT *
FROM accounts;

-- Querying by account id
SELECT *
FROM accounts
WHERE account_id = 4;

-- Querying by client id
SELECT *
FROM accounts
WHERE client_id = 2;

-- Querying by client id and account id
SELECT *
FROM accounts
WHERE client_id = 2 AND account_id = 2;

-- === DELETE 
DELETE FROM accounts 
WHERE account_id = 2;
