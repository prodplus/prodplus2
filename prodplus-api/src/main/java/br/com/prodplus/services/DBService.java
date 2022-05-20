package br.com.prodplus.services;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.prodplus.models.Amostra;
import br.com.prodplus.models.Configuracao;
import br.com.prodplus.models.Custo;
import br.com.prodplus.models.DemandaExec;
import br.com.prodplus.models.Feriado;
import br.com.prodplus.models.Material;
import br.com.prodplus.models.Processo;
import br.com.prodplus.models.Produto;
import br.com.prodplus.models.auxiliares.AmostraId;
import br.com.prodplus.models.auxiliares.Quantidade;
import br.com.prodplus.models.auxiliares.Turno;
import br.com.prodplus.models.enums.Condicao;
import br.com.prodplus.models.enums.Consistencia;
import br.com.prodplus.models.enums.Esforco;
import br.com.prodplus.models.enums.Habilidade;
import br.com.prodplus.models.enums.Periodo;
import br.com.prodplus.models.enums.PeriodoSetup;
import br.com.prodplus.models.enums.TipoMetrico;
import br.com.prodplus.models.enums.TipoProcesso;
import br.com.prodplus.models.forms.LoteMaterialForm;
import br.com.prodplus.models.forms.LoteProdutoForm;
import br.com.prodplus.models.forms.ProdutoSaidaForm;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@Service
public class DBService {

	@Autowired
	private CustoService custoService;
	@Autowired
	private ConfiguracaoService configService;
	@Autowired
	private MaterialService materialService;
	@Autowired
	private ProdutoService produtoService;
	@Autowired
	private ProcessoService processoService;
	@Autowired
	private AmostraService amostraService;
	@Autowired
	private DemandaExecService demandaExecService;
	@Autowired
	private LoteMaterialService loteMaterialService;
	@Autowired
	private LoteProdutoService loteProdutoService;
	@Autowired
	private FeriadoService feriadoService;
	@Autowired
	private ProdutoSaidaService produtoSaidaService;
	private Random random = new Random();

	public void initDatabase() {
		// Configuração
		Turno turno1 = new Turno(LocalTime.of(8, 0), LocalTime.of(11, 30));
		Turno turno2 = new Turno(LocalTime.of(13, 30), LocalTime.of(18, 0));
		this.configService.salvar(new Configuracao(1, new HashSet<>(Arrays.asList(turno1, turno2)),
				new HashSet<>(Arrays.asList(turno1)), new HashSet<>(), true, 0.0));

		// Feriados
		this.feriadoService
				.salvar(new Feriado(null, "CORPUS CHRISTI", LocalDate.of(2022, 6, 7), false));
		this.feriadoService.salvar(
				new Feriado(null, "INDEPENDÊNCIA DO BRASIL", LocalDate.of(2022, 9, 7), true));
		this.feriadoService.salvar(
				new Feriado(null, "NOSSA SENHORA APARECIDA", LocalDate.of(2022, 10, 12), true));
		this.feriadoService.salvar(new Feriado(null, "FINADOS", LocalDate.of(2022, 11, 2), true));
		this.feriadoService.salvar(
				new Feriado(null, "PROCLAMAÇÃO DA REPÚBLICA", LocalDate.of(2022, 11, 15), true));

		// Custos fixos
		this.custoService
				.salvar(new Custo(null, "LUZ", Periodo.MENSAL, BigDecimal.valueOf(1234.0), true));
		this.custoService
				.salvar(new Custo(null, "ÁGUA", Periodo.MENSAL, BigDecimal.valueOf(520.0), true));
		this.custoService.salvar(
				new Custo(null, "ALUGUEL", Periodo.MENSAL, BigDecimal.valueOf(8560.0), true));
		this.custoService.salvar(
				new Custo(null, "FOLHA", Periodo.MENSAL, BigDecimal.valueOf(12500.0), true));

		// Materiais
		Material pinus = this.materialService.salvar(new Material(null, "PINUS", TipoMetrico.M3,
				TipoMetrico.CM3, TipoMetrico.M3.getValor() / TipoMetrico.CM3.getValor(),
				BigDecimal.valueOf(254.0), BigDecimal.valueOf(200.0), BigDecimal.valueOf(280.0), 14,
				365));
		this.loteMaterialService.salvar(new LoteMaterialForm(null, pinus.getId(),
				LocalDateTime.of(LocalDate.of(2022, 1, 15), LocalTime.of(8, 0)),
				LocalDateTime.of(LocalDate.of(2022, 1, 1), LocalTime.of(8, 0)),
				BigDecimal.valueOf(1470.0), BigDecimal.valueOf(254.0), 5.0, 5.0, true));
		Material tinta_verniz = this.materialService.salvar(new Material(null, "TINTA VERNIZ",
				TipoMetrico.L, TipoMetrico.ML, TipoMetrico.L.getValor() / TipoMetrico.ML.getValor(),
				BigDecimal.valueOf(24.5), BigDecimal.valueOf(5.0), BigDecimal.valueOf(110.0), 5,
				90));
		this.loteMaterialService.salvar(new LoteMaterialForm(null, tinta_verniz.getId(),
				LocalDateTime.of(LocalDate.of(2022, 1, 6), LocalTime.of(8, 0)),
				LocalDateTime.of(LocalDate.of(2022, 1, 1), LocalTime.of(8, 0)),
				BigDecimal.valueOf(740.0), BigDecimal.valueOf(24.5), 3.0, 3.0, true));
		Material embuia = this.materialService.salvar(new Material(null, "EMBUIA", TipoMetrico.M3,
				TipoMetrico.CM3, TipoMetrico.M3.getValor() / TipoMetrico.CM3.getValor(),
				BigDecimal.valueOf(142.0), BigDecimal.valueOf(55.0), BigDecimal.valueOf(155.0), 10,
				365));
		this.loteMaterialService.salvar(new LoteMaterialForm(null, embuia.getId(),
				LocalDateTime.of(LocalDate.of(2022, 1, 11), LocalTime.of(8, 0)),
				LocalDateTime.of(LocalDate.of(2022, 1, 1), LocalTime.of(8, 0)),
				BigDecimal.valueOf(481.0), BigDecimal.valueOf(142.0), 3.0, 3.0, true));
		Material parafuso = this.materialService.salvar(new Material(null, "PARAFUSO",
				TipoMetrico.CE, TipoMetrico.UN,
				TipoMetrico.CE.getValor() / TipoMetrico.UN.getValor(), BigDecimal.valueOf(5.2),
				BigDecimal.valueOf(1.0), BigDecimal.valueOf(1.0), 5, 365));
		this.loteMaterialService.salvar(new LoteMaterialForm(null, parafuso.getId(),
				LocalDateTime.of(LocalDate.of(2022, 1, 6), LocalTime.of(8, 0)),
				LocalDateTime.of(LocalDate.of(2022, 1, 1), LocalTime.of(8, 0)),
				BigDecimal.valueOf(157.0), BigDecimal.valueOf(5.2), 30.0, 30.0, true));

		// Produtos
		Produto peCadeiraCortado = this.produtoService.salvar(new Produto(null,
				"PÉ CADEIRA CORTADO", TipoMetrico.UN, new HashSet<>(), new HashSet<>(), 365, true));
		Produto peCadeiraLixado = this.produtoService.salvar(new Produto(null, "PÉ CADEIRA LIXADO",
				TipoMetrico.UN, new HashSet<>(), new HashSet<>(), 365, true));
		Produto peCadeiraPintado = this.produtoService
				.salvar(new Produto(null, "PÉ CADEIRA PINTADO", TipoMetrico.UN, new HashSet<>(),
						new HashSet<>(), 3650, true));
		Produto baseCadeiraCortada = this.produtoService
				.salvar(new Produto(null, "BASE CADEIRA CORTADA", TipoMetrico.UN, new HashSet<>(),
						new HashSet<>(), 365, true));
		Produto baseCadeiraLixada = this.produtoService
				.salvar(new Produto(null, "BASE CADEIRA LIXADA", TipoMetrico.UN, new HashSet<>(),
						new HashSet<>(), 365, true));
		Produto baseCadeiraPintada = this.produtoService
				.salvar(new Produto(null, "BASE CADEIRA PINTADA", TipoMetrico.UN, new HashSet<>(),
						new HashSet<>(), 3650, true));
		Produto cadeira = this.produtoService.salvar(new Produto(null, "CADEIRA", TipoMetrico.UN,
				new HashSet<>(), new HashSet<>(), 3650, true));
		this.loteProdutoService.salvar(new LoteProdutoForm(null, cadeira.getId(),
				LocalDateTime.of(LocalDate.of(2022, 1, 15), LocalTime.of(15, 0)),
				BigDecimal.valueOf(348.0), 20.0, 20.0, true));
		this.produtoService.setMateriais(peCadeiraCortado.getId(),
				new HashSet<>(Arrays.asList(new Quantidade<>(pinus, 2205.0))));
		this.produtoService.setProdutos(peCadeiraLixado.getId(),
				new HashSet<>(Arrays.asList(new Quantidade<>(peCadeiraCortado, 1.0))));
		this.produtoService.setMateriais(peCadeiraPintado.getId(),
				new HashSet<>(Arrays.asList(new Quantidade<>(tinta_verniz, 80.0))));
		this.produtoService.setProdutos(peCadeiraPintado.getId(),
				new HashSet<>(Arrays.asList(new Quantidade<>(peCadeiraLixado, 1.0))));
		this.produtoService.setMateriais(baseCadeiraLixada.getId(),
				new HashSet<>(Arrays.asList(new Quantidade<>(pinus, 14200.0))));
		this.produtoService.setProdutos(baseCadeiraLixada.getId(),
				new HashSet<>(Arrays.asList(new Quantidade<>(baseCadeiraCortada, 1.0))));
		this.produtoService.setMateriais(baseCadeiraPintada.getId(),
				new HashSet<>(Arrays.asList(new Quantidade<>(tinta_verniz, 120.0))));
		this.produtoService.setProdutos(baseCadeiraPintada.getId(),
				new HashSet<>(Arrays.asList(new Quantidade<>(baseCadeiraLixada, 1.0))));
		this.produtoService.setProdutos(cadeira.getId(),
				new HashSet<>(Arrays.asList(new Quantidade<>(peCadeiraPintado, 4.0),
						new Quantidade<>(baseCadeiraPintada, 1.0))));
		this.produtoService.setMateriais(cadeira.getId(),
				new HashSet<>(Arrays.asList(new Quantidade<>(parafuso, 4.0))));

		Processo corte = this.processoService.salvar(new Processo(null, TipoProcesso.MAQUINA,
				"BANCADA DE CORTE", BigDecimal.valueOf(2.5), true));
		Processo lixa = this.processoService.salvar(new Processo(null, TipoProcesso.MANUAL,
				"BANCADA DE LIXA", BigDecimal.valueOf(2.5), true));
		Processo pintura = this.processoService.salvar(new Processo(null, TipoProcesso.MAQUINA,
				"BANCADA DE PINTURA", BigDecimal.valueOf(2.5), true));
		Processo montagem = this.processoService.salvar(new Processo(null, TipoProcesso.MAQUINA,
				"BANCADA DE MONTAGEM", BigDecimal.valueOf(2.5), true));

		this.produtoSaidaService
				.salvar(new ProdutoSaidaForm(cadeira.getId(), BigDecimal.valueOf(52.4), 15, null));

		this.amostraService
				.salvar(new Amostra(new AmostraId(peCadeiraCortado.getId(), corte.getId()), 900,
						Arrays.asList(38, 39, 37), PeriodoSetup.TURNO, Arrays.asList(90),
						new ArrayList<>(), 0, new ArrayList<>(), 0, new ArrayList<>(), 0, 4.0,
						Habilidade.MEDIA, Esforco.MEDIO, Condicao.MEDIA, Consistencia.MEDIA));
		this.amostraService
				.salvar(new Amostra(new AmostraId(baseCadeiraCortada.getId(), corte.getId()), 900,
						Arrays.asList(22, 23, 21), PeriodoSetup.TURNO, Arrays.asList(90),
						new ArrayList<>(), 0, new ArrayList<>(), 0, new ArrayList<>(), 0, 1.0,
						Habilidade.MEDIA, Esforco.MEDIO, Condicao.MEDIA, Consistencia.MEDIA));
		this.amostraService.salvar(new Amostra(new AmostraId(peCadeiraLixado.getId(), lixa.getId()),
				900, Arrays.asList(15, 16, 14), PeriodoSetup.DIA, Arrays.asList(90),
				new ArrayList<>(), 0, new ArrayList<>(), 0, new ArrayList<>(), 0, 1.0,
				Habilidade.MEDIA, Esforco.MEDIO, Condicao.MEDIA, Consistencia.MEDIA));
		this.amostraService
				.salvar(new Amostra(new AmostraId(baseCadeiraLixada.getId(), lixa.getId()), 900,
						Arrays.asList(22, 23, 21), PeriodoSetup.DIA, Arrays.asList(90),
						new ArrayList<>(), 0, new ArrayList<>(), 0, new ArrayList<>(), 0, 1.0,
						Habilidade.MEDIA, Esforco.MEDIO, Condicao.MEDIA, Consistencia.MEDIA));
		this.amostraService
				.salvar(new Amostra(new AmostraId(peCadeiraPintado.getId(), pintura.getId()), 900,
						Arrays.asList(15, 16, 14), PeriodoSetup.DIA, Arrays.asList(90),
						new ArrayList<>(), 0, new ArrayList<>(), 0, new ArrayList<>(), 0, 1.0,
						Habilidade.MEDIA, Esforco.MEDIO, Condicao.MEDIA, Consistencia.MEDIA));
		this.amostraService
				.salvar(new Amostra(new AmostraId(baseCadeiraPintada.getId(), pintura.getId()), 900,
						Arrays.asList(22, 23, 24), PeriodoSetup.DIA, Arrays.asList(90),
						new ArrayList<>(), 0, new ArrayList<>(), 0, new ArrayList<>(), 0, 1.0,
						Habilidade.MEDIA, Esforco.MEDIO, Condicao.MEDIA, Consistencia.MEDIA));
		this.amostraService.salvar(new Amostra(new AmostraId(cadeira.getId(), montagem.getId()),
				900, Arrays.asList(12, 11, 10), PeriodoSetup.DIA, Arrays.asList(90),
				new ArrayList<>(), 0, new ArrayList<>(), 0, new ArrayList<>(), 0, 1.0,
				Habilidade.MEDIA, Esforco.MEDIO, Condicao.MEDIA, Consistencia.MEDIA));

		List<DemandaExec> demandas2015 = this.demandaExecService.listar(cadeira.getId(), 2015);
		List<DemandaExec> demandas2016 = this.demandaExecService.listar(cadeira.getId(), 2016);
		List<DemandaExec> demandas2017 = this.demandaExecService.listar(cadeira.getId(), 2017);
		List<DemandaExec> demandas2018 = this.demandaExecService.listar(cadeira.getId(), 2018);
		List<DemandaExec> demandas2019 = this.demandaExecService.listar(cadeira.getId(), 2019);
		List<DemandaExec> demandas2020 = this.demandaExecService.listar(cadeira.getId(), 2020);
		List<DemandaExec> demandas2021 = this.demandaExecService.listar(cadeira.getId(), 2021);
		List<DemandaExec> demandas2022 = this.demandaExecService.listar(cadeira.getId(), 2022);

		for (DemandaExec d : demandas2015) {
			if (d.getId().getSemana() <= 45)
				d.setQuantidade((double) Math.round((this.random.nextDouble() * 20) + 30));
			else
				d.setQuantidade((double) Math.round((this.random.nextDouble() * 20) + 50));
			this.demandaExecService.salvar(d);
		}
		for (DemandaExec d : demandas2016) {
			if (d.getId().getSemana() <= 45)
				d.setQuantidade((double) Math.round((this.random.nextDouble() * 20) + 40));
			else
				d.setQuantidade((double) Math.round((this.random.nextDouble() * 20) + 60));
			this.demandaExecService.salvar(d);
		}
		for (DemandaExec d : demandas2017) {
			if (d.getId().getSemana() <= 45)
				d.setQuantidade((double) Math.round((this.random.nextDouble() * 20) + 50));
			else
				d.setQuantidade((double) Math.round((this.random.nextDouble() * 20) + 70));
			this.demandaExecService.salvar(d);
		}
		for (DemandaExec d : demandas2018) {
			if (d.getId().getSemana() <= 45)
				d.setQuantidade((double) Math.round((this.random.nextDouble() * 20) + 60));
			else
				d.setQuantidade((double) Math.round((this.random.nextDouble() * 20) + 80));
			this.demandaExecService.salvar(d);
		}
		for (DemandaExec d : demandas2019) {
			if (d.getId().getSemana() <= 45)
				d.setQuantidade((double) Math.round((this.random.nextDouble() * 20) + 70));
			else
				d.setQuantidade((double) Math.round((this.random.nextDouble() * 20) + 90));
			this.demandaExecService.salvar(d);
		}
		for (DemandaExec d : demandas2020) {
			if (d.getId().getSemana() <= 45)
				d.setQuantidade((double) Math.round((this.random.nextDouble() * 20) + 80));
			else
				d.setQuantidade((double) Math.round((this.random.nextDouble() * 20) + 100));
			this.demandaExecService.salvar(d);
		}
		for (DemandaExec d : demandas2021) {
			if (d.getId().getSemana() <= 45)
				d.setQuantidade((double) Math.round((this.random.nextDouble() * 20) + 90));
			else
				d.setQuantidade((double) Math.round((this.random.nextDouble() * 20) + 110));
			this.demandaExecService.salvar(d);
		}
		for (DemandaExec d : demandas2022) {
			if (d.getId().getSemana() < LocalDate.now()
					.get(WeekFields.of(DayOfWeek.SUNDAY, 5).weekOfYear())) {
				if (d.getId().getSemana() <= 45)
					d.setQuantidade((double) Math.round((this.random.nextDouble() * 20) + 100));
				else
					d.setQuantidade((double) Math.round((this.random.nextDouble() * 20) + 120));
			}
			this.demandaExecService.salvar(d);
		}
	}

}
