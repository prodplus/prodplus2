import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Page } from '../models/auxiliares/page';
import { ProdutoSaidaForm } from '../models/forms/produto-saida-form';
import { ProdutoSaida } from '../models/produto-saida';

const URL = environment.url + '/produtos-saida';

@Injectable({ providedIn: 'root' })
export class ProdutoSaidaService {
  constructor(private http: HttpClient) {}

  salvar(produto: ProdutoSaidaForm): Observable<ProdutoSaida> {
    return this.http.post<ProdutoSaida>(URL, produto);
  }

  atualizar(id: number, produto: ProdutoSaidaForm): Observable<ProdutoSaida> {
    return this.http.put<ProdutoSaida>(`${URL}/${id}`, produto);
  }

  buscar(id: number): Observable<ProdutoSaida> {
    return this.http.get<ProdutoSaida>(`${URL}/${id}`);
  }

  listar(): Observable<ProdutoSaida[]> {
    return this.http.get<ProdutoSaida[]>(`${URL}/listar`);
  }

  listarP(pagina: number, quant: number): Observable<Page<ProdutoSaida>> {
    return this.http.get<Page<ProdutoSaida>>(
      `${URL}/listar/${pagina}/${quant}`
    );
  }

  excluir(id: number) {
    return this.http.delete(`${URL}/${id}`);
  }
}
