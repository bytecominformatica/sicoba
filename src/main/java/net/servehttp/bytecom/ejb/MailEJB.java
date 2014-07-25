package net.servehttp.bytecom.ejb;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Stateless
public class MailEJB {

	@Resource(name = "java:/mail/bytecom")
	private Session session;
	private static final Logger LOGGER = Logger.getLogger(MailEJB.class
			.getSimpleName());

	public void send(String destinatario, String assunto, String mensagemTexto) {

		try {
			Message message = new MimeMessage(session);
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(destinatario));
			message.setSubject(assunto);
			message.setText(mensagemTexto);
			Transport.send(message);
		} catch (MessagingException e) {
			LOGGER.log(Level.WARNING, "EMAIL NÃO ENVIADO", e);
		}
	}
}
