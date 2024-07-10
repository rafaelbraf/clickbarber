export interface Barbeiro {
    idExterno: string;
    nome: string;
    celular: string;
    admin: boolean;
    ativo: boolean;
}

export interface BarbeiroCadastro {
    email: string;
    senha: string;
    role: "BARBEIRO";
    data: {
        nome: string;
        cpf: string;
        celular: string;
        admin: boolean;
        ativo: boolean;
        idExternoBarbearia: string;
    };    
}