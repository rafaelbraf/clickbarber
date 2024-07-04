import { BrowserRouter, Route, Routes } from "react-router-dom";
import Login from "../pages/Login";
import { Home } from "../pages/Inicio";
import { Servicos } from "../pages/Servicos";

function AppRoutes() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Login />} />
                <Route path="/inicio" element={<Home />} />
                <Route path="/servicos" element={<Servicos />} />
            </Routes>
        </BrowserRouter>
    );
}

export default AppRoutes;