import { BrowserRouter, Route, Routes } from "react-router-dom";
import Login from "../pages/Login";
import { Home } from "../pages/Inicio";
import { Barbeiros } from "../pages/Barbeiros";
import { Servicos } from "../pages/Servicos";
import { Agendamentos } from "../pages/Agendamentos";
import { Configuracoes } from "../pages/Configuracoes";

function AppRoutes() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Login />} />
                <Route path="/inicio" element={<Home />} />
                <Route path="/barbeiros" element={<Barbeiros />} />
                <Route path="/servicos" element={<Servicos />} />
                <Route path="/agendamentos" element={<Agendamentos />} />
                <Route path="/configuracoes" element={<Configuracoes />} />
            </Routes>
        </BrowserRouter>
    );
}

export default AppRoutes;