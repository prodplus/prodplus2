import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { PageChangedEvent } from 'ngx-bootstrap/pagination';
import { Page } from 'src/app/models/auxiliares/page';

@Component({
  selector: 'app-paginador',
  templateUrl: './paginador.component.html',
  styleUrls: ['./paginador.component.css'],
})
export class PaginadorComponent implements OnInit {
  @Input() page = new Page();
  @Output() mudaPagina = new EventEmitter<number>();

  constructor() {}

  ngOnInit(): void {}

  alteraPagina(event: PageChangedEvent) {
    this.page.pageable!.pageNumber = event.page;
    this.mudaPagina.emit(event.page);
  }
}
