import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';
import { PaginationModule } from 'ngx-bootstrap/pagination';
import { DialogComponent } from './dialog/dialog.component';
import { ErrorDialogComponent } from './error-dialog/error-dialog.component';
import { PaginadorComponent } from './paginador/paginador.component';
import { TabelaComponent } from './tabela/tabela.component';

@NgModule({
  declarations: [
    DialogComponent,
    PaginadorComponent,
    TabelaComponent,
    ErrorDialogComponent,
  ],
  imports: [CommonModule, MatDialogModule, MatButtonModule, PaginationModule],
  exports: [DialogComponent, TabelaComponent, ErrorDialogComponent],
})
export class ComponentsModule {}
