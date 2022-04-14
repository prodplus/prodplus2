export class Produto {
  id: number | null;
  descricao: '';
  tipoMetrico: string;
  validade: number;
  ativo: boolean;

  constructor();
  constructor(
    descricao: string,
    tipoMetrico: string,
    validade: number,
    ativo: boolean,
    id?: number
  );
  constructor(...args: any[]) {
    if (args.length == 0) {
      this.id = null;
      this.descricao = '';
      this.tipoMetrico = 'UN';
      this.validade = 1;
      this.ativo = true;
    } else {
      this.descricao = args[0];
      this.tipoMetrico = args[1];
      this.validade = args[2];
      this.ativo = args[3];
      if (typeof args[4] === 'undefined') this.id = null;
      else this.id = args[5];
    }
  }
}
