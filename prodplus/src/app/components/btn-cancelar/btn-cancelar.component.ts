import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { faWindowClose } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-btn-cancelar',
  templateUrl: './btn-cancelar.component.html',
  styleUrls: ['./btn-cancelar.component.css'],
})
export class BtnCancelarComponent implements OnInit {
  iClose = faWindowClose;

  constructor(private location: Location) {}

  ngOnInit(): void {}

  onClick() {
    this.location.back();
  }
}
