import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
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

const routes: Routes = [
  {
    path: 'custos',
    children: [
      { path: '', component: ListaCustosComponent },
      {
        path: 'novo',
        children: [
          { path: '', component: CadCustosComponent },
          { path: ':id', component: CadCustosComponent },
        ],
      },
    ],
  },
  {
    path: 'feriados',
    children: [
      { path: '', component: ListaFeriadosComponent },
      { path: 'novo', component: CadFeriadosComponent },
    ],
  },
  {
    path: 'materiais',
    children: [
      { path: '', component: ListaMateriaisComponent },
      {
        path: 'novo',
        children: [
          { path: '', component: CadMateriaisComponent },
          { path: ':id', component: CadMateriaisComponent },
        ],
      },
    ],
  },
  {
    path: 'produtos',
    children: [
      { path: '', component: ListaProdutosComponent },
      {
        path: 'novo',
        children: [
          { path: '', component: CadProdutosComponent },
          { path: ':id', component: CadProdutosComponent },
        ],
      },
    ],
  },
  {
    path: 'processos',
    children: [
      { path: '', component: ListaProcessosComponent },
      {
        path: 'novo',
        children: [
          { path: '', component: CadProcessosComponent },
          { path: ':id', component: CadProcessosComponent },
        ],
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CadastroRoutingModule {}
