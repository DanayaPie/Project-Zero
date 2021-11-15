DROP TABLE If EXISTS clients;

ROLLBACK;

COMMIT;

CREATE TABLE clients (
	client_id SERIAL PRIMARY KEY,
	client_first_name VARCHAR(255) NOT NULL,
	client_last_name VARCHAR(255) NOT NULL,
	client_birthdate CHAR(8) NOT NULL,
CONSTRAINT chk_client_birthdate CHECK (client_birthdate NOT LIKE '%[^0-9]%')
);

-- ========== INSERT
INSERT INTO clients (client_first_name, client_last_name, client_birthdate)
VALUES
	('Jinx', 'Fury', '11111996'),
	('Ash', 'Kueen', '03051954'),
	('Tryndamere', 'Qing', '09271893');

INSERT INTO clients (client_first_name, client_last_name, client_birthdate)
VALUES
	('Morgana', 'Dark', '11111111')
	
-- ========== UPDATE 
UPDATE clients
SET 
	client_first_name = 'Ash',
	client_last_name = 'Qing',
	client_birthdate = '111196',
WHERE 
	client_id = 2;

-- ========== QUERYING

-- Querying all columns
SELECT *
FROM clients;

-- Querying specific columns
SELECT client_id, client_first_name, client_last_name,
FROM clients;

-- Querying specific client by clientId
SELECT *
FROM clients
WHERE client_id = 2;

-- ========== DELETE
	
-- DELETE by ID
DELETE
FROM clients
WHERE 
client_id = 12;

-- DELETE all
DELETE
FROM clients



