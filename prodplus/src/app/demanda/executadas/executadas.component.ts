import {
  AfterViewInit,
  Component,
  ElementRef,
  OnInit,
  ViewChild,
} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Demanda } from 'src/app/models/demanda';
import { ProdutoSaida } from 'src/app/models/produto-saida';
import { DemandaExecService } from 'src/app/services/demanda-exec.service';
import { ProdutoSaidaService } from 'src/app/services/produto-saida.service';
import { openErrorDialog } from 'src/app/shared/mensagem-utils';

@Component({
  selector: 'app-executadas',
  templateUrl: './executadas.component.html',
  styleUrls: ['./executadas.component.css'],
})
export class ExecutadasComponent implements OnInit, AfterViewInit {
  isLoading = false;
  demandas: Demanda[] = [];
  demandaEdit?: Demanda;
  produtos: ProdutoSaida[] = [];
  anos: number[] = [];
  form!: FormGroup;
  formCad!: FormGroup;
  editando = false;
  exibirTabela = false;
  @ViewChild('input')
  input!: ElementRef<HTMLSelectElement>;
  @ViewChild('scrollEdit')
  scrollEdit!: ElementRef<HTMLDivElement>;

  constructor(
    private demandaService: DemandaExecService,
    private produtoService: ProdutoSaidaService,
    private dialog: MatDialog,
    private builder: FormBuilder
  ) {}

  ngOnInit(): void {
    this.form = this.builder.group({
      produto: [null, [Validators.required]],
      ano: [null, [Validators.required]],
    });

    this.formCad = this.builder.group({
      descricao: [null, [Validators.required]],
      quantidade: [null, [Validators.required]],
    });

    this.isLoading = true;
    this.produtoService.listar().subscribe({
      next: (p) => (this.produtos = p),
      error: (err) => {
        this.isLoading = false;
        openErrorDialog(this.dialog, err);
      },
      complete: () => {
        const hoje = new Date();
        for (
          let i = hoje.getUTCFullYear() - 5;
          i <= hoje.getUTCFullYear();
          i++
        ) {
          this.anos.push(i);
        }
        this.isLoading = false;
      },
    });
  }

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.input.nativeElement.focus();
    }, 100);
  }

  onBlurSelect(field: string): string {
    if (this.form.get(field)?.valid) return 'form-select is-valid';
    else return 'form-select is-invalid';
  }

  onBlurInput(field: string): string {
    if (this.formCad.get(field)?.valid) return 'form-control is-valid';
    else return 'form-control is-invalid';
  }

  onSelect() {
    const produto = this.form.get('produto')?.value;
    const ano = this.form.get('ano')?.value;
    if (produto != null && ano != null) {
      this.isLoading = true;
      this.demandaService.listarAno(produto, ano).subscribe({
        next: (d) => (this.demandas = d),
        error: (err) => {
          this.isLoading = false;
          openErrorDialog(this.dialog, err);
        },
        complete: () => {
          this.exibirTabela = true;
          this.isLoading = false;
        },
      });
    }
  }

  reset() {
    this.form.reset();
    this.demandas = [];
    this.editando = false;
    this.exibirTabela = false;
    this.formCad.reset();
  }

  cancelarEdit() {
    this.formCad.reset();
    this.editando = false;
    this.exibirTabela = true;
  }

  onSelectDemanda(d: Demanda) {
    this.demandaEdit = d;
    this.formCad.get('descricao')?.setValue(this.demandaEdit.descricao);
    this.formCad.get('quantidade')?.setValue(this.demandaEdit.quantidade);
    this.editando = true;
    this.exibirTabela = false;
  }

  salvarDemanda() {
    if (this.demandaEdit?.id.produto != null) {
      this.isLoading = true;
      this.demandaEdit.quantidade = this.formCad.get('quantidade')?.value;
      this.demandaService.salvar(this.demandaEdit).subscribe({
        next: (d) => (this.demandaEdit = d),
        error: (err) => {
          this.isLoading = false;
          openErrorDialog(this.dialog, err);
        },
        complete: () => {
          const produto = this.form.get('produto')?.value;
          const ano = this.form.get('ano')?.value;
          this.demandaService.listarAno(produto, ano).subscribe({
            next: (d) => (this.demandas = d),
            error: (err) => {
              this.isLoading = false;
              openErrorDialog(this.dialog, err);
            },
            complete: () => {
              this.cancelarEdit();
              this.isLoading = false;
            },
          });
        },
      });
    }
  }
}
