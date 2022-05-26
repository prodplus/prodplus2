import {
  AfterViewInit,
  Component,
  ElementRef,
  OnInit,
  ViewChild,
} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { debounceTime } from 'rxjs';
import { getTipoMetrico } from 'src/app/models/enums';
import { LoteProdutoForm } from 'src/app/models/forms/lote-produto-form';
import { Produto } from 'src/app/models/produto';
import { LoteProdutoService } from 'src/app/services/lote-produto.service';
import { ProdutoService } from 'src/app/services/produto.service';
import { toDateTimeApi } from 'src/app/shared/date-utils';
import { openErrorDialog } from 'src/app/shared/mensagem-utils';

@Component({
  selector: 'app-cad-lotesp',
  templateUrl: './cad-lotesp.component.html',
  styleUrls: ['./cad-lotesp.component.css'],
})
export class CadLotespComponent implements OnInit, AfterViewInit {
  isLoading = false;
  form!: FormGroup;
  loteForm: LoteProdutoForm = new LoteProdutoForm();
  produtoSelecionado: Produto = new Produto();
  produtos: Produto[] = [];
  @ViewChild('input')
  input!: ElementRef<HTMLInputElement>;

  constructor(
    private builder: FormBuilder,
    private produtoService: ProdutoService,
    private loteService: LoteProdutoService,
    private dialog: MatDialog,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.form = this.builder.group({
      produto: [null, [Validators.required]],
      producao: [null, [Validators.required]],
      quantInicial: [null, [Validators.required, Validators.min(1)]],
      custoTotal: [null, [Validators.required, Validators.min(0)]],
      quantAtual: [null, [Validators.required]],
    });
  }

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.input.nativeElement.focus();
      this.form.get('producao')?.disable();
      this.form.get('quantInicial')?.disable();
      this.form.get('quantAtual')?.disable();
      this.form.get('custoTotal')?.disable();
    }, 100);

    this.form
      .get('produto')
      ?.valueChanges.pipe(debounceTime(300))
      .subscribe({
        next: (value) => {
          if (value.length > 0) {
            this.produtoService.listarPD(value, true, 0, 10).subscribe({
              next: (p) => (this.produtos = p.content),
              error: (err) => openErrorDialog(this.dialog, err),
            });
          } else {
            this.produtos = [];
          }
        },
      });

    this.route.paramMap.subscribe((values) => {
      if (values.get('id')) {
        const id: number | null = Number(values.get('id'));
        if (id != null) {
          this.loteService.buscar(id).subscribe({
            next: (l) => (this.loteForm = new LoteProdutoForm(l)),
            error: (err) => {
              this.isLoading = false;
              openErrorDialog(this.dialog, err);
            },
            complete: () => {
              this.isLoading = false;
              this.carregaForm(this.loteForm);
            },
          });
        }
      }
    });
  }

  private carregaForm(l: LoteProdutoForm) {
    this.produtoService.buscar(l.produto).subscribe({
      next: (p) => (this.produtoSelecionado = p),
      error: (err) => {
        this.isLoading = false;
        openErrorDialog(this.dialog, err);
      },
      complete: () => {
        this.form.patchValue({
          produto: this.produtoSelecionado.descricao,
          producao: l.producao,
          quantInicial: l.quantInicial,
          custoTotal: l.custoTotal,
          quantAtual: l.quantAtual,
        });
      },
    });
  }

  private carregaLote(): LoteProdutoForm {
    return new LoteProdutoForm(
      this.loteForm.produto,
      toDateTimeApi(new Date(this.form.get('producao')?.value)),
      +this.form.get('custoTotal')?.value,
      +this.form.get('quantInicial')?.value,
      +this.form.get('quantAtual')?.value,
      this.loteForm.ativo,
      this.loteForm.id != null ? this.loteForm.id : undefined
    );
  }

  onSelectProduto(value: string) {
    let selecionado: Produto = new Produto();
    for (var prod of this.produtos)
      if (prod.descricao === value) selecionado = prod;
    if (selecionado.id != null) {
      this.produtoSelecionado = selecionado;
      this.loteForm.produto = selecionado.id;
      this.form.get('produto')?.setValue(selecionado.descricao);
      this.form.get('produto')?.disable();
      this.form.get('producao')?.enable();
      this.form.get('quantInicial')?.enable();
      this.form.get('quantAtual')?.enable();
      this.form.get('custoTotal')?.enable();
    }
  }

  resetProduto() {
    this.produtos = [];
    this.produtoSelecionado = new Produto();
    this.loteForm.produto = 0;
    this.form.get('produto')?.reset();
    this.form.get('produto')?.enable();
  }

  onBlurInput(field: string): string {
    if (this.form.get(field)?.valid) return 'form-control is-valid';
    else return 'form-control is-invalid';
  }

  getTipoMetricoDesc(): string {
    if (this.produtoSelecionado.id == null) return '';
    else return ' ' + getTipoMetrico(this.produtoSelecionado.tipoMetrico).desc;
  }

  onBlurQuantInicial() {
    const max: number = +this.form.get('quantInicial')?.value;
    this.form
      .get('quantAtual')
      ?.setValidators([Validators.required, Validators.max(max)]);
  }

  salvar() {
    this.isLoading = true;
    if (this.loteForm.id != null) {
      this.loteService
        .atualizar(this.loteForm.id, this.carregaLote())
        .subscribe({
          next: (l) => (this.loteForm = new LoteProdutoForm(l)),
          error: (err) => {
            this.isLoading = false;
            openErrorDialog(this.dialog, err);
          },
          complete: () => {
            this.isLoading = false;
            this.router.navigate(['/controle/lotes-produto']);
          },
        });
    } else {
      this.loteService.salvar(this.carregaLote()).subscribe({
        next: (l) => (this.loteForm = new LoteProdutoForm(l)),
        error: (err) => {
          this.isLoading = false;
          openErrorDialog(this.dialog, err);
        },
        complete: () => {
          this.isLoading = false;
          this.router.navigate(['/controle/lotes-produto']);
        },
      });
    }
  }
}
