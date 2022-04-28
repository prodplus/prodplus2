import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { LoteProdutoForm } from '../models/forms/lote-produto-form';
import { LoteProduto } from '../models/lote-produto';

const URL = environment.url + '/lotes-produtos';

@Injectable({ providedIn: 'root' })
export class LoteProdutoService {
  constructor(private http: HttpClient) {}

  salvar(lote: LoteProdutoForm): Observable<LoteProduto> {
    return this.http.post<LoteProduto>(URL, lote);
  }

  buscar(id: number): Observable<LoteProduto> {
    return this.http.get<LoteProduto>(`${URL}/${id}`);
  }

  buscarR(rastreio: string): Observable<LoteProduto> {
    return this.http.get<LoteProduto>(`${URL}/rastreio/${rastreio}`);
  }

  atualizar(id: number, lote: LoteProdutoForm): Observable<LoteProduto> {
    return this.http.put<LoteProduto>(`${URL}/${id}`, lote);
  }

  listar(idProduto: number): Observable<LoteProduto[]> {
    return this.http.get<LoteProduto[]>(`${URL}/listar/${idProduto}`);
  }

  excluir(id: number): Observable<any> {
    return this.http.delete(`${URL}/${id}`);
  }
}
