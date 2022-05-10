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
import { ProdutoSaidaForm } from 'src/app/models/forms/produto-saida-form';
import { Produto } from 'src/app/models/produto';
import { ProdutoSaida } from 'src/app/models/produto-saida';
import { ProdutoSaidaService } from 'src/app/services/produto-saida.service';
import { ProdutoService } from 'src/app/services/produto.service';
import { openErrorDialog } from 'src/app/shared/mensagem-utils';

@Component({
  selector: 'app-cad-saidas',
  templateUrl: './cad-saidas.component.html',
  styleUrls: ['./cad-saidas.component.css'],
})
export class CadSaidasComponent implements OnInit, AfterViewInit {
  isLoading = false;
  idProduto: number | null = null;
  form!: FormGroup;
  produtos: Produto[] = [];
  @ViewChild('input')
  input!: ElementRef<HTMLInputElement>;

  constructor(
    private builder: FormBuilder,
    private produtoSaidaService: ProdutoSaidaService,
    private produtoService: ProdutoService,
    private router: Router,
    private route: ActivatedRoute,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.form = this.builder.group({
      produto: [null, [Validators.required]],
      valorVenda: [null, [Validators.required, Validators.min(0)]],
      prazoEntrega: [null, [Validators.required, Validators.min(0)]],
      tempoPadrao: [null],
    });

    this.isLoading = true;
    this.produtoService.listar(true).subscribe({
      next: (p) => (this.produtos = p),
      error: (err) => {
        this.isLoading = false;
        openErrorDialog(this.dialog, err);
      },
      complete: () => (this.isLoading = false),
    });
  }

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.input.nativeElement.focus();
    }, 100);

    this.route.paramMap.subscribe((values) => {
      if (values.get('id')) {
        this.isLoading = true;
        const id: number | null = Number(values.get('id'));
        if (id != null) {
          this.produtoSaidaService.buscar(id).subscribe({
            next: (p) => this.carregaForm(p),
            error: (err) => {
              this.isLoading = false;
              openErrorDialog(this.dialog, err);
            },
            complete: () => (this.isLoading = false),
          });
        }
      }
    });
  }

  private carregaForm(prod: ProdutoSaida) {
    this.idProduto = prod.id;
    this.form.patchValue({
      produto: prod.produto.id,
      valorVenda: prod.valorVenda,
      prazoEntrega: prod.prazoEntrega,
      tempoPadrao: prod.tempoPadrao,
    });
  }

  private carregaProduto(): ProdutoSaidaForm {
    return new ProdutoSaidaForm(
      this.form.get('produto')?.value,
      this.form.get('valorVenda')?.value,
      this.form.get('prazoEntrega')?.value,
      this.form.get('tempoPadrao')?.value
    );
  }

  onBlurInput(field: string): string {
    if (this.form.get(field)?.valid) return 'form-control is-valid';
    else return 'form-control is-invalid';
  }

  onBlurSelect(field: string): string {
    if (this.form.get(field)?.valid) return 'form-select is-valid';
    else return 'form-select is-invalid';
  }

  salvar() {
    if (this.idProduto != null) {
      this.produtoSaidaService
        .atualizar(this.idProduto, this.carregaProduto())
        .subscribe({
          error: (err) => {
            this.isLoading = false;
            openErrorDialog(this.dialog, err);
          },
          complete: () => {
            this.isLoading = false;
            this.router.navigate(['/cadastro/produtos-saida']);
          },
        });
    } else {
      this.produtoSaidaService.salvar(this.carregaProduto()).subscribe({
        error: (err) => {
          this.isLoading = false;
          openErrorDialog(this.dialog, err);
        },
        complete: () => {
          this.isLoading = false;
          this.router.navigate(['/cadastro/produtos-saida']);
        },
      });
    }
  }
}
