#!/bin/bash

echo "STARTING CONFIGURATIONS..."

USER_HOME=$(eval echo ~${SUDO_USER})

echo "DIGITE O CAMINHO ONDE DESEJA INSTALAR O WILDFLY EX: /home/usuario "
read DIRETORIO_INSTALACAO
mkdir -p $DIRETORIO_INSTALACAO

echo "DIGITE SUA SENHA PARA UTILIZAÇÃO DO COMANDO SUDO "
sudo chmod 777 /tmp

WILDFLY_DIR=$DIRETORIO_INSTALACAO/wildfly-8.1.0.Final;


######################### CONFIGURANDO BANCO ###########################

echo "DIGITE A SENHA DO USUARIO ROOT DO MYSQL. CASO SUA SEU USUARIO ROOT ESTEJA EM SENHA PRECIONE ENTER QUE IREMOS MODIFICAR A SENHA DO USUARIO ROOT PARA 'root'"
read MYSQL_SENHA;
if [ $MYSQL_SENHA == "" ]; then
 $MYSQL_SENHA = "root";
 mysqladmin -u root password $MYSQL_SENHA
fi

mysql -u root -p$MYSQL_SENHA -e 'CREATE DATABASE IF NOT EXISTS bytecom'

mysql -u root -p$MYSQL_SENHA -e "grant all privileges on bytecom.* to bytecom@localhost identified by 'bytecom'"

######################### ENVIRONMENT VARIABLE #########################
WF_FILE=/etc/profile.d/wf.sh;

echo "SETTING ENVIRONMENT VARIABLE";
echo "CREATING $WF_FILE...";
sudo echo "export WILDFLY_HOME=$WILDFLY_DIR" > $WF_FILE;

######################### WILDFLY SERVER ###############################
 
cd /tmp
WILDFLY_ZIP=wildfly-8.1.0.Final.zip;
 
if ! [ -d "$WILDFLY_DIR" ]; then
	if ! [ -f "$WILDFLY_ZIP" ]; then
		echo "DOWNLOADING WILDFLY 8 FINAL...";
		wget http://download.jboss.org/wildfly/8.1.0.Final/wildfly-8.1.0.Final.zip;
	fi;
	echo "EXTRACTING $WILDFLY_ZIP";
	unzip wildfly-8.1.0.Final.zip -d $DIRETORIO_INSTALACAO;	
fi;

#################### WILDFLY SERVER CONFIGURATION ######################
echo "SETTING THE SERVER WILDFLY";
echo "STARTING SERVER WILDFLY";

$WILDFLY_DIR/bin/./standalone.sh &

sleep 10


######################### ADD MODULOS #################################

MYSQLDRIVER=/tmp/mysql-connector-java-5.1.29.jar;
if ! [ -f $MYSQLDRIVER ]; then

	echo "DOWNLOADING MYSQL DRIVER 5.1.29";
	wget http://repo1.maven.org/maven2/mysql/mysql-connector-java/5.1.29/mysql-connector-java-5.1.29.jar;
	
fi;

$WILDFLY_DIR/bin/./jboss-cli.sh --connect --command='module add --name=com.mysql --resources=/tmp/mysql-connector-java-5.1.29.jar --dependencies=javax.api' 


######################### POOL MYSQL #################################
echo "SETTING POOLS";

$WILDFLY_DIR/bin/./jboss-cli.sh --connect --commands='./subsystem=datasources/jdbc-driver=com.mysql:add(driver-name="com.mysql", driver-module-name="com.mysql", driver-xa-datasource-class-name="com.mysql.jdbc.jdbc2.optional.MysqlXADataSource" )';

######################### DEFININDO DATASOURCES #################################

echo "SETTING DATASOURCES";
$WILDFLY_DIR/bin/./jboss-cli.sh --connect --commands='./subsystem=datasources/data-source=bytecomDS:add(enabled=true , jndi-name="java:/bytecomDS" , use-java-context=true, driver-name="com.mysql" , min-pool-size=10 , max-pool-size=100 , pool-prefill=true, user-name="bytecom", password="bytecom" , connection-url="jdbc:mysql://localhost:3306/bytecom" )'

######################### CONFIGURANDO EMAIL #################################

# $WILDFLY_DIR/bin/./jboss-cli.sh --connect --commands='/socket-binding-group=standard-sockets/remote-destination-outbound-socket-binding=mail-smtp-gmail:add(host=smtp.gmail.com, port=465)'
# $WILDFLY_DIR/bin/./jboss-cli.sh --connect --commands='/subsystem=mail/mail-session=bytecom:add(jndi-name=java:/mail/bytecom)'
# $WILDFLY_DIR/bin/./jboss-cli.sh --connect --commands='/subsystem=mail/mail-session=bytecom/server=smtp:add(outbound-socket-binding-ref=mail-smtp-gmail,username=your_email@gmail.com,password=secret,ssl=true)'

######################### CONFIGURANDO SECURITY REALM #################################

# $WILDFLY_DIR/bin/./jboss-cli.sh --connect --commands='./subsystem=security/security-domain=bytecom:add(cache-type="default")'
#$WILDFLY_DIR/bin/./jboss-cli.sh --connect --commands='cd ./subsystem=security/security-domain=bytecom, 
#./authentication=classic:add(login-modules=[ {code="Database", flag="required", module-options={dsJndiName="java:/bytecomDS", principalsQuery="select password from authentication where username=?", rolesQuery="select group_name, 'Roles' from user_group ug inner join authentication a on ug.user_id = a.user_account_id where a.username = ?", hashAlgorithm="SHA-256", hashEncoding="BASE64", unauthenticatedIdentity="guest"}}, {code="RoleMapping", flag="required", module-options={rolesProperties="file:${jboss.server.config.dir}/bytecom.properties", replaceRole="false"}}])'

#echo admins=admin,financial,report		> $WILDFLY_DIR/standalone/configuration/bytecom.properties;
#echo technical=report			>> $WILDFLY_DIR/standalone/configuration/bytecom.properties;

sleep 2

killall java

sleep 2

echo "#######################################################";
echo "#                                                     #";
echo "# CREATE BY: CLAIRTON CARNEIRO LUZ                    #";
echo "# CREATE TO: BYTECOM                                  #";
echo "#                                                     #";
echo "#                    TERMINOU                         #";
echo "#######################################################";
