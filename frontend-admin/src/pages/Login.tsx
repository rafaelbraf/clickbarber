import React, { useState } from "react";
import { Alert, Button, Card, Col, Form, InputGroup, Row, Spinner } from "react-bootstrap";
import { FaEye, FaEyeSlash } from "react-icons/fa";
import { Link, useNavigate } from "react-router-dom";
import { Toast, ToastContainer } from "react-bootstrap";
import AutenticacaoService from "../services/AutenticacaoService";
import { MdError } from "react-icons/md";

export default function Login() {
    const [email, setEmail] = useState('');
    const [senha, setSenha] = useState('');
    const [mostrarSenha, setMostrarSenha] = useState(false);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const navigate = useNavigate();
    const [showToastSuccess, setShowToastSuccess] = useState(false);
    const [showToastFailed, setShowToastFailed] = useState(false);

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
            setShowToastFailed(true);
        } finally {
            setLoading(false);
        }
    };

    const togglePasswordVisibility = () => {
        setMostrarSenha(!mostrarSenha);
    };


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

                                <div className="text-center mt-3">
                                    <span>Ainda não tem conta? <Link to="/">Registre-se aqui</Link></span>
                                </div>
                            </Form>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
            <ToastContainer position="top-end" className="p-3" style={{ zIndex: 1 }}>
                <Toast
                 bg="danger"
                 show={showToastFailed}
                 delay={5000}
                 autohide 
                 onClose={() => setShowToastFailed(false)}>
                    <Toast.Body className="text-white"><MdError /><span> {error}</span></Toast.Body>
                    
                </Toast>
            </ToastContainer>
        </div>
    );
}
