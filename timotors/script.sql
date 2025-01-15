CREATE DATABASE tiMotors;

USE tiMotors;

CREATE TABLE veiculo (
	id_veiculo INT AUTO_INCREMENT PRIMARY KEY,
    modelo VARCHAR(100) NOT NULL,
    marca VARCHAR(100) NOT NULL,
    ano INT NOT NULL,
    preco DECIMAL(10, 2) NOT NULL,
    cor VARCHAR(50) NOT NULL,
    estado VARCHAR(30) NOT NULL 
);

CREATE TABLE cliente (
	id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(11) NOT NULL,
    telefone VARCHAR(11) NOT NULL,
    email VARCHAR(100) NOT NULL,
    interesse VARCHAR(30) NOT NULL
);

CREATE TABLE funcionario (
	id_funcionario INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(11) NOT NULL,
    telefone VARCHAR(11) NOT NULL,
    email VARCHAR(100) NOT NULL
);