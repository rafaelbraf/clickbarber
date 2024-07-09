export interface Servico {
    idExterno: string;
    nome: string;
    preco: number;
    tempoDuracaoEmMinutos: number;
    ativo: boolean;
}

export interface ServicoCadastro {
    nome: string;
    preco: number;
    tempoDuracaoEmMinutos: number;
    ativo: boolean;
    idExternoBarbearia: string;
}