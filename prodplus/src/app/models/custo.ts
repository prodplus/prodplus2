export class Custo {
  id: number | null;
  descricao: string;
  periodo: string;
  valor: number;
  ativo: boolean;

  constructor();
  constructor(
    descricao: string,
    periodo: string,
    valor: number,
    ativo: boolean,
    id?: number
  );
  constructor(...args: any[]) {
    if (args.length == 0) {
      this.id = null;
      this.descricao = '';
      this.periodo = '';
      this.valor = 0;
      this.ativo = true;
    } else {
      this.descricao = args[0];
      this.periodo = args[1];
      this.valor = args[2];
      this.ativo = args[3];
      if (typeof args[4] === 'undefined') this.id = null;
      else this.id = args[4];
    }
  }
}
