export interface TipoMetrico {
  valor: number;
  descricao: string;
}

export function getTipoMetrico(
  value: string,
  tipos: TipoMetrico[]
): TipoMetrico {
  for (let t of tipos) if (t.descricao === value) return t;
  return { valor: 1, descricao: 'UN' };
}
