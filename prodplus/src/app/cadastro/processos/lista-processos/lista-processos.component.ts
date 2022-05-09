import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PageChangedEvent } from 'ngx-bootstrap/pagination';
import { DialogComponent } from 'src/app/components/dialog/dialog.component';
import { Page } from 'src/app/models/auxiliares/page';
import { Processo } from 'src/app/models/processo';
import { ProcessoService } from 'src/app/services/processo.service';
import { openErrorDialog } from 'src/app/shared/mensagem-utils';

@Component({
  selector: 'app-lista-processos',
  templateUrl: './lista-processos.component.html',
  styleUrls: ['./lista-processos.component.css'],
})
export class ListaProcessosComponent implements OnInit {
  isLoading = false;
  processos: Page<Processo> = new Page();
  ativos: boolean = true;
  pagina: number = 1;

  constructor(
    private processoService: ProcessoService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.isLoading = true;
    this.recarregar(this.pagina);
  }

  private recarregar(pagina: number) {
    this.processoService.listarP(this.ativos, pagina - 1, 20).subscribe({
      next: (p) => (this.processos = p),
      error: (err) => {
        this.isLoading = false;
        openErrorDialog(this.dialog, err);
      },
      complete: () => (this.isLoading = false),
    });
  }

  mudaPagina(event: PageChangedEvent) {
    this.recarregar(event.page);
  }

  private excluir(id: number) {
    this.isLoading = true;
    this.processoService.excluir(id).subscribe({
      error: (err) => {
        this.isLoading = false;
        openErrorDialog(this.dialog, err);
      },
      complete: () => this.recarregar(this.pagina),
    });
  }

  private ativar(id: number) {
    this.isLoading = true;
    this.processoService.ativar(id).subscribe({
      error: (err) => {
        this.isLoading = false;
        openErrorDialog(this.dialog, err);
      },
      complete: () => this.recarregar(this.pagina),
    });
  }

  openDialogExcluir(processo: Processo) {
    const dialogRef = this.dialog.open(DialogComponent, {
      width: '350px',
      data: { desc: `Excluir ${processo.descricao}??`, value: processo.id },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result?.value != null) this.excluir(result.value);
    });
  }

  openDialogAtivar(processo: Processo) {
    const quest = this.ativos ? 'Desativar' : 'Ativar';
    const dialogRef = this.dialog.open(DialogComponent, {
      width: '350px',
      data: { desc: `${quest} ${processo.descricao}??`, value: processo.id },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result?.value != null) this.ativar(result.value);
    });
  }

  alteraAtivos() {
    this.ativos = !this.ativos;
    this.pagina = 1;
    this.recarregar(this.pagina);
  }
}
