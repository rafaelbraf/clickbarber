CREATE TABLE formas_pagamento (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    id_externo UUID UNIQUE NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,
    barbearia_id BIGINT REFERENCES barbearias(id),
    FOREIGN KEY (barbearia_id) REFERENCES barbearias(id) ON DELETE CASCADE
);