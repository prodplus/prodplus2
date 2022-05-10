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
import { CadFeriadosComponent } from './feriados/cad-feriados/cad-feriados.component';
import { ListaFeriadosComponent } from './feriados/lista-feriados/lista-feriados.component';
import { CadMateriaisComponent } from './materiais/cad-materiais/cad-materiais.component';
import { ListaMateriaisComponent } from './materiais/lista-materiais/lista-materiais.component';
import { CadProcessosComponent } from './processos/cad-processos/cad-processos.component';
import { ListaProcessosComponent } from './processos/lista-processos/lista-processos.component';
import { CadProdutosComponent } from './produtos/cad-produtos/cad-produtos.component';
import { ListaProdutosComponent } from './produtos/lista-produtos/lista-produtos.component';
import { CadSaidasComponent } from './saidas/cad-saidas/cad-saidas.component';
import { ListaSaidasComponent } from './saidas/lista-saidas/lista-saidas.component';

@NgModule({
  declarations: [
    CadCustosComponent,
    ListaCustosComponent,
    ListaFeriadosComponent,
    CadFeriadosComponent,
    ListaMateriaisComponent,
    CadMateriaisComponent,
    CadProdutosComponent,
    ListaProdutosComponent,
    ListaProcessosComponent,
    CadProcessosComponent,
    ListaSaidasComponent,
    CadSaidasComponent,
  ],
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
