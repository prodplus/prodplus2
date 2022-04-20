import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { BtnAtivarComponent } from './btn-ativar/btn-ativar.component';
import { BtnAtivosComponent } from './btn-ativos/btn-ativos.component';
import { BtnCancelarComponent } from './btn-cancelar/btn-cancelar.component';
import { BtnEditarComponent } from './btn-editar/btn-editar.component';
import { BtnExcluirComponent } from './btn-excluir/btn-excluir.component';
import { BtnHomeComponent } from './btn-home/btn-home.component';
import { BtnNovoComponent } from './btn-novo/btn-novo.component';
import { CadTurnoComponent } from './cad-turno/cad-turno.component';
import { ControleAtivosComponent } from './controle-ativos/controle-ativos.component';
import { ControleComponent } from './controle/controle.component';
import { ModalComponent } from './modal/modal.component';
import { PaginaComponent } from './pagina/pagina.component';
import { PaginadorComponent } from './paginador/paginador.component';
import { SearchComboComponent } from './search-combo/search-combo.component';
import { SearchInputComponent } from './search-input/search-input.component';
import { SpinnerComponent } from './spinner/spinner.component';
import { TabelaComponent } from './tabela/tabela.component';
import { TituloComponent } from './titulo/titulo.component';

@NgModule({
  declarations: [
    BtnAtivarComponent,
    BtnAtivosComponent,
    BtnCancelarComponent,
    BtnEditarComponent,
    BtnExcluirComponent,
    BtnHomeComponent,
    BtnNovoComponent,
    CadTurnoComponent,
    ControleAtivosComponent,
    ControleComponent,
    ModalComponent,
    PaginaComponent,
    SpinnerComponent,
    PaginadorComponent,
    SearchComboComponent,
    SearchInputComponent,
    TabelaComponent,
    TituloComponent,
  ],
  imports: [
    CommonModule,
    FontAwesomeModule,
    RouterModule,
    FormsModule,
    ReactiveFormsModule,
    NgbModule,
  ],
  exports: [
    BtnAtivarComponent,
    BtnAtivosComponent,
    BtnCancelarComponent,
    BtnEditarComponent,
    BtnExcluirComponent,
    BtnHomeComponent,
    BtnNovoComponent,
    CadTurnoComponent,
    ControleAtivosComponent,
    ControleComponent,
    PaginaComponent,
    SearchComboComponent,
    SearchInputComponent,
    TabelaComponent,
    TituloComponent,
  ],
})
export class ComponentsModule {}
