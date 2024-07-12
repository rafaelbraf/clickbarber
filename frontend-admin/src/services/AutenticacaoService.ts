import { apiUrl } from "../api/Constants";
import axios, { AxiosError } from "axios";
import { Barbeiro, BarbeiroCadastro } from "../models/Barbeiro";

interface ParamsLogin {
    email: string;
    senha: string;
}

interface LoginResponse {
    statusCode: number;
    success: boolean;
    message: string;
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    result: any;
    accessToken: string;
}

class AutenticacaoService {
    static async fazerLogin(params: ParamsLogin): Promise<LoginResponse> {
        try {
            const response = await axios.post(`${apiUrl}/auth/login`, params);
            return response.data;
        } catch (error) {
            const axiosError = error as AxiosError;
            if (axiosError.response) {
                throw axiosError.response;
            }

            throw error;
        }
    }

    static async cadastrarBarbeiro(novoBarbeiro: BarbeiroCadastro): Promise<Barbeiro> {
        try {
            const response = await axios.post(`${apiUrl}/auth/cadastrar`, novoBarbeiro);
            const data = await response.data;
            return data.result;
        } catch (error) {
            const axiosError = error as AxiosError;
            if (axiosError.response) {
                throw axiosError.response;
            }

            throw error;
        }
    }
}

export default AutenticacaoService;