import { toDateApi } from 'src/app/shared/date-utils';
import { LoteMaterial } from '../lote-material';

export class LoteMaterialForm {
  id: number | null;
  material: number;
  entrada: string | null;
  pedido: string;
  custoTotal: number;
  custoUnitario: number;
  quantInicial: number;
  quantAtual: number;
  ativo: boolean;

  constructor();
  constructor(lote: LoteMaterial);
  constructor(
    material: number,
    pedido: string,
    custoTotal: number,
    custoUnitario: number,
    quantInicial: number,
    quantAtual: number,
    ativo: boolean,
    entrada?: string,
    id?: number
  );
  constructor(...args: any[]) {
    if (args.length == 0) {
      this.id = null;
      this.entrada = null;
      this.material = 0;
      this.pedido = toDateApi(new Date());
      this.custoTotal = 0;
      this.custoUnitario = 0;
      this.quantInicial = 0;
      this.quantAtual = 0;
      this.ativo = true;
    } else if (args.length == 1) {
      const loteI: LoteMaterial = args[0];
      this.id = loteI.id;
      this.entrada = loteI.entrada;
      this.material = loteI.material.id != null ? loteI.material.id : 0;
      this.pedido = loteI.pedido;
      this.custoTotal = loteI.custoTotal;
      this.custoUnitario = loteI.custoUnitario;
      this.quantInicial = loteI.quantInicial;
      this.quantAtual = loteI.quantAtual;
      this.ativo = loteI.ativo;
    } else {
      this.material = args[0];
      this.pedido = args[1];
      this.custoTotal = args[2];
      this.custoUnitario = args[3];
      this.quantInicial = args[4];
      this.quantAtual = args[5];
      this.ativo = args[6];
      if (typeof args[7] === 'undefined') this.entrada = null;
      else this.entrada = args[7];
      if (typeof args[8] === 'undefined') this.id = null;
      else this.id = args[8];
    }
  }
}
