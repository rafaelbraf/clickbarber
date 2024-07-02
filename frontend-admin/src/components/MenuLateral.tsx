import React, { useState } from "react";
import { Button, Nav } from "react-bootstrap";
import '../css/menu-lateral.css';
import { BiHome, BiLogOut } from "react-icons/bi";
import { TiThMenu } from "react-icons/ti";
import { IoSettingsOutline } from "react-icons/io5";
import { RiCalendarScheduleLine, RiScissorsFill } from "react-icons/ri";
import { FaUsers } from "react-icons/fa";

const MenuLateral: React.FC = () => {
    const [isExpanded, setIsExpanded] = useState(false);

    const toggleSidebar = () => {
        setIsExpanded(!isExpanded);
    };

    return (
        <div className={`sidebar ${isExpanded ? 'expanded' : 'collapsed'}`}>
            <Button variant="primary" onClick={toggleSidebar} className="toggle-btn">
                <TiThMenu />
            </Button>
            <Nav defaultActiveKey="/inicio" className="flex-column">
                <Nav.Link href="/inicio">
                    <BiHome />
                    {isExpanded && <span>Início</span>}
                </Nav.Link>
                <Nav.Link href="/agendamentos">
                    <RiCalendarScheduleLine />
                    {isExpanded && <span>Agendamentos</span>}
                </Nav.Link>
                <Nav.Link href="/barbeiros">
                    <FaUsers />
                    {isExpanded && <span>Barbeiros</span>}
                </Nav.Link>
                <Nav.Link href="/servicos">
                    <RiScissorsFill />
                    {isExpanded && <span>Serviços</span>}
                </Nav.Link>
                <Nav.Link href="/configuracoes">
                    <IoSettingsOutline />
                    {isExpanded && <span>Configurações</span>}
                </Nav.Link>
                <Nav.Link href="/">
                    <BiLogOut />
                    {isExpanded && <span>Sair</span>}
                </Nav.Link>
            </Nav>
        </div>
    );
};

export default MenuLateral;