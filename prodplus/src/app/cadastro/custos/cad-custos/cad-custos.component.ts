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
import { Custo } from 'src/app/models/custo';
import { Periodos } from 'src/app/models/enums';
import { CustoService } from 'src/app/services/custo.service';
import { openErrorDialog } from 'src/app/shared/mensagem-utils';

@Component({
  selector: 'app-cad-custos',
  templateUrl: './cad-custos.component.html',
  styleUrls: ['./cad-custos.component.css'],
})
export class CadCustosComponent implements OnInit, AfterViewInit {
  isLoading = false;
  custo: Custo = new Custo();
  form!: FormGroup;
  periodos: string[] = Periodos;
  @ViewChild('input')
  input!: ElementRef<HTMLInputElement>;

  constructor(
    private builder: FormBuilder,
    private custoService: CustoService,
    private router: Router,
    private route: ActivatedRoute,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.form = this.builder.group({
      descricao: ['', [Validators.required]],
      periodo: [null, [Validators.required]],
      valor: [null, [Validators.required, Validators.min(0)]],
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
          this.custoService.buscar(id).subscribe({
            next: (c) => (this.custo = c),
            error: (err) => {
              this.isLoading = false;
              openErrorDialog(this.dialog, err);
            },
            complete: () => {
              this.carregaForm(this.custo);
              this.isLoading = false;
            },
          });
        }
      }
    });
  }

  onBlurInput(field: string): string {
    if (this.form.get(field)?.valid) return 'form-control is-valid';
    else return 'form-control is-invalid';
  }

  onBlurSelect(field: string): string {
    if (this.form.get(field)?.valid) return 'form-select is-valid';
    else return 'form-select is-invalid';
  }

  private carregaCusto(): Custo {
    return new Custo(
      this.form.get('descricao')?.value.toUpperCase(),
      this.form.get('periodo')?.value,
      this.form.get('valor')?.value,
      this.custo.ativo
    );
  }

  private carregaForm(c: Custo) {
    this.form.patchValue({
      descricao: c.descricao,
      periodo: c.periodo,
      valor: c.valor,
    });
  }

  salvar() {
    this.isLoading = true;
    if (this.custo.id != null) {
      this.custoService
        .atualizar(this.custo.id, this.carregaCusto())
        .subscribe({
          next: (c) => (this.custo = c),
          error: (err) => {
            this.isLoading = false;
            openErrorDialog(this.dialog, err);
          },
          complete: () => {
            this.isLoading = false;
            this.router.navigate(['/cadastro/custos']);
          },
        });
    } else {
      this.custoService.salvar(this.carregaCusto()).subscribe({
        next: (c) => (this.custo = c),
        error: (err) => {
          this.isLoading = false;
          openErrorDialog(this.dialog, err);
        },
        complete: () => {
          this.isLoading = false;
          this.router.navigate(['/cadastro/custos']);
        },
      });
    }
  }
}
