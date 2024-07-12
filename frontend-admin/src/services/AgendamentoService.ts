import axios, { AxiosError } from "axios";
import { Agendamento, AgendamentoReduzido } from "../models/Agendamento";
import { apiUrl } from "../api/Constants";

const token = localStorage.getItem('token') as string;

export class AgendamentoService {
    static async buscarAgendamentosDaBarbearia(idExternoBarbearia: string): Promise<AgendamentoReduzido[]> {
        try {
            const response = await axios.get(`${apiUrl}/agendamentos/barbearia/${idExternoBarbearia}/reduzido`, {
                headers: {
                    'Authorization': `Bearer ${token}`
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

    static async buscarAgendamentoPorIdExterno(idExterno: string): Promise<Agendamento> {
        try {
            const response = await axios.get(`${apiUrl}/agendamentos/${idExterno}`, {
                headers: {
                    'Authorization': `Bearer ${token}`
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