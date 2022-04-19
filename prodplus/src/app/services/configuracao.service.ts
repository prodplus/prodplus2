import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Turno } from '../models/auxiliares/turno';
import { Configuracao } from '../models/configuracao';

const URL = environment.url + '/configuracoes';

@Injectable({ providedIn: 'root' })
export class ConfiguracaoService {
  constructor(private http: HttpClient) {}

  salvar(config: Configuracao): Observable<Configuracao> {
    return this.http.post<Configuracao>(URL, config);
  }

  buscar(): Observable<Configuracao> {
    return this.http.get<Configuracao>(URL);
  }

  inserirTurno(periodo: string, turno: Turno): Observable<Configuracao> {
    return this.http.put<Configuracao>(`${URL}/${periodo}`, turno);
  }
}
