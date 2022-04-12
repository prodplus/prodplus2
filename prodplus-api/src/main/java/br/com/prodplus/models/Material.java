package br.com.prodplus.models;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import br.com.prodplus.models.enums.TipoMetrico;
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
@Entity
public class Material implements Serializable {

	private static final long serialVersionUID = 9124232686938407016L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable = false, unique = true)
	@NotBlank(message = "a descrição é obrigatória!")
	private String descricao;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 10)
	@NotNull(message = "o tipo compra é obrigatório!")
	private TipoMetrico tipoCompra;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 10)
	@NotNull(message = "o tipo produção é obrigatório!")
	private TipoMetrico tipoProducao;
	@Column(nullable = false)
	@NotNull(message = "o fator é obrigatório!")
	private Double fator;
	@Column(nullable = false)
	@NotNull(message = "o custo compra é obrigatório!")
	@PositiveOrZero(message = "custo compra inválido!")
	private BigDecimal custoCompra;
	@Column(nullable = false)
	@NotNull(message = "o custo pedido é obrigatório!")
	@PositiveOrZero(message = "custo pedido inválido!")
	private BigDecimal custoPedido;
	@Column(nullable = false)
	@NotNull(message = "o custo pedido é obrigatório!")
	@PositiveOrZero(message = "custo pedido inválido!")
	private BigDecimal custoEstoque;
	@Column(nullable = false)
	@NotNull(message = "o lead time é obrigatório!")
	@Positive(message = "lead time inválido!")
	private Integer leadTime;
	@Column(nullable = false)
	@NotNull(message = "a validade é obrigatória!")
	@Positive(message = "validade inválida!")
	private Integer validade;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Material other = (Material) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		return true;
	}

}
