package net.servehttp.bytecom.service.extra;

import java.io.Serializable;

public class LogService implements Serializable {

    private static final long serialVersionUID = 3939090465717197037L;

    public String getMensagemLog(Exception e) {
        StringBuilder sb = new StringBuilder("Error: ");
        sb.append(e.getMessage());
        sb.append("\nCausa: ");
        sb.append(e.getCause() != null ? e.getCause().getMessage() : null);
        sb.append("\n##############################");

        for (StackTraceElement s : e.getStackTrace()) {
            if (s.getClassName().contains("bytecom")) {
                sb.append("\nFileName: ");
                sb.append(s.getFileName());
                sb.append("\nMethodName: ");
                sb.append(s.getMethodName());
                sb.append("\nClassName: ");
                sb.append(s.getClassName());
                sb.append("\nLineNumber: ");
                sb.append(s.getLineNumber());
                sb.append("\n##############################");
            }
        }
        return sb.toString();
    }

}
