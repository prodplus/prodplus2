import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { DemandaId } from 'src/app/models/auxiliares/demanda-id';
import { Demanda } from 'src/app/models/demanda';
import { ProdutoSaida } from 'src/app/models/produto-saida';
import { DemandaExecService } from 'src/app/services/demanda-exec.service';
import { DemandaPrevService } from 'src/app/services/demanda-prev.service';
import { ProdutoSaidaService } from 'src/app/services/produto-saida.service';
import { openErrorDialog } from 'src/app/shared/mensagem-utils';

@Component({
  selector: 'app-previstas',
  templateUrl: './previstas.component.html',
  styleUrls: ['./previstas.component.css'],
})
export class PrevistasComponent implements OnInit {
  isLoading = false;
  previstas: Demanda[] = [];
  executadas: Demanda[] = [];
  produtos: ProdutoSaida[] = [];
  prodForm!: FormGroup;
  tipoPrevisao: string = 'conjugada';

  constructor(
    private builder: FormBuilder,
    private produtoService: ProdutoSaidaService,
    private demandaService: DemandaExecService,
    private previsaoService: DemandaPrevService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.prodForm = this.builder.group({
      produto: [null, [Validators.required]],
    });

    this.produtoService.listar().subscribe((p) => (this.produtos = p));
  }

  previsaoFuncional(idProduto: number) {
    this.isLoading = true;
    this.previsaoService.previsaoF(idProduto).subscribe({
      next: (p) => (this.previstas = p),
      error: (err) => {
        this.isLoading = false;
        openErrorDialog(this.dialog, err);
      },
      complete: () => this.correspondentes(this.previstas),
    });
  }

  previsaoNeural(idProduto: number) {
    this.isLoading = true;
    this.previsaoService.previsao(idProduto).subscribe({
      next: (p) => (this.previstas = p),
      error: (err) => {
        this.isLoading = false;
        openErrorDialog(this.dialog, err);
      },
      complete: () => this.correspondentes(this.previstas),
    });
  }

  previsaoConjugada(idProduto: number) {
    this.isLoading = true;
    this.previsaoService.previsaoY(idProduto).subscribe({
      next: (p) => (this.previstas = p),
      error: (err) => {
        this.isLoading = false;
        openErrorDialog(this.dialog, err);
      },
      complete: () => this.correspondentes(this.previstas),
    });
  }

  private correspondentes(array: Demanda[]) {
    let ids: DemandaId[] = [];
    for (var i of array) {
      const value = new DemandaId(i.id.produto, i.id.ano - 1, i.id.semana);
      ids.push(value);
    }
    this.demandaService.buscarM(ids).subscribe({
      next: (d) => (this.executadas = d),
      error: (err) => {
        this.isLoading = false;
        openErrorDialog(this.dialog, err);
      },
      complete: () => {
        this.salvarCenario();
        this.isLoading = false;
      },
    });
  }

  previsao() {
    const idProduto: number | null = this.prodForm.get('produto')?.value;
    if (idProduto != null) {
      switch (this.tipoPrevisao) {
        case 'funcional':
          this.previsaoFuncional(idProduto);
          break;
        case 'neural':
          this.previsaoNeural(idProduto);
          break;
        case 'conjugada':
          this.previsaoConjugada(idProduto);
          break;
        default:
          break;
      }
    }
  }

  getExecSemana(dem: Demanda): number {
    for (var d of this.executadas) {
      if (d.id.semana == dem.id.semana)
        if (d.quantidade != null) return d.quantidade;
    }
    return 0;
  }

  salvarCenario() {
    this.previsaoService.salvarM(this.previstas).subscribe();
  }
}
