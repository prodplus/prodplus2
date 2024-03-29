import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'cadastro',
    loadChildren: () =>
      import('./cadastro/cadastro.module').then((m) => m.CadastroModule),
  },
  {
    path: 'demanda',
    loadChildren: () =>
      import('./demanda/demanda.module').then((m) => m.DemandaModule),
  },
  {
    path: 'controle',
    loadChildren: () =>
      import('./controle/controle.module').then((m) => m.ControleModule),
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
