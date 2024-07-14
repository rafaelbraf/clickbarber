import axios, { AxiosError } from "axios";
import { Cliente } from "../models/Cliente";
import { apiUrl } from "../api/Constants";

const idBarbearia = localStorage.getItem('idBarbearia') as string;
const token = localStorage.getItem('token') as string;

export const ClienteService = {
    buscarClientesDaBarbearia: async (): Promise<Cliente[]> => {
        try {
            const response = await axios.get(`${apiUrl}/clientes/barbearia/${idBarbearia}`, {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });
    
            const data = await response.data;
            return await data.result;
        } catch (error) {
            const axiosError = error as AxiosError;
            if (axiosError.response) {
                throw axiosError.response;
            }

            throw error;
        }
    }
}