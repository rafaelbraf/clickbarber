import React, { useState } from "react";
import { Alert, Button, Card, Col, Form, InputGroup, Row, Spinner } from "react-bootstrap";
import { FaEye, FaEyeSlash } from "react-icons/fa";
import { Link, useNavigate } from "react-router-dom";
import AutenticacaoService from "../services/AutenticacaoService";

export default function Login() {
    const [email, setEmail] = useState('');
    const [senha, setSenha] = useState('');
    const [mostrarSenha, setMostrarSenha] = useState(false);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        setLoading(true);

        const paramsLogin = { email, senha };

        try {
            const response = await AutenticacaoService.fazerLogin(paramsLogin);
            if (response.success) {
                localStorage.setItem('token', response.accessToken);
                localStorage.setItem('idBarbearia', response.result.idExterno);
                navigate('/inicio');
            }
        // eslint-disable-next-line @typescript-eslint/no-explicit-any
        } catch (error: any) {
            setError(error.data.message);
        } finally {
            setLoading(false);
        }
    };

    const togglePasswordVisibility = () => {
        setMostrarSenha(!mostrarSenha);
    };

    const handleCloseAlert = () => {
        setError('');
    }

    return (
        <div className="login-container">
            <Row className="justify-content-center">
                <Col lg={3} md={8} sm={10}>
                    <Card>
                        <Card.Body>
                            <Card.Title className="text-center mt-3">Click Barber</Card.Title>

                            <Form className="mt-5" onSubmit={handleSubmit}>
                                <Form.Group controlId="email">
                                    <Form.Label>Email</Form.Label>
                                    <Form.Control
                                        type="email"
                                        value={email}
                                        onChange={(e) => setEmail(e.target.value)}
                                        placeholder="Digite seu email"
                                        required
                                    />
                                </Form.Group>

                                <Form.Group controlId="senha" className="mt-3">
                                    <Form.Label>Senha</Form.Label>
                                    <InputGroup>                                    
                                        <Form.Control
                                            type={mostrarSenha ? "text" : "password"}
                                            value={senha}
                                            onChange={(e) => setSenha(e.target.value)}
                                            placeholder="Digite sua senha"
                                            required
                                        />
                                        <InputGroup.Text onClick={togglePasswordVisibility} style={{ cursor: 'pointer' }}>
                                            {mostrarSenha ? <FaEyeSlash /> : <FaEye />}
                                        </InputGroup.Text>
                                    </InputGroup>
                                </Form.Group>

                                <Button variant="primary" type="submit" className="w-100 mt-4" disabled={loading}>
                                    {loading ? (
                                        <Spinner animation="border" size="sm" />
                                    ) : (
                                        'Entrar'
                                    )}
                                </Button>

                                {error && (
                                    <Alert variant="danger" onClose={handleCloseAlert} dismissible className="mt-2">{error}</Alert>
                                )}

                                <div className="text-center mt-3">
                                    <span>Ainda não tem conta? <Link to="/">Registre-se aqui</Link></span>
                                </div>
                            </Form>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </div>
    );
}
