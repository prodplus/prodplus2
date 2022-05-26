import { AfterViewInit, Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { debounceTime } from 'rxjs';
import { getTipoMetrico } from 'src/app/models/enums';
import { LoteProduto } from 'src/app/models/lote-produto';
import { Produto } from 'src/app/models/produto';
import { LoteProdutoService } from 'src/app/services/lote-produto.service';
import { ProdutoService } from 'src/app/services/produto.service';
import { openErrorDialog } from 'src/app/shared/mensagem-utils';

@Component({
  selector: 'app-lista-lotesp',
  templateUrl: './lista-lotesp.component.html',
  styleUrls: ['./lista-lotesp.component.css'],
})
export class ListaLotespComponent implements OnInit, AfterViewInit {
  isLoading = false;
  lotes: LoteProduto[] = [];
  produtos: Produto[] = [];
  prodForm!: FormGroup;
  produtoSelecionado: Produto = new Produto();
  exibirTabela: boolean = false;
  loadingTabela: boolean = false;

  constructor(
    private dialog: MatDialog,
    private loteService: LoteProdutoService,
    private produtoService: ProdutoService,
    private builder: FormBuilder
  ) {}

  ngOnInit(): void {
    this.prodForm = this.builder.group({
      produto: [null, [Validators.required]],
    });
  }

  ngAfterViewInit(): void {
    this.prodForm
      .get('produto')
      ?.valueChanges.pipe(debounceTime(300))
      .subscribe((value) => {
        if (value?.length > 0) {
          this.produtoService
            .listarPD(value, true, 0, 10)
            .subscribe((p) => (this.produtos = p.content));
        } else {
          this.produtos = [];
        }
      });
  }

  private buscarLotes(idProduto: number) {
    this.loadingTabela = true;
    this.loteService.listar(idProduto).subscribe({
      next: (l) => (this.lotes = l),
      error: (err) => {
        this.loadingTabela = false;
        openErrorDialog(this.dialog, err);
      },
      complete: () => (this.loadingTabela = false),
    });
  }

  onSelectProduto(value: string) {
    for (var prod of this.produtos)
      if (prod.descricao === value) this.produtoSelecionado = prod;
    if (this.produtoSelecionado.id != null) {
      this.prodForm.get('produto')?.disable();
      this.buscarLotes(this.produtoSelecionado.id);
    }
  }

  resetProduto() {
    this.produtos = [];
    this.prodForm.reset();
    this.produtoSelecionado = new Produto();
    this.prodForm.get('produto')?.enable();
    this.lotes = [];
  }

  getTiposDesc(lote: LoteProduto): string {
    return getTipoMetrico(lote.produto.tipoMetrico).desc;
  }
}
