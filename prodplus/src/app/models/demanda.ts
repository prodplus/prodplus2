import { DemandaId } from './auxiliares/demanda-id';

export class Demanda {
  id: DemandaId;
  quantidade: number | null;
  descricao: string | null;

  constructor(
    produto: number,
    ano: number,
    semana: number,
    quantidade?: number
  ) {
    this.id = new DemandaId(produto, ano, semana);
    if (typeof quantidade !== 'undefined') this.quantidade = quantidade;
    else this.quantidade = null;
    this.descricao = null;
  }
}
