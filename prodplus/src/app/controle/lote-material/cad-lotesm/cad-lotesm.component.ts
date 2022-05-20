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
import { LoteMaterialForm } from 'src/app/models/forms/lote-material-form';
import { Material } from 'src/app/models/material';
import { LoteMaterialService } from 'src/app/services/lote-material.service';
import { MaterialService } from 'src/app/services/material.service';
import { toDateTimeApi } from 'src/app/shared/date-utils';
import { openErrorDialog } from 'src/app/shared/mensagem-utils';

@Component({
  selector: 'app-cad-lotesm',
  templateUrl: './cad-lotesm.component.html',
  styleUrls: ['./cad-lotesm.component.css'],
})
export class CadLotesmComponent implements OnInit, AfterViewInit {
  isLoading = false;
  form!: FormGroup;
  loteForm: LoteMaterialForm = new LoteMaterialForm();
  materialSelecionado: Material = new Material();
  materiais: Material[] = [];
  recebido = false;
  @ViewChild('input')
  input!: ElementRef<HTMLInputElement>;

  constructor(
    private builder: FormBuilder,
    private materialService: MaterialService,
    private loteService: LoteMaterialService,
    private dialog: MatDialog,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.form = this.builder.group({
      material: [null, [Validators.required]],
      pedido: [null, [Validators.required]],
      quantInicial: [null, [Validators.required, Validators.min(1)]],
      custoUnitario: [null, [Validators.required, Validators.min(0)]],
      custoTotal: [null, [Validators.required]],
      entrada: [null],
      quantAtual: [null],
    });
  }

  ngAfterViewInit(): void {
    this.form
      .get('material')
      ?.valueChanges.pipe(debounceTime(300))
      .subscribe({
        next: (value) => {
          if (value.length > 0) {
            this.materialService.listarPD(value, 0, 20).subscribe({
              next: (m) => (this.materiais = m.content),
              error: (err) => openErrorDialog(this.dialog, err),
            });
          }
        },
      });

    this.route.paramMap.subscribe((values) => {
      if (values.get('id')) {
        const id: number | null = Number(values.get('id'));
        if (id != null) {
          this.loteService.buscar(id).subscribe({
            next: (l) => (this.loteForm = new LoteMaterialForm(l)),
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

  private carregaForm(l: LoteMaterialForm) {
    this.materialService.buscar(l.material).subscribe({
      next: (m) => (this.materialSelecionado = m),
      error: (err) => {
        this.isLoading = false;
        openErrorDialog(this.dialog, err);
      },
      complete: () => {
        this.form.patchValue({
          material: this.materialSelecionado.descricao,
          pedido: l.pedido,
          quantInicial: l.quantInicial,
          custoUnitario: l.custoUnitario,
          custoTotal: l.custoTotal,
          entrada: l.entrada,
          quantAtual: l.quantAtual,
        });
      },
    });
  }

  private carregaLote(): LoteMaterialForm {
    const quantAtual: number = this.form.get('quantAtual')?.value
      ? +this.form.get('quantAtual')?.value
      : +this.form.get('quantInicial')?.value;
    return new LoteMaterialForm(
      this.loteForm.material,
      toDateTimeApi(new Date(this.form.get('pedido')?.value)),
      +this.form.get('custoTotal')?.value,
      +this.form.get('custoUnitario')?.value,
      +this.form.get('quantInicial')?.value,
      quantAtual,
      this.loteForm.ativo,
      this.recebido
        ? toDateTimeApi(new Date(this.form.get('entrada')?.value))
        : undefined,
      this.recebido && this.loteForm.id != null ? this.loteForm.id : undefined
    );
  }

  onSelectMaterial(value: string) {
    let selecionado: Material = new Material();
    for (var mat of this.materiais)
      if (mat.descricao === value) selecionado = mat;
    if (selecionado.id != null) {
      this.materialSelecionado = selecionado;
      this.loteForm.material = selecionado.id;
      this.form.get('material')?.setValue(selecionado.descricao);
      this.form.get('material')?.disable();
    }
  }

  resetMaterial() {
    this.materiais = [];
    this.materialSelecionado = new Material();
    this.loteForm.material = 0;
    this.form.get('material')?.reset();
    this.form.get('material')?.enable();
  }

  onBlurInput(field: string): string {
    if (this.form.get(field)?.valid) return 'form-control is-valid';
    else return 'form-control is-invalid';
  }

  onBlurSelect(field: string): string {
    if (this.form.get(field)?.valid) return 'form-select is-valid';
    else return 'form-select is-invalid';
  }

  getTipoMetrico() {
    if (this.materialSelecionado.id == null) return '';
    else return ' ' + getTipoMetrico(this.materialSelecionado.tipoCompra).desc;
  }

  alteraRecebidos() {
    this.recebido = !this.recebido;
    if (this.recebido) {
      this.form.get('entrada')?.setValidators([Validators.required]);
      this.form.get('quantAtual')?.setValidators([Validators.required]);
    }
  }

  onBlurQuantInicial() {
    if (this.recebido) {
      const max: number = +this.form.get('quantInicial')?.value;
      console.log(max);
      this.form.get('quantAtual')?.setValue(0);
      this.form
        .get('quantAtual')
        ?.setValidators([Validators.required, Validators.max(max)]);
    }
  }

  onBlurCustoUnitario() {
    const value: number = +this.form.get('custoUnitario')?.value;
    const max: number = +this.form.get('quantInicial')?.value;
    const pedido: number = this.materialSelecionado.custoPedido;
    if (value && max) {
      const totalMin: number = value * max + pedido;
      this.form.get('custoTotal')?.setValue(totalMin);
      this.form
        .get('custoTotal')
        ?.setValidators([Validators.required, Validators.min(0)]);
    }
  }

  salvar() {
    if (this.loteForm.id != null) {
      this.loteService
        .atualizar(this.loteForm.id, this.carregaLote())
        .subscribe({
          next: (l) => (this.loteForm = new LoteMaterialForm(l)),
          error: (err) => {
            this.isLoading = false;
            openErrorDialog(this.dialog, err);
          },
          complete: () => {
            this.isLoading = false;
            this.router.navigate(['/controle/lotes-material']);
          },
        });
    } else {
      this.loteService.salvar(this.carregaLote()).subscribe({
        next: (l) => (this.loteForm = new LoteMaterialForm(l)),
        error: (err) => {
          this.isLoading = false;
          openErrorDialog(this.dialog, err);
        },
        complete: () => {
          this.isLoading = false;
          this.router.navigate(['/controle/lotes-material']);
        },
      });
    }
  }
}
