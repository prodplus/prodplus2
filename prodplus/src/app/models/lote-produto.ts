import { Produto } from './produto';

export interface LoteProduto {
  id: number | null;
  rastreio: string;
  produto: Produto;
  producao: string;
  custoTotal: number;
  quantInicial: number;
  quantAtual: number;
  ativo: boolean;
}
