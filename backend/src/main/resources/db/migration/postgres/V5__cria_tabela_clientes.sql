CREATE TABLE IF NOT EXISTS clientes (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    id_externo UUID UNIQUE NOT NULL,
    nome VARCHAR(100) NOT NULL,
    data_nascimento DATE,
    celular VARCHAR(15) NOT NULL,
    usuario_id BIGINT REFERENCES usuarios(id),
    CONSTRAINT fk_usuario_clientes FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);