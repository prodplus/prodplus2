import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { EstoqueMaterial } from '../models/estoque-material';

const URL = environment.url + '/estoques-materiais';

@Injectable({ providedIn: 'root' })
export class EstoqueMaterialService {
  constructor(private http: HttpClient) {}

  salvar(estoque: EstoqueMaterial): Observable<EstoqueMaterial> {
    return this.http.post<EstoqueMaterial>(URL, estoque);
  }

  buscar(id: number): Observable<EstoqueMaterial> {
    return this.http.get<EstoqueMaterial>(`${URL}/${id}`);
  }

  inserir(estoque: EstoqueMaterial): Observable<EstoqueMaterial> {
    return this.http.post<EstoqueMaterial>(`${URL}/inserir`, estoque);
  }

  listar(idMaterial: number): Observable<EstoqueMaterial[]> {
    return this.http.get<EstoqueMaterial[]>(`${URL}/listar/${idMaterial}`);
  }

  listarP(
    idMaterial: number,
    de: string,
    ate: string
  ): Observable<EstoqueMaterial[]> {
    return this.http.get<EstoqueMaterial[]>(
      `${URL}/listar/${idMaterial}/${de}/${ate}`
    );
  }

  excluir(id: number): Observable<any> {
    return this.http.delete(`${URL}/${id}`);
  }

  atual(idMaterial: number): Observable<EstoqueMaterial> {
    return this.http.get<EstoqueMaterial>(`${URL}/atual/${idMaterial}`);
  }
}
