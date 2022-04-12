package br.com.prodplus.models.auxiliares;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

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
public class AmostraId implements Serializable, Comparable<AmostraId> {

	private static final long serialVersionUID = -6505657622938784878L;
	@Column(nullable = false)
	@NotNull(message = "campo obrigatório!")
	private Integer produto;
	@Column(nullable = false)
	@NotNull(message = "campo obrigatório!")
	private Integer processo;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((processo == null) ? 0 : processo.hashCode());
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
		AmostraId other = (AmostraId) obj;
		if (processo == null) {
			if (other.processo != null)
				return false;
		} else if (!processo.equals(other.processo))
			return false;
		if (produto == null) {
			if (other.produto != null)
				return false;
		} else if (!produto.equals(other.produto))
			return false;
		return true;
	}

	@Override
	public int compareTo(AmostraId o) {
		if (this.processo != null && o.getProcesso() != null)
			if (this.processo.compareTo(o.getProcesso()) == 0)
				if (this.produto != null && o.getProduto() != null)
					return this.produto.compareTo(o.getProduto());
				else
					return 0;
			else
				return this.processo.compareTo(o.getProcesso());
		else
			return 0;

	}

}
