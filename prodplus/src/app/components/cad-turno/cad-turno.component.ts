import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { faMinus, faPlus } from '@fortawesome/free-solid-svg-icons';
import { Turno } from 'src/app/models/auxiliares/turno';

@Component({
  selector: 'app-cad-turno',
  templateUrl: './cad-turno.component.html',
  styleUrls: ['./cad-turno.component.css'],
})
export class CadTurnoComponent implements OnInit {
  @Input() turnos: Turno[] = [];
  @Input() titulo: string = '';
  @Input() idInputInicio: string = '';
  @Input() idInputFim: string = '';
  @Output() alteracao = new EventEmitter<Turno | number>();
  iPlus = faPlus;
  iMinus = faMinus;
  form!: FormGroup;

  constructor(private builder: FormBuilder) {}

  ngOnInit(): void {
    this.form = this.builder.group({
      inicio: ['', [Validators.required]],
      fim: ['', [Validators.required]],
    });
  }

  adicionaTurno() {
    const turno = new Turno(
      this.form.get('inicio')?.value,
      this.form.get('fim')?.value
    );
    this.turnos.push(turno);
    this.form.reset();
    this.alteracao.emit(turno);
  }

  removerTurno(index: number) {
    this.turnos.splice(index, 1);
    this.form.reset();
    this.alteracao.emit(index);
  }
}
