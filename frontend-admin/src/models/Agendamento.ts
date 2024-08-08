import { Barbeiro } from "./Barbeiro";
import { Cliente } from "./Cliente";
import { FormaPagamento } from "./FormaPagamento";
import { Servico } from "./Servico";
import { Usuario } from "./Usuario";

export interface Agendamento {
    idExterno: number;
    dataHora: string;
    valorTotal: number;
    tempoDuracaoEmMinutos: number;
    cliente: Cliente;
    barbeiros: Barbeiro[];
    servicos: Servico[];
    formaPagamento: FormaPagamento;
    criadoPor: Usuario;
}

export interface AgendamentoReduzido {
    idExterno: string;
    nomeCliente: string;
    servicos: string[];
    dataHoraInicio: Date;
    dataHoraFim: Date;
}

export interface AgendamentoCadastro {
    dataHora: string;
    valorTotal: number;
    tempoDuracaoEmMinutos: number;
    barbeariaIdExterno: string;
    clienteIdExterno: string;
    servicosIdsExterno: string[];
    barbeirosIdsExterno: string[];
    formaPagamentoIdExterno: string;
}