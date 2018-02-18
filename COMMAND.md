#### Restore file dump made by autobus.io
    pg_restore --verbose --clean --no-acl --no-owner -h localhost -U myuser -d mydb DATABASE_54d4041969702d0295380301.dump
   
#### Create backup database    
    heroku pg:backups:capture --app appname
    
#### Download lastest backup database    
    heroku pg:backups:download --app appname
    
#### Copy database from a app to another one    
    heroku pg:copy appnameorigin::DATABASE DATABASE --app appnamedestiny

#### Publicação em uma EC2
    https://docs.spring.io/spring-boot/docs/current/reference/html/deployment-install.html

###### depois crie um arquivo appname.conf com as configuraçoes iniciais
    RUN_ARGS="
    --spring.profiles.active=production
    --spring.datasource.url=jdbc:postgresql://localhost:5432/bytecom?user=bytecom&password=bytecom
    --myapp.email.admin=clairton.c.l@gmail.com
    --myapp.boleto.notification-url=https://sicoba.bytecominformatica.com.br/api/gerencianet/%d/notification
    --spring.mail.username=sicoba@bytecominformatica.com.br
    --spring.mail.password=myPassword"
                                                 

    
    
