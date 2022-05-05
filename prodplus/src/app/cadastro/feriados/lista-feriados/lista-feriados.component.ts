import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PageChangedEvent } from 'ngx-bootstrap/pagination';
import { Page } from 'src/app/models/auxiliares/page';
import { Feriado } from 'src/app/models/feriado';
import { FeriadoService } from 'src/app/services/feriado.service';
import { toDateApi } from 'src/app/shared/date-utils';
import { openDialog, openErrorDialog } from 'src/app/shared/mensagem-utils';

@Component({
  selector: 'app-lista-feriados',
  templateUrl: './lista-feriados.component.html',
  styleUrls: ['./lista-feriados.component.css'],
})
export class ListaFeriadosComponent implements OnInit {
  isLoading = false;
  feriados: Page<Feriado> = new Page();
  pagina: number = 1;

  constructor(
    private feriadoService: FeriadoService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.isLoading = true;
    this.recarregar(this.pagina);
  }

  private recarregar(pagina: number) {
    this.feriadoService
      .listar(toDateApi(new Date()), pagina - 1, 20)
      .subscribe({
        next: (f) => (this.feriados = f),
        error: (err) => {
          this.isLoading = false;
          openErrorDialog(this.dialog, err.error.message);
        },
        complete: () => (this.isLoading = false),
      });
  }

  mudaPagina(event: PageChangedEvent) {
    this.recarregar(event.page);
  }

  openDialogExcluir(feriado: Feriado) {
    if (feriado.id != null) {
      const dialogRef = openDialog(
        this.dialog,
        'Excluir feriado??',
        feriado.id
      );
      dialogRef.afterClosed().subscribe((result) => {
        if (result.value != null) this.excluir(result.value);
      });
    }
  }

  private excluir(id: number) {
    this.isLoading = true;
    this.feriadoService.excluir(id).subscribe({
      error: (err) => {
        this.isLoading = false;
        openErrorDialog(this.dialog, err.error.message);
      },
      complete: () => this.recarregar(this.pagina),
    });
  }
}
