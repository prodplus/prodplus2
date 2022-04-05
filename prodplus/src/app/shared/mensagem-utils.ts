export interface Mensagem {
  tipo: string;
  titulo: string;
  mensagem: string;
}

export interface RespModal {
  confirmou: boolean;
  tipo: string;
}

export function mensagemPadrao(
  tipo: string,
  titulo: string,
  mensagem: string,
  err?: any
): Mensagem {
  let iErr = '';
  if (typeof err != 'undefined') {
    console.log(err);
    iErr = err.error.message;
  }

  return {
    tipo: 'modal-title text-' + tipo,
    titulo: titulo,
    mensagem: mensagem === '' ? iErr : mensagem === 'sem' ? '' : mensagem,
  };
}

export function trataMensagem(tipo: string): string {
  return tipo === 'd'
    ? 'desativar'
    : tipo === 'a'
    ? 'ativar'
    : tipo === 'e'
    ? 'excluir DEFINITIVAMENTE'
    : tipo === 'c'
    ? 'cancelar'
    : '';
}
