import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-cad-tempo',
  templateUrl: './cad-tempo.component.html',
  styleUrls: ['./cad-tempo.component.css'],
})
export class CadTempoComponent implements OnInit {
  @Input() tempos: number[] = [];
  @Input() titulo: string = '';
  form!: FormGroup;

  constructor(private builder: FormBuilder) {}

  ngOnInit(): void {
    this.form = this.builder.group({
      tempo: [null, Validators.required],
    });
  }

  addTempo() {
    const tempo = +this.form.get('tempo')?.value;
    this.tempos.push(tempo);
    this.form.reset();
  }

  removerTempo(i: number) {
    this.tempos.splice(i, 1);
  }
}
