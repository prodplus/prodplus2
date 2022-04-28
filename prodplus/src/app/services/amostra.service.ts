import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Amostra } from '../models/amostra';

const URL = environment.url + '/amostras';

@Injectable({ providedIn: 'root' })
export class AmostraService {
  constructor(private http: HttpClient) {}

  salvar(amostra: Amostra): Observable<Amostra> {
    return this.http.post<Amostra>(URL, amostra);
  }

  buscar(idProduto: number, idProcesso: number): Observable<Amostra> {
    return this.http.get<Amostra>(`${URL}/${idProduto}/${idProcesso}`);
  }

  listarPorProcesso(idProcesso: number): Observable<Amostra[]> {
    return this.http.get<Amostra[]>(`${URL}/listar-processo/${idProcesso}`);
  }

  listarPorProduto(idProduto: number): Observable<Amostra[]> {
    return this.http.get<Amostra[]>(`${URL}/listar-produto/${idProduto}`);
  }

  getTempoMedio(idProduto: number, idProcesso: number): Observable<number> {
    return this.http.get<number>(
      `${URL}/tempo-medio/${idProduto}/${idProcesso}`
    );
  }

  getTempoNormal(idProduto: number, idProcesso: number): Observable<number> {
    return this.http.get<number>(
      `${URL}/tempo-normal/${idProduto}/${idProcesso}`
    );
  }

  getTempoPadrao(idProduto: number, idProcesso: number): Observable<number> {
    return this.http.get<number>(
      `${URL}/tempo-padrao/${idProduto}/${idProcesso}`
    );
  }

  excluir(idProduto: number, idProcesso: number): Observable<any> {
    return this.http.delete(`${URL}/${idProduto}/${idProcesso}`);
  }
}
