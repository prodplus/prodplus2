package br.com.prodplus.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.prodplus.models.auxiliares.Quantidade;
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
public class LoteProduto implements Serializable, Comparable<LoteProduto> {

	private static final long serialVersionUID = 4143746704072557612L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable = false, unique = true)
	private String rastreio;
	@ManyToOne
	@JoinColumn(nullable = false)
	private Produto produto;
	@Column(nullable = false)
	private LocalDateTime producao;
	@Column(nullable = false)
	private BigDecimal custoTotal;
	@Column(nullable = false)
	private Double quantInicial;
	@Column(nullable = false)
	private Double quantAtual;
	@ElementCollection
	@JsonIgnore
	private Set<Quantidade<LoteMaterial>> materiais = new HashSet<>();
	@ElementCollection
	@JsonIgnore
	private Set<Quantidade<LoteProduto>> produtos = new HashSet<>();
	@Column(nullable = false)
	private boolean ativo = true;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rastreio == null) ? 0 : rastreio.hashCode());
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
		LoteProduto other = (LoteProduto) obj;
		if (rastreio == null) {
			if (other.rastreio != null)
				return false;
		} else if (!rastreio.equals(other.rastreio))
			return false;
		return true;
	}

	@Override
	public int compareTo(LoteProduto o) {
		if (this.produto != null && o.getProduto() != null)
			if (this.produto.getDescricao().compareTo(o.getProduto().getDescricao()) == 0)
				if (this.producao != null && o.getProducao() != null)
					return this.producao.compareTo(o.getProducao());
				else
					return 0;
			else
				return this.produto.getDescricao().compareTo(o.getProduto().getDescricao());
		else
			return 0;
	}

}
