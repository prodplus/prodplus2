import { toDateApi } from '../shared/date-utils';
import { TIPO_ENTRADA } from './enums';
import { Material } from './material';

export class EstoqueMaterial {
  id: number | null;
  data: string;
  quantidade: number;
  entrada: number;
  tipo: string;
  material: Material;

  constructor();
  constructor(
    data: string,
    quantidade: number,
    entrada: number,
    tipo: string,
    material: Material,
    id?: number
  );
  constructor(...args: any[]) {
    if (args.length == 0) {
      this.id = null;
      this.data = toDateApi(new Date());
      this.quantidade = 0;
      this.entrada = 0;
      this.tipo = TIPO_ENTRADA[0];
      this.material = new Material();
    } else {
      this.data = args[0];
      this.quantidade = args[1];
      this.entrada = args[2];
      this.tipo = args[3];
      this.material = args[4];
      if (typeof args[5] === 'undefined') this.id = null;
      else this.id = args[5];
    }
  }
}
