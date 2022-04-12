package br.com.prodplus.models.auxiliares;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.prodplus.models.enums.PeriodoSetup;
import br.com.prodplus.models.enums.SituacaoProcesso;
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
public class Estrutura implements Serializable, Comparable<Estrutura> {

	private static final long serialVersionUID = -3731323935936042169L;

	private Integer produto;
	private Integer processo;
	private Set<MapaJson> abaixo = new HashSet<>();
	private Set<MapaJson> acima = new HashSet<>();
	private Integer batelada;
	private PeriodoSetup setup;
	private Integer tempoSetup;
	private Integer tempoSetupC;
	private int periodoSetup;
	private Integer tempoSetupA;
	private int quantSetupA;
	private Integer tempoSetupF;
	private int quantSetupF;
	private Integer tempoNormal;
	private Integer tempoPadrao;
	private int nivel;
	private Integer destino;
	private SituacaoProcesso situacao;
	private double canban;
	private int contador;
	private boolean contSetup;
	private double produzido;

	public static Integer getIndexPorProduto(Integer idProduto, List<Estrutura> estruturas) {
		for (int i = 0; i < estruturas.size(); i++)
			if (idProduto == estruturas.get(i).getProduto())
				return i;
		return null;
	}

	public static Estrutura buscarPorProduto(Integer produto, List<Estrutura> estruturas) {
		for (Estrutura est : estruturas)
			if (est.getProduto() == produto)
				return est;
		return null;
	}

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
		Estrutura other = (Estrutura) obj;
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
	public int compareTo(Estrutura o) {
		return this.nivel - o.getNivel();
	}

}
