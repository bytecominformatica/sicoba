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

echo "export NOTIFICATION_URL='http://www.example.com.br/api/gerencianet/%d/notification';" > /etc/profile.d/gerencianet.sh

```
**Importante:** o caractere '%d' será substituido pelo o id da sua conta gerencianet automaticamente na hora de criar a cobrana. Você não precisa colocar o id na url apenas deixe o '%d' no local onde o id deve ser inserido.

Agora você pode acessar o sistema e ir no menu **Finaneiro > Contas Gerencianet** e cadastrar a suas contas gerencianet que será utilizada para a geração das cobranças. Depois disso quando você for gerar as cobranças será possível escolher em qual conta deseja gerar aquela cobrana.

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
