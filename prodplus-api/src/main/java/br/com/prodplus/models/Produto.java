package br.com.prodplus.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.prodplus.models.auxiliares.Quantidade;
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
public class Produto implements Serializable {

	private static final long serialVersionUID = 5847514134730605795L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable = false)
	private String descricao;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 10)
	private TipoMetrico tipoMetrico;
	@ElementCollection
	@JsonIgnore
	private Set<Quantidade<Material>> materiais = new HashSet<>();
	@ElementCollection
	@JsonIgnore
	private Set<Quantidade<Produto>> produtos = new HashSet<>();
	@Column(nullable = false)
	private Integer validade;
	@Column(nullable = false)
	private boolean ativo = true;

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
		Produto other = (Produto) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		return true;
	}

}
