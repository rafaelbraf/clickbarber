CREATE TABLE IF NOT EXISTS horario_funcionamento (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    dia_semana VARCHAR(10) NOT NULL CHECK (dia_semana IN('SEGUNDA', 'TERÇA', 'QUARTA', 'QUINTA', 'SEXTA', 'SÁBADO', 'DOMINGO')),
    hora_abertura TIME,
    hora_fechamento TIME,
    barbearia_id BIGINT NOT NULL,
    CONSTRAINT fk_barbearia_horario_funcionamento FOREIGN KEY (barbearia_id) REFERENCES barbearias(id),
    CONSTRAINT check_hours CHECK (hora_abertura < hora_fechamento)
);