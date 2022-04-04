package br.com.prodplus.models.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@JsonFormat(shape = Shape.STRING)
public enum Consistencia {

	FRACA(-0.04), REGULAR(-0.02), MEDIA(0.00), BOA(0.01), EXCELENTE(0.03), PERFEICAO(0.04);

	private double valor;

	private Consistencia(double valor) {
		this.valor = valor;
	}

	public double getValor() {
		return this.valor;
	}

}
