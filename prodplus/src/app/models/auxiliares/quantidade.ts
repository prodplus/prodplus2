export class Quantidade<T> {
  tipo: T;
  quantidade: number;

  constructor(tipo: T, quantidade: number) {
    this.tipo = tipo;
    this.quantidade = quantidade;
  }
}
