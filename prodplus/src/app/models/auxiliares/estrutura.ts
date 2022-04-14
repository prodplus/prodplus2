import { MapaJson } from './mapa-json';

export interface Estrutura {
  produto: number;
  processo: number;
  abaixo: MapaJson[];
  acima: MapaJson[];
  batelada: number;
  setup: string;
  tempoSetup: number;
  tempoSetupC: number;
  periodoSetup: number;
  tempoSetupA: number;
  quantSetupA: number;
  tempoSetupF: number;
  quantSetupF: number;
  tempoPadrao: number;
  nivel: number;
  destino: number;
  situacao: string;
  canban: number;
  contador: number;
  contSetup: boolean;
  produzido: number;
}
