import { Component, Input, OnInit } from '@angular/core';
import { faFile } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-btn-novo',
  templateUrl: './btn-novo.component.html',
  styleUrls: ['./btn-novo.component.css'],
})
export class BtnNovoComponent implements OnInit {
  @Input() rota: string[] = [];
  @Input() mensagem: string = '';
  icon = faFile;

  constructor() {}

  ngOnInit(): void {}
}
