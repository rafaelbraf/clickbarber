import { Barbeiro } from "./Barbeiro";
import { Cliente } from "./Cliente";
import { Servico } from "./Servico";

export interface Agendamento {
    id: number;
    dataHora: string;
    valorTotal: number;
    tempoDuracaoEmMinutos: number;
    cliente: Cliente;
    barbeiros: Barbeiro[];
    servicos: Servico[];
}

export interface AgendamentoReduzido {
    idExterno: string;
    nomeCliente: string;
    servicos: string[];
    dataHoraInicio: Date;
    dataHoraFim: Date;
}