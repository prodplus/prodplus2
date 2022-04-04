package br.com.prodplus.models.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@JsonFormat(shape = Shape.STRING)
public enum Condicao {

	FRACA(-0.07), REGULAR(-0.03), MEDIA(0.00), BOA(0.02), EXCELENTE(0.03), IDEAL(0.06);

	private double valor;

	private Condicao(double valor) {
		this.valor = valor;
	}

	public double getValor() {
		return this.valor;
	}

}
