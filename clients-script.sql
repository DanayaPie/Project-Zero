DELETE TABLE If EXISTS clients;

ROLLBACK;

COMMIT;

CREATE TABLE clients (
	client_id SERIAL PRIMARY KEY,
	client_first_name VARCHAR(255) NOT NULL,
	client_last_name VARCHAR(255) NOT NULL,
	client_birthdate DATE NOT NULL,
	client_phone_number VARCHAR(255) NOT NULL,
	client_email VARCHAR(255) NOT NULL
);

INSERT INTO clients (client_first_name, client_last_name, client_birthdate, client_phone_number, client_email)
VALUES
	('Jinx', 'Fury', '1996-11-11', '5711112222', 'jinx11@gmail.com'),
	('Ash', 'Kuen', '1895-03-05', '5712223333', 'ash0305@outlook.com'),
	('Tryndamere', 'Qing', '1893-09-27', '5713334444', 'tryn0927@ymail.com');

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

-- Update 
UPDATE clients
SET 
	client_first_name = 'Ash',
	client_last_name = 'Qing',
	client_birthdate = '111196',
	client_phone_number = '5712223333'
	client_email = 'jinx01@gmail.com'
WHERE 
	client_id = 2;
	
-- DELETE by ID
DELETE
FROM clients
WHERE 
client_id = 3;

-- DELETE all
DELETE
FROM clients


