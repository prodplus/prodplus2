import { AfterViewInit, Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { debounceTime } from 'rxjs';
import { getTipoMetrico } from 'src/app/models/enums';
import { LoteMaterial } from 'src/app/models/lote-material';
import { Material } from 'src/app/models/material';
import { LoteMaterialService } from 'src/app/services/lote-material.service';
import { MaterialService } from 'src/app/services/material.service';
import { openErrorDialog } from 'src/app/shared/mensagem-utils';

@Component({
  selector: 'app-lista-lotesm',
  templateUrl: './lista-lotesm.component.html',
  styleUrls: ['./lista-lotesm.component.css'],
})
export class ListaLotesmComponent implements OnInit, AfterViewInit {
  isLoading = false;
  lotes: LoteMaterial[] = [];
  materiais: Material[] = [];
  matForm!: FormGroup;
  materialSelecionado: Material = new Material();
  exibirTabela: boolean = false;
  loadingTabela: boolean = false;

  constructor(
    private dialog: MatDialog,
    private loteService: LoteMaterialService,
    private materialService: MaterialService,
    private builder: FormBuilder
  ) {}

  ngOnInit(): void {
    this.matForm = this.builder.group({
      material: [null, [Validators.required]],
    });
  }

  ngAfterViewInit(): void {
    this.matForm
      .get('material')
      ?.valueChanges.pipe(debounceTime(300))
      .subscribe((value) => {
        if (value?.length > 0) {
          this.materialService
            .listarPD(value, 0, 10)
            .subscribe((p) => (this.materiais = p.content));
        }
      });
  }

  onSelectMaterial(value: string) {
    for (var mat of this.materiais)
      if (mat.descricao === value) this.materialSelecionado = mat;
    if (this.materialSelecionado.id != null) {
      this.matForm.get('material')?.disable();
      this.buscarLotes(this.materialSelecionado.id);
    }
  }

  resetMaterial() {
    this.materiais = [];
    this.matForm.reset();
    this.materialSelecionado = new Material();
    this.matForm.get('material')?.enable();
    this.lotes = [];
  }

  private buscarLotes(idMaterial: number) {
    this.loadingTabela = true;
    this.loteService.listar(idMaterial).subscribe({
      next: (l) => (this.lotes = l),
      error: (err) => {
        this.loadingTabela = false;
        openErrorDialog(this.dialog, err);
      },
      complete: () => (this.loadingTabela = false),
    });
  }

  getTipoDesc(lote: LoteMaterial): string {
    return getTipoMetrico(lote.material.tipoProducao).desc;
  }
}
