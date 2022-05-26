import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EstoqueMaterialComponent } from './estoque-material/estoque-material.component';
import { CadLotesmComponent } from './lote-material/cad-lotesm/cad-lotesm.component';
import { ListaLotesmComponent } from './lote-material/lista-lotesm/lista-lotesm.component';
import { CadLotespComponent } from './lote-produto/cad-lotesp/cad-lotesp.component';
import { ListaLotespComponent } from './lote-produto/lista-lotesp/lista-lotesp.component';

const routes: Routes = [
  {
    path: 'lotes-material',
    children: [
      { path: '', component: ListaLotesmComponent },
      {
        path: 'novo',
        children: [
          { path: '', component: CadLotesmComponent },
          { path: ':id', component: CadLotesmComponent },
        ],
      },
    ],
  },
  {
    path: 'lotes-produto',
    children: [
      { path: '', component: ListaLotespComponent },
      {
        path: 'novo',
        children: [
          { path: '', component: CadLotespComponent },
          { path: ':id', component: CadLotespComponent },
        ],
      },
    ],
  },
  { path: 'estoque-material', component: EstoqueMaterialComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ControleRoutingModule {}
