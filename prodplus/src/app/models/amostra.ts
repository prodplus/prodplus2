import { AmostraId } from './auxiliares/amostra-id';

export class Amostra {
  id: AmostraId;
  temposMedidos: number[];
  periodoSetup: string;
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

  constructor();
  constructor(produto: number, processo: number);
  constructor(
    produto: number,
    processo: number,
    temposMedidos: number[],
    periodoSetup: string,
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
    if (args.length == 0) {
      this.id = new AmostraId(0, 0);
      this.temposMedidos = [];
      this.periodoSetup = '';
      this.setupsCiclicos = [];
      this.periodo = 0;
      this.setupsAciclicos = [];
      this.quantidadeAciclica = 0;
      this.finalizacoesMedidas = [];
      this.quantidadeFinalizacao = 0;
      this.batelada = 0;
      this.habilidade = 'MEDIA';
      this.esforco = 'MEDIO';
      this.condicao = 'MEDIA';
      this.consistencia = 'MEDIA';
    } else if (args.length == 2) {
      this.id = new AmostraId(args[0], args[1]);
      this.temposMedidos = [];
      this.periodoSetup = '';
      this.setupsCiclicos = [];
      this.periodo = 0;
      this.setupsAciclicos = [];
      this.quantidadeAciclica = 0;
      this.finalizacoesMedidas = [];
      this.quantidadeFinalizacao = 0;
      this.batelada = 0;
      this.habilidade = 'MEDIA';
      this.esforco = 'MEDIO';
      this.condicao = 'MEDIA';
      this.consistencia = 'MEDIA';
    } else {
      this.id = new AmostraId(args[0], args[1]);
      this.temposMedidos = args[2];
      this.periodoSetup = args[3];
      this.setupsCiclicos = args[4];
      this.periodo = args[5];
      this.setupsAciclicos = args[6];
      this.quantidadeAciclica = args[7];
      this.finalizacoesMedidas = args[8];
      this.quantidadeFinalizacao = args[9];
      this.batelada = args[10];
      this.habilidade = args[11];
      this.esforco = args[12];
      this.condicao = args[13];
      this.consistencia = args[14];
    }
  }
}
