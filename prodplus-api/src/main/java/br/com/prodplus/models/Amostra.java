package br.com.prodplus.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import br.com.prodplus.models.auxiliares.AmostraId;
import br.com.prodplus.models.enums.Condicao;
import br.com.prodplus.models.enums.Consistencia;
import br.com.prodplus.models.enums.Esforco;
import br.com.prodplus.models.enums.Habilidade;
import br.com.prodplus.models.enums.PeriodoSetup;
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
public class Amostra implements Serializable, Comparable<Amostra> {

	private static final long serialVersionUID = 6380777644172113331L;
	@EmbeddedId
	private AmostraId id;
	@ElementCollection
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Integer> temposMedidos = new ArrayList<>();
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private PeriodoSetup periodoSetup;
	@ElementCollection
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Integer> setupsCiclicos = new ArrayList<>();
	@Column(nullable = false)
	private Integer periodo = 0;
	@ElementCollection
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Integer> setupsAciclicos = new ArrayList<>();
	@Column(nullable = false)
	private Integer quantidadeAciclica = 0;
	@ElementCollection
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Integer> finalizacoesMedidas = new ArrayList<>();
	@Column(nullable = false)
	private Integer quantidadeFinalizacao = 0;
	@Column(nullable = false)
	private Double batelada;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private Habilidade habilidade;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private Esforco esforco;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private Condicao condicao;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private Consistencia consistencia;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Amostra other = (Amostra) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int compareTo(Amostra o) {
		if (this.id != null && o.getId() != null)
			return this.id.compareTo(o.getId());
		return 0;
	}

}
