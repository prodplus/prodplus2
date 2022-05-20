import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CadLotesmComponent } from './lote-material/cad-lotesm/cad-lotesm.component';
import { ListaLotesmComponent } from './lote-material/lista-lotesm/lista-lotesm.component';

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
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ControleRoutingModule {}
