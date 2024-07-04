import React, { useEffect, useState } from "react";
import { ServicoService } from "../services/ServicoService";
import MenuLateral from "../components/MenuLateral";
import { Alert, Badge, Button, Card, Col, Container, Row, Spinner } from "react-bootstrap";
import { Servico } from "../models/Servico";

import { BiPlus } from "react-icons/bi";
import { MdDelete, MdEdit } from "react-icons/md";

export const Servicos: React.FC = () => {
    const idBarbearia: string = localStorage.getItem('idBarbearia') as string;
    const token: string = localStorage.getItem('token') as string;
    const [servicos, setServicos] = useState<Servico[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

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
                        <Button className="mt-2"><BiPlus />Novo serviço</Button>
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
                                                <strong>Preço:</strong> R$ {servico.preco.toFixed(2)}
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
            </Container>
        </div>
    );
};