import { Material } from './material';

export interface LoteMaterial {
  id: number | null;
  rastreio: string;
  material: Material;
  entrada: string | null;
  pedido: string;
  custoTotal: number;
  custoUnitario: number;
  quantInicial: number;
  quantAtual: number;
  ativo: boolean;
}
