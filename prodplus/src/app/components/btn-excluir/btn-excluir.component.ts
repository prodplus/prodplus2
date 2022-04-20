import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { faTrash } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-btn-excluir',
  templateUrl: './btn-excluir.component.html',
  styleUrls: ['./btn-excluir.component.css'],
})
export class BtnExcluirComponent implements OnInit {
  @Input() id: number = 0;
  @Input() disabled: boolean = false;
  @Output() click = new EventEmitter<{ id: number; tipo: string }>();
  iTrash = faTrash;

  constructor() {}

  ngOnInit(): void {}

  onClick() {
    this.click.emit({ id: this.id, tipo: 'e' });
  }
}
