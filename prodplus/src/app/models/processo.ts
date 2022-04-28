export class Processo {
  id: number | null;
  tipo: string;
  descricao: string;
  custoAdicional: number;
  ativo: boolean;

  constructor();
  constructor(
    tipo: string,
    descricao: string,
    custoAdicional: number,
    ativo: boolean,
    id?: number
  );
  constructor(...args: any[]) {
    if (args.length == 0) {
      this.id = null;
      this.tipo = '';
      this.descricao = '';
      this.custoAdicional = 0;
      this.ativo = true;
    } else {
      this.tipo = args[0];
      this.descricao = args[1];
      this.custoAdicional = args[2];
      this.ativo = args[3];
      if (typeof args[4] === 'undefined') this.id = null;
      else this.id = args[4];
    }
  }
}
