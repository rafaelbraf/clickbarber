import { Barbeiro } from "./Barbeiro";
import { HorarioFuncionamento } from "./HorarioFuncionamento";
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
    horarios: HorarioFuncionamento[];
}

export interface BarbeariaAtualizarDto {
    idExterno: string;
    cnpj: string;
    nome: string;
    email: string;
    endereco: string;
    telefone: string;
}