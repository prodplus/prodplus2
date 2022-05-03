import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PageChangedEvent } from 'ngx-bootstrap/pagination';
import { DialogComponent } from 'src/app/components/dialog/dialog.component';
import { Page } from 'src/app/models/auxiliares/page';
import { Custo } from 'src/app/models/custo';
import { CustoService } from 'src/app/services/custo.service';

@Component({
  selector: 'app-lista-custos',
  templateUrl: './lista-custos.component.html',
  styleUrls: ['./lista-custos.component.css'],
})
export class ListaCustosComponent implements OnInit {
  custos: Page<Custo> = new Page();
  pagina: number = 1;
  ativos: boolean = true;
  totalMensal: number = 0;

  constructor(private custoService: CustoService, private dialog: MatDialog) {}

  ngOnInit(): void {
    this.recarregar(this.pagina);
  }

  mudaPagina(event: PageChangedEvent) {
    this.recarregar(event.page);
  }

  private recarregar(pagina: number) {
    this.custoService.listarP(this.ativos, pagina - 1, 20).subscribe({
      next: (c) => (this.custos = c),
      complete: () => {
        this.custoService
          .getTotalMes()
          .subscribe((t) => (this.totalMensal = t));
      },
    });
  }

  alteraAtivos() {
    this.ativos = !this.ativos;
    this.pagina = 1;
    this.recarregar(this.pagina);
  }

  openDialogAtivar(custo: Custo) {
    const quest = this.ativos ? 'Desativar' : 'Ativar';
    const dialogRef = this.dialog.open(DialogComponent, {
      width: '350px',
      data: { desc: `${quest} o custo??`, value: custo.id },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result?.value != null) this.ativar(result.value);
    });
  }

  openDialogExcluir(custo: Custo) {
    const dialogRef = this.dialog.open(DialogComponent, {
      width: '350px',
      data: { desc: 'Excluir o custo??', value: custo.id },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result?.value != null) this.excluir(result.value);
    });
  }

  private ativar(id: number) {
    this.custoService.ativar(id).subscribe({
      error: (err) => console.log(err),
      complete: () => this.recarregar(this.pagina),
    });
  }

  private excluir(id: number) {
    this.custoService.excluir(id).subscribe({
      error: (err) => console.log(err),
      complete: () => this.recarregar(this.pagina),
    });
  }
}
