import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PageChangedEvent } from 'ngx-bootstrap/pagination';
import { DialogComponent } from 'src/app/components/dialog/dialog.component';
import { Page } from 'src/app/models/auxiliares/page';
import { getTipoMetrico } from 'src/app/models/enums';
import { Produto } from 'src/app/models/produto';
import { ProdutoSaida } from 'src/app/models/produto-saida';
import { ProdutoSaidaService } from 'src/app/services/produto-saida.service';
import { openErrorDialog } from 'src/app/shared/mensagem-utils';

@Component({
  selector: 'app-lista-saidas',
  templateUrl: './lista-saidas.component.html',
  styleUrls: ['./lista-saidas.component.css'],
})
export class ListaSaidasComponent implements OnInit {
  isLoading = false;
  produtosSaida: Page<ProdutoSaida> = new Page();
  pagina: number = 1;

  constructor(
    private produtoSaidaService: ProdutoSaidaService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.isLoading = true;
    this.recarregar(this.pagina);
  }

  private recarregar(pagina: number) {
    this.produtoSaidaService.listarP(pagina - 1, 20).subscribe({
      next: (p) => (this.produtosSaida = p),
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
    this.produtoSaidaService.excluir(id).subscribe({
      error: (err) => {
        this.isLoading = false;
        openErrorDialog(this.dialog, err);
      },
      complete: () => this.recarregar(this.pagina),
    });
  }

  openDialogExcluir(prod: ProdutoSaida) {
    const dialogRef = this.dialog.open(DialogComponent, {
      width: '350px',
      data: { desc: `Excluir ${prod.produto.descricao}??`, value: prod.id },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result?.value != null) this.excluir(result.value);
    });
  }

  getTipoDesc(prod: Produto): string {
    return getTipoMetrico(prod.tipoMetrico).desc;
  }
}
