import { Produto } from './produto';

export interface ProdutoSaida {
  id: number | null;
  produto: Produto;
  valorVenda: number;
  prazoEntrega: number;
  tempoPadrao: number;
}
