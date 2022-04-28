import { Material } from './material';

export class EstoqueMaterial {
  id: number | null;
  data: string;
  quantidade: number;
  entrada: number;
  tipo: string;
  material: Material;

  constructor(
    data: string,
    quantidade: number,
    entrada: number,
    tipo: string,
    material: Material,
    id?: number
  ) {
    this.data = data;
    this.quantidade = quantidade;
    this.entrada = entrada;
    this.tipo = tipo;
    this.material = material;
    if (typeof id === 'undefined') this.id = null;
    else this.id = id;
  }
}
