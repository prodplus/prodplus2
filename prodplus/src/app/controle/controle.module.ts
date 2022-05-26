import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { CurrencyMaskModule } from 'ng2-currency-mask';
import { NgxMaskModule } from 'ngx-mask';
import { ComponentsModule } from '../components/components.module';
import { ControleRoutingModule } from './controle-routing.module';
import { EstoqueMaterialComponent } from './estoque-material/estoque-material.component';
import { CadLotesmComponent } from './lote-material/cad-lotesm/cad-lotesm.component';
import { ListaLotesmComponent } from './lote-material/lista-lotesm/lista-lotesm.component';
import { CadLotespComponent } from './lote-produto/cad-lotesp/cad-lotesp.component';
import { ListaLotespComponent } from './lote-produto/lista-lotesp/lista-lotesp.component';

@NgModule({
  declarations: [
    ListaLotesmComponent,
    CadLotesmComponent,
    EstoqueMaterialComponent,
    CadLotespComponent,
    ListaLotespComponent,
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    ControleRoutingModule,
    ComponentsModule,
    MatProgressSpinnerModule,
    MatAutocompleteModule,
    MatIconModule,
    CurrencyMaskModule,
    NgxMaskModule,
    MatSlideToggleModule,
  ],
})
export class ControleModule {}
