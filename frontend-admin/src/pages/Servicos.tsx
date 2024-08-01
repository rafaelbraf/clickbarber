import React, { ChangeEvent, useEffect, useState } from "react";
import { ServicoService } from "../services/ServicoService";
import MenuLateral from "../components/MenuLateral";
import { Badge, Button, Card, Col, Container, Modal, Row, Toast, ToastContainer } from "react-bootstrap";
import { Servico, ServicoCadastro } from "../models/Servico";

import { BiPlus } from "react-icons/bi";
import { MdDelete, MdEdit } from "react-icons/md";
import { HiCheckCircle } from "react-icons/hi";
import { Loading } from "../components/Loading";

export const Servicos: React.FC = () => {
    const idBarbearia: string = localStorage.getItem('idBarbearia') as string;
    const token: string = localStorage.getItem('token') as string;
    const [servicos, setServicos] = useState<Servico[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);
    const [showModal, setShowModal] = useState(false);
    const [showToastSuccess, setShowToastSuccess] = useState(false);
    const [showToastFailed, setShowToastFailed] = useState(false);
    const [messageLoading, setMessageLoading] = useState<string>('');
    const [messageToast, setMessageToast] = useState<string>('');
    const servicoCadastroDefaultValues: ServicoCadastro = {
        nome: '',
        preco: 0,
        tempoDuracaoEmMinutos: 0,
        ativo: true,
        idExternoBarbearia: idBarbearia
    }
    const [novoServico, setNovoServico] = useState<ServicoCadastro>(servicoCadastroDefaultValues);    

    const fetchServicos = async () => {
        try {
            const servicosEncontrados: Servico[] = await ServicoService.buscarServicosDaBarbearia(idBarbearia, token);
            setServicos(servicosEncontrados);
        } catch (error) {
            setError(`Erro ao buscar serviços ${servicos}`);
        } finally {
            setLoading(false);
        }
    }

    useEffect(() => {
        fetchServicos();
    }, [idBarbearia, token]);

    const handleShowModal = () => setShowModal(true);
    const handleCloseModal = () => setShowModal(false);

    const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setNovoServico({
            ...novoServico,
            [name]: value
        });
    };

    const handleCheckboxChange = (e: ChangeEvent<HTMLInputElement>) => {
        setNovoServico({
            ...novoServico,
            ativo: e.target.checked
        })
    }

    const handleSalvar = async () => {
        setMessageLoading("Cadastrando serviço...");
        setLoading(true);
        setError(null);

        try {
            const servicoCadastrado = await ServicoService.cadastrarServico(novoServico, token);
            if (servicoCadastrado) {
                setMessageToast("Serviço cadastrado com sucesso!");
                setShowToastSuccess(true);
                setServicos([...servicos, servicoCadastrado]);
                setNovoServico(servicoCadastroDefaultValues);
                handleCloseModal();
            }
        } catch (error) {
            setError("Erro ao cadastrar serviço: " + error);
            setShowToastFailed(true);
        } finally {
            setLoading(false);
        }
    }

    const handleEditar = (idExterno: string) => {
        console.log(`Editar serviço com ID externo: ${idExterno}`);
    };

    const handleExcluir = async (idExterno: string) => {
        setError(null);
        setLoading(true);
        setMessageLoading("Excluindo serviço...");

        try {
            await ServicoService.deletarServicoPorIdExterno(idExterno, token);
            setMessageToast("Serviço excluído com sucesso!");
            setShowToastSuccess(true);
            fetchServicos();
        } catch (error) {
            setError("Erro ao excluir serviço: " + error);
            setShowToastFailed(true);
        } finally {
            setLoading(false);
            
        }
    };

    if (loading) {
        return <Loading message={messageLoading} />
    }

    return (
        <div className="app-container">
            <MenuLateral />
            <Container fluid className="content">
                <Row>
                    <Col xs={12} md={6} lg={9}>
                        <h1>Serviços</h1>
                    </Col>
                    <Col xs={12} md={3} lg={3} className="d-flex justify-content-end">
                        <Button onClick={handleShowModal} className="mt-2"><BiPlus />Novo serviço</Button>
                    </Col>
                </Row>
                <Row className="mt-3">
                    <Col xs={12} className="text-right">
                        <p>Total de itens: {servicos.length}</p>
                    </Col>
                    {servicos.length > 0 ? (
                        servicos.map((servico) => (
                            <Col xs={12} key={servico.idExterno} className="mb-2">
                                <Card className="shadow-sm">
                                    <Card.Body>
                                        <Container>
                                            <Row>
                                                <Col sm={12} lg={3}>
                                                    <strong>Nome:</strong> {servico.nome}
                                                </Col>

                                                <Col sm={12} lg={2}>
                                                    <strong>Preço:</strong> R${servico.preco.toFixed(2)}
                                                </Col>

                                                <Col sm={12} lg={2}>
                                                    <strong>Duração:</strong> {servico.tempoDuracaoEmMinutos} min
                                                </Col>

                                                <Col sm={12} lg={2}>
                                                    <strong>Status:</strong>{' '}
                                                    <Badge bg={servico.ativo ? 'success' : 'secondary'}>
                                                        {servico.ativo ? 'Ativo' : 'Inativo'}
                                                    </Badge>
                                                </Col>

                                                <Col sm={12} lg={3} className="text-end">
                                                    <Button variant="primary" onClick={() => handleEditar(servico.idExterno)}><MdEdit /></Button>{' '}
                                                    <Button variant="danger" onClick={() => handleExcluir(servico.idExterno)}><MdDelete /></Button>
                                                </Col>
                                            </Row>
                                        </Container>                                        
                                    </Card.Body>
                                </Card>
                            </Col>
                        ))
                    ) : (
                        <Col xs={12}>
                            <p>Nenhum serviço encontrado.</p>
                        </Col>
                    )}
                </Row>

                {/* Modal para adicionar novo serviço */}
                <Modal 
                    show={showModal} 
                    onHide={handleCloseModal}
                    size="lg">
                    <Modal.Header closeButton>
                        <Modal.Title>Novo Serviço</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        {loading && <p>Carregando...</p>}
                        {error && <p>{error}</p>}
                        <form>
                            <div className="mb-3">
                                <label htmlFor="nome" className="form-label">Nome</label>
                                <input 
                                    type="text" 
                                    className="form-control" 
                                    id="nome" 
                                    name="nome"
                                    value={novoServico.nome}
                                    onChange={handleChange}
                                    required 
                                />
                            </div>
                            <div className="mb-3">
                                <label htmlFor="preco" className="form-label">Preço</label>
                                <input 
                                    type="number" 
                                    className="form-control" 
                                    id="preco" 
                                    name="preco"
                                    value={novoServico.preco}
                                    onChange={handleChange}
                                    required 
                                />
                            </div>
                            <div className="mb-3">
                                <label htmlFor="tempoDuracaoEmMinutos" className="form-label">Duração (minutos)</label>
                                <input 
                                    type="number" 
                                    className="form-control" 
                                    id="tempoDuracaoEmMinutos" 
                                    name="tempoDuracaoEmMinutos"
                                    value={novoServico.tempoDuracaoEmMinutos}
                                    onChange={handleChange}
                                    required 
                                />
                            </div>
                            <div className="form-check">
                                <input 
                                    type="checkbox" 
                                    className="form-check-input" 
                                    id="ativo" 
                                    name="ativo"
                                    checked={novoServico.ativo}
                                    onChange={handleCheckboxChange}
                                />
                                <label className="form-check-label" htmlFor="ativo">Ativo</label>
                            </div>
                        </form>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button variant="secondary" onClick={handleCloseModal}>
                            Fechar
                        </Button>
                        <Button variant="primary" onClick={handleSalvar}>
                            Salvar
                        </Button>
                    </Modal.Footer>
                </Modal>

                <ToastContainer
                    className="p-3"
                    position="bottom-end"
                    style={{ zIndex: 1 }} >
                    <Toast
                        bg="success"
                        show={showToastSuccess}
                        onClose={() => setShowToastSuccess(false)}
                        delay={5000}
                        autohide >
                        <Toast.Body className="text-white">
                            <HiCheckCircle size={20} />
                            <span> {messageToast}</span>
                        </Toast.Body>
                    </Toast>
                </ToastContainer>

                <ToastContainer
                    className="p-3"
                    position="bottom-end"
                    style={{ zIndex: 1 }} >
                    <Toast
                        bg="success"
                        show={showToastFailed}
                        onClose={() => setShowToastFailed(false)}
                        delay={5000}
                        autohide >
                        <Toast.Body className="text-white">
                            <HiCheckCircle size={20} />
                            <span> {error}</span>
                        </Toast.Body>
                    </Toast>
                </ToastContainer>
            </Container>
        </div>
    );
};