#!/bin/bash
echo "Starting configurations...";
 
DIR="/home/dev/servers";
USER="dev";
WILDFLY_DIR=$DIR/wildfly-8.0.0.Final;

######################### ENVIRONMENT VARIABLE #########################
WF_FILE=/etc/profile.d/wf.sh;
 
if ! [ -f "$WF_FILE" ]; then
	echo "Setting environment variable";
	echo "creating $WF_FILE...";
	echo "export WILDFLY_HOME=$DIR/wildfly-8.0.0.Final" >> $WF_FILE;
fi;
 
source $WF_FILE;
######################### WILDFLY SERVER ###############################
 
WILDFLY_ZIP=wildfly-8.0.0.Final.zip;
 
if ! [ -d "$WILDFLY_DIR/bin" ]; then
	if ! [ -f "$WILDFLY_ZIP" ]; then
		echo "Downloading Wildfly 8 Final...";
		wget http://download.jboss.org/wildfly/8.0.0.Final/wildfly-8.0.0.Final.zip;
	fi;
	echo "Extracting $WILDFLY_ZIP";
	unzip wildfly-8.0.0.Final.zip -d $DIR;	
fi;
 
######################### MYSQL DRIVER #################################
 
MYSQL_DRIVE=mysql-connector-java-5.1.29.jar;
if ! [ -f "$WILDFLY_DIR/modules/com/mysql/main/mysql-connector-java-5.1.18-bin.jar" ]; then
	echo "setting mysql driver";
	mkdir -p "$WILDFLY_DIR/modules/com/mysql/main";
	
	if ! [ -f "$MYSQL_DRIVE" ]; then
		echo "Downloading $MYSQL_DRIVE...";
		wget http://repo1.maven.org/maven2/mysql/mysql-connector-java/5.1.29/mysql-connector-java-5.1.29.jar;
	fi;

	cp mysql-connector-java-5.1.29.jar $WILDFLY_DIR/modules/com/mysql/main;
	
	if [ -f "$WILDFLY_DIR/modules/com/mysql/main/module.xml" ]; then
		rm -f $WILDFLY_DIR/modules/com/mysql/main/;
	fi;

echo '<module xmlns="urn:jboss:module:1.0" name="com.mysql">
		<resources>
			<resource-root path="'$MYSQL_DRIVE'"/>
		</resources>
		<dependencies>
			<module name="javax.api"/>
		</dependencies>
	</module>' >> $WILDFLY_DIR/modules/com/mysql/main/module.xml;
	
fi;


#################### WILDFLY SERVER CONFIGURATION ######################
echo "Setting the server Wildfly";
echo "Starting server wildfly";

cd $WILDFLY_DIR/bin;
./standalone.sh &

sleep 5

echo "SETTING DRIVER MYSQL";
./jboss-cli.sh --commands='connect localhost:9990,
./subsystem=datasources/jdbc-driver=com.mysql:add(driver-name="com.mysql", driver-module-name="com.mysql", driver-xa-datasource-class-name="com.mysql.jdbc.jdbc2.optional.MysqlXADataSource" )';

echo "SETTING DATASOURCE";
./jboss-cli.sh --commands='connect localhost:9990,
./subsystem=datasources/data-source=bytecomDS:add(enabled=true , jndi-name="java:/bytecomDS" , use-java-context=true, driver-name="com.mysql" , min-pool-size=10 , max-pool-size=100 , pool-prefill=true, user-name="bytecom", password="bytecom" , connection-url="jdbc:mysql://localhost:3306/bytecom" )';
 
echo "SETTING OWNER";
chown -R $USER. $WILDFLY_DIR

sleep 2

killall java
