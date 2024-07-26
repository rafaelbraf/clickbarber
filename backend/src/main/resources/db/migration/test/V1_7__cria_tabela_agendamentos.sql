CREATE TABLE IF NOT EXISTS agendamentos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_externo UUID DEFAULT random_uuid(),
    data_hora TIMESTAMP NOT NULL,
    valor_total NUMERIC(10, 2) NOT NULL,
    tempo_duracao_em_minutos INTEGER NOT NULL,
    barbearia_id BIGINT REFERENCES barbearias(id),
    cliente_id BIGINT REFERENCES clientes(id),
    CONSTRAINT fk_barbearia_agendamentos FOREIGN KEY (barbearia_id) REFERENCES barbearias(id),
    CONSTRAINT fk_cliente FOREIGN KEY (cliente_id) REFERENCES clientes(id)
);