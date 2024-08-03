CREATE TABLE IF NOT EXISTS horario_funcionamento (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    dia_semana VARCHAR(10) NOT NULL CHECK (dia_semana IN ('SEGUNDA', 'TERÇA', 'QUARTA', 'QUINTA', 'SEXTA', 'SÁBADO', 'DOMINGO')),
    hora_abertura TIME,
    hora_fechamento TIME,
    barbearia_id BIGINT NOT NULL,
    FOREIGN KEY (barbearia_id) REFERENCES barbearia(id),
    CONSTRAINT check_hours CHECK (hora_abertura < hora_fechamento)
);