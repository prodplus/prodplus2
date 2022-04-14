export const CONDICAO = [
  'FRACA',
  'REGULAR',
  'MEDIA',
  'BOA',
  'EXCELENTE',
  'IDEAL',
];

export const CONSISTENCIA = [
  'FRACA',
  'REGULAR',
  'MEDIA',
  'BOA',
  'EXCELENTE',
  'PERFEICAO',
];

export const ESFORCO = [
  'FRACO',
  'REGULAR',
  'MEDIO',
  'BOM',
  'EXCELENTE',
  'SUPER_ESFORCO',
];

export const HABILIDADE = [
  'FRACA',
  'REGULAR',
  'MEDIA',
  'BOA',
  'EXCELENTE',
  'SUPER_HABIL',
];

export const PERIODO = [
  'POR_SEGUNDO',
  'POR_MINUTO',
  'HORARIO',
  'DIARIO',
  'SEMANAL',
  'QUINZENAL',
  'MENSAL',
  'BIMESTRAL',
  'TRIMESTRAL',
  'SEMESTRAL',
  'ANUAL',
  'BATELADA',
];

export const PERIODO_SETUP = ['TURNO', 'DIA', 'MANUAL', 'NENHUM'];

export const SITUACAO_PROCESSO = [
  'ESPERA',
  'SETUPN',
  'SETUPC',
  'SETUPA',
  'SETUPF',
  'PRODUCAO',
  'PARADO',
];

export const TIPO_ENTRADA = [
  'MANUAL',
  'AUTOMATICA',
  'CHEGADA_MATERIAL',
  'PRODUCAO',
];

export const TIPO_METRICO = [
  { nome: 'UN', valor: 1, descricao: 'un' },
  { nome: 'DE', valor: 10, descricao: 'dezena' },
  { nome: 'CE', valor: 100, descricao: 'centena' },
  { nome: 'MI', valor: 1000, descricao: 'milhar' },
  { nome: 'CM', valor: 0.01, descricao: 'cm' },
  { nome: 'CM2', valor: 0.0001, descricao: 'cm²' },
  { nome: 'CM3', valor: 0.000001, descricao: 'cm³' },
  { nome: 'M', valor: 1, descricao: 'm' },
  { nome: 'M2', valor: 1, descricao: 'm²' },
  { nome: 'M3', valor: 1, descricao: 'm³' },
  { nome: 'ML', valor: 0.001, descricao: 'ml' },
  { nome: 'L', valor: 1, descricao: 'l' },
  { nome: 'G', valor: 1, descricao: 'g' },
  { nome: 'KG', valor: 1000, descricao: 'kg' },
];

export function getTipoMetrico(nome: string): {
  nome: string;
  valor: number;
  descricao: string;
} {
  for (let t of TIPO_METRICO) {
    if (t.nome === nome) return t;
  }
  return { nome: 'UN', valor: 1, descricao: 'un' };
}

export const TIPO_PROCESSO = [
  'MANUAL',
  'MAQUINA',
  'AUTOMATIZADO',
  'TERCEIRIZADO',
];

export const TIPO_SETUP = ['NORMAL', 'CICLICO', 'ACICLICO', 'FINALIZACAO'];
