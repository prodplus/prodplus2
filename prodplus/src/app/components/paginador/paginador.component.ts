import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { PageChangedEvent } from 'ngx-bootstrap/pagination';

@Component({
  selector: 'app-paginador',
  templateUrl: './paginador.component.html',
  styleUrls: ['./paginador.component.css'],
})
export class PaginadorComponent implements OnInit {
  @Input() tamanhoTotal: number = 0;
  @Input() tamanhoPagina: number = 0;
  @Input() pagina: number = 1;
  @Output() mudaPagina = new EventEmitter<number>();

  constructor() {}

  ngOnInit(): void {}

  alteraPagina(event: PageChangedEvent) {
    this.pagina = event.page;
    this.mudaPagina.emit(this.pagina);
  }
}
