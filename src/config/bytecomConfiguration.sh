#!/bin/bash
echo "#################################################################";
echo "ATENÇÃO ESSE SCRIPT DEVE SER EXECUTADO COMO ROOT.";
echo "#################################################################";

echo "Digite o nome do seu usuario linux";
read USUARIO;

echo "Starting configurations...";

USER_HOME=$(eval echo ~${SUDO_USER});

echo "Digite o caminho onde deseja instalar o wildfly: ";
read DIRETORIO_INSTALACAO;

WILDFLY_DIR=$DIRETORIO_INSTALACAO/wildfly-8.0.0.Final;

######################### ENVIRONMENT VARIABLE #########################
WF_FILE=/etc/profile.d/wf.sh;

echo "Setting environment variable";
echo "creating $WF_FILE...";
echo "export WILDFLY_HOME=$WILDFLY_DIR" > $WF_FILE;

 
source $WF_FILE;
######################### WILDFLY SERVER ###############################
 
WILDFLY_ZIP=wildfly-8.0.0.Final.zip;
 
if ! [ -d "$WILDFLY_DIR/bin" ]; then
	if ! [ -f "$WILDFLY_ZIP" ]; then
		echo "Downloading Wildfly 8 Final...";
		wget http://download.jboss.org/wildfly/8.0.0.Final/wildfly-8.0.0.Final.zip;
	fi;
	echo "Extracting $WILDFLY_ZIP";
	unzip wildfly-8.0.0.Final.zip -d $DIRETORIO_INSTALACAO;	
fi;

#################### WILDFLY SERVER CONFIGURATION ######################
echo "Setting the server Wildfly";
echo "Starting server wildfly";

cd $WILDFLY_DIR/bin;
./standalone.sh &

sleep 5


######################### ADD MODULOS #################################

MYSQLDRIVER=$USER_HOME/.m2/repository/mysql/mysql-connector-java/5.1.29/mysql-connector-java-5.1.29.jar;
if ! [ -f $MYSQLDRIVER ]; then

	echo "Downloading Mysql driver 5.1.29";
	wget http://repo1.maven.org/maven2/mysql/mysql-connector-java/5.1.29/mysql-connector-java-5.1.29.jar;
	
	mkdir -p $USER_HOME/.m2/repository/mysql/mysql-connector-java/5.1.29;
	mv mysql-connector-java-5.1.29.jar $USER_HOME/.m2/repository/mysql/mysql-connector-java/5.1.29;
fi;

./jboss-cli.sh --connect --command='module add --name=com.mysql --resources=/home/clairton/.m2/repository/mysql/mysql-connector-java/5.1.29/mysql-connector-java-5.1.29.jar --dependencies=javax.api' 


######################### POOL MYSQL #################################
echo "SETTING POOLS";

./jboss-cli.sh --connect --commands='./subsystem=datasources/jdbc-driver=com.mysql:add(driver-name="com.mysql", driver-module-name="com.mysql", driver-xa-datasource-class-name="com.mysql.jdbc.jdbc2.optional.MysqlXADataSource" )';

######################### DEFININDO DATASOURCES #################################

echo "SETTING DATASOURCES";
./jboss-cli.sh --connect --commands='./subsystem=datasources/data-source=bytecomDS:add(enabled=true , jndi-name="java:/bytecomDS" , use-java-context=true, driver-name="com.mysql" , min-pool-size=10 , max-pool-size=100 , pool-prefill=true, user-name="bytecom", password="bytecom" , connection-url="jdbc:mysql://localhost:3306/bytecom" )'

sleep 2

killall java

sleep 2

echo "SETTING OWNER";
chown -R $USUARIO. $WILDFLY_DIR
chown -R $USUARIO. $USER_HOME/.m2

echo "############";
echo "# TERMINOU #";
echo "############";
