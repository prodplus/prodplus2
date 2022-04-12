package br.com.prodplus.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import br.com.prodplus.models.enums.TipoEntrada;
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
	@NotNull(message = "o material é obrigatório!")
	private Material material;

	@Builder
	public EstoqueMaterial(Integer id, LocalDateTime data, Double quantidade, Double entrada,
			TipoEntrada tipo, Material material) {
		super(id, data, quantidade, entrada, tipo);
		this.setMaterial(material);
	}

}
