-- data.sql
-- Primero, creamos las cuentas
INSERT INTO account (balance) VALUES (1000.00);
INSERT INTO account (balance) VALUES (500.50);
INSERT INTO account (balance) VALUES (75.25);

-- Segundo, creamos los usuarios con contraseñas ENCRIPTADAS
-- Contraseña para todos: "password123"
-- Hash generado con BCrypt con strength=10 (60 caracteres)
INSERT INTO app_user (name, email, password, account_id)
VALUES ('Luigi', 'luigi@mail.com', '$2a$10$Zy5QlIPhmmekwDQceIXnG.sH47a5G1bvGzEM5vy7Bg0PJzXUPTzIe', 1);

INSERT INTO app_user (name, email, password, account_id)
VALUES ('Mario', 'mario@mail.com', '$2a$10$Zy5QlIPhmmekwDQceIXnG.sH47a5G1bvGzEM5vy7Bg0PJzXUPTzIe', 2);

INSERT INTO app_user (name, email, password, account_id)
VALUES ('Peach', 'peach@mail.com', '$2a$10$Zy5QlIPhmmekwDQceIXnG.sH47a5G1bvGzEM5vy7Bg0PJzXUPTzIe', 3);