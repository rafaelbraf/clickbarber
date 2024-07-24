import React from "react";
import { Nav } from "react-bootstrap";
import { Link } from "react-router-dom";

export const MenuConfiguracoes: React.FC = () => {
    return (
        <Nav className="flex-column">
            <Nav.Link as={Link} to="/configuracoes/perfil">Perfil</Nav.Link>
            <Nav.Link as={Link} to="/configuracoes/seguranca">Segurança</Nav.Link>
            <Nav.Link as={Link} to="/configuracoes/notificacoes">Notificações</Nav.Link>
            <Nav.Link as={Link} to="/configuracoes/privacidade">Privacidade</Nav.Link>
        </Nav>
    );
}