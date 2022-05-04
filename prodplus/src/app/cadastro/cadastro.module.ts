import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatTooltipModule } from '@angular/material/tooltip';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { CurrencyMaskModule } from 'ng2-currency-mask';
import { PaginationModule } from 'ngx-bootstrap/pagination';
import { NgxMaskModule } from 'ngx-mask';
import { ComponentsModule } from '../components/components.module';
import { CadastroRoutingModule } from './cadastro-routing.module';
import { CadCustosComponent } from './custos/cad-custos/cad-custos.component';
import { ListaCustosComponent } from './custos/lista-custos/lista-custos.component';

@NgModule({
  declarations: [CadCustosComponent, ListaCustosComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    FontAwesomeModule,
    CadastroRoutingModule,
    MatCardModule,
    MatSlideToggleModule,
    MatIconModule,
    ComponentsModule,
    MatTooltipModule,
    CurrencyMaskModule,
    NgxMaskModule,
    PaginationModule,
    MatProgressSpinnerModule,
  ],
})
export class CadastroModule {}
