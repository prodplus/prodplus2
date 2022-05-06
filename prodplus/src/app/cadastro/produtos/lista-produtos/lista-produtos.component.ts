import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PageChangedEvent } from 'ngx-bootstrap/pagination';
import { DialogComponent } from 'src/app/components/dialog/dialog.component';
import { Page } from 'src/app/models/auxiliares/page';
import { getTipoMetrico } from 'src/app/models/enums';
import { Produto } from 'src/app/models/produto';
import { ProdutoService } from 'src/app/services/produto.service';
import { openErrorDialog } from 'src/app/shared/mensagem-utils';

@Component({
  selector: 'app-lista-produtos',
  templateUrl: './lista-produtos.component.html',
  styleUrls: ['./lista-produtos.component.css'],
})
export class ListaProdutosComponent implements OnInit {
  isLoading = false;
  produtos: Page<Produto> = new Page();
  ativos: boolean = true;
  pagina: number = 1;

  constructor(
    private produtoService: ProdutoService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.isLoading = true;
    this.recarregar(this.pagina);
  }

  private recarregar(pagina: number) {
    this.produtoService.listarP(this.ativos, pagina - 1, 20).subscribe({
      next: (p) => (this.produtos = p),
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
    this.produtoService.excluir(id).subscribe({
      error: (err) => {
        this.isLoading = false;
        openErrorDialog(this.dialog, err);
      },
      complete: () => {
        this.recarregar(this.pagina);
      },
    });
  }

  private ativar(id: number) {
    this.isLoading = true;
    this.produtoService.ativar(id).subscribe({
      error: (err) => {
        this.isLoading = false;
        openErrorDialog(this.dialog, err);
      },
      complete: () => this.recarregar(this.pagina),
    });
  }

  openDialogExcluir(produto: Produto) {
    const dialogRef = this.dialog.open(DialogComponent, {
      width: '350px',
      data: { desc: `Excluir ${produto.descricao}??`, value: produto.id },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result?.value != null) this.excluir(result.value);
    });
  }

  openDialogAtivar(produto: Produto) {
    const quest = this.ativos ? 'Desativar' : 'Ativar';
    const dialogRef = this.dialog.open(DialogComponent, {
      width: '350px',
      data: { desc: `${quest} ${produto.descricao}??`, value: produto.id },
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

  getTipoDesc(tipo: string): string {
    return getTipoMetrico(tipo).desc;
  }
}
