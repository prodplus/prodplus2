import { Configuracao } from '../configuracao';

export class Turno {
  inicio: string;
  fim: string;

  constructor(inicio: string, fim: string) {
    this.inicio = inicio;
    this.fim = fim;
  }
}

function ordenaTurno(turnos: Turno[]): Turno[] {
  turnos.sort((a, b) =>
    a.inicio < b.inicio ? -1 : a.inicio > b.inicio ? 1 : 0
  );
  return turnos;
}

export function ordenaTurnos(config: Configuracao): Configuracao {
  config.turnosSemana = ordenaTurno(config.turnosSemana);
  config.turnosSabado = ordenaTurno(config.turnosSabado);
  config.turnosDomingo = ordenaTurno(config.turnosDomingo);
  return config;
}
