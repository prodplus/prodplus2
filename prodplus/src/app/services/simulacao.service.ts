import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Estrutura } from '../models/auxiliares/estrutura';

const URL = environment.url + '/simulacoes';

@Injectable({ providedIn: 'root' })
export class SimulacaoService {
  constructor(private http: HttpClient) {}

  getCustos(produto: number): Observable<Estrutura[]> {
    return this.http.get<Estrutura[]>(`${URL}/custos/${produto}`);
  }

  getTempoPadrao(produto: number): Observable<Estrutura[]> {
    return this.http.get<Estrutura[]>(`${URL}/tempo-padrao/${produto}`);
  }
}
