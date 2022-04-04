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
	private String descricao;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 10)
	private TipoMetrico tipoCompra;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 10)
	private TipoMetrico tipoProducao;
	@Column(nullable = false)
	private Double fator;
	@Column(nullable = false)
	private BigDecimal custoCompra;
	@Column(nullable = false)
	private BigDecimal custoPedido;
	@Column(nullable = false)
	private BigDecimal custoEstoque;
	@Column(nullable = false)
	private Integer leadTime;
	@Column(nullable = false)
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
