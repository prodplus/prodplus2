import { Component, OnInit, ViewChild } from '@angular/core';
import { ModalComponent } from '../components/modal/modal.component';
import { ordenaTurnos, Turno } from '../models/auxiliares/turno';
import { Configuracao } from '../models/configuracao';
import { ConfiguracaoService } from '../services/configuracao.service';

@Component({
  selector: 'app-configuracao',
  templateUrl: './configuracao.component.html',
  styleUrls: ['./configuracao.component.css'],
})
export class ConfiguracaoComponent implements OnInit {
  isLoading = false;
  configuracao: Configuracao = new Configuracao();
  @ViewChild('modal')
  modal!: ModalComponent;

  constructor(private configService: ConfiguracaoService) {}

  ngOnInit(): void {
    this.isLoading = true;
    this.configService.buscar().subscribe({
      next: (c) => (this.configuracao = c),
      error: (err) => {
        this.isLoading = false;
        this.modal.openPadrao(err);
      },
      complete: () => {
        this.configuracao = ordenaTurnos(this.configuracao);
        this.isLoading = false;
      },
    });
  }

  private salvar() {
    this.isLoading = true;
    this.configService.salvar(this.configuracao).subscribe({
      next: (c) => (this.configuracao = c),
      error: (err) => {
        this.isLoading = false;
        this.modal.openPadrao(err);
      },
      complete: () => {
        this.configuracao = ordenaTurnos(this.configuracao);
        this.isLoading = false;
      },
    });
  }

  private inserirTurno(periodo: string, t: Turno) {
    this.isLoading = true;
    this.configService.inserirTurno(periodo, t).subscribe({
      next: (c) => (this.configuracao = c),
      error: (err) => {
        this.isLoading = false;
        this.modal.openPadrao(err);
      },
      complete: () => {
        this.configuracao = ordenaTurnos(this.configuracao);
        this.isLoading = false;
      },
    });
  }

  alteraTurnosSemana(t: Turno | number) {
    if (typeof t === 'number') this.salvar();
    if (t instanceof Turno) this.inserirTurno('SEMANA', t);
  }

  alteraTurnosSabado(t: Turno | number) {
    if (typeof t === 'number') this.salvar();
    if (t instanceof Turno) this.inserirTurno('SABADO', t);
  }

  alteraTurnosDomingo(t: Turno | number) {
    if (typeof t === 'number') this.salvar();
    if (t instanceof Turno) this.inserirTurno('DOMINGO', t);
  }
}
