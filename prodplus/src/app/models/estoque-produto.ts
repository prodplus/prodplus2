import { toDateApi } from '../shared/date-utils';
import { TIPO_ENTRADA } from './enums';
import { Produto } from './produto';

export class EstoqueProduto {
  id: number | null;
  data: string;
  quantidade: number;
  entrada: number;
  tipo: string;
  produto: Produto;

  constructor();
  constructor(
    data: string,
    quantidade: number,
    entrada: number,
    tipo: string,
    produto: Produto,
    id?: number
  );
  constructor(...args: any[]) {
    if (args.length == 0) {
      this.id = null;
      this.data = toDateApi(new Date());
      this.quantidade = 0;
      this.entrada = 0;
      this.tipo = TIPO_ENTRADA[0];
      this.produto = new Produto();
    } else {
      this.data = args[0];
      this.quantidade = args[1];
      this.entrada = args[2];
      this.tipo = args[3];
      this.produto = args[4];
      if (typeof args[5] === 'undefined') this.id = null;
      else this.id = args[5];
    }
  }
}
