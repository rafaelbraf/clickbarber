import React, { useEffect, useState } from "react";
import { Accordion, Button, Form, ListGroup, Modal, Toast, ToastContainer } from "react-bootstrap";
import { Servico } from "../models/Servico";
import { ServicoService } from "../services/ServicoService";
import { Barbeiro } from "../models/Barbeiro";
import { BarbeiroService } from "../services/BarbeiroService";
import { MdDelete, MdError } from "react-icons/md";
import { Cliente } from "../models/Cliente";
import { ClienteService } from "../services/ClienteService";
import { AgendamentoCadastro } from "../models/Agendamento";
import { AgendamentoService } from "../services/AgendamentoService";
import axios from "axios";

interface CadastroModalProps {
    showModal: boolean;
    handleCloseModal: () => void;
}

export const AgendamentoCadastroModal: React.FC<CadastroModalProps> = ({
    showModal,
    handleCloseModal,
}) => {
    const [formData, setFormData] = useState({
        cliente: null,
        barbeiros: [] as Barbeiro[],
        servicos: [] as Servico[],
        dataHora: '',
        valorTotal: 0
    });
    const [servicos, setServicos] = useState<Servico[]>([]);
    const [selectedServico, setSelectedServico] = useState<string>('');
    const [barbeiros, setBarbeiros] = useState<Barbeiro[]>([]);
    const [selectedBarbeiro, setSelectedBarbeiro] = useState<string>('');
    const [clientes, setClientes] = useState<Cliente[]>();
    const [selectedCliente, setSelectedCliente] = useState<string>('');
    const [showToast, setShowToast] = useState(false);
    const [toastMessage, setToastMessage] = useState('');
    const idBarbearia = localStorage.getItem('idBarbearia') as string;
    const token: string = localStorage.getItem('token') as string;

    useEffect(() => {
        const fetchServicos = async () => {
            try {
                const servicos: Servico[] = await ServicoService.buscarServicosDaBarbearia(idBarbearia, token);
                setServicos(servicos);
            } catch (error) {
                console.error(`Erro ao buscar serviços: ${error}`);
            }
        };

        const fetchBarbeiros = async () => {
            try {
                const barbeiros: Barbeiro[] = await BarbeiroService.buscarBarbeirosDaBarbearia(idBarbearia);
                setBarbeiros(barbeiros);
            } catch (error) {
                console.error(`Erro ao buscar serviços: ${error}`);
            }
        }

        const fetchClientes = async () => {
            try {
                const clientes: Cliente[] = await ClienteService.buscarClientesDaBarbearia();
                setClientes(clientes);
            } catch (error) {
                console.error(`Erro ao buscar serviços: ${error}`);
            }
        }

        fetchServicos();
        fetchBarbeiros();
        fetchClientes();
    }, [idBarbearia, token]);

    const handleChange = (e: React.ChangeEvent<HTMLInputElement> | React.ChangeEvent<HTMLSelectElement>) => {
        const { name, value } = e.target;
        const options = (e.target as HTMLSelectElement).options;

        if (e.target.multiple) {
            const values = Array.from(options)
                .filter(option => option.selected)
                .map(option => option.value);

            setFormData(prevState => ({
                ...prevState,
                [name]: values
            }));
        } else if (name === 'dataHora') {
            const dataFormatada = new Date(value).toISOString();
            setFormData(prevState => ({
                ...prevState,
                [name]: dataFormatada
            }))
        }
        else {
            setFormData(prevState => ({
                ...prevState,
                [name]: value
            }));
        }
    };

    const handleFormSubmit = async () => {        
        const servicosIdsExterno: string[] = [];
        formData.servicos.forEach(servico => {
            servicosIdsExterno.push(servico.idExterno);
        });

        const barbeirosIdsExterno: string[] = [];
        formData.barbeiros.forEach(barbeiro => {
            barbeirosIdsExterno.push(barbeiro.idExterno);
        });

        const agendamentoCadastro: AgendamentoCadastro = {
            dataHora: formData.dataHora.toString(),
            valorTotal: formData.valorTotal,
            tempoDuracaoEmMinutos: 0,
            barbeariaIdExterno: idBarbearia,
            clienteIdExterno: selectedCliente,
            servicosIdsExterno: servicosIdsExterno,
            barbeirosIdsExterno: barbeirosIdsExterno
        };

        try {
            const agendamentoCadastrado = await AgendamentoService.cadastrarAgendamento(agendamentoCadastro);
            if (agendamentoCadastrado) {
                console.log('Agendamento cadastrado com sucesso!');
                window.location.reload();           
            } else {
                setToastMessage('Não foi possível cadastrar agendamento.');
                setShowToast(true);
            }
        } catch (error) {
            if (axios.isAxiosError(error)) {
                if (error.response) {
                    setToastMessage(`Erro ao cadastrar agendamento: ${error.response.data}`);
                } else if (error.request) {
                    setToastMessage('Erro ao tentar se comunicar com o servidor. Verifique sua conexão de internet.');
                } else {
                    setToastMessage('Erro ao tentar cadastrar agendamento. Por favor, tente novamente mais tarde.');
                }
            } else {
                setToastMessage(`Erro ao cadastrar agendamento: ${error.data.message}`);
            }            
            setShowToast(true);
        }
        
        handleCloseModal();
    }

    const handleAddServico = () => {
        const servico = servicos.find(s => s.idExterno === selectedServico);
        if (servico && !formData.servicos.includes(servico)) {
            setFormData(prevState => ({
                ...prevState,
                servicos: [...prevState.servicos, servico]
            }));
        }

        setSelectedServico('');
    }

    const handleRemoveServico = (servicoId: string) => {
        setFormData(prevState => ({
            ...prevState,
            servicos: prevState.servicos.filter(s => s.idExterno !== servicoId)
        }));
    };

    const handleAddbarbeiro = () => {
        const barbeiro = barbeiros.find(b => b.idExterno === selectedBarbeiro);
        if (barbeiro && !formData.barbeiros.includes(barbeiro)) {
            setFormData(prevState => ({
                ...prevState,
                barbeiros: [...prevState.barbeiros, barbeiro]
            }));
        }

        setSelectedBarbeiro('');
    }

    const handleRemoveBarbeiro = (barbeiroId: string) => {
        setFormData(prevState => ({
            ...prevState,
            barbeiros: prevState.barbeiros.filter(b => b.idExterno !== barbeiroId)
        }));
    };

    return (
        <>
            <Modal show={showModal} onHide={handleCloseModal} size="lg">
                <Modal.Header closeButton>
                    <Modal.Title>Cadastro de Agendamento</Modal.Title>
                </Modal.Header>

                <Modal.Body>
                    <Accordion defaultActiveKey="0" alwaysOpen>
                        <Accordion.Item eventKey="0">
                            <Accordion.Header>Agendamento</Accordion.Header>
                            <Accordion.Body>
                                <Form.Group controlId="dataHora" className="mb-2">
                                    <Form.Label>Data e Hora</Form.Label>
                                    <Form.Control
                                        type="datetime-local"
                                        name="dataHora"
                                        onChange={handleChange}
                                    />
                                </Form.Group>
                            </Accordion.Body>
                        </Accordion.Item>
                        <Accordion.Item eventKey="1">
                            <Accordion.Header>Cliente</Accordion.Header>
                            <Accordion.Body>
                                <Form.Group controlId="clientes">
                                    <Form.Label>Cliente</Form.Label>
                                    <Form.Control
                                        as="select"
                                        name="selectedCliente"
                                        value={selectedCliente}
                                        onChange={(e) => setSelectedCliente(e.target.value)}
                                    >
                                        <option value="">Selecione um cliente</option>
                                        {clientes?.map(cliente => (
                                            <option key={cliente.idExterno} value={cliente.idExterno}>
                                                {cliente.nome}
                                            </option>
                                        ))}
                                    </Form.Control>
                                </Form.Group>
                            </Accordion.Body>
                        </Accordion.Item>
                        <Accordion.Item eventKey="2">
                            <Accordion.Header>Barbeiros</Accordion.Header>
                            <Accordion.Body>
                                <Form.Group controlId="barbeiros">
                                    <Form.Label>Barbeiros</Form.Label>
                                    <Form.Control
                                        as="select"
                                        name="selectedBarbeiro"
                                        value={selectedBarbeiro}
                                        onChange={(e) => setSelectedBarbeiro(e.target.value)}
                                    >
                                        <option value="">Selecione um ou mais barbeiros</option>
                                        {barbeiros.map(barbeiro => (
                                            <option key={barbeiro.idExterno} value={barbeiro.idExterno}>
                                                {barbeiro.nome}
                                            </option>
                                        ))}
                                    </Form.Control>
                                    <Button onClick={handleAddbarbeiro} className="mt-2">Adicionar</Button>
                                </Form.Group>
                                <ListGroup className="mt-3">
                                    {formData.barbeiros.map(barbeiro => (
                                        <ListGroup.Item key={barbeiro.idExterno}>
                                            {barbeiro.nome}
                                            <Button variant="danger" size="sm" onClick={() => handleRemoveBarbeiro(barbeiro.idExterno)} className="float-end"><MdDelete size={20} /> Remover</Button>
                                        </ListGroup.Item>
                                    ))}
                                </ListGroup>
                            </Accordion.Body>
                        </Accordion.Item>
                        <Accordion.Item eventKey="3">
                            <Accordion.Header>Serviços</Accordion.Header>
                            <Accordion.Body>
                                <Form.Group controlId="servicos">
                                    <Form.Label>Serviços</Form.Label>
                                    <Form.Control
                                        as="select"
                                        name="selectedServico"
                                        value={selectedServico}
                                        onChange={(e) => setSelectedServico(e.target.value)}
                                    >
                                        <option value="">Selecione um ou mais serviços</option>
                                        {servicos.map(servico => (
                                            <option key={servico.idExterno} value={servico.idExterno}>
                                                {servico.nome} - R${servico.preco}
                                            </option>
                                        ))}
                                    </Form.Control>
                                    <Button onClick={handleAddServico} className="mt-2">Adicionar</Button>
                                </Form.Group>
                                <ListGroup className="mt-3">
                                    {formData.servicos.map(servico => (
                                        <ListGroup.Item key={servico.idExterno}>
                                            {servico.nome} - R${servico.preco}
                                            <Button variant="danger" size="sm" onClick={() => handleRemoveServico(servico.idExterno)} className="float-end"><MdDelete size={20} /> Remover</Button>
                                        </ListGroup.Item>
                                    ))}
                                </ListGroup>
                            </Accordion.Body>
                        </Accordion.Item>                    
                    </Accordion>
                </Modal.Body>

                <Modal.Footer>
                    <Button variant="secondary" onClick={handleCloseModal}>Fechar</Button>
                    <Button variant="primary" onClick={handleFormSubmit}>Salvar</Button>
                </Modal.Footer>
            </Modal>

            <ToastContainer position="top-end" className="p-3">
                <Toast onClose={() => setShowToast(false)} show={showToast} delay={3000} autohide bg="danger">
                    <Toast.Body className="text-white"><MdError size={20}/> {toastMessage}</Toast.Body>
                </Toast>
            </ToastContainer>
        </>        
    );
}