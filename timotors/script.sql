CREATE DATABASE tiMotors;

USE tiMotors;

CREATE TABLE veiculo (
	id_veiculo INT AUTO_INCREMENT PRIMARY KEY,
    modelo VARCHAR(100) NOT NULL,
    marca VARCHAR(100) NOT NULL,
    ano INT NOT NULL,
    preco DECIMAL(10, 2) NOT NULL,
    cor VARCHAR(50) NOT NULL,
    estado ENUM('USADO', 'SEMINOVO', 'NOVO') NOT NULL
);

CREATE TABLE cliente (
	id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(11) NOT NULL,
    telefone VARCHAR(11) NOT NULL,
    email VARCHAR(100) NOT NULL,
    endereco VARCHAR(255) NOT NULL,
    interesse ENUM('USADO', 'SEMINOVO', 'NOVO') NOT NULL
);

CREATE TABLE funcionario (
	id_funcionario INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(11) NOT NULL,
    telefone VARCHAR(11) NOT NULL,
    email VARCHAR(100) NOT NULL,
    salario DECIMAL(10, 2) NOT NULL,
    anosNaEmpresa INT NOT NULL,
    dataAdmissao DATE NOT NULL,
    cargo ENUM('ANALISTA', 'GERENTE', 'DIRETOR', 'VENDEDOR') NOT NULL
);
