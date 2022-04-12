package br.com.prodplus.services;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.prodplus.models.Amostra;
import br.com.prodplus.models.Configuracao;
import br.com.prodplus.models.Material;
import br.com.prodplus.models.Produto;
import br.com.prodplus.models.auxiliares.AmostraId;
import br.com.prodplus.models.auxiliares.Estrutura;
import br.com.prodplus.models.auxiliares.Quantidade;
import br.com.prodplus.models.auxiliares.Turno;
import br.com.prodplus.utils.ConfigUtils;
import br.com.prodplus.utils.SimulacoesUtils;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@Service
public class SimulacaoService {

	@Autowired
	private ConfiguracaoService configService;
	@Autowired
	private EstruturaService estruturaService;
	@Autowired
	private AmostraService amostraService;
	@Autowired
	private CustoService custoService;
	@Autowired
	private ProdutoService produtoService;

	public List<Estrutura> getCustos(Integer produto) {
		try {
			List<Estrutura> estrutura = this.getTempoPadrao(produto);
			Double custoMensal = this.custoService.getTotalMes().doubleValue();
			Long segundosSemana = ConfigUtils.segundosSemana(this.configService.buscar());
			segundosSemana *= 4;
			Double custoSegundo = custoMensal / segundosSemana;
			for (Estrutura est : estrutura) {
				Double custo = custoSegundo * est.getTempoPadrao();
				custo /= est.getBatelada();
				Set<Quantidade<Material>> materiais = this.produtoService
						.getMateriais(est.getProduto());
				Set<Quantidade<Produto>> produtos = this.produtoService
						.getProdutos(est.getProduto());
				if (materiais != null && !materiais.isEmpty()) {
					for (Quantidade<Material> qm : materiais) {
						Double custoM = qm.getTipo().getCustoCompra().doubleValue()
								/ qm.getTipo().getFator();
						custoM *= qm.getQuantidade();
						custo += custoM;
					}
				}
				if (produtos != null && !produtos.isEmpty()) {
					for (Quantidade<Produto> qp : produtos) {
						Double custoP = qp.getQuantidade() * Estrutura
								.buscarPorProduto(qp.getTipo().getId(), estrutura).getCanban();
						custo += custoP;
					}
				}
				est.setCanban(custo);
			}
			return estrutura;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public List<Estrutura> getTempoPadrao(Integer produto) {
		try {
			List<Estrutura> estruturas = this.estruturaService.getEstrutura(produto);
			List<Amostra> amostras = new ArrayList<>();
			for (Estrutura est : estruturas)
				amostras.add(this.amostraService
						.buscar(new AmostraId(est.getProduto(), est.getProcesso())));
			long seconds = 0;
			Integer tempoTolerancia = 0;
			for (Amostra a : amostras)
				if (a.getTempoTolerancia() > tempoTolerancia)
					tempoTolerancia = a.getTempoTolerancia();
			Configuracao config = this.configService.buscar();

			int cont = 0; // sinal se é o primeiro turno ou não...
			for (Turno turno : config.getTurnosSemana()) {
				long temp = ChronoUnit.SECONDS.between(turno.getInicio(), turno.getFim());
				estruturas = calculaTurno(temp, estruturas, tempoTolerancia, cont == 0);
				seconds += temp;
				cont++;
			}
			seconds *= 5;
			estruturas.forEach(e -> e.setProduzido(e.getProduzido() * 5));

			cont = 0;
			for (Turno turno : config.getTurnosSabado()) {
				long temp = ChronoUnit.SECONDS.between(turno.getInicio(), turno.getFim());
				estruturas = calculaTurno(temp, estruturas, tempoTolerancia, cont == 0);
				seconds += temp;
				cont++;
			}

			cont = 0;
			for (Turno turno : config.getTurnosDomingo()) {
				long temp = ChronoUnit.SECONDS.between(turno.getInicio(), turno.getFim());
				estruturas = calculaTurno(temp, estruturas, tempoTolerancia, cont == 0);
				seconds += temp;
				cont++;
			}

			estruturas = getTemposPadroes(estruturas, seconds);
			return estruturas;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	private List<Estrutura> getTemposPadroes(List<Estrutura> estruturas, long seconds) {
		for (Estrutura est : estruturas)
			est.setTempoPadrao((int) Math.round(seconds / est.getProduzido()) * est.getBatelada());
		return estruturas;
	}

	private List<Estrutura> calculaTurno(long seconds, List<Estrutura> estruturas,
			Integer tolerancia, boolean primeiroTurno) {
		seconds -= tolerancia;
		long tempo = 0;
		long faltante = seconds;
		boolean t = true;
		while (t) {
			Collections.sort(estruturas);
			estruturas = SimulacoesUtils.trataSegundo(estruturas, faltante, primeiroTurno, tempo);
			tempo++;
			faltante--;
			t = !SimulacoesUtils.todosParados(estruturas, tempo < 1);
		}
		return estruturas;
	}

}
