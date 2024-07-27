import axios, { AxiosError } from "axios";
import { Barbeiro } from "../models/Barbeiro";
import { apiUrl } from "../api/Constants";

const token = localStorage.getItem('token') as string;

export class BarbeiroService {
    static async buscarBarbeirosDaBarbearia(idExternoBarbearia: string): Promise<Barbeiro[]> {
        try {
            const response = await axios.get(`${apiUrl}/barbeiros/barbearia/${idExternoBarbearia}`, {
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

    static async deletarBarbeiroPorIdExterno(idExternoBarbeiro: string): Promise<void> {
        try {
            const response = await axios.delete(`${apiUrl}/barbeiros/${idExternoBarbeiro}`, {
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