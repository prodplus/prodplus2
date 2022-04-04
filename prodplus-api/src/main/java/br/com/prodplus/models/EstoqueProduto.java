package br.com.prodplus.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
public class EstoqueProduto extends Estoque {

	private static final long serialVersionUID = -2164031057795902091L;
	@ManyToOne
	@JoinColumn(nullable = false)
	private Produto produto;

	@Builder
	public EstoqueProduto(Integer id, LocalDateTime data, Double quantidade, Double entrada,
			Produto produto) {
		super(id, data, quantidade, entrada);
		this.setProduto(produto);
	}

}
