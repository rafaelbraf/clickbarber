import React, { ChangeEvent, useEffect, useState } from "react";
import { Barbeiro, BarbeiroCadastro } from "../models/Barbeiro";
import { BarbeiroService } from "../services/BarbeiroService";
import { Badge, Button, Card, Col, Container, Modal, Row, Toast, ToastContainer } from "react-bootstrap";
import MenuLateral from "../components/MenuLateral";
import { BiPlus, BiSolidError } from "react-icons/bi";
import { MdDelete, MdEdit } from "react-icons/md";
import { HiCheckCircle } from "react-icons/hi";
import AutenticacaoService from "../services/AutenticacaoService";
import { Loading } from "../components/Loading";

export const Barbeiros: React.FC = () => {
    const idBarbearia = localStorage.getItem('idBarbearia') as string;
    const [barbeiros, setBarbeiros] = useState<Barbeiro[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [messageLoading, setMessageLoading] = useState<string>('');
    const [error, setError] = useState<string | null>(null);
    const [showModal, setShowModal] = useState(false);
    const [showToastSuccess, setShowToastSuccess] = useState(false);
    const [showToastFailed, setShowToastFailed] = useState(false);
    const [messageToast, setMessageToast] = useState<string>('');
    const barbeiroCadastroDefaultValues: BarbeiroCadastro = {
        email: '',
        senha: '',
        role: "BARBEIRO",
        data: {
            nome: '',
            cpf: '',
            celular: '',
            admin: false,
            ativo: true,
            idExternoBarbearia: idBarbearia
        }
    };
    const [novoBarbeiro, setNovoBarbeiro] = useState<BarbeiroCadastro>(barbeiroCadastroDefaultValues);

    useEffect(() => {
        setMessageLoading("Buscando barbeiros...");

        const fetchBarbeiros = async () => {
            try {
                const barbeirosEncontrados: Barbeiro[] = await BarbeiroService.buscarBarbeirosDaBarbearia(idBarbearia);
                setBarbeiros(barbeirosEncontrados);
            } catch (error) {
                setError(`Erro ao buscar barbeiros ${barbeiros}`);
                setShowToastFailed(true);
            } finally {
                setLoading(false);
            }
        }

        fetchBarbeiros();
    }, []);

    const handleShowModal = () => setShowModal(true);
    const handleCloseModal = () => setShowModal(false);

    const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        if (name in novoBarbeiro.data) {
            setNovoBarbeiro({
                ...novoBarbeiro,
                data: {
                    ...novoBarbeiro.data,
                    [name]: value
                }
            });
        } else {
            setNovoBarbeiro({
                ...novoBarbeiro,
                [name]: value
            });
        }
    };

    const handleCheckboxChange = (e: ChangeEvent<HTMLInputElement>) => {
        const { name, checked } = e.target;
        setNovoBarbeiro({
            ...novoBarbeiro,
            data: {
                ...novoBarbeiro.data,
                [name]: checked
            }
        });
    };

    const handleSalvar = async () => {
        setMessageLoading("Cadastrando barbeiro...");
        setLoading(true);
        setError(null);

        try {
            const barbeiroCadastrado = await AutenticacaoService.cadastrarBarbeiro(novoBarbeiro);
            if (barbeiroCadastrado) {
                setMessageToast("Barbeiro cadastrado com sucesso!");
                setShowToastSuccess(true);
                setBarbeiros([...barbeiros, barbeiroCadastrado]);
                setNovoBarbeiro(barbeiroCadastroDefaultValues);
                handleCloseModal();
            }
        } catch (error) {
            setError("Erro ao cadastrar barbeiro.");
            handleCloseModal();
            setShowToastFailed(true);
        } finally {
            setLoading(false);
        }
    }

    const handleEditar = (idExterno: string) => {
        console.log(`Editar barbeiro com ID externo: ${idExterno}`);
    };

    const handleExcluir = async (idExterno: string) => {
        setMessageLoading("Excluindo barbeiro...");
        setLoading(true);
        setError(null);

        try {
            await BarbeiroService.deletarBarbeiroPorIdExterno(idExterno);
        } catch (error) {
            setError("Não foi possível excluir barbeiro.");
            setShowToastFailed(true);
        } finally {
            window.location.reload();
            setLoading(false);
            setMessageToast("Barbeiro excluído com sucesso!")
            setShowToastSuccess(true);
        }
    };

    if (loading) {
        return (
            <Loading message={messageLoading} />
        );
    }

    return (
        <div className="app-container">
            <MenuLateral />
            <Container fluid className="content">
                <Row>
                    <Col xs={12} md={6} lg={9}>
                        <h1>Barbeiros</h1>
                    </Col>
                    <Col xs={12} md={3} lg={3} className="d-flex justify-content-end">
                        <Button onClick={handleShowModal} className="mt-2"><BiPlus />Novo barbeiro</Button>
                    </Col>
                </Row>
                <Row className="mt-3">
                    <Col xs={12} className="text-right">
                        <p>Total de itens: {barbeiros.length}</p>
                    </Col>
                    {barbeiros.length > 0 ? (
                        barbeiros.map((barbeiro) => (
                            <Col xs={12} key={barbeiro.idExterno} className="mb-2">
                                <Card className="shadow-sm">
                                    <Card.Body>
                                        <Container>
                                            <Row>
                                                <Col sm={12} lg={3}>
                                                    <strong>Nome:</strong> {barbeiro.nome}
                                                </Col>

                                                <Col sm={12} lg={2}>
                                                    <strong>Celular:</strong> {barbeiro.celular}
                                                </Col>

                                                <Col sm={12} lg={2}>
                                                    <strong>Admin:</strong> {barbeiro.admin ? 'Sim' : 'Não'}
                                                </Col>

                                                <Col sm={12} lg={2}>
                                                    <strong>Ativo:</strong>{' '}
                                                    <Badge bg={barbeiro.ativo ? 'success' : 'secondary'}>
                                                        {barbeiro.ativo ? 'Ativo' : 'Inativo'}
                                                    </Badge>
                                                </Col>

                                                <Col sm={12} lg={3} className="text-end">
                                                    <Button variant="primary" onClick={() => handleEditar(barbeiro.idExterno)}><MdEdit /></Button>{' '}
                                                    <Button variant="danger" onClick={() => handleExcluir(barbeiro.idExterno)}><MdDelete /></Button>
                                                </Col>
                                            </Row>
                                        </Container>                                        
                                    </Card.Body>
                                </Card>
                            </Col>
                        ))
                    ) : (
                        <Col xs={12}>
                            <p>Nenhum barbeiro encontrado.</p>
                        </Col>
                    )}
                </Row>

                {/* Modal para adicionar novo barbeiro */}
                <Modal 
                    show={showModal} 
                    onHide={handleCloseModal}
                    size="lg">
                    <Modal.Header closeButton>
                        <Modal.Title>Novo Barbeiro</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        {loading && <p>Carregando...</p>}
                        <form>
                            <div className="mb-3">
                                <label htmlFor="nome" className="form-label">Nome</label>
                                <input 
                                    type="text" 
                                    className="form-control" 
                                    id="nome" 
                                    name="nome"
                                    value={novoBarbeiro.data.nome}
                                    onChange={handleChange}
                                    required
                                />
                            </div>
                            <div className="mb-3">
                                <label htmlFor="cpf" className="form-label">CPF</label>
                                <input 
                                    type="number" 
                                    className="form-control" 
                                    id="cpf" 
                                    name="cpf"
                                    value={novoBarbeiro.data.cpf}
                                    onChange={handleChange}
                                    required 
                                />
                            </div>
                            <div className="mb-3">
                                <label htmlFor="celular" className="form-label">Celular</label>
                                <input 
                                    type="number" 
                                    className="form-control" 
                                    id="celular" 
                                    name="celular"
                                    value={novoBarbeiro.data.celular}
                                    onChange={handleChange}
                                    required 
                                />
                            </div>
                            <div className="mb-3">
                                <label htmlFor="email" className="form-label">Email</label>
                                <input 
                                    type="email" 
                                    className="form-control" 
                                    id="email" 
                                    name="email"
                                    value={novoBarbeiro.email}
                                    onChange={handleChange}
                                    required 
                                />
                            </div>
                            <div className="mb-3">
                                <label htmlFor="senha" className="form-label">Senha</label>
                                <input 
                                    type="password" 
                                    className="form-control" 
                                    id="senha" 
                                    name="senha"
                                    value={novoBarbeiro.senha}
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
                                    checked={novoBarbeiro.data.ativo}
                                    onChange={handleCheckboxChange}
                                />
                                <label className="form-check-label" htmlFor="ativo">Ativo</label>
                            </div>
                            <div className="form-check">
                                <input 
                                    type="checkbox" 
                                    className="form-check-input" 
                                    id="admin" 
                                    name="admin"
                                    checked={novoBarbeiro.data.admin}
                                    onChange={handleCheckboxChange}
                                />
                                <label className="form-check-label" htmlFor="admin">Admin</label>
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
                {/* Fim Modal para adicionar novo barbeiro */}

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
                        bg="danger"
                        show={showToastFailed}
                        onClose={() => setShowToastFailed(false)}
                        delay={5000}
                        autohide >
                        <Toast.Body className="text-white">
                            <BiSolidError size={20} />
                            <span> {error}</span>
                        </Toast.Body>
                    </Toast>
                </ToastContainer>
            </Container>
        </div>
    );
}