import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Page } from '../models/auxiliares/page';
import { Custo } from '../models/custo';

const URL = environment.url + '/custos';

@Injectable({ providedIn: 'root' })
export class CustoService {
  constructor(private http: HttpClient) {}

  salvar(custo: Custo): Observable<Custo> {
    return this.http.post<Custo>(URL, custo);
  }

  atualizar(id: number, custo: Custo): Observable<Custo> {
    return this.http.put<Custo>(`${URL}/${id}`, custo);
  }

  buscar(id: number): Observable<Custo> {
    return this.http.get<Custo>(`${URL}/${id}`);
  }

  listar(ativos: boolean): Observable<Custo[]> {
    return this.http.get<Custo[]>(`${URL}/listar/${ativos}`);
  }

  listarP(
    ativos: boolean,
    pagina: number,
    quant: number
  ): Observable<Page<Custo>> {
    return this.http.get<Page<Custo>>(
      `${URL}/listar/${pagina}/${quant}/${ativos}`
    );
  }

  ativar(id: number): Observable<any> {
    return this.http.delete(`${URL}/ativar/${id}`);
  }

  excluir(id: number): Observable<any> {
    return this.http.delete(`${URL}/${id}`);
  }

  getTotalMes(): Observable<number> {
    return this.http.get<number>(`${URL}/total`);
  }
}
