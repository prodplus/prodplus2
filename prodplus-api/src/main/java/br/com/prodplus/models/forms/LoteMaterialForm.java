package br.com.prodplus.models.forms;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import br.com.prodplus.models.LoteMaterial;
import br.com.prodplus.services.LoteMaterialService;
import br.com.prodplus.services.MaterialService;
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
public class LoteMaterialForm implements Serializable {

	private static final long serialVersionUID = 2540615422583570687L;

	private Integer id;
	@NotNull(message = "o material é obrigatório!")
	private Integer material;
	private LocalDateTime entrada;
	@NotNull(message = "a data do pedido é obrigatória!")
	private LocalDateTime pedido;
	@NotNull(message = "o custo total é obrigatório!")
	@PositiveOrZero(message = "custo total inválido!")
	private BigDecimal custoTotal;
	@NotNull(message = "o custo unitário é obrigatório!")
	@PositiveOrZero(message = "custo unitário inválido!")
	private BigDecimal custoUnitario;
	@NotNull(message = "a quantidade inicial é obrigatória!")
	@PositiveOrZero(message = "quantidade inicial inválida!")
	private Double quantInicial;
	@NotNull(message = "a quantidade atual é obrigatória!")
	@PositiveOrZero(message = "quantidade atual inválida!")
	private Double quantAtual;
	@NotNull(message = "campo obrigatório!")
	private boolean ativo = true;

	public LoteMaterialForm(LoteMaterial lote) {
		this.setId(lote.getId());
		this.setMaterial(lote.getMaterial().getId());
		this.setEntrada(lote.getEntrada());
		this.setPedido(lote.getPedido());
		this.setCustoTotal(lote.getCustoTotal());
		this.setCustoUnitario(lote.getCustoUnitario());
		this.setQuantInicial(lote.getQuantInicial());
		this.setQuantAtual(lote.getQuantAtual());
		this.setAtivo(lote.isAtivo());
	}

	public LoteMaterial converter(MaterialService materialService,
			LoteMaterialService loteService) {
		if (this.getId() != null) {
			LoteMaterial lote = loteService.buscar(this.getId());
			lote.setMaterial(materialService.buscar(this.getMaterial()));
			lote.setEntrada(this.getEntrada());
			lote.setPedido(this.getPedido());
			lote.setCustoTotal(this.getCustoTotal());
			lote.setCustoUnitario(this.getCustoUnitario());
			lote.setQuantInicial(this.getQuantInicial());
			lote.setQuantAtual(this.getQuantAtual());
			lote.setAtivo(this.isAtivo());
			return lote;
		} else {
			return new LoteMaterial(null, null, materialService.buscar(this.getMaterial()),
					this.getEntrada(), this.getPedido(), this.getCustoTotal(),
					this.getCustoUnitario(), this.getQuantInicial(), this.getQuantAtual(),
					this.isAtivo());
		}
	}

}
