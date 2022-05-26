import { AfterViewInit, Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Material } from 'src/app/models/material';
import { EstoqueMaterialService } from 'src/app/services/estoque-material.service';
import { MaterialService } from 'src/app/services/material.service';

@Component({
  selector: 'app-estoque-material',
  templateUrl: './estoque-material.component.html',
  styleUrls: ['./estoque-material.component.css'],
})
export class EstoqueMaterialComponent implements OnInit, AfterViewInit {
  isLoading = false;
  materiais: Material[] = [];
  matForm!: FormGroup;

  constructor(
    private dialog: MatDialog,
    private materialService: MaterialService,
    private estoqueService: EstoqueMaterialService,
    private builder: FormBuilder
  ) {}

  ngOnInit(): void {
    this.matForm = this.builder.group({
      material: [null, [Validators.required]],
    });
  }

  ngAfterViewInit(): void {
    this.matForm.get('material')?.valueChanges.subscribe((value) => {
      if (value?.length > 0) {
        this.materialService
          .listarPD(value, 0, 10)
          .subscribe((m) => (this.materiais = m.content));
      }
    });
  }
}
