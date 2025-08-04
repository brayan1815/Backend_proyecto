CREATE DATABASE bd_proyecto_brayan CHARACTER SET utf8mb4;
CREATE USER 'brayan_adso2894667'@'localhost' IDENTIFIED BY 'Aprendiz2025';

GRANT ALL PRIVILEGES ON bd_proyecto_brayan.* TO "brayan_adso2894667"@"localhost";

FLUSH PRIVILEGES;