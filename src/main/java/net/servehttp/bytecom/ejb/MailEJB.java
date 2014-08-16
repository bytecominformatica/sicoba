package net.servehttp.bytecom.ejb;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

@Stateless
public class MailEJB {

  @Resource(name = "java:/mail/bytecom")
  private Session session;
  private static final Logger LOGGER = Logger.getLogger(MailEJB.class.getSimpleName());
  private static final String FROM = "bytecominformatica@gmail.com";

  @Asynchronous
  public void send(String destinatario, String assunto, String mensagemTexto) {

    try {
      Message message = new MimeMessage(session);
      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
      message.setSubject(assunto);
      message.setText(mensagemTexto);
      Transport.send(message);
    } catch (MessagingException e) {
      LOGGER.log(Level.WARNING, "EMAIL NÃO ENVIADO", e);
    }
  }

  @Asynchronous
  public void sendAttachment(String destinatario, String assunto, String mensagemTexto,
      String file, String fileName) {

    try {
      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress(FROM));
      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
      message.setSubject(assunto);
      message.setText(mensagemTexto);


      MimeBodyPart messageBodyPart = new MimeBodyPart();

      Multipart multipart = new MimeMultipart();

      messageBodyPart = new MimeBodyPart();

      DataSource source = new FileDataSource(file);
      messageBodyPart.setDataHandler(new DataHandler(source));
      messageBodyPart.setFileName(fileName);
      multipart.addBodyPart(messageBodyPart);

      message.setContent(multipart);

      Transport.send(message);

    } catch (MessagingException e) {
      LOGGER.log(Level.WARNING, "EMAIL NÃO ENVIADO", e);
    }
  }
}
