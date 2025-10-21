-- data.sql --
-- Primero, creamos las cuentas.
INSERT INTO account (balance) VALUES (1000.00);
INSERT INTO account (balance) VALUES (500.50);
INSERT INTO account (balance) VALUES (75.25);

-- Segundo, creamos los usuarios con contraseñas ENCRIPTADAS.
-- La contraseña para todos es "password123".
-- El hash fue generado usando BCrypt.
-- IMPORTANTE: El campo 'password' debe coincidir con el de tu clase User.
INSERT INTO app_user (name, email, password, account_id) VALUES ('Luigi', 'luigi@mail.com', '$2a$10$DRqjMyL5lW1f.e2A3J.pgeiH530a13d.9a/B3R.t.Y5.L.1i8.f.G', 1);
INSERT INTO app_user (name, email, password, account_id) VALUES ('Mario', 'mario@mail.com', '$2a$10$DRqjMyL5lW1f.e2A3J.pgeiH530a13d.9a/B3R.t.Y5.L.1i8.f.G', 2);
INSERT INTO app_user (name, email, password, account_id) VALUES ('Peach', 'peach@mail.com', '$2a$10$DRqjMyL5lW1f.e2A3J.pgeiH530a13d.9a/B3R.t.Y5.L.1i8.f.G', 3);
