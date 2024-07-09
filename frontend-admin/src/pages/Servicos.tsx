import React, { useEffect, useState } from "react";
import { ServicoService } from "../services/ServicoService";
import MenuLateral from "../components/MenuLateral";
import { Alert, Badge, Button, Card, Col, Container, Modal, Row, Spinner } from "react-bootstrap";
import { Servico, ServicoCadastro } from "../models/Servico";

import { BiPlus } from "react-icons/bi";
import { MdDelete, MdEdit } from "react-icons/md";

export const Servicos: React.FC = () => {
    const idBarbearia: string = localStorage.getItem('idBarbearia') as string;
    const token: string = localStorage.getItem('token') as string;
    const [servicos, setServicos] = useState<Servico[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);
    const [showModal, setShowModal] = useState(false);
    const [novoServico, setNovoServico] = useState<ServicoCadastro>({
        nome: '',
        preco: 0,
        tempoDuracaoEmMinutos: 0,
        ativo: true,
        idExternoBarbearia: idBarbearia
    });

    useEffect(() => {
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

        fetchServicos();
    }, []);

    const handleShowModal = () => setShowModal(true);
    const handleCloseModal = () => setShowModal(false);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setNovoServico({
            ...novoServico,
            [name]: value
        });
    };

    const handleCheckboxChange = (e) => {
        setNovoServico({
            ...novoServico,
            ativo: e.target.checked
        })
    }

    const handleSalvar = async () => {
        setLoading(true);
        setError(null);

        try {
            const servicoCadastrado = await ServicoService.cadastrarServico(novoServico, token);
            if (servicoCadastrado) {
                handleCloseModal();
            }
        } catch (error) {
            setError("Erro ao cadastrar serviço: " + error);
        } finally {
            setLoading(false);
            window.location.reload();
        }        
    }

    const handleEditar = (idExterno: string) => {
        console.log(`Editar serviço com ID externo: ${idExterno}`);
    };

    const handleExcluir = (idExterno: string) => {
        console.log(`Excluir serviço com ID externo: ${idExterno}`);
    };

    if (loading) {
        return (
            <div className="d-flex flex-column justify-content-center align-items-center" style={{ height: '100vh' }}>
                <Spinner animation="border" />
                <p className="mt-3">Buscando serviços...</p>
            </div>
        );
    }

    if (error) {
        return <Alert variant="danger">{error}</Alert>
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
                            <Col xs={12} key={servico.idExterno} className="mb-4">
                                <Card className="shadow-sm">
                                    <Card.Body>
                                        <div className="d-flex justify-content-between align-items-center mb-2">
                                            <div>
                                                <strong>Nome:</strong> {servico.nome}
                                            </div>
                                            <div>
                                                <strong>ID:</strong> {servico.idExterno}
                                            </div>
                                            <div>
                                                <strong>Preço:</strong> R${servico.preco.toFixed(2)}
                                            </div>
                                            <div>
                                                <strong>Duração:</strong> {servico.tempoDuracaoEmMinutos} min
                                            </div>
                                            <div>
                                                <strong>Status:</strong>{' '}
                                                <Badge bg={servico.ativo ? 'success' : 'secondary'}>
                                                    {servico.ativo ? 'Ativo' : 'Inativo'}
                                                </Badge>
                                            </div>
                                            <div>
                                                <Button variant="primary" onClick={() => handleEditar(servico.idExterno)}><MdEdit /></Button>{' '}
                                                <Button variant="danger" onClick={() => handleExcluir(servico.idExterno)}><MdDelete /></Button>
                                            </div>
                                        </div>
                                        
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
            </Container>
        </div>
    );
};