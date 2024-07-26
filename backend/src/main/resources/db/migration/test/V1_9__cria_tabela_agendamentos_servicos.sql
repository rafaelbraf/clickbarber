CREATE TABLE IF NOT EXISTS agendamentos_servicos (
    agendamento_id BIGINT NOT NULL,
    servico_id BIGINT NOT NULL,
    PRIMARY KEY (agendamento_id, servico_id),
    FOREIGN KEY (agendamento_id) REFERENCES agendamentos(id),
    FOREIGN KEY (servico_id) REFERENCES servicos(id)
);