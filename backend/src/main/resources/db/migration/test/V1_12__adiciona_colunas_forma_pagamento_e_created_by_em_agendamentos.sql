ALTER TABLE agendamentos
ADD COLUMN forma_pagamento_id BIGINT
REFERENCES formas_pagamento(id);

ALTER TABLE agendamentos
ADD COLUMN created_by BIGINT NOT NULL DEFAULT 1
REFERENCES usuarios(id);