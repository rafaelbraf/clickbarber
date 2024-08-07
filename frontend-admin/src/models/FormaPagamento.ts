export interface FormaPagamento {
    idExterno: string;
    tipo: string;
    ativo: boolean;
}

export interface FormaPagamentoCadastroDto {
    tipo: string;
    ativo: boolean;
    idExternoBarbearia: string;
}