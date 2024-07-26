CREATE TABLE IF NOT EXISTS barbeiros (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    id_externo UUID UNIQUE NOT NULL,
    cpf VARCHAR(11) UNIQUE NOT NULL,
    nome VARCHAR(100) NOT NULL,
    celular VARCHAR(15) NOT NULL,
    admin BOOLEAN NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    barbearia_id BIGINT REFERENCES barbearias(id),
    CONSTRAINT fk_barbearia_barbeiros FOREIGN KEY (barbearia_id) REFERENCES barbearias(id),
    usuario_id BIGINT REFERENCES usuarios(id),
    CONSTRAINT fk_usuario_barbeiros FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);