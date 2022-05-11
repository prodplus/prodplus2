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
import { Amostra } from 'src/app/models/amostra';
import {
  Condicoes,
  Consistencias,
  Esforcos,
  getTipoMetrico,
  Habilidades,
  PeriodosSetup,
} from 'src/app/models/enums';
import { Processo } from 'src/app/models/processo';
import { Produto } from 'src/app/models/produto';
import { AmostraService } from 'src/app/services/amostra.service';
import { ProcessoService } from 'src/app/services/processo.service';
import { ProdutoService } from 'src/app/services/produto.service';
import { openDialog, openErrorDialog } from 'src/app/shared/mensagem-utils';

@Component({
  selector: 'app-cad-amostras',
  templateUrl: './cad-amostras.component.html',
  styleUrls: ['./cad-amostras.component.css'],
})
export class CadAmostrasComponent implements OnInit, AfterViewInit {
  isLoading = false;
  amostra: Amostra = new Amostra();
  processo: Processo = new Processo();
  produto: Produto = new Produto();
  periodosSetup: string[] = PeriodosSetup;
  habilidades: string[] = Habilidades;
  esforcos: string[] = Esforcos;
  condicoes: string[] = Condicoes;
  consistencias: string[] = Consistencias;
  form!: FormGroup;
  @ViewChild('input')
  input!: ElementRef<HTMLInputElement>;

  constructor(
    private amostraService: AmostraService,
    private route: ActivatedRoute,
    private router: Router,
    private dialog: MatDialog,
    private builder: FormBuilder,
    private processoService: ProcessoService,
    private produtoService: ProdutoService
  ) {}

  ngOnInit(): void {
    this.form = this.builder.group({
      tempoTolerancia: [null, [Validators.required, Validators.min(0)]],
      periodoSetup: ['NENHUM', [Validators.required]],
      periodo: [0, [Validators.required, Validators.min(0)]],
      quantidadeAciclica: [0, [Validators.required, Validators.min(0)]],
      quantidadeFinalizacao: [0, [Validators.required, Validators.min(0)]],
      batelada: [null, [Validators.required]],
      habilidade: [null, [Validators.required]],
      esforco: [null, [Validators.required]],
      condicao: [null, [Validators.required]],
      consistencia: [null, [Validators.required]],
    });

    this.isLoading = true;
    this.route.paramMap.subscribe((values) => {
      if (values.get('idProcesso') && values.get('idProcesso')) {
        const idProcesso: number | null = Number(values.get('idProcesso'));
        const idProduto: number | null = Number(values.get('idProduto'));
        if (idProcesso != null && idProduto != null) {
          this.amostraService.buscar(idProduto, idProcesso).subscribe({
            next: (a) => (this.amostra = a),
            error: (err) => {
              this.isLoading = false;
              openErrorDialog(this.dialog, err);
            },
            complete: () => {
              this.processoService.buscar(idProcesso).subscribe({
                next: (p) => (this.processo = p),
                complete: () => {
                  this.produtoService.buscar(idProduto).subscribe({
                    next: (p) => (this.produto = p),
                    complete: () => {
                      this.carregaForm(this.amostra);
                      this.isLoading = false;
                    },
                  });
                },
              });
            },
          });
        } else {
          this.isLoading = false;
          openDialog(this.dialog, 'Erro ao carregar Amostra!', 0);
        }
      }
    });
  }

  private carregaForm(a: Amostra) {
    this.form.patchValue({
      tempoTolerancia: a.tempoTolerancia,
      periodoSetup: a.periodoSetup,
      periodo: a.periodo,
      quantidadeAciclica: a.quantidadeAciclica,
      quantidadeFinalizacao: a.quantidadeFinalizacao,
      batelada: a.batelada,
      habilidade: a.habilidade,
      esforco: a.esforco,
      condicao: a.condicao,
      consistencia: a.consistencia,
    });
  }

  private carregaAmostra(): Amostra {
    this.amostra.tempoTolerancia = +this.form.get('tempoTolerancia')?.value;
    this.amostra.periodoSetup = this.form.get('periodoSetup')?.value;
    this.amostra.periodo = +this.form.get('periodo')?.value;
    this.amostra.quantidadeAciclica =
      +this.form.get('quantidadeAciclica')?.value;
    this.amostra.quantidadeFinalizacao = +this.form.get('quantidadeFinalizacao')
      ?.value;
    this.amostra.batelada = +this.form.get('batelada')?.value;
    this.amostra.habilidade = this.form.get('habilidade')?.value;
    this.amostra.esforco = this.form.get('esforco')?.value;
    this.amostra.condicao = this.form.get('condicao')?.value;
    this.amostra.consistencia = this.form.get('consistencia')?.value;
    return this.amostra;
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

  onBlurSelect(field: string): string {
    if (this.form.get(field)?.valid) return 'form-select is-valid';
    else return 'form-select is-invalid';
  }

  exibirSetups(): boolean {
    return this.form.get('periodoSetup')?.value !== 'NENHUM';
  }

  exibirCiclicos(): boolean {
    return +this.form.get('periodo')?.value != 0;
  }

  exibirAciclicos(): boolean {
    return +this.form.get('quantidadeAciclica')?.value != 0;
  }

  exibirFinalizacoes(): boolean {
    return +this.form.get('finalizacoesMedidas')?.value != 0;
  }

  getTipoDesc(prod: Produto): string {
    return getTipoMetrico(prod.tipoMetrico).desc;
  }

  salvar() {
    this.isLoading = true;
    this.amostraService.salvar(this.carregaAmostra()).subscribe({
      next: (a) => (this.amostra = a),
      error: (err) => {
        this.isLoading = false;
        openErrorDialog(this.dialog, err);
      },
      complete: () => {
        this.isLoading = false;
        this.router.navigate(['/cadastro/amostras']);
      },
    });
  }
}
