CREATE TABLE barbearias (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    id_externo UUID UNIQUE NOT NULL,
    cnpj VARCHAR(14) UNIQUE NOT NULL,
    nome VARCHAR(100) NOT NULL,
    endereco VARCHAR(255),
    telefone VARCHAR(15) NOT NULL,
    usuario_id BIGINT REFERENCES usuarios(id),
    CONSTRAINT fk_usuario_barbearias FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);