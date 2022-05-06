import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PageChangedEvent } from 'ngx-bootstrap/pagination';
import { DialogComponent } from 'src/app/components/dialog/dialog.component';
import { Page } from 'src/app/models/auxiliares/page';
import { getTipoMetrico } from 'src/app/models/enums';
import { Material } from 'src/app/models/material';
import { MaterialService } from 'src/app/services/material.service';
import { openErrorDialog } from 'src/app/shared/mensagem-utils';

@Component({
  selector: 'app-lista-materiais',
  templateUrl: './lista-materiais.component.html',
  styleUrls: ['./lista-materiais.component.css'],
})
export class ListaMateriaisComponent implements OnInit {
  isLoading = false;
  materiais: Page<Material> = new Page();
  pagina: number = 1;

  constructor(
    private materialService: MaterialService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.isLoading = true;
    this.recarregar(this.pagina);
  }

  private recarregar(pagina: number) {
    this.materialService.listarP(pagina - 1, 20).subscribe({
      next: (m) => (this.materiais = m),
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

  openDialogExcluir(mat: Material) {
    const dialogRef = this.dialog.open(DialogComponent, {
      width: '350px',
      data: { desc: `Excluir ${mat.descricao}??`, value: mat.id },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result?.value != null) this.excluir(result.value);
    });
  }

  private excluir(id: number) {
    this.materialService.excluir(id).subscribe({
      error: (err) => openErrorDialog(this.dialog, err),
      complete: () => this.recarregar(this.pagina),
    });
  }

  getDescricaoTipo(tipo: string): string {
    return getTipoMetrico(tipo).desc;
  }
}
