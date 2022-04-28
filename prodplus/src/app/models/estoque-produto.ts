import { Produto } from './produto';

export class EstoqueProduto {
  id: number | null;
  data: string;
  quantidade: number;
  entrada: number;
  tipo: string;
  produto: Produto;

  constructor(
    data: string,
    quantidade: number,
    entrada: number,
    tipo: string,
    produto: Produto,
    id?: number
  ) {
    this.data = data;
    this.quantidade = quantidade;
    this.entrada = entrada;
    this.tipo = tipo;
    this.produto = produto;
    if (typeof id === 'undefined') this.id = null;
    else this.id = id;
  }
}
