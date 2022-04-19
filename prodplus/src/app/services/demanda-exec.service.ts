import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Demanda } from '../models/demanda';

const URL = environment.url + '/demandas-exec';

@Injectable({ providedIn: 'root' })
export class DemandaExecService {
  constructor(private http: HttpClient) {}

  salvar(demanda: Demanda): Observable<Demanda> {
    return this.http.post<Demanda>(URL, demanda);
  }

  salvarM(demandas: Demanda[]): Observable<boolean> {
    return this.http.post<boolean>(`${URL}/multiplos`, demandas);
  }

  buscar(produto: number, ano: number, semana: number): Observable<Demanda> {
    return this.http.get<Demanda>(`${URL}/${produto}/${ano}/${semana}`);
  }

  listar(idProduto: number): Observable<Demanda[]> {
    return this.http.get<Demanda[]>(`${URL}/listar/${idProduto}`);
  }

  listarAno(idProduto: number, ano: number): Observable<Demanda[]> {
    return this.http.get<Demanda[]>(`${URL}/listar/${idProduto}/${ano}`);
  }

  excluirAno(idProduto: number, ano: number): Observable<any> {
    return this.http.delete(`${URL}/${idProduto}/${ano}`);
  }

  mediaMovel(idProduto: number): Observable<number> {
    return this.http.get<number>(`${URL}/media-movel/${idProduto}`);
  }
}
