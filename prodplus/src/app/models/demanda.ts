import { DemandaId } from './auxiliares/demanda-id';

export class Demanda {
  id: DemandaId;
  quantidade: number;

  constructor(
    produto: number,
    ano: number,
    semana: number,
    quantidade: number
  ) {
    this.id = new DemandaId(produto, ano, semana);
    this.quantidade = quantidade;
  }
}
