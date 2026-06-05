CREATE TABLE usuario (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(11) UNIQUE NOT NULL,
    telefone VARCHAR(11),
    email VARCHAR(100),
    endereco TEXT,
    data_nascimento DATE
);

CREATE TABLE cliente (
    id_cliente SERIAL PRIMARY KEY,
    id_usuario INTEGER UNIQUE NOT NULL,

    CONSTRAINT fk_cliente_usuario
        FOREIGN KEY (id_usuario)
        REFERENCES usuario(id)
        ON DELETE CASCADE
);

CREATE TABLE funcionario (
    id_funcionario SERIAL PRIMARY KEY,
    id_usuario INTEGER UNIQUE NOT NULL,
    cargo VARCHAR(100) NOT NULL,
    login VARCHAR(50) UNIQUE NOT NULL,
    senha VARCHAR(100) NOT NULL,

    CONSTRAINT fk_funcionario_usuario
        FOREIGN KEY (id_usuario)
        REFERENCES usuario(id)
        ON DELETE CASCADE
);

CREATE TABLE automovel (
    id SERIAL PRIMARY KEY,
    placa VARCHAR(10) UNIQUE NOT NULL,
    marca VARCHAR(100) NOT NULL,
    modelo VARCHAR(100) NOT NULL,
    ano INTEGER NOT NULL,
    disponivel BOOLEAN DEFAULT TRUE,
    valor_diaria DOUBLE PRECISION NOT NULL
);

CREATE TABLE aluguel (
    id SERIAL PRIMARY KEY,
    data_inicio DATE NOT NULL,
    data_fim DATE NOT NULL,
    valor_total DOUBLE PRECISION NOT NULL,
    status VARCHAR(50) NOT NULL,

    id_cliente INTEGER NOT NULL,
    id_funcionario INTEGER NOT NULL,
    id_automovel INTEGER NOT NULL,

    FOREIGN KEY (id_cliente)
        REFERENCES cliente(id_cliente),

    FOREIGN KEY (id_funcionario)
        REFERENCES funcionario(id_funcionario),

    FOREIGN KEY (id_automovel)
        REFERENCES automovel(id)
);