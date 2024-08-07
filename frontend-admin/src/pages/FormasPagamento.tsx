import React, { useEffect, useState } from "react"
import MenuLateral from "../components/MenuLateral";
import { Badge, Button, Card, Col, Container, Form, Modal, Row, Toast, ToastContainer } from "react-bootstrap";
import { FormaPagamentoService } from "../services/FormaPagamentoService";
import { FormaPagamento, FormaPagamentoCadastroDto } from "../models/FormaPagamento";
import { Loading } from "../components/Loading";
import { HiCheckCircle } from "react-icons/hi";
import { MdDelete, MdEdit } from "react-icons/md";
import { BiPlus } from "react-icons/bi";

export const FormasPagamento: React.FC = () => {
    const idBarbearia: string = localStorage.getItem('idBarbearia') as string;
    const token: string = localStorage.getItem('token') as string;

    const [error, setError] = useState<string | null>('');
    const [loading, setLoading] = useState<boolean>(true);
    const [messageLoading, setMessageLoading] = useState<string>('');
    const [formasPagamento, setFormasPagamento] = useState<FormaPagamento[]>([]);
    const [showToastSuccess, setShowToastSuccess] = useState<boolean>(false);
    const [showToastFailed, setShowToastFailed] = useState<boolean>(false);
    const [messageToast, setMessageToast] = useState<string>('');
    const [showModal, setShowModal] = useState<boolean>(false);
    const [showModalCriar, setShowModalCriar] = useState<boolean>(false);
    const [formaPagamentoAtual, setFormaPagamentoAtual] = useState<FormaPagamento | null>(null);
    const [novaFormaPagamento, setNovaFormaPagamento] = useState<FormaPagamentoCadastroDto>({ tipo: '', ativo: true, idExternoBarbearia: idBarbearia });
    const [showConfirmarExclusaoModal, setShowConfirmarExclusaoModal] = useState<boolean>(false);
    const [idParaExclusao, setIdParaExclusao] = useState<string | null>(null);

    const handleShowModalConfirmarExclusao = (idExterno: string) => {
        setIdParaExclusao(idExterno);
        setShowConfirmarExclusaoModal(true);
    }

    const handleCloseModalConfirmarExclusao = () => {
        setShowConfirmarExclusaoModal(false);
        setIdParaExclusao(null);
    }

    const handleConfirmarExclusao = async () => {
        if (idParaExclusao) {
            await handleExcluir(idParaExclusao);
        }

        handleCloseModalConfirmarExclusao();
    }

    const handleShowModal = (formaPagamento?: FormaPagamento) => {
        setFormaPagamentoAtual(formaPagamento || null);
        setShowModal(true);
    } 
    const handleCloseModal = () => setShowModal(false);

    const handleShowModalCriar = () => {
        setNovaFormaPagamento({ tipo: '', ativo: true, idExternoBarbearia: idBarbearia });
        setShowModalCriar(true);
    }

    const handleCloseModalCriar = () => setShowModalCriar(false);

    const fetchFormasPagamento = async () => {
        setMessageLoading('Buscando formas de pagamento...');
        setLoading(true);

        try {
            const formasPagamentoEncontradas: FormaPagamento[] = await FormaPagamentoService.buscarFormasDePagamentoDaBarbearia();
            setFormasPagamento(formasPagamentoEncontradas);

            console.log(formasPagamento);
            
        } catch (error) {
            setError(`Erro ao buscar formas de pagamento: ${error}`);
        } finally {
            setLoading(false);
            setMessageLoading('');
        }
    }

    useEffect(() => {
        fetchFormasPagamento();
    }, [idBarbearia, token]);

    const handleEditar = async () => {
        if (!formaPagamentoAtual) return;

        setLoading(true);
        setMessageLoading("Atualizando forma de pagamento...");

        try {
            await FormaPagamentoService.atualizar(formaPagamentoAtual);
            setMessageToast("Forma de pagamento atualizada com sucesso!");
            setShowToastSuccess(true);
            fetchFormasPagamento();
        } catch (error) {
            setError("Erro ao atualizar forma de pagamento: " + error);
            setShowToastFailed(true);
        } finally {
            setLoading(false);
            handleCloseModal();
        }
    };

    const handleExcluir = async (idExterno: string) => {
        setError(null);
        setLoading(true);
        setMessageLoading("Excluindo forma de pagamento...");

        try {
            await FormaPagamentoService.excluir(idExterno);            
            setMessageToast("Forma de pagamento excluída com sucesso!");
            setShowToastSuccess(true);
            fetchFormasPagamento();
        } catch (error) {
            setError("Erro ao excluir forma de pagamento: " + error);
            setShowToastFailed(true);
        } finally {
            setLoading(false);
        }
    };

    const handleCriar = async () => {
        setLoading(true);
        setMessageLoading("Cadastrando nova forma de pagamento...");

        try {
            await FormaPagamentoService.cadastrar(novaFormaPagamento);
            fetchFormasPagamento();
            setMessageToast("Nova forma de pagamento cadastrada com sucesso!");
            setShowToastSuccess(true);            
        } catch (error) {
            setError("Erro ao cadastrar forma de pagamento: " + error);
            setShowToastFailed(true);
        } finally {
            setLoading(false);
            handleCloseModalCriar();
        }
    }

    if (loading) {
        return <Loading message={messageLoading} />
    }

    return (
        <div className="app-container">
            <MenuLateral />
            <Container fluid className="content">
                <Row>
                    <Col xs={12} md={6} lg={9}>
                        <h1>Formas de Pagamento</h1>
                    </Col>
                    <Col xs={12} md={3} lg={3} className="d-flex justify-content-end">
                        <Button onClick={handleShowModalCriar} className="mt-2"><BiPlus />Nova forma de pagamento</Button>
                    </Col>
                </Row>

                <Row className="mt-3">
                    <Col xs={12} className="text-right">
                        <p>Total de itens: {formasPagamento.length}</p>
                    </Col>
                </Row>

                {formasPagamento.length > 0 ? (
                    formasPagamento.map((formaPagamento) => (
                        <Col xs={12} key={formaPagamento.idExterno} className="mb-2">
                            <Card className="shadow-sm">
                                <Card.Body>
                                    <Container>
                                        <Row>
                                            <Col sm={12} lg={3}>
                                                <strong>Tipo:</strong> {formaPagamento.tipo}
                                            </Col>

                                            <Col sm={12} lg={2}>
                                                <strong>Status:</strong>{' '}
                                                <Badge bg={formaPagamento.ativo ? 'success' : 'secondary'}>
                                                    {formaPagamento.ativo ? 'Ativo' : 'Inativo'}
                                                </Badge>
                                            </Col>

                                            <Col sm={12} lg={3} className="text-end">
                                                <Button variant="primary" onClick={() => handleShowModal(formaPagamento)}><MdEdit /></Button>{' '}
                                                <Button variant="danger" onClick={() => handleShowModalConfirmarExclusao(formaPagamento.idExterno)}><MdDelete /></Button>
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

                <Modal size="lg" show={showModal} onHide={handleCloseModal}>
                    <Modal.Header closeButton>
                        <Modal.Title>Editar Forma de Pagamento</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        {formaPagamentoAtual && (
                            <Form>
                                <Row>
                                    <Col md={6}>
                                        <Form.Group controlId="formTipo">
                                            <Form.Label>Tipo</Form.Label>
                                            <Form.Control
                                                type="text"
                                                value={formaPagamentoAtual.tipo}
                                                onChange={(e) => setFormaPagamentoAtual({ ...formaPagamentoAtual, tipo: e.target.value })}
                                            />
                                        </Form.Group>
                                    </Col>
                                    <Col md={6}>
                                        <Form.Group controlId="formStatus">
                                            <Form.Label>Status</Form.Label>
                                            <Form.Check
                                                type="checkbox"
                                                label="Ativo"
                                                checked={formaPagamentoAtual.ativo}
                                                onChange={(e) => setFormaPagamentoAtual({ ...formaPagamentoAtual, ativo: e.target.checked })}
                                            />
                                        </Form.Group>
                                    </Col>
                                </Row>                                
                            </Form>
                        )}
                    </Modal.Body>
                    <Modal.Footer>
                        <Button variant="secondary" onClick={handleCloseModal}>Cancelar</Button>
                        <Button variant="primary" onClick={handleEditar}>Salvar</Button>
                    </Modal.Footer>
                </Modal>

                <Modal size="lg" show={showModalCriar} onHide={handleCloseModal}>
                    <Modal.Header closeButton>
                        <Modal.Title>Nova Forma de Pagamento</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <Form>
                            <Row>
                                <Col md={6}>
                                    <Form.Group controlId="formTipo">
                                        <Form.Label>Tipo</Form.Label>
                                        <Form.Control
                                            type="text"
                                            value={novaFormaPagamento.tipo}
                                            onChange={(e) => setNovaFormaPagamento({ ...novaFormaPagamento, tipo: e.target.value })}
                                        />
                                    </Form.Group>
                                </Col>
                                <Col md={6}>
                                    <Form.Group controlId="formStatus">
                                        <Form.Label>Status</Form.Label>
                                        <Form.Check
                                            type="checkbox"
                                            label="Ativo"
                                            checked={novaFormaPagamento.ativo}
                                            onChange={(e) => setNovaFormaPagamento({ ...novaFormaPagamento, ativo: e.target.checked })}
                                        />
                                    </Form.Group>
                                </Col>
                            </Row>                                
                        </Form>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button variant="secondary" onClick={handleCloseModalCriar}>Cancelar</Button>
                        <Button variant="primary" onClick={handleCriar}>Salvar</Button>
                    </Modal.Footer>
                </Modal>

                <Modal show={showConfirmarExclusaoModal} onHide={handleCloseModalConfirmarExclusao}>
                    <Modal.Header closeButton>
                        <Modal.Title>Confirmar exclusão</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <p>Tem certeza de que deseja excluir esta forma de pagamento?</p>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button variant="secondary" onClick={handleCloseModalConfirmarExclusao}>
                            Cancelar
                        </Button>
                        <Button variant="danger" onClick={handleConfirmarExclusao}>
                            Sim
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
}