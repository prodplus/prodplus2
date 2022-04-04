package br.com.prodplus.models.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@JsonFormat(shape = Shape.STRING)
public enum Habilidade {

	FRACA(-0.16), REGULAR(-0.05), MEDIA(0.00), BOA(0.05), EXCELENTE(0.11), SUPER_HABIL(0.15);

	private double valor;

	private Habilidade(double valor) {
		this.valor = valor;
	}

	public double getValor() {
		return this.valor;
	}

}
