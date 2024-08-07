import axios, { AxiosError } from "axios";
import { FormaPagamento, FormaPagamentoCadastroDto } from "../models/FormaPagamento";
import { apiUrl } from "../api/Constants";

const idBarbearia = localStorage.getItem('idBarbearia') as string;
const token = localStorage.getItem('token') as string;

export const FormaPagamentoService = {
    buscarFormasDePagamentoDaBarbearia: async (): Promise<FormaPagamento[]> => {
        try {
            const response = await axios.get(`${apiUrl}/formas-pagamento/barbearia/${idBarbearia}`, {
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
    },

    cadastrar: async(formaPagamentoCadastroDto: FormaPagamentoCadastroDto): Promise<FormaPagamento> => {
        try {
            const response = await axios.post(`${apiUrl}/formas-pagamento`, formaPagamentoCadastroDto, {
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
    },

    atualizar: async (formaPagamento: FormaPagamento): Promise<FormaPagamento> => {
        try {
            const response = await axios.put(`${apiUrl}/formas-pagamento`, formaPagamento, {
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
    },

    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    excluir: async (idExterno: string): Promise<any> => {
        try {
            await axios.delete(`${apiUrl}/formas-pagamento/${idExterno}`, {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });            
        } catch (error) {
            const axiosError = error as AxiosError;
            if (axiosError.response) {
                throw axiosError.response;
            }

            throw error;
        }
    }
}