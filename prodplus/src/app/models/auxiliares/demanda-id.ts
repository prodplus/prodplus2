export class DemandaId {
  produto: number;
  ano: number;
  semana: number;

  constructor(produto: number, ano: number, semana: number) {
    this.produto = produto;
    this.ano = ano;
    this.semana = semana;
  }
}
