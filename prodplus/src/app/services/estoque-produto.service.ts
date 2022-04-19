import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { EstoqueProduto } from '../models/estoque-produto';

const URL = environment.url + '/estoques-produtos';

@Injectable({ providedIn: 'root' })
export class EstoqueProdutoService {
  constructor(private http: HttpClient) {}

  salvar(estoque: EstoqueProduto): Observable<EstoqueProduto> {
    return this.http.post<EstoqueProduto>(URL, estoque);
  }

  buscar(id: number): Observable<EstoqueProduto> {
    return this.http.get<EstoqueProduto>(`${URL}/${id}`);
  }

  listar(idProduto: number): Observable<EstoqueProduto[]> {
    return this.http.get<EstoqueProduto[]>(`${URL}/listar/${idProduto}`);
  }

  listarEntre(
    idProduto: number,
    de: string,
    ate: string
  ): Observable<EstoqueProduto[]> {
    return this.http.get<EstoqueProduto[]>(
      `${URL}/listar/${idProduto}/${de}/${ate}`
    );
  }

  inserir(estoque: EstoqueProduto): Observable<EstoqueProduto> {
    return this.http.post<EstoqueProduto>(`${URL}/inserir`, estoque);
  }

  atual(idProduto: number): Observable<EstoqueProduto> {
    return this.http.get<EstoqueProduto>(`${URL}/inserir/${idProduto}`);
  }

  excluir(id: number): Observable<any> {
    return this.http.delete(`${URL}/${id}`);
  }
}
