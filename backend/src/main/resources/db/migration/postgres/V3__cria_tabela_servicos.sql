CREATE TABLE servicos (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    id_externo UUID UNIQUE NOT NULL,
    nome VARCHAR(100) NOT NULL,
    preco NUMERIC(10, 2) NOT NULL,
    tempo_duracao_em_minutos INTEGER NOT NULL,
    barbearia_id BIGINT NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,
    CONSTRAINT fk_barbearia_servicos FOREIGN KEY (barbearia_id) REFERENCES barbearias(id)
);