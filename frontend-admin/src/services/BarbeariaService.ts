import axios, { AxiosError } from "axios";
import { apiUrl } from "../api/Constants";
import { Barbearia, BarbeariaAtualizarDto } from "../models/Barbearia";

const idBarbearia = localStorage.getItem('idBarbearia') as string;
const token = localStorage.getItem('token') as string;

export class BarbeariaService {
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    static async buscarInformacoesDaBarbearia(): Promise<Barbearia> {
        try {
            const response = await axios.get(`${apiUrl}/barbearias/idExterno?idExterno=${idBarbearia}`, {
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

    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    static async atualizarBarbearia(barbeariaAtualizar: BarbeariaAtualizarDto): Promise<any> {
        try {
            const response = await axios.patch(`${apiUrl}/barbearias`, barbeariaAtualizar, {
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