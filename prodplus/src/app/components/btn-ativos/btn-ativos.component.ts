import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { faToggleOff, faToggleOn } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-btn-ativos',
  templateUrl: './btn-ativos.component.html',
  styleUrls: ['./btn-ativos.component.css'],
})
export class BtnAtivosComponent implements OnInit {
  @Input() ativos: boolean = true;
  iToggleOn = faToggleOn;
  iToggleOff = faToggleOff;
  @Output() click = new EventEmitter();

  constructor() {}

  ngOnInit(): void {}

  onClick() {
    this.click.emit();
  }
}
