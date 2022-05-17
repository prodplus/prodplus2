import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ExecutadasComponent } from './executadas/executadas.component';
import { PrevistasComponent } from './previstas/previstas.component';

const routes: Routes = [
  { path: 'executadas', component: ExecutadasComponent },
  { path: 'previsoes', component: PrevistasComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class DemandaRoutingModule {}
