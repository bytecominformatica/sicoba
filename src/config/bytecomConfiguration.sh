#!/bin/bash

echo "STARTING CONFIGURATIONS..."

USER_HOME=$(eval echo ~${SUDO_USER})

echo "DIGITE O CAMINHO ONDE DESEJA INSTALAR O WILDFLY EX: /home/usuario "
read DIRETORIO_INSTALACAO
mkdir -p $DIRETORIO_INSTALACAO

echo "DIGITE SUA SENHA PARA UTILIZAÇÃO DO COMANDO SUDO "
sudo chmod 777 /tmp

WILDFLY_DIR=$DIRETORIO_INSTALACAO/wildfly-8.1.0.Final;

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
