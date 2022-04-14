package br.com.prodplus.models.enums;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@JsonFormat(shape = Shape.STRING)
public enum TipoMetrico {

	UN(1.0, "un"), DE(10.0, "dezena"), CE(100.0, "centena"), MI(1000.0, "milhar"), CM(0.01, "cm"),
	CM2(0.0001, "cm²"), CM3(0.000001, "cm³"), M(1.0, "m"), M2(1.0, "m²"), M3(1.0, "m³"),
	ML(0.001, "ml"), L(1.0, "l"), G(1, "g"), KG(1000.0, "kg");

	private double valor;
	private String descricao;

	private TipoMetrico(double valor, String descricao) {
		this.valor = valor;
		this.descricao = descricao;
	}

	public double getValor() {
		return valor;
	}

	public String getDescricao() {
		return descricao;
	}

	public static class TipoMetricoDeserializer extends StdDeserializer<TipoMetrico> {

		private static final long serialVersionUID = 8561038611502959324L;

		protected TipoMetricoDeserializer() {
			super(TipoMetrico.class);
		}

		@Override
		public TipoMetrico deserialize(JsonParser p, DeserializationContext ctxt)
				throws IOException, JacksonException {
			final JsonNode node = p.readValueAsTree();
			String descricao = node.get("descricao").asText();

			for (TipoMetrico t : TipoMetrico.values())
				if (t.descricao.equals(descricao))
					return t;

			return null;
		}

	}

}
