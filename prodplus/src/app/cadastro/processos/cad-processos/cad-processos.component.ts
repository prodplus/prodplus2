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
import { TiposProcesso } from 'src/app/models/enums';
import { Processo } from 'src/app/models/processo';
import { ProcessoService } from 'src/app/services/processo.service';
import { openErrorDialog } from 'src/app/shared/mensagem-utils';

@Component({
  selector: 'app-cad-processos',
  templateUrl: './cad-processos.component.html',
  styleUrls: ['./cad-processos.component.css'],
})
export class CadProcessosComponent implements OnInit, AfterViewInit {
  isLoading = false;
  processo: Processo = new Processo();
  form!: FormGroup;
  tipos: string[] = TiposProcesso;
  @ViewChild('input')
  input!: ElementRef<HTMLSelectElement>;

  constructor(
    private builder: FormBuilder,
    private processoService: ProcessoService,
    private router: Router,
    private route: ActivatedRoute,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.form = this.builder.group({
      tipo: [null, [Validators.required]],
      descricao: ['', [Validators.required]],
      custoAdicional: [null, [Validators.required, Validators.min(0)]],
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
          this.processoService.buscar(id).subscribe({
            next: (p) => (this.processo = p),
            error: (err) => {
              this.isLoading = false;
              openErrorDialog(this.dialog, err);
            },
            complete: () => {
              this.carregaForm(this.processo);
              this.isLoading = false;
            },
          });
        }
      }
    });
  }

  private carregaForm(proc: Processo) {
    this.form.patchValue({
      tipo: proc.tipo,
      descricao: proc.descricao,
      custoAdicional: proc.custoAdicional,
    });
  }

  private carregaProcesso(): Processo {
    return new Processo(
      this.form.get('tipo')?.value,
      this.form.get('descricao')?.value.toUpperCase(),
      this.form.get('custoAdicional')?.value,
      this.processo.ativo
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
    if (this.processo.id != null) {
      this.processoService
        .atualizar(this.processo.id, this.carregaProcesso())
        .subscribe({
          next: (p) => (this.processo = p),
          error: (err) => {
            this.isLoading = false;
            openErrorDialog(this.dialog, err);
          },
          complete: () => {
            this.isLoading = false;
            this.router.navigate(['/cadastro/processos']);
          },
        });
    } else {
      this.processoService.salvar(this.carregaProcesso()).subscribe({
        next: (p) => (this.processo = p),
        error: (err) => {
          this.isLoading = false;
          openErrorDialog(this.dialog, err);
        },
        complete: () => {
          this.isLoading = false;
          this.router.navigate(['/cadastro/processos']);
        },
      });
    }
  }
}
