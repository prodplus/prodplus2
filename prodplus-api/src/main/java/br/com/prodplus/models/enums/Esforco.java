package br.com.prodplus.models.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@JsonFormat(shape = Shape.STRING)
public enum Esforco {

	FRACO(-0.12), REGULAR(-0.04), MEDIO(0.00), BOM(0.05), EXCELENTE(0.10), SUPER_ESFORCO(0.13);

	private double valor;

	private Esforco(double valor) {
		this.valor = valor;
	}

	public double getValor() {
		return this.valor;
	}

}
