import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const URL =
  'http://api.bcb.gov.br/dados/serie/bcdata.sgs.16121/dados/ultimos/12?formato=json';

@Injectable({ providedIn: 'root' })
export class BancoCentralService {
  constructor(private http: HttpClient) {}

  getUltimosDoze(): Observable<Resultado[]> {
    return this.http.get<Resultado[]>(URL);
  }
}

export interface Resultado {
  data: string;
  valor: number;
}
