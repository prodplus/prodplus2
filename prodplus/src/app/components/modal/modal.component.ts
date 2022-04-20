import {
  Component,
  ElementRef,
  EventEmitter,
  OnInit,
  Output,
  ViewChild,
} from '@angular/core';
import { faWindowClose } from '@fortawesome/free-solid-svg-icons';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import {
  Mensagem,
  mensagemPadrao,
  RespModal,
} from 'src/app/shared/mensagem-utils';

@Component({
  selector: 'app-modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.css'],
})
export class ModalComponent implements OnInit {
  mensagem!: Mensagem;
  confirmar: boolean = false;
  tipo: string = '';
  iClose = faWindowClose;
  @Output() sim = new EventEmitter<RespModal>();
  @ViewChild('modal')
  modal!: ElementRef;

  constructor(private modalService: NgbModal) {}

  ngOnInit(): void {}

  fechaModal() {
    this.modalService.dismissAll();
  }

  open(mensagem: Mensagem, tipo: string, confirmar: boolean) {
    this.mensagem = mensagem;
    this.tipo = tipo;
    this.confirmar = confirmar;
    this.modalService.open(this.modal);
  }

  openPadrao(err: any) {
    this.mensagem = mensagemPadrao('danger', 'Erro!!', '', err);
    this.confirmar = false;
    this.modalService.open(this.modal);
  }

  concordar() {
    this.sim.emit({ confirmou: true, tipo: this.tipo });
    this.fechaModal();
  }
}
