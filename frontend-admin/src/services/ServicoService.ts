import axios, { AxiosError } from "axios";
import { apiUrl } from "../api/Constants";
import { Servico, ServicoCadastro } from "../models/Servico";

export class ServicoService {
    static async buscarServicosDaBarbearia(idExterno: string, token: string): Promise<Servico[]> {
        try {
            const response = await axios.get(`${apiUrl}/servicos/barbearia/${idExterno}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });

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

    static async cadastrarServico(servico: ServicoCadastro, token: string): Promise<Servico> {
        try {
            const response = await axios.post(`${apiUrl}/servicos`, servico, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });

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