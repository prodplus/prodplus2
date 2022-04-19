import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Demanda } from '../models/demanda';

const URL = environment.url + '/demandas-prev';

@Injectable({ providedIn: 'root' })
export class DemandaPrevService {
  constructor(private http: HttpClient) {}

  salvar(demanda: Demanda): Observable<Demanda> {
    return this.http.post<Demanda>(URL, demanda);
  }

  salvarM(demandas: Demanda[]): Observable<any> {
    return this.http.post(`${URL}/multiplos`, demandas);
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

  previsao(idProduto: number): Observable<Demanda[]> {
    return this.http.get<Demanda[]>(`${URL}/previsao/${idProduto}`);
  }

  previsaoF(idProduto: number): Observable<Demanda[]> {
    return this.http.get<Demanda[]>(`${URL}/previsao-f/${idProduto}`);
  }

  previsaoX(idProduto: number): Observable<Demanda[]> {
    return this.http.get<Demanda[]>(`${URL}/previsao-x/${idProduto}`);
  }

  previsaoYU(
    idProduto: number,
    semana: number,
    ano: number
  ): Observable<Demanda[]> {
    return this.http.get<Demanda[]>(
      `${URL}/previsao-yu/${idProduto}/${semana}/${ano}`
    );
  }

  previsaoY(idProduto: number): Observable<Demanda[]> {
    return this.http.get<Demanda[]>(`${URL}/previsao-y/${idProduto}`);
  }

  previsaoU(idProduto: number): Observable<Demanda> {
    return this.http.get<Demanda>(`${URL}/previsao-u/${idProduto}`);
  }
}
