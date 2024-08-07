CREATE TABLE formas_pagamento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_externo UUID DEFAULT random_uuid(),
    tipo VARCHAR(50) NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,
    barbearia_id INT NOT NULL,
    FOREIGN KEY (barbearia_id) REFERENCES barbearias(id) ON DELETE CASCADE
);