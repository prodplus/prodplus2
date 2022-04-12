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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

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
	@Column(nullable = false)
	@PositiveOrZero(message = "tempo tolerância inválido!")
	@NotNull(message = "o tempo de tolerância é inválido!")
	private Integer tempoTolerancia;
	@ElementCollection
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Integer> temposMedidos = new ArrayList<>();
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	@NotNull(message = "o período de setup é obrigatório!")
	private PeriodoSetup periodoSetup;
	@ElementCollection
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Integer> setups = new ArrayList<>();
	@ElementCollection
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Integer> setupsCiclicos = new ArrayList<>();
	@Column(nullable = false)
	@NotNull(message = "o período é obrigatório!")
	@PositiveOrZero(message = "período inválido!")
	private Integer periodo = 0;
	@ElementCollection
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Integer> setupsAciclicos = new ArrayList<>();
	@Column(nullable = false)
	@NotNull(message = "a quantidade acíclica é obrigatória!")
	@PositiveOrZero(message = "quantidade acíclica inválida!")
	private Integer quantidadeAciclica = 0;
	@ElementCollection
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Integer> finalizacoesMedidas = new ArrayList<>();
	@Column(nullable = false)
	@NotNull(message = "a quantidade finalização é obrigatória!")
	@PositiveOrZero(message = "quantidade finalização inválida!")
	private Integer quantidadeFinalizacao = 0;
	@Column(nullable = false)
	@NotNull(message = "a batelada é obrigatória!")
	@Positive(message = "batelada inválida!")
	private Double batelada;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	@NotNull(message = "a habilidade é obrigatória!")
	private Habilidade habilidade;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	@NotNull(message = "o esforço é obrigatório!")
	private Esforco esforco;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	@NotNull(message = "a condição é obrigatória!")
	private Condicao condicao;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	@NotNull(message = "a consistência é obrigatória!")
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
