import {
  AfterViewInit,
  Component,
  ElementRef,
  OnInit,
  ViewChild,
} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Feriado } from 'src/app/models/feriado';
import { FeriadoService } from 'src/app/services/feriado.service';
import { toDateApi } from 'src/app/shared/date-utils';
import { openErrorDialog } from 'src/app/shared/mensagem-utils';

@Component({
  selector: 'app-cad-feriados',
  templateUrl: './cad-feriados.component.html',
  styleUrls: ['./cad-feriados.component.css'],
})
export class CadFeriadosComponent implements OnInit, AfterViewInit {
  isLoading = false;
  feriado: Feriado = new Feriado();
  form!: FormGroup;
  @ViewChild('input')
  input!: ElementRef<HTMLInputElement>;

  constructor(
    private builder: FormBuilder,
    private feriadoService: FeriadoService,
    private dialog: MatDialog,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.form = this.builder.group({
      descricao: ['', [Validators.required]],
      data: ['', [Validators.required]],
    });
  }

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.input.nativeElement.focus();
    }, 100);
  }

  onBlurInput(field: string): string {
    if (this.form.get(field)?.valid) return 'form-control is-valid';
    else return 'form-control is-invalid';
  }

  private carregaFeriado(): Feriado {
    return new Feriado(
      this.form.get('descricao')?.value.toUpperCase(),
      toDateApi(new Date(this.form.get('data')?.value)),
      false
    );
  }

  salvar() {
    this.isLoading = true;
    this.feriadoService.salvar(this.carregaFeriado()).subscribe({
      next: (f) => (this.feriado = f),
      error: (err) => {
        this.isLoading = false;
        openErrorDialog(this.dialog, err);
      },
      complete: () => {
        this.isLoading = false;
        this.router.navigate(['/cadastro/feriados']);
      },
    });
  }
}
