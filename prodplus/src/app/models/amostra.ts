import { AmostraId } from './auxiliares/amostra-id';
import {
  CONDICAO,
  CONSISTENCIA,
  ESFORCO,
  HABILIDADE,
  PERIODO_SETUP,
} from './enums';

export class Amostra {
  id: AmostraId;
  tempoTolerancia: number;
  temposMedidos: number[];
  periodoSetup: string;
  setups: number[];
  setupsCiclicos: number[];
  periodo: number;
  setupsAciclicos: number[];
  quantidadeAciclica: number;
  finalizacoesMedidas: number[];
  quantidadeFinalizacao: number;
  batelada: number;
  habilidade: string;
  esforco: string;
  condicao: string;
  consistencia: string;

  constructor(produto: number, processo: number);
  constructor(
    produto: number,
    processo: number,
    tempoTolerancia: number,
    temposMedidos: number[],
    periodoSetup: string,
    setups: number[],
    setupsCiclicos: number[],
    periodo: number,
    setupsAciclicos: number[],
    quantidadeAciclica: number,
    finalizacoesMedidas: number[],
    quantidadeFinalizacao: number,
    batelada: number,
    habilidade: string,
    esforco: string,
    condicao: string,
    consistencia: string
  );
  constructor(...args: any[]) {
    if (args.length == 2) {
      this.id = new AmostraId(args[0], args[1]);
      this.tempoTolerancia = 0;
      this.temposMedidos = [];
      this.periodoSetup = PERIODO_SETUP[3];
      this.setups = [];
      this.setupsCiclicos = [];
      this.periodo = 0;
      this.setupsAciclicos = [];
      this.quantidadeAciclica = 0;
      this.finalizacoesMedidas = [];
      this.quantidadeFinalizacao = 0;
      this.batelada = 1;
      this.habilidade = HABILIDADE[2];
      this.esforco = ESFORCO[2];
      this.condicao = CONDICAO[2];
      this.consistencia = CONSISTENCIA[2];
    } else {
      this.id = new AmostraId(args[0], args[1]);
      this.tempoTolerancia = args[2];
      this.temposMedidos = args[3];
      this.periodoSetup = args[4];
      this.setups = args[5];
      this.setupsCiclicos = args[6];
      this.periodo = args[7];
      this.setupsAciclicos = args[8];
      this.quantidadeAciclica = args[9];
      this.finalizacoesMedidas = args[10];
      this.quantidadeFinalizacao = args[11];
      this.batelada = args[12];
      this.habilidade = args[13];
      this.esforco = args[14];
      this.condicao = args[15];
      this.consistencia = args[16];
    }
  }
}
