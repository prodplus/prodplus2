import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { faToggleOff, faToggleOn } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-btn-ativar',
  templateUrl: './btn-ativar.component.html',
  styleUrls: ['./btn-ativar.component.css'],
})
export class BtnAtivarComponent implements OnInit {
  @Input() ativos: boolean = true;
  @Input() id: number = 0;
  iToggleOn = faToggleOn;
  iToggleOff = faToggleOff;
  @Output() click = new EventEmitter<{ id: number; tipo: string }>();

  constructor() {}

  ngOnInit(): void {}

  onClick() {
    this.click.emit({ id: this.id, tipo: this.ativos ? 'd' : 'a' });
  }
}
