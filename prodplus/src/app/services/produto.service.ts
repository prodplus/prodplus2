import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { OrganoNode } from '../models/auxiliares/organo-node';
import { Page } from '../models/auxiliares/page';
import { Quantidade } from '../models/auxiliares/quantidade';
import { Material } from '../models/material';
import { Produto } from '../models/produto';

const URL = environment.url + '/produtos';

@Injectable({ providedIn: 'root' })
export class ProdutoService {
  constructor(private http: HttpClient) {}

  salvar(produto: Produto): Observable<Produto> {
    return this.http.post<Produto>(URL, produto);
  }

  atualizar(id: number, produto: Produto): Observable<Produto> {
    return this.http.put<Produto>(`${URL}/${id}`, produto);
  }

  buscar(id: number): Observable<Produto> {
    return this.http.get<Produto>(`${URL}/${id}`);
  }

  listar(ativos: boolean): Observable<Produto[]> {
    return this.http.get<Produto[]>(`${URL}/listar/${ativos}`);
  }

  listarP(
    ativos: boolean,
    pagina: number,
    quant: number
  ): Observable<Page<Produto>> {
    return this.http.get<Page<Produto>>(
      `${URL}/listar/${pagina}/${quant}/${ativos}`
    );
  }

  listarPD(
    descricao: string,
    ativos: boolean,
    pagina: number,
    quant: number
  ): Observable<Page<Produto>> {
    return this.http.get<Page<Produto>>(
      `${URL}/listar/${pagina}/${quant}/${ativos}/${descricao};`
    );
  }

  ativar(id: number): Observable<any> {
    return this.http.delete(`${URL}/ativar/${id}`);
  }

  excluir(id: number): Observable<any> {
    return this.http.delete(`${URL}/${id}`);
  }

  getMateriais(id: number): Observable<Quantidade<Material>[]> {
    return this.http.get<Quantidade<Material>[]>(`${URL}/materiais/${id}`);
  }

  getProdutos(id: number): Observable<Quantidade<Produto>[]> {
    return this.http.get<Quantidade<Produto>[]>(`${URL}/produtos/${id}`);
  }

  setMateriais(
    id: number,
    materiais: Quantidade<Material>[]
  ): Observable<Produto> {
    return this.http.put<Produto>(`${URL}/materiais/${id}`, materiais);
  }

  setProdutos(
    id: number,
    produtos: Quantidade<Produto>[]
  ): Observable<Produto> {
    return this.http.put<Produto>(`${URL}/produtos/${id}`, produtos);
  }

  getOrgano(id: number, quantidade: number): Observable<OrganoNode> {
    return this.http.get<OrganoNode>(`${URL}/organo/${id}/${quantidade}`);
  }
}
