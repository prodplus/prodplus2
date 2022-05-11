import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Processo } from 'src/app/models/processo';
import { Produto } from 'src/app/models/produto';
import { ProcessoService } from 'src/app/services/processo.service';
import { ProdutoService } from 'src/app/services/produto.service';
import { openErrorDialog } from 'src/app/shared/mensagem-utils';

@Component({
  selector: 'app-lista-amostras',
  templateUrl: './lista-amostras.component.html',
  styleUrls: ['./lista-amostras.component.css'],
})
export class ListaAmostrasComponent implements OnInit {
  isLoading = false;
  processos: Processo[] = [];
  produtos: Produto[] = [];
  form!: FormGroup;
  pagina: number = 1;

  constructor(
    private processoService: ProcessoService,
    private produtoService: ProdutoService,
    private dialog: MatDialog,
    private builder: FormBuilder,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.isLoading = true;

    this.form = this.builder.group({
      processo: [null, [Validators.required]],
      produto: [null, [Validators.required]],
    });

    this.processoService.listar(true).subscribe({
      next: (r) => (this.processos = r),
      error: (err) => {
        this.isLoading = false;
        openErrorDialog(this.dialog, err);
      },
      complete: () => {
        this.produtoService.listar(true).subscribe({
          next: (p) => (this.produtos = p),
          error: (err) => {
            this.isLoading = false;
            openErrorDialog(this.dialog, err);
          },
          complete: () => (this.isLoading = false),
        });
      },
    });
  }

  selecionar() {
    const idProcesso: number | null = this.form.get('processo')?.value;
    const idProduto: number | null = this.form.get('produto')?.value;
    if (idProcesso != null && idProduto != null) {
      this.router.navigate(['/cadastro/amostras/novo', idProcesso, idProduto]);
    }
  }

  onBlurSelect(field: string): string {
    if (this.form.get(field)?.valid) return 'form-select is-valid';
    else return 'form-select is-invalid';
  }
}
