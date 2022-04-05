import { toDateApi } from '../shared/date-utils';

export class Feriado {
  id: number | null;
  descricao: string;
  data: string;
  repete: boolean;

  constructor();
  constructor(descricao: string, data: string, repete: boolean, id?: number);
  constructor(...args: any[]) {
    if (args.length == 0) {
      this.id = null;
      this.descricao = '';
      this.data = toDateApi(new Date());
      this.repete = false;
    } else {
      this.descricao = args[0];
      this.data = args[1];
      this.repete = args[2];
      if (typeof args[3] === 'undefined') this.id = null;
      else this.id = args[3];
    }
  }
}
