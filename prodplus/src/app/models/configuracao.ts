import { Turno } from './auxiliares/turno';

export class Configuracao {
  turnosSemana: Turno[];
  turnosSabado: Turno[];
  turnosDomingo: Turno[];
  estruturaCompleta: boolean;
  mediaIpcm: number;

  constructor();
  constructor(
    turnosSemana: Turno[],
    turnosSabado: Turno[],
    turnosDomingo: Turno[],
    estruturaCompleta: boolean,
    mediaIpcm: number
  );
  constructor(...args: any[]) {
    if (args.length == 0) {
      this.turnosSemana = [];
      this.turnosSabado = [];
      this.turnosDomingo = [];
      this.estruturaCompleta = false;
      this.mediaIpcm = 0;
    } else {
      this.turnosSemana = args[0];
      this.turnosSabado = args[1];
      this.turnosDomingo = args[2];
      this.estruturaCompleta = args[3];
      this.mediaIpcm = args[4];
    }
  }
}
