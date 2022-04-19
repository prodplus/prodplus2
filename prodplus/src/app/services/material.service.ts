import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Page } from '../models/auxiliares/page';
import { Material } from '../models/material';

const URL = environment.url + '/materiais';

@Injectable({ providedIn: 'root' })
export class MaterialService {
  constructor(private http: HttpClient) {}

  salvar(material: Material): Observable<Material> {
    return this.http.post<Material>(URL, material);
  }

  atualizar(id: number, material: Material): Observable<Material> {
    return this.http.put<Material>(`${URL}/${id}`, material);
  }

  buscar(id: number): Observable<Material> {
    return this.http.get<Material>(`${URL}/${id}`);
  }

  listar(): Observable<Material[]> {
    return this.http.get<Material[]>(`${URL}/listar`);
  }

  listarP(pagina: number, quant: number): Observable<Page<Material>> {
    return this.http.get<Page<Material>>(`${URL}/listar/${pagina}/${quant}`);
  }

  listarPD(
    descricao: string,
    pagina: number,
    quant: number
  ): Observable<Page<Material>> {
    return this.http.get<Page<Material>>(
      `${URL}/listar/${pagina}/${quant}/${descricao}`
    );
  }

  excluir(id: number): Observable<any> {
    return this.http.delete(`${URL}/${id}`);
  }
}
