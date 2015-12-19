//package br.com.clairtonluz.bytecom.setup;
//
//import org.jboss.resteasy.annotations.interception.ServerInterceptor;
//import org.jboss.resteasy.core.ResourceMethodInvoker;
//import org.jboss.resteasy.core.ServerResponse;
//import org.jboss.resteasy.spi.Failure;
//import org.jboss.resteasy.spi.HttpRequest;
//import org.jboss.resteasy.spi.interception.MessageBodyWriterContext;
//import org.jboss.resteasy.spi.interception.MessageBodyWriterInterceptor;
//import org.jboss.resteasy.spi.interception.PreProcessInterceptor;
//
//import javax.ws.rs.WebApplicationException;
//import javax.ws.rs.ext.Provider;
//import java.io.IOException;
//
///**
// * Created by clairtonluz on 19/12/15.
// */
//@Provider
//@ServerInterceptor
//public class CorsInterceptor implements PreProcessInterceptor, MessageBodyWriterInterceptor {
//
//    /**
//     * The Origin header set by the browser at each request.
//     */
//    private static final String ORIGIN = "Origin";
//
//
//    /**
//     * The Access-Control-Allow-Origin header indicates which origin a resource it is specified for can be
//     * shared with. ABNF: Access-Control-Allow-Origin = "Access-Control-Allow-Origin" ":" source origin string | "*"
//     */
//    private static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
//
//    @Override
//    public void write(MessageBodyWriterContext context) throws IOException, WebApplicationException {
//        context.getHeaders().add(ACCESS_CONTROL_ALLOW_ORIGIN, "*");
//        System.out.println(context.getHeaders());
//        context.proceed();
//    }
//
//    @Override
//    public ServerResponse preProcess(HttpRequest request, ResourceMethodInvoker resourceMethodInvoker) throws Failure, WebApplicationException {
//        return null;
//    }
//}
