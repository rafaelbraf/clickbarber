export enum DiaDaSemana {
    DOMINGO = "Domingo",
    SEGUNDA = "Segunda",
    TERCA = "Terça",
    QUARTA = "Quarta",
    QUINTA = "Quinta",
    SEXTA = "Sexta",
    SABADO = "Sábado"    
}

export const mapDiaDaSemana = (dia: string): DiaDaSemana | undefined => {
    switch (dia) {
        case 'SEGUNDA': return DiaDaSemana.SEGUNDA;
        case 'TERCA': return DiaDaSemana.TERCA;
        case 'QUARTA': return DiaDaSemana.QUARTA;
        case 'QUINTA': return DiaDaSemana.QUINTA;
        case 'SEXTA': return DiaDaSemana.SEXTA;
        case 'SABADO': return DiaDaSemana.SABADO;
        case 'DOMINGO': return DiaDaSemana.DOMINGO;
        default: return undefined;
    }
}