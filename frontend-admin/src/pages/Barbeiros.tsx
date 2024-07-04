import React, { useEffect, useState } from "react";
import { Barbeiro } from "../models/Barbeiro";
import { BarbeiroService } from "../services/BarbeiroService";
import { Alert, Badge, Button, Card, Col, Container, Row, Spinner } from "react-bootstrap";
import MenuLateral from "../components/MenuLateral";
import { BiPlus } from "react-icons/bi";
import { MdDelete, MdEdit } from "react-icons/md";

export const Barbeiros: React.FC = () => {
    const idBarbearia = localStorage.getItem('idBarbearia') as string;
    const [barbeiros, setBarbeiros] = useState<Barbeiro[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const fetchBarbeiros = async () => {
            try {
                const barbeirosEncontrados: Barbeiro[] = await BarbeiroService.buscarBarbeirosDaBarbearia(idBarbearia);
                setBarbeiros(barbeirosEncontrados);
            } catch (error) {
                setError(`Erro ao buscar barbeiros ${barbeiros}`);
            } finally {
                setLoading(false);
            }
        }

        fetchBarbeiros();
    }, []);

    const handleEditar = (idExterno: string) => {
        console.log(`Editar barbeiro com ID externo: ${idExterno}`);
    };

    const handleExcluir = (idExterno: string) => {
        console.log(`Excluir barbeiro com ID externo: ${idExterno}`);
    };

    if (loading) {
        return (
            <div className="d-flex flex-column justify-content-center align-items-center" style={{ height: '100vh' }}>
                <Spinner animation="border" />
                <p className="mt-3">Buscando barbeiros...</p>
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
                        <h1>Barbeiros</h1>
                    </Col>
                    <Col xs={12} md={3} lg={3} className="d-flex justify-content-end">
                        <Button className="mt-2"><BiPlus />Novo barbeiro</Button>
                    </Col>
                </Row>
                <Row className="mt-3">
                    <Col xs={12} className="text-right">
                        <p>Total de itens: {barbeiros.length}</p>
                    </Col>
                    {barbeiros.length > 0 ? (
                        barbeiros.map((barbeiro) => (
                            <Col xs={12} key={barbeiro.idExterno} className="mb-4">
                                <Card className="shadow-sm">
                                    <Card.Body>
                                        <div className="d-flex justify-content-between align-items-center mb-2">
                                            <div>
                                                <strong>ID:</strong> {barbeiro.idExterno}
                                            </div>
                                            <div>
                                                <strong>Nome:</strong> {barbeiro.nome}
                                            </div>
                                            <div>
                                                <strong>Celular:</strong> {barbeiro.celular}
                                            </div>
                                            <div>
                                                <strong>Admin:</strong> {barbeiro.admin ? 'Sim' : 'Não'}
                                            </div>
                                            <div>
                                                <strong>Ativo:</strong>{' '}
                                                <Badge bg={barbeiro.ativo ? 'success' : 'secondary'}>
                                                    {barbeiro.ativo ? 'Ativo' : 'Inativo'}
                                                </Badge>
                                            </div>
                                            <div>
                                                <Button variant="primary" onClick={() => handleEditar(barbeiro.idExterno)}><MdEdit /></Button>{' '}
                                                <Button variant="danger" onClick={() => handleExcluir(barbeiro.idExterno)}><MdDelete /></Button>
                                            </div>
                                        </div>
                                        
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
            </Container>
        </div>
    );
}