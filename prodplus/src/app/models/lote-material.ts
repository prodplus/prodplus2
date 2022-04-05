import { Material } from './material';

export class LoteMaterial {
  id: number | null;
  rastreio: string | null;
  material: Material;
  entrada: string;
  pedido: string;
  custoTotal: number;
  custoUnitario: number;
  quantInicial: number;
  quantAtual: number;
  ativo: boolean;

  constructor();
  constructor(
    material: Material,
    entrada: string,
    pedido: string,
    custoTotal: number,
    custoUnitario: number,
    quantInicial: number,
    quantAtual: number,
    ativo: boolean,
    id?: number
  );
  constructor(...args: any[]) {
    if (args.length == 0) {
      this.id = null;
      this.rastreio = null;
      this.material = new Material();
      this.entrada = '';
      this.pedido = '';
      this.custoTotal = 0;
      this.custoUnitario = 0;
      this.quantInicial = 0;
      this.quantAtual = 0;
      this.ativo = true;
    } else {
      this.rastreio = null;
      this.material = args[0];
      this.entrada = args[1];
      this.pedido = args[2];
      this.custoTotal = args[3];
      this.custoUnitario = args[4];
      this.quantInicial = args[5];
      this.quantAtual = args[6];
      this.ativo = args[7];
      if (typeof args[8] === 'undefined') this.id = null;
      else this.id = args[8];
    }
  }
}
