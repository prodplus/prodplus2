package br.com.prodplus.models;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;

import javax.persistence.EmbeddedId;
import javax.persistence.MappedSuperclass;

import br.com.prodplus.models.auxiliares.DemandaId;
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
public class Demanda implements Serializable, Comparable<Demanda> {

	private static final long serialVersionUID = 7047531584768978305L;
	@EmbeddedId
	private DemandaId id;
	private Double quantidade;

	public String getDescricao() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dataDemanda = LocalDate.now().withYear(this.id.getAno())
				.with(WeekFields.of(DayOfWeek.SUNDAY, 5).weekOfYear(), this.id.getSemana());
		return String.format("Semana %02d: %s - %s", this.id.getSemana(),
				dataDemanda.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))
						.format(formatter),
				dataDemanda.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY))
						.format(formatter));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Demanda other = (Demanda) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int compareTo(Demanda o) {
		if (this.id != null && o.getId() != null)
			return this.id.compareTo(o.getId());
		return 0;
	}

}
