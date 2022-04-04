package br.com.prodplus.models;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

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
@MappedSuperclass
public class Estoque implements Serializable, Comparable<Estoque> {

	private static final long serialVersionUID = 4339377292041036504L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable = false)
	private LocalDateTime data;
	@Column(nullable = false)
	private Double quantidade;
	@Column(nullable = false)
	private Double entrada;

	@Override
	public int compareTo(Estoque o) {
		if (this.data != null && o.getData() != null)
			return this.data.compareTo(o.getData());
		return 0;
	}

}
