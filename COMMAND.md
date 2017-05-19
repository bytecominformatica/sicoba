#### Restore file dump made by autobus.io
    pg_restore --verbose --clean --no-acl --no-owner -h localhost -U myuser -d mydb DATABASE_54d4041969702d0295380301.dump
   
#### Create backup database    
    heroku pg:backups:capture --app appname
    
#### Download lastest backup database    
    heroku pg:backups:download --app appname
    
#### Copy database from a app to another one    
    heroku pg:copy appnameorigin::DATABASE DATABASE --app appnamedestiny
    