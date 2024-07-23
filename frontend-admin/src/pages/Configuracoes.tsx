import React from "react";
import MenuLateral from "../components/MenuLateral";
import { Col, Container, Row } from "react-bootstrap";

export const Configuracoes: React.FC = () => {
    return (
        <div className="app-container">
            <MenuLateral />
            <Container fluid className="content">
                <Row>
                    <Col xs={12} md={6} lg={9}>
                        <h1>Configurações</h1>
                    </Col>
                </Row>
            </Container>
        </div>
    );
}