import React from "react";
import MenuLateral from "../components/MenuLateral";
import { Col, Container, Row } from "react-bootstrap";
import { MenuConfiguracoes } from "../components/MenuConfiguracoes";
import { Outlet } from "react-router-dom";

export const Configuracoes: React.FC = () => {
    return (
        <div className="app-container">
            <MenuLateral />
            <Container fluid className="content">
                <Col xs={12} md={6} lg={9}>
                    <h1>Configurações</h1>
                </Col>
                <Row>
                    <Col xs={12} md={4} lg={3}>
                        <MenuConfiguracoes />                        
                    </Col>
                    <Col xs={12} md={8} lg={9}>
                        <Outlet />                        
                    </Col>
                </Row>
            </Container>
        </div>
    );
}