import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';
import { PaginationModule } from 'ngx-bootstrap/pagination';
import { NgxMaskModule } from 'ngx-mask';
import { CadTempoComponent } from './cad-tempo/cad-tempo.component';
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
    CadTempoComponent,
  ],
  imports: [
    CommonModule,
    MatDialogModule,
    MatButtonModule,
    PaginationModule,
    FormsModule,
    ReactiveFormsModule,
    NgxMaskModule,
  ],
  exports: [
    DialogComponent,
    TabelaComponent,
    ErrorDialogComponent,
    CadTempoComponent,
  ],
})
export class ComponentsModule {}
