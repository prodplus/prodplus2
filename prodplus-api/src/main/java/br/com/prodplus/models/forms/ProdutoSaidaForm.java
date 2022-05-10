package br.com.prodplus.models.forms;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import br.com.prodplus.models.ProdutoSaida;
import br.com.prodplus.services.ProdutoService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoSaidaForm {

	@NotNull(message = "o produto é obrigatório!")
	private Integer produto;
	@NotNull(message = "o valor venda é obrigatório!")
	@Min(value = 0, message = "valor de venda inválido!")
	private BigDecimal valorVenda;
	@NotNull(message = "o prazo de entrega é obrigatório!")
	@Min(value = 0, message = "prazo de entrega inválido!")
	private Integer prazoEntrega;
	@Min(value = 0, message = "tempo padrão inválido!")
	private Integer tempoPadrao;

	public ProdutoSaidaForm(ProdutoSaida saida) {
		this.setProduto(saida.getProduto().getId());
		this.setValorVenda(saida.getValorVenda());
		this.setPrazoEntrega(saida.getPrazoEntrega());
		this.setTempoPadrao(saida.getTempoPadrao());
	}

	public ProdutoSaida converter(ProdutoService service) {
		return new ProdutoSaida(this.getProduto(), service.buscar(this.getProduto()),
				this.getValorVenda(), this.getPrazoEntrega(), this.getTempoPadrao());
	}

}
