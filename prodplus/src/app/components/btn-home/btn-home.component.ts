import { Component, OnInit } from '@angular/core';
import { faHome } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-btn-home',
  templateUrl: './btn-home.component.html',
  styleUrls: ['./btn-home.component.css'],
})
export class BtnHomeComponent implements OnInit {
  icon = faHome;

  constructor() {}

  ngOnInit(): void {}
}
