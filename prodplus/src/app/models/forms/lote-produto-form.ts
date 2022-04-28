import { toDateApi } from 'src/app/shared/date-utils';

export class LoteProdutoForm {
  id: number | null;
  produto: number;
  producao: string;
  custoTotal: number;
  quantInicial: number;
  quantAtual: number;
  ativo: boolean;

  constructor();
  constructor(
    produto: number,
    producao: string,
    custoTotal: number,
    quantInicial: number,
    quantAtual: number,
    ativo: boolean,
    id?: number
  );
  constructor(...args: any[]) {
    if (args.length == 0) {
      this.id = null;
      this.produto = 0;
      this.producao = toDateApi(new Date());
      this.custoTotal = 0;
      this.quantInicial = 0;
      this.quantAtual = 0;
      this.ativo = true;
    } else {
      this.produto = args[0];
      this.producao = args[1];
      this.custoTotal = args[2];
      this.quantInicial = args[3];
      this.quantAtual = args[4];
      this.ativo = args[5];
      if (typeof args[6] === 'undefined') this.id = null;
      else this.id = args[6];
    }
  }
}
