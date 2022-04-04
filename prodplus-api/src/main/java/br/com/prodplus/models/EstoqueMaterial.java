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
public class EstoqueMaterial extends Estoque {

	private static final long serialVersionUID = -3726573901519980368L;
	@ManyToOne
	@JoinColumn(nullable = false)
	private Material material;

	@Builder
	public EstoqueMaterial(Integer id, LocalDateTime data, Double quantidade, Double entrada,
			Material material) {
		super(id, data, quantidade, entrada);
		this.setMaterial(material);
	}

}
