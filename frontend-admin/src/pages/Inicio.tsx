import { Container } from "react-bootstrap";
import MenuLateral from "../components/MenuLateral";

export const Home = () => {
    return (
        <div className="app-container">
            <MenuLateral />
            <Container fluid className="content">
                <h1>Início</h1>
            </Container>
        </div>
    );
}