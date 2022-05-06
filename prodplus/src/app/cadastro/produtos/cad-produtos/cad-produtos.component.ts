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
import { TiposMetricos } from 'src/app/models/enums';
import { Produto } from 'src/app/models/produto';
import { ProdutoService } from 'src/app/services/produto.service';
import { openErrorDialog } from 'src/app/shared/mensagem-utils';

@Component({
  selector: 'app-cad-produtos',
  templateUrl: './cad-produtos.component.html',
  styleUrls: ['./cad-produtos.component.css'],
})
export class CadProdutosComponent implements OnInit, AfterViewInit {
  isLoading = false;
  produto: Produto = new Produto();
  form!: FormGroup;
  tiposMetricos: { value: string; multi: number; desc: string }[] =
    TiposMetricos;
  @ViewChild('input')
  input!: ElementRef<HTMLInputElement>;

  constructor(
    private builder: FormBuilder,
    private produtoService: ProdutoService,
    private router: Router,
    private route: ActivatedRoute,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.form = this.builder.group({
      descricao: ['', [Validators.required]],
      tipoMetrico: [null, [Validators.required]],
      validade: [null, [Validators.required, Validators.min(1)]],
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
          this.produtoService.buscar(id).subscribe({
            next: (p) => (this.produto = p),
            error: (err) => {
              this.isLoading = false;
              openErrorDialog(this.dialog, err);
            },
            complete: () => {
              this.carregaForm(this.produto);
              this.isLoading = false;
            },
          });
        }
      }
    });
  }

  private carregaForm(prod: Produto) {
    this.form.patchValue({
      descricao: prod.descricao,
      tipoMetrico: prod.tipoMetrico,
      validade: prod.validade,
    });
  }

  private carregaProd(): Produto {
    return new Produto(
      this.form.get('descricao')?.value.toUpperCase(),
      this.form.get('tipoMetrico')?.value,
      this.form.get('validade')?.value,
      this.produto.ativo
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
    this.isLoading = true;
    if (this.produto.id != null) {
      this.produtoService
        .atualizar(this.produto.id, this.carregaProd())
        .subscribe({
          next: (p) => (this.produto = p),
          error: (err) => {
            this.isLoading = false;
            openErrorDialog(this.dialog, err);
          },
          complete: () => {
            this.isLoading = false;
            this.router.navigate(['/cadastro/produtos']);
          },
        });
    } else {
      this.produtoService.salvar(this.carregaProd()).subscribe({
        next: (p) => (this.produto = p),
        error: (err) => {
          this.isLoading = false;
          openErrorDialog(this.dialog, err);
        },
        complete: () => {
          this.isLoading = false;
          this.router.navigate(['/cadastro/produtos']);
        },
      });
    }
  }
}
