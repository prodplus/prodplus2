export class Material {
  id: number | null;
  descricao: string;
  tipoCompra: string;
  tipoProducao: string;
  fator: number;
  custoCompra: number;
  custoPedido: number;
  custoEstoque: number;
  leadTime: number;
  validade: number;

  constructor();
  constructor(
    descricao: string,
    tipoCompra: string,
    tipoProducao: string,
    fator: number,
    custoCompra: number,
    custoPedido: number,
    custoEstoque: number,
    leadTime: number,
    validade: number,
    id?: number
  );
  constructor(...args: any[]) {
    if (args.length == 0) {
      this.id = null;
      this.descricao = '';
      this.tipoCompra = 'UN';
      this.tipoProducao = 'UN';
      this.fator = 1;
      this.custoCompra = 0;
      this.custoPedido = 0;
      this.custoEstoque = 0;
      this.leadTime = 1;
      this.validade = 1;
    } else {
      this.descricao = args[0];
      this.tipoCompra = args[1];
      this.tipoProducao = args[2];
      this.fator = args[3];
      this.custoCompra = args[4];
      this.custoPedido = args[5];
      this.custoEstoque = args[6];
      this.leadTime = args[7];
      this.validade = args[8];
      if (typeof args[9] === 'undefined') this.id = null;
      else this.id = args[9];
    }
  }
}
