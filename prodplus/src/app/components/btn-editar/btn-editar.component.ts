import { Component, Input, OnInit } from '@angular/core';
import { faEdit } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-btn-editar',
  templateUrl: './btn-editar.component.html',
  styleUrls: ['./btn-editar.component.css'],
})
export class BtnEditarComponent implements OnInit {
  @Input() rota: string[] = [];
  @Input() mensagem: string = '';
  @Input() disabled: boolean = false;
  iEdit = faEdit;

  constructor() {}

  ngOnInit(): void {}
}
