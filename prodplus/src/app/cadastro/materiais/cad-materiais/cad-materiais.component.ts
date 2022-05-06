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
import { getTipoMetrico, TiposMetricos } from 'src/app/models/enums';
import { Material } from 'src/app/models/material';
import {
  BancoCentralService,
  Resultado,
} from 'src/app/services/banco-central.service';
import { MaterialService } from 'src/app/services/material.service';
import { openErrorDialog } from 'src/app/shared/mensagem-utils';

@Component({
  selector: 'app-cad-materiais',
  templateUrl: './cad-materiais.component.html',
  styleUrls: ['./cad-materiais.component.css'],
})
export class CadMateriaisComponent implements OnInit, AfterViewInit {
  isLoading = false;
  material: Material = new Material();
  form!: FormGroup;
  tiposMetricos: { value: string; multi: number; desc: string }[] =
    TiposMetricos;
  @ViewChild('input')
  input!: ElementRef<HTMLInputElement>;
  mediaIpcm: number = 0.07583;
  correspondentes: string[] = [];

  constructor(
    private builder: FormBuilder,
    private materialService: MaterialService,
    private router: Router,
    private route: ActivatedRoute,
    private dialog: MatDialog,
    private bcService: BancoCentralService
  ) {}

  ngOnInit(): void {
    this.form = this.builder.group({
      descricao: ['', [Validators.required]],
      tipoCompra: [null, [Validators.required]],
      tipoProducao: [null, [Validators.required]],
      fator: [null, [Validators.required, Validators.min(0)]],
      custoCompra: [null, [Validators.required, Validators.min(0)]],
      custoPedido: [null, [Validators.required, Validators.min(0)]],
      custoEstoque: [null, [Validators.required, Validators.min(0)]],
      leadTime: [null, [Validators.required, Validators.min(1)]],
      validade: [null, [Validators.required, Validators.min(1)]],
    });

    let indices: Resultado[] = [];
    this.bcService.getUltimosDoze().subscribe({
      next: (i) => (indices = i),
      error: (err) => openErrorDialog(this.dialog, err),
      complete: () => {
        this.mediaIpcm = 0;
        for (let i of indices) this.mediaIpcm += i.valor;
        this.mediaIpcm /= 12;
      },
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
          this.materialService.buscar(id).subscribe({
            next: (m) => (this.material = m),
            error: (err) => {
              this.isLoading = false;
              openErrorDialog(this.dialog, err);
            },
            complete: () => {
              this.carregaForm(this.material);
              this.isLoading = false;
            },
          });
        }
      }
    });
  }

  private carregaForm(m: Material) {
    this.form.patchValue({
      descricao: m.descricao,
      tipoCompra: m.tipoCompra,
      tipoProducao: m.tipoProducao,
      fator: m.fator,
      custoCompra: m.custoCompra,
      custoPedido: m.custoPedido,
      custoEstoque: m.custoEstoque,
      leadTime: m.leadTime,
      validade: m.validade,
    });
  }

  private carregaMaterial(): Material {
    return new Material(
      this.form.get('descricao')?.value.toUpperCase(),
      this.form.get('tipoCompra')?.value,
      this.form.get('tipoProducao')?.value,
      this.form.get('fator')?.value,
      this.form.get('custoCompra')?.value,
      this.form.get('custoPedido')?.value,
      this.form.get('custoEstoque')?.value,
      this.form.get('leadTime')?.value,
      this.form.get('validade')?.value
    );
  }

  calculaCustoEstoque() {
    const valorCompra = +this.form.get('custoCompra')?.value;
    const validade = +this.form.get('validade')?.value;
    let custoEstoque = 0;
    if (!this.mediaIpcm) this.mediaIpcm = 0.07583;
    if (valorCompra && validade)
      custoEstoque =
        valorCompra * this.mediaIpcm + valorCompra * (365 / validade);
    else custoEstoque = 0;
    if (custoEstoque > valorCompra) custoEstoque = valorCompra;
    this.form.get('custoEstoque')?.setValue(custoEstoque);
  }

  onSelectTipo() {
    this.materialService
      .getCorrespondentes(this.form.get('tipoCompra')?.value)
      .subscribe({
        next: (t) => (this.correspondentes = t),
        error: (err) => openErrorDialog(this.dialog, err),
        complete: () => {
          if (this.material.tipoProducao) {
            this.form.get('tipoProducao')?.setValue(this.material.tipoProducao);
          } else {
            this.form.get('tipoProducao')?.setValue(null);
          }
        },
      });
  }

  getSufixoMinimo(): string {
    return this.form.get('tipoCompra')?.value == null
      ? ''
      : getTipoMetrico(this.form.get('tipoCompra')?.value).desc;
  }

  salvar() {
    this.isLoading = true;
    if (this.material.id != null) {
      this.materialService
        .atualizar(this.material.id, this.carregaMaterial())
        .subscribe({
          next: (m) => (this.material = m),
          error: (err) => {
            this.isLoading = false;
            openErrorDialog(this.dialog, err);
          },
          complete: () => {
            this.isLoading = false;
            this.router.navigate(['/cadastro/materiais']);
          },
        });
    } else {
      this.materialService.salvar(this.carregaMaterial()).subscribe({
        next: (m) => (this.material = m),
        error: (err) => {
          this.isLoading = false;
          openErrorDialog(this.dialog, err);
        },
        complete: () => {
          this.isLoading = false;
          this.router.navigate(['/cadastro/materiais']);
        },
      });
    }
  }

  calculaFator() {
    const compra = getTipoMetrico(this.form.get('tipoCompra')?.value).multi;
    const prod = getTipoMetrico(this.form.get('tipoProducao')?.value).multi;
    const fator = compra / prod;
    this.form.get('fator')?.setValue(fator);
  }

  habilitaCalc(): boolean {
    return !(
      this.form.get('tipoCompra')?.value && this.form.get('tipoProducao')?.value
    );
  }

  habilitaCalcEst(): boolean {
    return !(
      this.form.get('custoCompra')?.value && this.form.get('validade')?.value
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
}
