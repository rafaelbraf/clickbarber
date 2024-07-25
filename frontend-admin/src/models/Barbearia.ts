import { Barbeiro } from "./Barbeiro";
import { Servico } from "./Servico";

export interface Barbearia {
    idExterno: string;
    cnpj: string;
    nome: string;
    email: string;
    endereco: string;
    telefone: string;
    servicos: Servico[];
    barbeiros: Barbeiro[];
}

export interface BarbeariaAtualizarDto {
    idExterno: string;
    cnpj: string;
    nome: string;
    email: string;
    endereco: string;
    telefone: string;
}