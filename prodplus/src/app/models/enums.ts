interface TipoMetrico {
  value: string;
  multi: number;
  desc: string;
}

export const TiposMetricos: TipoMetrico[] = [
  { value: 'UN', multi: 1, desc: 'un' },
  { value: 'DE', multi: 10, desc: 'dezena' },
  { value: 'CE', multi: 100, desc: 'centena' },
  { value: 'MI', multi: 1000, desc: 'milhar' },
  { value: 'CM', multi: 0.01, desc: 'cm' },
  { value: 'CM2', multi: 0.0001, desc: 'cm²' },
  { value: 'CM3', multi: 0.000001, desc: 'cm³' },
  { value: 'M', multi: 1, desc: 'm' },
  { value: 'M2', multi: 1, desc: 'm²' },
  { value: 'M3', multi: 1, desc: 'm³' },
  { value: 'ML', multi: 0.001, desc: 'ml' },
  { value: 'L', multi: 1, desc: 'l' },
  { value: 'G', multi: 1, desc: 'g' },
  { value: 'KG', multi: 1000, desc: 'kg' },
];

export function getTipoMetrico(tipo: string): TipoMetrico {
  for (let t of TiposMetricos) {
    if (t.value === tipo) return t;
  }
  return { value: 'UN', multi: 1, desc: 'un' };
}

export const Condicoes: string[] = [
  'FRACA',
  'REGULAR',
  'MEDIA',
  'BOA',
  'EXCELENTE',
  'IDEAL',
];

export const Consistencias: string[] = [
  'FRACA',
  'REGULAR',
  'MEDIA',
  'BOA',
  'EXCELENTE',
  'PERFEICAO',
];

export const Esforcos: string[] = [
  'FRACO',
  'REGULAR',
  'MEDIO',
  'BOM',
  'EXCELENTE',
  'SUPER_ESFORCO',
];

export const Habilidades: string[] = [
  'FRACA',
  'REGULAR',
  'MEDIA',
  'BOA',
  'EXCELENTE',
  'SUPER_HABIL',
];

export const Periodos: string[] = [
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

export const PeriodosSetup: string[] = ['TURNO', 'DIA', 'MANUAL', 'NENHUM'];

export const SituacoesProcesso: string[] = [
  'ESPERA',
  'SETUPN',
  'SETUPC',
  'SETUPA',
  'SETUPF',
  'PRODUCAO',
  'PARADO',
];

export const TiposEntrada: string[] = [
  'MANUAL',
  'AUTOMATICA',
  'CHEGADA_MATERIAL',
  'PRODUCAO',
];

export const TiposProcesso: string[] = [
  'MANUAL',
  'MAQUINA',
  'AUTOMATIZADO',
  'TERCEIRIZADO',
];

export const TiposSetup: string[] = [
  'NORMAL',
  'CICLICO',
  'ACICLICO',
  'FINALIZACAO',
];
