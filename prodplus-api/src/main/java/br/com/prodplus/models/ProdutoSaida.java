package br.com.prodplus.models;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

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
public class ProdutoSaida implements Serializable, Comparable<ProdutoSaida> {

	private static final long serialVersionUID = -9216837480187986607L;
	@Id
	@Column(name = "prod_id")
	private Integer id;
	@OneToOne
	@MapsId
	private Produto produto;
	private BigDecimal valorVenda;
	private Integer prazoEntrega;
	private Integer tempoPadrao;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
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
		ProdutoSaida other = (ProdutoSaida) obj;
		if (produto == null) {
			if (other.produto != null)
				return false;
		} else if (!produto.equals(other.produto))
			return false;
		return true;
	}

	@Override
	public int compareTo(ProdutoSaida o) {
		if (this.produto != null && o.getProduto() != null)
			return this.produto.getDescricao().compareTo(o.getProduto().getDescricao());
		return 0;
	}

}
