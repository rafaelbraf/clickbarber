import { BrowserRouter, Route, Routes } from "react-router-dom";
import Login from "../pages/Login";
import { Home } from "../pages/Inicio";
import { Barbeiros } from "../pages/Barbeiros";
import { Servicos } from "../pages/Servicos";
import { Agendamentos } from "../pages/Agendamentos";
import { Configuracoes } from "../pages/Configuracoes";
import { ConfiguracoesPerfil } from "../pages/configuracoes/ConfiguracoesPerfil";
import { ConfiguracoesSeguranca } from "../pages/configuracoes/ConfiguracoesSeguranca";
import { ConfiguracoesNotificacoes } from "../pages/configuracoes/ConfiguracoesNotificacoes";
import { ConfiguracoesPrivacidade } from "../pages/configuracoes/ConfiguracoesPrivacidade";
import { FormasPagamento } from "../pages/FormasPagamento";

function AppRoutes() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Login />} />
                <Route path="/inicio" element={<Home />} />
                <Route path="/barbeiros" element={<Barbeiros />} />
                <Route path="/servicos" element={<Servicos />} />
                <Route path="/agendamentos" element={<Agendamentos />} />
                <Route path="/configuracoes" element={<Configuracoes />}>
                    <Route path="perfil" element={<ConfiguracoesPerfil />} />
                    <Route path="seguranca" element={<ConfiguracoesSeguranca />} />
                    <Route path="notificacoes" element={<ConfiguracoesNotificacoes />} />
                    <Route path="privacidade" element={<ConfiguracoesPrivacidade />} />
                </Route>
                <Route path="/formas-pagamento" element={<FormasPagamento />} />
            </Routes>
        </BrowserRouter>
    );
}

export default AppRoutes;