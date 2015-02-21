[1mdiff --git a/pom.xml b/pom.xml[m
[1mindex 772d72f..21cd196 100644[m
[1m--- a/pom.xml[m
[1m+++ b/pom.xml[m
[36m@@ -158,13 +158,20 @@[m
 			<version>2.2.1.GA</version>[m
 			<scope>provided</scope>[m
 		</dependency>[m
[31m-		[m
[32m+[m
 		<dependency>[m
[31m-     		<groupId>org.jboss.resteasy</groupId>[m
[31m-     		<artifactId>resteasy-jaxb-provider</artifactId>[m
[31m-     		<version>2.2.2.GA</version>[m
[31m-     		<scope>provided</scope>[m
[31m-   		</dependency>[m
[32m+[m			[32m<groupId>org.jboss.resteasy</groupId>[m
[32m+[m			[32m<artifactId>resteasy-jaxb-provider</artifactId>[m
[32m+[m			[32m<version>2.2.2.GA</version>[m
[32m+[m			[32m<scope>provided</scope>[m
[32m+[m		[32m</dependency>[m
[32m+[m
[32m+[m		[32m<dependency>[m
[32m+[m			[32m<groupId>org.jboss.resteasy</groupId>[m
[32m+[m			[32m<artifactId>jaxrs-api</artifactId>[m
[32m+[m			[32m<version>2.3.0.GA</version>[m
[32m+[m			[32m<scope>provided</scope>[m
[32m+[m		[32m</dependency>[m
 [m
 		<dependency>[m
 			<groupId>org.jboss.resteasy</groupId>[m
[36m@@ -172,13 +179,13 @@[m
 			<version>2.2.1.GA</version>[m
 			<scope>provided</scope>[m
 		</dependency>[m
[31m-		[m
[32m+[m
 		<dependency>[m
 			<groupId>org.jboss.resteasy</groupId>[m
 			<artifactId>resteasy-cdi</artifactId>[m
 			<version>3.0.10.Final</version>[m
 		</dependency>[m
[31m-		[m
[32m+[m
 		<dependency>[m
 			<groupId>org.liquibase</groupId>[m
 			<artifactId>liquibase-core</artifactId>[m
[1mdiff --git a/src/main/java/net/servehttp/bytecom/extra/jpa/entity/ClienteGeoReferencia.java b/src/main/java/net/servehttp/bytecom/extra/jpa/entity/ClienteGeoReferencia.java[m
[1mindex 7272afa..9a21fc1 100644[m
[1m--- a/src/main/java/net/servehttp/bytecom/extra/jpa/entity/ClienteGeoReferencia.java[m
[1m+++ b/src/main/java/net/servehttp/bytecom/extra/jpa/entity/ClienteGeoReferencia.java[m
[36m@@ -9,6 +9,8 @@[m [mimport javax.persistence.FetchType;[m
 import javax.persistence.JoinColumn;[m
 import javax.persistence.OneToOne;[m
 import javax.persistence.Table;[m
[32m+[m[32mimport javax.xml.bind.annotation.XmlAccessType;[m
[32m+[m[32mimport javax.xml.bind.annotation.XmlAccessorType;[m
 import javax.xml.bind.annotation.XmlRootElement;[m
 [m
 import net.servehttp.bytecom.comercial.jpa.entity.Cliente;[m
[36m@@ -19,6 +21,7 @@[m [mimport net.servehttp.bytecom.comercial.jpa.entity.Cliente;[m
  *[m
  */[m
 @XmlRootElement[m
[32m+[m[32m@XmlAccessorType(XmlAccessType.FIELD)[m
 @Entity[m
 @Table(name = "cliente_georeferencia")[m
 public class ClienteGeoReferencia extends EntityGeneric implements Serializable {[m
[1mdiff --git a/src/main/java/net/servehttp/bytecom/extra/rest/ClientGeoRefServices.java b/src/main/java/net/servehttp/bytecom/extra/rest/ClientGeoRefServices.java[m
[1mindex 26103f5..fade313 100644[m
[1m--- a/src/main/java/net/servehttp/bytecom/extra/rest/ClientGeoRefServices.java[m
[1m+++ b/src/main/java/net/servehttp/bytecom/extra/rest/ClientGeoRefServices.java[m
[36m@@ -1,29 +1,33 @@[m
 package net.servehttp.bytecom.extra.rest;[m
 [m
[32m+[m[32mimport java.util.ArrayList;[m
 import java.util.List;[m
 [m
 import javax.inject.Inject;[m
 import javax.ws.rs.GET;[m
 import javax.ws.rs.Path;[m
 import javax.ws.rs.Produces;[m
[32m+[m[32mimport javax.ws.rs.core.MediaType;[m
 [m
 import net.servehttp.bytecom.extra.jpa.entity.ClienteGeoReferencia;[m
 import net.servehttp.bytecom.extra.service.ClienteGeorefereciaBussiness;[m
 [m
 @Path("maps")[m
[32m+[m[32m@Produces(MediaType.APPLICATION_JSON)[m
 public class ClientGeoRefServices {[m
 [m
   @Inject[m
[31m-  ClienteGeorefereciaBussiness clienteGeoBusssiness;[m
[32m+[m[32m  private ClienteGeorefereciaBussiness clienteGeoBusssiness;[m
   [m
   private List<ClienteGeoReferencia> listClientes;[m
   [m
   @GET[m
   @Path("clientesGeo")[m
[31m-  @Produces("application/json")[m
   public List<ClienteGeoReferencia> listarClientes(){[m
[32m+[m[32m    System.out.println("BBBBBBBB");[m
     listClientes = clienteGeoBusssiness.buscar();[m
[31m-    return listClientes;[m
[32m+[m[32m    System.out.println("aaaaaa" + listClientes.size());[m
[32m+[m[32m    return new ArrayList<ClienteGeoReferencia>();[m
   }[m
   [m
   @GET[m
[1mdiff --git a/src/main/webapp/WEB-INF/shiro.ini b/src/main/webapp/WEB-INF/shiro.ini[m
[1mindex 04fbb87..1ff1976 100644[m
[1m--- a/src/main/webapp/WEB-INF/shiro.ini[m
[1m+++ b/src/main/webapp/WEB-INF/shiro.ini[m
[36m@@ -27,6 +27,7 @@[m [mjdbcRealm.credentialsMatcher = $credentialsMatcher[m
 /css/** = anon[m
 /fonts/** = anon[m
 /js/** = anon[m
[32m+[m[32m/rest/** = anon[m
 /error/** = user[m
 ##/pages/cadastros/** = user, roles[ADMIN][m
 ##/pages/caixa/** = user, roles[ADMIN][m
[1mdiff --git a/src/main/webapp/WEB-INF/web.xml b/src/main/webapp/WEB-INF/web.xml[m
[1mindex c28daf1..2434121 100644[m
[1m--- a/src/main/webapp/WEB-INF/web.xml[m
[1m+++ b/src/main/webapp/WEB-INF/web.xml[m
[36m@@ -9,11 +9,6 @@[m
 	</context-param>[m
 	[m
 	<!-- RESTeasy -->[m
[31m-	<!-- <context-param>[m
[31m-		<param-name>resteasy.scan</param-name>[m
[31m-		<param-value>true</param-value>[m
[31m-	</context-param>-->[m
[31m-	[m
 	<listener>[m
         <listener-class>[m
             org.jboss.resteasy.plugins.server.servlet.ResteasyBootstrap[m
[36m@@ -25,28 +20,24 @@[m
         <servlet-class>[m
             org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher[m
         </servlet-class>[m
[31m-        <init-param>[m
[31m-            <param-name>javax.ws.rs.Application</param-name>[m
[31m-            <param-value>net.servehttp.bytecom.extra.rest.RegisterServices</param-value>[m
[31m-        </init-param>[m
[31m- [m
     </servlet>[m
  [m
[32m+[m[41m [m	[32m<servlet-mapping>[m
[32m+[m		[32m<servlet-name>Resteasy</servlet-name>[m
[32m+[m		[32m<url-pattern>/rest/*</url-pattern>[m
[32m+[m	[32m</servlet-mapping>[m
[32m+[m[41m [m
[32m+[m[41m [m
     <context-param>[m
       <param-name>resteasy.servlet.mapping.prefix</param-name>[m
       <param-value>/rest</param-value>[m
    </context-param>[m
[31m-    [m
[31m-    <context-param>  [m
[31m-     <param-name>resteasy.injector.factory</param-name>  [m
[31m-     <param-value>org.jboss.resteasy.cdi.CdiInjectorFactory</param-value>  [m
[32m+[m[41m   [m
[32m+[m[32m    <context-param>[m
[32m+[m		[32m<param-name>resteasy.scan</param-name>[m
[32m+[m		[32m<param-value>true</param-value>[m
 	</context-param>[m
[31m- [m
[31m-	<servlet-mapping>[m
[31m-		<servlet-name>Resteasy</servlet-name>[m
[31m-		<url-pattern>/rest/*</url-pattern>[m
[31m-	</servlet-mapping>[m
[31m-[m
[32m+[m[41m	[m
 	<!-- JSF -->[m
 	<servlet>[m
 	  <servlet-name>Faces Servlet</servlet-name>[m
