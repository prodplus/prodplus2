import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CadCustosComponent } from './custos/cad-custos/cad-custos.component';
import { ListaCustosComponent } from './custos/lista-custos/lista-custos.component';

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
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CadastroRoutingModule {}
