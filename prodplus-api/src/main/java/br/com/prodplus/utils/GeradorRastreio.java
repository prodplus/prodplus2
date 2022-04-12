package br.com.prodplus.utils;

import br.com.prodplus.models.LoteMaterial;
import br.com.prodplus.models.LoteProduto;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
public class GeradorRastreio {

	/**
	 * Gera um rastreio para o lote de material, utilizando o id do material (4), o
	 * ano, mês, dia do pedido (8), a hora, o minuto e o segundo do pedido (6).
	 * 
	 * @param lote
	 * @return
	 */
	public static String loteMaterial(LoteMaterial lote) {
		return String.format("%04d%02d%02d%02d%02d%02d%04d", lote.getPedido().getYear(),
				lote.getPedido().getMonthValue(), lote.getPedido().getDayOfMonth(),
				lote.getPedido().getHour(), lote.getPedido().getMinute(),
				lote.getPedido().getSecond(), lote.getMaterial().getId());
	}

	/**
	 * Gera um rastreio para o lote de produto, utilizando o id do material (4), o
	 * ano, mês, dia do pedido (8), a hora, o minuto e o segundo do pedido (6).
	 * 
	 * @param lote
	 * @return
	 */
	public static String loteProduto(LoteProduto lote) {
		return String.format("%04d%02d%02d%02d%02d%02d%04d", lote.getProducao().getYear(),
				lote.getProducao().getMonthValue(), lote.getProducao().getDayOfMonth(),
				lote.getProducao().getHour(), lote.getProducao().getMinute(),
				lote.getProducao().getSecond(), lote.getProduto().getId());
	}

}
