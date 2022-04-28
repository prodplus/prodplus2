import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { LoteMaterialForm } from '../models/forms/lote-material-form';
import { LoteMaterial } from '../models/lote-material';

const URL = environment.url + '/lotes-materiais';

@Injectable({ providedIn: 'root' })
export class LoteMaterialService {
  constructor(private http: HttpClient) {}

  salvar(lote: LoteMaterialForm): Observable<LoteMaterial> {
    return this.http.post<LoteMaterial>(URL, lote);
  }

  atualizar(id: number, lote: LoteMaterialForm): Observable<LoteMaterial> {
    return this.http.put<LoteMaterial>(`${URL}/${id}`, lote);
  }

  buscar(id: number): Observable<LoteMaterial> {
    return this.http.get<LoteMaterial>(`${URL}/${id}`);
  }

  buscarR(rastreio: string): Observable<LoteMaterial> {
    return this.http.get<LoteMaterial>(`${URL}/rastreio/${rastreio}`);
  }

  listar(idMaterial: number): Observable<LoteMaterial[]> {
    return this.http.get<LoteMaterial[]>(`${URL}/listar/${idMaterial}`);
  }

  excluir(id: number): Observable<any> {
    return this.http.delete(`${URL}/${id}`);
  }

  descartar(id: number): Observable<any> {
    return this.http.delete(`${URL}/descartar/${id}`);
  }
}
