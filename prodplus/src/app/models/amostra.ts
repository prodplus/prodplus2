export class Amostra {
  id: number | null;
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

  constructor() {
    this.id = null;
    this.tempoTolerancia = 0;
    this.temposMedidos = [];
    this.periodoSetup = '';
    this.setups = [];
    this.setupsCiclicos = [];
    this.periodo = 0;
    this.setupsAciclicos = [];
    this.quantidadeAciclica = 0;
    this.finalizacoesMedidas = [];
    this.quantidadeFinalizacao = 0;
    this.batelada = 0;
    this.habilidade = '';
    this.esforco = '';
    this.condicao = '';
    this.consistencia = '';
  }
}
