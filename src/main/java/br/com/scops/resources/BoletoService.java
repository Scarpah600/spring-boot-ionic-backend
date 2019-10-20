package br.com.scops.resources;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import br.com.scops.domain.PagamentoComBoleto;

@Service
public class BoletoService {
	public void preencherPagamentoComBoleto(PagamentoComBoleto pagto, Date instanteDoPedido) {
		//Poderia criar webservice para gerar o faturamento
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(instanteDoPedido);
		calendario.add(Calendar.DAY_OF_MONTH, 7);
		pagto.setDataVencimento(calendario.getTime());
	}
}
