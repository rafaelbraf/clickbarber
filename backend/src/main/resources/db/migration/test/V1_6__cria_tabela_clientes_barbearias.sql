CREATE TABLE IF NOT EXISTS clientes_barbearias (
    cliente_id BIGINT NOT NULL,
    barbearia_id BIGINT NOT NULL,
    PRIMARY KEY (cliente_id, barbearia_id),
    FOREIGN KEY (cliente_id) REFERENCES clientes(id),
    FOREIGN KEY (barbearia_id) REFERENCES barbearias(id)
);