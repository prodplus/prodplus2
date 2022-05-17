import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { PaginationModule } from 'ngx-bootstrap/pagination';
import { NgxMaskModule } from 'ngx-mask';
import { ComponentsModule } from '../components/components.module';
import { DemandaRoutingModule } from './demanda-routing.module';
import { ExecutadasComponent } from './executadas/executadas.component';
import { PrevistasComponent } from './previstas/previstas.component';

@NgModule({
  declarations: [ExecutadasComponent, PrevistasComponent],
  imports: [
    CommonModule,
    DemandaRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    MatCardModule,
    MatIconModule,
    ComponentsModule,
    NgxMaskModule,
    PaginationModule,
    MatProgressSpinnerModule,
    MatButtonToggleModule,
  ],
})
export class DemandaModule {}
