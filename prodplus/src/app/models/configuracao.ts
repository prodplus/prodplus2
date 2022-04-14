import { Turno } from './auxiliares/turno';

export class Configuracao {
  turnosSemana: Turno[];
  turnosSabado: Turno[];
  turnosDomingo: Turno[];
  estruturaCompleta: boolean;
  mediaIpcm: number;

  constructor() {
    this.turnosSemana = [];
    this.turnosSabado = [];
    this.turnosDomingo = [];
    this.estruturaCompleta = false;
    this.mediaIpcm = 0;
  }
}
