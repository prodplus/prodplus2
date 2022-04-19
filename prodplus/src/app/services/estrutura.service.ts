import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Estrutura } from '../models/auxiliares/estrutura';
import { Quantidade } from '../models/auxiliares/quantidade';
import { Material } from '../models/material';

const URL = environment.url + '/estruturas';

@Injectable({ providedIn: 'root' })
export class EstruturaService {
  constructor(private http: HttpClient) {}

  getQuantidadeMaterial(
    idProduto: number,
    quant: number
  ): Observable<Quantidade<Material>[]> {
    return this.http.get<Quantidade<Material>[]>(
      `${URL}/quantidade-material/${idProduto}/${quant}`
    );
  }

  getEstrutura(idProduto: number): Observable<Estrutura[]> {
    return this.http.get<Estrutura[]>(`${URL}/estrutura/${idProduto}`);
  }
}
