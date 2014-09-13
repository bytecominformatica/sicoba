[1mdiff --git a/pom.xml b/pom.xml[m
[1mindex e57b621..cd0178f 100644[m
[1m--- a/pom.xml[m
[1m+++ b/pom.xml[m
[36m@@ -55,6 +55,7 @@[m
 			<groupId>junit</groupId>[m
 			<artifactId>junit</artifactId>[m
 			<version>4.11</version>[m
[32m+[m			[32m<scope>test</scope>[m
 		</dependency>[m
 [m
 		<dependency>[m
[1mdiff --git a/src/main/java/net/servehttp/bytecom/web/controller/SecurityController.java b/src/main/java/net/servehttp/bytecom/web/controller/SecurityController.java[m
[1mindex a0d8500..e1b8a01 100644[m
[1m--- a/src/main/java/net/servehttp/bytecom/web/controller/SecurityController.java[m
[1m+++ b/src/main/java/net/servehttp/bytecom/web/controller/SecurityController.java[m
[36m@@ -7,8 +7,10 @@[m [mimport java.util.logging.Logger;[m
 import javax.annotation.PostConstruct;[m
 import javax.ejb.EJB;[m
 import javax.enterprise.context.RequestScoped;[m
[32m+[m[32mimport javax.faces.context.FacesContext;[m
 import javax.inject.Inject;[m
 import javax.inject.Named;[m
[32m+[m[32mimport javax.servlet.ServletRequest;[m
 [m
 import net.servehttp.bytecom.business.AccountBussiness;[m
 import net.servehttp.bytecom.ejb.MailEJB;[m
[36m@@ -20,6 +22,8 @@[m [mimport org.apache.shiro.SecurityUtils;[m
 import org.apache.shiro.authc.AuthenticationException;[m
 import org.apache.shiro.authc.UsernamePasswordToken;[m
 import org.apache.shiro.subject.Subject;[m
[32m+[m[32mimport org.apache.shiro.web.util.SavedRequest;[m
[32m+[m[32mimport org.apache.shiro.web.util.WebUtils;[m
 [m
 /**[m
  * [m
[1mdiff --git a/src/main/webapp/WEB-INF/shiro.ini b/src/main/webapp/WEB-INF/shiro.ini[m
[1mindex 6230c54..438e682 100644[m
[1m--- a/src/main/webapp/WEB-INF/shiro.ini[m
[1m+++ b/src/main/webapp/WEB-INF/shiro.ini[m
[36m@@ -1,6 +1,9 @@[m
 [main][m
 user.loginUrl = /login.xhtml[m
 [m
[32m+[m[32mbuiltInCacheManager = org.apache.shiro.cache.MemoryConstrainedCacheManager[m
[32m+[m[32msecurityManager.cacheManager = $builtInCacheManager[m
[32m+[m
 # Create JDBC realm.[m
 jdbcRealm = org.apache.shiro.realm.jdbc.JdbcRealm[m
 [m
[36m@@ -19,8 +22,6 @@[m [mcredentialsMatcher = org.apache.shiro.authc.credential.HashedCredentialsMatcher[m
 credentialsMatcher.hashAlgorithmName = SHA-256[m
 jdbcRealm.credentialsMatcher = $credentialsMatcher[m
 [m
[31m-jdbcRealm.authorizationCachingEnabled = true[m
[31m-[m
 [urls][m
 /login.xhtml = user[m
 /css/** = anon[m
[1mdiff --git a/src/main/webapp/WEB-INF/web.xml b/src/main/webapp/WEB-INF/web.xml[m
[1mindex 694306c..3b4222a 100644[m
[1m--- a/src/main/webapp/WEB-INF/web.xml[m
[1m+++ b/src/main/webapp/WEB-INF/web.xml[m
[36m@@ -57,10 +57,6 @@[m
 	  <param-value>pt_BR</param-value>[m
 	</context-param>[m
 	[m
[31m-	<!-- SESSION -->[m
[31m-	<session-config>[m
[31m-	  <session-timeout>20</session-timeout>[m
[31m-	</session-config>[m
 	[m
 	<!-- PAGES -->[m
 	<welcome-file-list>[m
