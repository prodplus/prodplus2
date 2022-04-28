import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Page } from '../models/auxiliares/page';
import { Processo } from '../models/processo';

const URL = environment.url + '/processos';

@Injectable({ providedIn: 'root' })
export class ProcessoService {
  constructor(private http: HttpClient) {}

  salvar(processo: Processo): Observable<Processo> {
    return this.http.post<Processo>(URL, processo);
  }

  atualizar(id: number, processo: Processo): Observable<Processo> {
    return this.http.put<Processo>(`${URL}/${id}`, processo);
  }

  buscar(id: number): Observable<Processo> {
    return this.http.get<Processo>(`${URL}/${id}`);
  }

  listar(ativos: boolean): Observable<Processo[]> {
    return this.http.get<Processo[]>(`${URL}/listar/${ativos}`);
  }

  listarP(
    ativos: boolean,
    pagina: number,
    quant: number
  ): Observable<Page<Processo>> {
    return this.http.get<Page<Processo>>(
      `${URL}/listar/${pagina}/${quant}/${ativos}`
    );
  }

  listarPD(
    descricao: string,
    ativos: boolean,
    pagina: number,
    quant: number
  ): Observable<Page<Processo>> {
    return this.http.get<Page<Processo>>(
      `${URL}/listar/${pagina}/${quant}/${ativos}/${descricao}`
    );
  }

  ativar(id: number): Observable<any> {
    return this.http.delete(`${URL}/ativar/${id}`);
  }

  excluir(id: number): Observable<any> {
    return this.http.delete(`${URL}/${id}`);
  }
}
