

CREATE TABLE cliente (
    id_cliente      SERIAL PRIMARY KEY,
    nome            VARCHAR(100) NOT NULL,
    cnh             CHAR(11)     UNIQUE NOT NULL,
    cpf             CHAR(11)     UNIQUE NOT NULL,
    data_nascimento DATE         NOT NULL,
    rua             TEXT         NOT NULL,
    cep             TEXT         NOT NULL,
    numero          TEXT         NOT NULL,
    bairro          TEXT         NOT NULL,
    cidade          TEXT         NOT NULL,
    email1          VARCHAR(100) NOT NULL,
    email2          VARCHAR(100),
    telefone1       VARCHAR(11)  NOT NULL,
    telefone2       VARCHAR(11)
);

CREATE TABLE automovel (
    id_automovel    SERIAL PRIMARY KEY,
    marca           TEXT         NOT NULL,
    modelo          TEXT         NOT NULL,
    cor             TEXT         NOT NULL,
    ano             VARCHAR(4)   NOT NULL,
    chassi          VARCHAR(17)  UNIQUE NOT NULL,
    cambio          VARCHAR(20)  NOT NULL,
    disponibilidade BOOLEAN      DEFAULT TRUE,
    quilometragem   DECIMAL(10,2) NOT NULL,
    valor_diaria    DECIMAL(10,2) NOT NULL
);

CREATE TABLE funcionario (
    id_funcionario  SERIAL PRIMARY KEY,
    nome            TEXT         NOT NULL,
    cpf             CHAR(11)     UNIQUE NOT NULL,
    data_nascimento DATE         NOT NULL,
    cep             TEXT         NOT NULL,
    rua             TEXT         NOT NULL,
    bairro          TEXT         NOT NULL,
    numero          TEXT         NOT NULL,
    cidade          TEXT         NOT NULL,
    email1          VARCHAR(100) NOT NULL,
    email2          VARCHAR(100),
    telefone1       VARCHAR(11)  NOT NULL,
    telefone2       VARCHAR(11),
    senha           TEXT         NOT NULL
);

CREATE TABLE aluguel (
    id_aluguel      SERIAL PRIMARY KEY,
    id_automovel    INTEGER      NOT NULL,
    id_cliente      INTEGER      NOT NULL,
    id_funcionario  INTEGER      NOT NULL,
    data_do_aluguel DATE         NOT NULL,
    data_prevista   DATE         NOT NULL,
    data_entregue   DATE,
    valor           DECIMAL(10,2),

    FOREIGN KEY (id_automovel)   REFERENCES automovel(id_automovel),
    FOREIGN KEY (id_cliente)     REFERENCES cliente(id_cliente),
    FOREIGN KEY (id_funcionario) REFERENCES funcionario(id_funcionario)
);


INSERT INTO funcionario (nome, cpf, data_nascimento, cep, rua, bairro, numero, cidade,
                         email1, telefone1, senha)
VALUES ('Administrador', '00000000000', '1990-01-01', '00000000', 'Rua Exemplo',
        'Centro', '1', 'Pau dos Ferros', 'admin@katchow.com', '00000000000', 'admin123');
