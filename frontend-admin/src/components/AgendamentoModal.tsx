import React from "react";
import { Agendamento } from "../models/Agendamento";
import { Accordion, Button, Modal } from "react-bootstrap";
import { Loading } from "./Loading";

interface ModalProps {
    loadingAgendamento: boolean;
    selectedAgendamento: Agendamento | null;
    showModal: boolean;
    handleCloseModal: () => void;
}

export const AgendamentoModal: React.FC<ModalProps> = ({
    loadingAgendamento,
    selectedAgendamento,
    showModal,
    handleCloseModal
}) => (
    <Modal show={showModal} onHide={handleCloseModal} size="xl">
        <Modal.Header closeButton>
            <Modal.Title>Detalhes do Agendamento</Modal.Title>
        </Modal.Header>
        <Modal.Body>
            {loadingAgendamento ? (
                <Loading message="Buscando detalhes do agendamento... Por favor, aguarde!" height="20vh"/>
            ) : (
                selectedAgendamento && (
                    <Accordion defaultActiveKey={['0', '1', '2', '3']} alwaysOpen>
                        <Accordion.Item eventKey="0">
                            <Accordion.Header>Agendamento</Accordion.Header>
                            <Accordion.Body>
                                <p><strong>Id:</strong> {selectedAgendamento.idExterno}</p>
                                <p><strong>Duração (em minutos):</strong> {selectedAgendamento.tempoDuracaoEmMinutos}</p>
                                <p><strong>Valor total:</strong> R${selectedAgendamento.valorTotal}</p>
                                <p><strong>Data e Hora de Início: </strong>{selectedAgendamento.dataHora}</p>
                                <p><strong>Data e Hora de Fim: </strong>{selectedAgendamento.dataHora}</p>
                            </Accordion.Body>
                        </Accordion.Item>
                        <Accordion.Item eventKey="1">
                            <Accordion.Header>Cliente</Accordion.Header>
                            <Accordion.Body>
                                <p><strong>Nome:</strong> {selectedAgendamento.cliente.nome}</p>
                                <p><strong>Celular:</strong> {selectedAgendamento.cliente.celular}</p>
                            </Accordion.Body>
                        </Accordion.Item>
                        <Accordion.Item eventKey="2">
                            <Accordion.Header>Barbeiros</Accordion.Header>
                            <Accordion.Body>
                                {selectedAgendamento.barbeiros.map((barbeiro, index) => (
                                    <div key={barbeiro.idExterno}>
                                        <p><strong>Barbeiro {index + 1}</strong>:</p>
                                        <p><strong>Id: </strong>{barbeiro.idExterno}</p>
                                        <p><strong>Nome: </strong>{barbeiro.nome}</p>
                                        <p><strong>Celular: </strong>{barbeiro.celular}</p>
                                    </div>
                                ))}
                            </Accordion.Body>
                        </Accordion.Item>
                        <Accordion.Item eventKey="3">
                            <Accordion.Header>Serviços</Accordion.Header>
                            <Accordion.Body>
                                {selectedAgendamento.servicos.map((servico) => (
                                    <div key={servico.idExterno}>
                                        <p><strong>Id: </strong>{servico.idExterno}</p>
                                        <p><strong>Nome: </strong>{servico.nome}</p>
                                        <p><strong>Preço: </strong>{servico.preco}</p>
                                        <p><strong>Duração</strong>(em minutos): {servico.tempoDuracaoEmMinutos}</p>
                                    </div>
                                ))}
                            </Accordion.Body>
                        </Accordion.Item>
                    </Accordion>
                )
            )}
        </Modal.Body>
        <Modal.Footer>
            <Button variant="secondary" onClick={handleCloseModal}>Fechar</Button>
        </Modal.Footer>
    </Modal>
)