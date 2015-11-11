package br.com.clairtonluz.bytecom.setup;

import br.com.clairtonluz.bytecom.util.web.AlertaUtil;

import javax.faces.FacesException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by clairtonluz on 07/11/15.
 */
public class CustomExceptionHandler extends ExceptionHandlerWrapper {
    private static final Logger log = Logger.getLogger(CustomExceptionHandler.class.getCanonicalName());
    private ExceptionHandler wrapped;

    CustomExceptionHandler(ExceptionHandler exception) {
        this.wrapped = exception;
    }

    @Override
    public ExceptionHandler getWrapped() {
        return wrapped;
    }

    @Override
    public void handle() throws FacesException {

        final Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator();
        while (i.hasNext()) {
            ExceptionQueuedEvent event = i.next();
            ExceptionQueuedEventContext context =
                    (ExceptionQueuedEventContext) event.getSource();

            // get the exception from context
            Throwable t = context.getException();
//            String erroCompleto = ExceptionUtils.getStackTrace(t);
//            erroCompleto = erroCompleto.replaceAll("\n", "<br>");
//            erroCompleto.split("<br>");

            StringWriter writer = new StringWriter();
            t.printStackTrace(new PrintWriter(writer));
            String[] erros = writer.toString().split("\n");

            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < erros.length; j++) {
                if (erros[j].contains("br.com.clairtonluz")) {
                    sb.append("<font color=red>")
                            .append(erros[j])
                            .append("</font><br>");
                } else if (erros[j].contains("Caused by:")) {
                    sb.append("<font color=orange>")
                            .append(erros[j])
                            .append("</font><br>");
                } else {
                    sb.append(erros[j])
                            .append("<br>");
                }
            }

            try {
                System.out.println(t.getCause());
//                InitialContext ctx = new InitialContext();
//                Session session = (Session) ctx.lookup("java:/mail/bytecom");
//                Message message = new MimeMessage(session);
//                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("clairton.c.l@gmail.com", false));
//                message.setSubject("[ERRO SICOBA]");
//                message.setSentDate(new Date());
//                message.setContent(sb.toString(), "text/html; charset=ISO-8859-1");
//                Transport.send(message);

                AlertaUtil.error(t.getMessage());
//            } catch (AddressException e) {
//                log.log(Level.SEVERE, null, e);
//            } catch (MessagingException e) {
//                log.log(Level.SEVERE, null, e);
//            } catch (NamingException e) {
//                log.log(Level.SEVERE, null, e);
            } finally {
                //remove it from queue
                i.remove();
            }
        }
        //parent hanle
        getWrapped().handle();
    }

}
