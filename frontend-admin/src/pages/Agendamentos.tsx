import React, { useCallback, useEffect, useState } from "react";
import { Agendamento, AgendamentoReduzido } from "../models/Agendamento";
import { AgendamentoService } from "../services/AgendamentoService";
import { Button, Col, Container, Row } from "react-bootstrap";
import MenuLateral from "../components/MenuLateral";
import { BiPlus } from "react-icons/bi";
import Calendario, { AgendamentoCalendario } from "../components/Calendario";
import { Loading } from "../components/Loading";
import { Error } from "../components/Error";
import { AgendamentoModal } from "../components/AgendamentoModal";

export const Agendamentos: React.FC = () => {
    const idBarbearia = localStorage.getItem('idBarbearia') as string;
    const [agendamentos, setAgendamentos] = useState<AgendamentoReduzido[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [loadingAgendamento, setLoadingAgendamento] = useState<boolean>(false);
    const [error, setError] = useState<string | null>(null);
    const [showModal, setShowModal] = useState<boolean>(false);
    const [selectedAgendamento, setSelectedAgendamento] = useState<Agendamento | null>(null);

    const fetchAgendamentos = useCallback(async () => {
        try {
            const agendamentosEncontrados: AgendamentoReduzido[] = await AgendamentoService.buscarAgendamentosDaBarbearia(idBarbearia);
            setAgendamentos(agendamentosEncontrados);
        } catch (error) {
            setError(`Erro ao buscar agendamentos ${error}`);
        } finally {
            setLoading(false);
        }
    }, [idBarbearia]);

    const fetchAgendamento = async (idExternoAgendamento: string) => {
        try {
            setShowModal(true);
            const agendamentoDetalhado: Agendamento = await AgendamentoService.buscarAgendamentoPorIdExterno(idExternoAgendamento);
            setSelectedAgendamento(agendamentoDetalhado);
            console.log(agendamentoDetalhado);                        
        } catch (error) {
            setError(`Èrro ao buscar agendamento: ${error}`);
        } finally {
            setLoadingAgendamento(false);
        }
    }

    const handleAgendamentoClick = (idExternoAgendamento: string) => {
        setLoadingAgendamento(true);
        fetchAgendamento(idExternoAgendamento);
    };

    const handleCloseModal = () => {
        setShowModal(false);
        setSelectedAgendamento(null);
    }

    useEffect(() => {
        fetchAgendamentos();
    }, [fetchAgendamentos]);

    if (loading) {
        return (
            <Loading message="Buscando agendamentos..." />
        );
    }

    if (error) {
        return <Error error={error} />
    }

    const agendamentosCalendarios: AgendamentoCalendario[] = agendamentos.map(agendamento => ({
        id: agendamento.idExterno,
        title: `${agendamento.nomeCliente} - ${agendamento.servicos.toString()}`,
        start: new Date(agendamento.dataHoraInicio),
        end: new Date(agendamento.dataHoraFim),
        extendedProps: { 
            idExternoAgendamento: agendamento.idExterno
        }
    }));

    return (
        <div className="app-container">
            <MenuLateral />
            <Container fluid className="content">
                <Row>
                    <Col xs={12} md={6} lg={9}>
                        <h1>Agendamentos</h1>
                    </Col>
                    <Col xs={12} md={3} lg={3} className="d-flex justify-content-end">
                        <Button className="mt-2"><BiPlus />Novo agendamento</Button>
                    </Col>
                </Row>
                <Row className="mt-5">
                    <Calendario 
                        agendamentos={agendamentosCalendarios} 
                        onAgendamentoClick={handleAgendamentoClick} />
                </Row>
            </Container>

            <AgendamentoModal
                loadingAgendamento={loadingAgendamento}
                selectedAgendamento={selectedAgendamento}
                showModal={showModal}
                handleCloseModal={handleCloseModal}
            />
        </div>
    );
}