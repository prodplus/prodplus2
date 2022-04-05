export class Turno {
  inicio: string;
  fim: string;

  constructor();
  constructor(inicio: string, fim: string);
  constructor(...args: any[]) {
    if (args.length == 0) {
      this.inicio = '00:00';
      this.fim = '00:00';
    } else {
      this.inicio = args[0];
      this.fim = args[1];
    }
  }
}

export function ordenaTurnos(turnos: Turno[]): Turno[] {
  turnos.sort((a, b) =>
    a.inicio > b.inicio ? 1 : a.inicio < b.inicio ? -1 : 0
  );
  return turnos;
}
