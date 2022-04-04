package br.com.prodplus.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
public class LoteMaterial implements Serializable {

	private static final long serialVersionUID = 1802160444559348220L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable = false, unique = true)
	private String rastreio;
	@ManyToOne
	@JoinColumn(nullable = false)
	private Material material;
	private LocalDateTime entrada;
	@Column(nullable = false)
	private LocalDateTime pedido;
	@Column(nullable = false)
	private BigDecimal custoTotal;
	@Column(nullable = false)
	private BigDecimal custoUnitario;
	@Column(nullable = false)
	private Double quantInicial;
	@Column(nullable = false)
	private Double quantAtual;
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
		LoteMaterial other = (LoteMaterial) obj;
		if (rastreio == null) {
			if (other.rastreio != null)
				return false;
		} else if (!rastreio.equals(other.rastreio))
			return false;
		return true;
	}

}
