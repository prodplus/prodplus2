package br.com.prodplus.models.forms;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import br.com.prodplus.models.LoteProduto;
import br.com.prodplus.services.LoteProdutoService;
import br.com.prodplus.services.ProdutoService;
import br.com.prodplus.utils.GeradorRastreio;
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
@NoArgsConstructor
@AllArgsConstructor
public class LoteProdutoForm implements Serializable {

	private static final long serialVersionUID = 7378683833844448034L;

	private Integer id;
	@NotNull(message = "o produto é obrigatório!")
	private Integer produto;
	@NotNull(message = "a data de produção é obrigatória!")
	private LocalDateTime producao;
	@NotNull(message = "o custo total é obrigatório!")
	@PositiveOrZero(message = "o custo total é inválido!")
	private BigDecimal custoTotal;
	@NotNull(message = "a quantidade inicial é obrigatória!")
	@PositiveOrZero(message = "quantidade inicial inválida!")
	private Double quantInicial;
	@NotNull(message = "a quantidade atual é obrigatória!")
	@PositiveOrZero(message = "quantidade atual inválida!")
	private Double quantAtual;
	@NotNull(message = "campo obrigatório!")
	private boolean ativo = true;

	public LoteProdutoForm(LoteProduto lote) {
		this.setId(lote.getId());
		this.setProduto(lote.getProduto().getId());
		this.setProducao(lote.getProducao());
		this.setCustoTotal(lote.getCustoTotal());
		this.setQuantInicial(lote.getQuantInicial());
		this.setQuantAtual(lote.getQuantAtual());
		this.setAtivo(lote.isAtivo());
	}

	public LoteProduto converter(ProdutoService produtoService, LoteProdutoService loteService) {
		if (this.getId() != null) {
			LoteProduto lote = loteService.buscar(this.getId());
			lote.setAtivo(this.isAtivo());
			lote.setCustoTotal(this.getCustoTotal());
			lote.setProducao(this.getProducao());
			lote.setProduto(produtoService.buscar(this.getProduto()));
			lote.setQuantAtual(this.getQuantAtual());
			lote.setQuantInicial(this.getQuantInicial());
			lote.setRastreio(GeradorRastreio.loteProduto(lote));
			return lote;
		} else {
			return new LoteProduto(null, null, produtoService.buscar(this.getProduto()),
					this.getProducao(), this.getCustoTotal(), this.getQuantInicial(),
					this.getQuantAtual(), new HashSet<>(), new HashSet<>(), this.isAtivo());
		}
	}

}
