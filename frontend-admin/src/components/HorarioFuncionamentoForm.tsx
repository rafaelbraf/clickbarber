import React, { useEffect, useState } from "react";
import { Col, Form, Row } from "react-bootstrap";
import { HorarioFuncionamento } from "../models/HorarioFuncionamento";
import { DiaDaSemana } from "../models/DiaDaSemana";

interface HorarioFuncionamentoFormProps {
    diaDaSemana: DiaDaSemana
    horario: HorarioFuncionamento | undefined;
    disabled: boolean;
}

export const HorarioFuncionamentoForm: React.FC<HorarioFuncionamentoFormProps> = ({ diaDaSemana, horario, disabled }) => {
    const [horaAbertura, setHoraAbertura] = useState<string | undefined>('');
    const [horaFechamento, setHoraFechamento] = useState<string | undefined>('');

    useEffect(() => {        
        setHoraAbertura(horario?.horaAbertura);
        setHoraFechamento(horario?.horaFechamento);
    }, [horario]);

    const isHorarioVazio = !horaAbertura || !horaFechamento;

    return (
        <Form.Group className="mt-3" as={Row} controlId="formHorarioFuncionamento">
            <Form.Label column sm={2}>{diaDaSemana}</Form.Label>

            {isHorarioVazio && disabled ? (
                <Col sm={6}>
                    <p className="mt-2">Nenhum horário cadastrado para {diaDaSemana}.</p>
                </Col>
            ) : (
                <>
                    <Col sm={3}>
                        <Form.Control
                            type="text"
                            placeholder="00:00:00"
                            value={horaAbertura}
                            disabled={disabled}
                            onChange={(e) => setHoraAbertura(e.target.value)}
                        />
                    </Col>
                    <Col sm={3}>
                        <Form.Control
                            type="text"
                            placeholder="00:00:00"
                            value={horaFechamento}
                            disabled={disabled}
                            onChange={(e) => setHoraFechamento(e.target.value)}
                        />
                    </Col>
                </>
            )}
            
            
        </Form.Group>
    );
}