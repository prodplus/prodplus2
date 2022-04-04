package br.com.prodplus.models.auxiliares;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

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
@Embeddable
public class DemandaId implements Serializable, Comparable<DemandaId> {

	private static final long serialVersionUID = 3022282564380011543L;
	@Column(nullable = false)
	private Integer produto;
	@Column(nullable = false)
	private Integer ano;
	@Column(nullable = false)
	private Integer semana;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ano == null) ? 0 : ano.hashCode());
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
		result = prime * result + ((semana == null) ? 0 : semana.hashCode());
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
		DemandaId other = (DemandaId) obj;
		if (ano == null) {
			if (other.ano != null)
				return false;
		} else if (!ano.equals(other.ano))
			return false;
		if (produto == null) {
			if (other.produto != null)
				return false;
		} else if (!produto.equals(other.produto))
			return false;
		if (semana == null) {
			if (other.semana != null)
				return false;
		} else if (!semana.equals(other.semana))
			return false;
		return true;
	}

	@Override
	public int compareTo(DemandaId o) {
		if (this.produto != null && o.getProduto() != null)
			if (this.produto.compareTo(o.getProduto()) == 0)
				if (this.ano != null && o.getAno() != null)
					if (this.ano.compareTo(o.getAno()) == 0)
						if (this.semana != null && o.getSemana() != null)
							return this.semana.compareTo(o.getSemana());
						else
							return 0;
					else
						return this.ano.compareTo(o.getAno());
				else
					return 0;
			else
				return this.produto.compareTo(o.getProduto());
		else
			return 0;
	}

}
