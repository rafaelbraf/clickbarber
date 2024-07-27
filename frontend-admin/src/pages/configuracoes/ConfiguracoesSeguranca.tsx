import { Button, Col, Container, Form, Row } from "react-bootstrap";

export const ConfiguracoesSeguranca = () => {
    return (
        <div>
            <h2>Segurança</h2>
            <Container className="mt-5">
                <Row className="align-items-center">
                    <Col><h3>Alterar senha</h3></Col>
                    <Form.Group className="mt-3" as={Row} controlId="formSenhaAtual">
                        <Form.Label column sm={2}>Senha atual</Form.Label>
                        <Col sm={10}>
                            <Form.Control
                                type="text"
                                placeholder="Senha atual"
                            />
                        </Col>
                    </Form.Group>

                    <Form.Group className="mt-3" as={Row} controlId="formNovaSenha">
                        <Form.Label column sm={2}>Nova senha</Form.Label>
                        <Col sm={10}>
                            <Form.Control
                                type="text"
                                placeholder="Nova senha"
                            />
                        </Col>
                    </Form.Group>

                    <Form.Group className="mt-3" as={Row} controlId="formConfirmarNovaSenha">
                        <Form.Label column sm={2}>Confirmar nova senha</Form.Label>
                        <Col sm={10}>
                            <Form.Control
                                type="password"
                                placeholder="Confirmar nova senha"
                            />
                        </Col>
                    </Form.Group>

                    <Form.Group className="mt-3" as={Row}>
                        <Col sm={{ span: 10, offset: 2 }} className="text-end">
                            <Button variant="primary" type="submit">Alterar senha</Button>
                        </Col>
                    </Form.Group>
                </Row>
            </Container>
        </div>
    );
}