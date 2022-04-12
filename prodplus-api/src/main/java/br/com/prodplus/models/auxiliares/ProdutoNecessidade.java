package br.com.prodplus.models.auxiliares;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import br.com.prodplus.models.Material;
import br.com.prodplus.models.Produto;
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
public class ProdutoNecessidade implements Serializable {

	private static final long serialVersionUID = -3363379226937635135L;

	private Produto produto;
	private Set<Quantidade<Material>> materiais = new HashSet<>();
	private Set<Quantidade<Produto>> produtos = new HashSet<>();

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
		ProdutoNecessidade other = (ProdutoNecessidade) obj;
		if (produto == null) {
			if (other.produto != null)
				return false;
		} else if (!produto.equals(other.produto))
			return false;
		return true;
	}

}
