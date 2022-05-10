export class ProdutoSaidaForm {
  produto: number;
  valorVenda: number;
  prazoEntrega: number;
  tempoPadrao: number | null;

  constructor(
    produto: number,
    valorVenda: number,
    prazoEntrega: number,
    tempoPadrao?: number
  ) {
    this.produto = produto;
    this.valorVenda = valorVenda;
    this.prazoEntrega = prazoEntrega;
    if (typeof tempoPadrao === 'undefined') this.tempoPadrao = null;
    else this.tempoPadrao = tempoPadrao;
  }
}
