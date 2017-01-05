SICOBA - SISTEMA DE CONTROLE DE BANDA
=====================================

Contact
-------

            Project Leader: Clairton Carneiro Luz
            Personal Blog: http://blog.clairtonluz.com.br
            Email Address: clairton.c.l@gmail.com

Setup database
--------------
```shell
sudo -i
adduser bytecom
passwd bytecom

echo "export JDBC_DATABASE_URL='jdbc:postgresql://localhost:5432/bytecom?user=bytecom&password=bytecom'" > /etc/profile.d/database.sh

sudo -i -u postgres

```

```sql
CREATE USER bytecom WITH PASSWORD 'bytecom';
GRANT ALL PRIVILEGES ON DATABASE bytecom to bytecom;
\q
```

Setup project
-------------
```shell
cd path_to_project

npm install
bower install

```

Integration SendGrid (Optional)
-------------
```shell
sudo -i

echo "export SENDGRID_API_KEY='YOUR API KEY'" > /etc/profile.d/sendgrid.env

```


Integração Gerencianet (Optional)
---------------------------------------
```shell
sudo -i

echo "export CLIENT_ID='Client_Id';" > /etc/profile.d/gerencianet.sh
echo "export CLIENT_SECRET='Client_Secret';" > /etc/profile.d/gerencianet.sh
echo "export SANDBOX='true';" > /etc/profile.d/gerencianet.sh
echo "export NOTIFICATION_URL='http://www.example.com.br/api/notification';" > /etc/profile.d/gerencianet.sh

```

Credentials default to login in system
--------------------------------------
user:admin
pass:admin

Technologies In Use
-------------------

1. Front end
    - AngularJS
    - HTML5
    - Bootstrap
    - gulp
    - jshint
<br/>

2. Back end
    - JDK 8
    - Mikrotik API
    - Spring Boot
    - JPA
    - PostGreSQL
    - Hibernate
    - JUnit 4.12
    - Flyway
    - JAX-RS
    - Gradle


--------------------------------------------
Copyright (C) 2013-2016, Clairton Carneiro Luz
