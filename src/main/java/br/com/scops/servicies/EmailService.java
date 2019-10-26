package br.com.scops.servicies;

import org.springframework.mail.SimpleMailMessage;

import br.com.scops.domain.Pedido;

public interface EmailService {
	void sendOrderConfirmationEmail(Pedido obj);

	void sendEmail(SimpleMailMessage msg);
}
