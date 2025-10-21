-- data.sql --

-- Primero, creamos las cuentas.
-- Dejamos que la base de datos asigne los IDs automáticamente (ID=1, ID=2, ID=3)
INSERT INTO account (balance) VALUES (1000.00);
INSERT INTO account (balance) VALUES (500.50);
INSERT INTO account (balance) VALUES (75.25);

-- Segundo, creamos los usuarios y los vinculamos a las cuentas por su ID
-- app_user (name, email, account_id)
INSERT INTO app_user (name, email, account_id) VALUES ('Luigi', 'luigi@mail.com', 1);
INSERT INTO app_user (name, email, account_id) VALUES ('Mario', 'mario@mail.com', 2);
INSERT INTO app_user (name, email, account_id) VALUES ('Peach', 'peach@mail.com', 3);