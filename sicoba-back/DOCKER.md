#### Build
    docker build -t bytecom/sicoba:latest .

#### Create a network to connect app with database
    docker network create --driver bridge postgres-network
    
#### Database in docker (you need run the code down below into root folder of the project)
    docker pull postgres
    docker run --name bytecom-postgres --network postgres-network -e POSTGRES_PASSWORD=postgres \
        -p 5432:5432 \
        -v "$PWD/docker-entrypoint-initdb.d/init-user-db.sh":/docker-entrypoint-initdb.d/init-user-db.sh \
        -v /var/lib/postgresql/sicoba/data:/var/lib/postgresql/data \
        -d postgres

#### Database via psql
    docker run -it --rm --link bytecom-postgres postgres psql -h bytecom -U bytecom


#### Variables to run docker image

* __DATABASE_HOST__     : (default: "localhost"                           ) __required__
* __DATABASE_PORT__     : (default: 5432                                  ) 
* __DATABASE_NAME__     : (default: "bytecom"                             ) 
* __DATABASE_USER__     : (default: "bytecom"                             ) 
* __DATABASE_PASS__     : (default: "bytecom"                             ) 
* __PROFILE__           : (default: "staging"                             ) 
* __APP_TOKEN__         : (default: "TOKEN_EXAMPLE"                       ) 
* __EMAIL_SAC__         : (default: "sac@bytecominformatica.com.br"       ) 
* __EMAIL_ADMIN__       : (default: "admin@bytecominformatica.com.br"     ) 
* __EMAIL_SUPORTE__     : (default: "suporte@bytecominformatica.com.br"   ) 
* __EMAIL_HOST__        : (default: "smtp.zoho.com"                       )
* __EMAIL_USERNAME__    : (default: "sicoba@bytecominformatica.com.br"    ) 
* __EMAIL_PASSWORD__    : (default: "secretPassword"                      ) __required__
* __EMAIL_PORT__        : (default: 465                                   ) 
* __DOMAIN__            : (default: "localhost:8080"                      ) __required__
* __SENDGRID_API_KEY__  : (default: "SENDGRID_API_KEY_SECRET"             ) __required__

#### Run in staging
    docker run -p 80:8080 -e DATABASE_HOST="ip_do_banco" bytecom/sicoba
    
#### Run in production
    docker run -p 80:8080 \
    -e PROFILE="production" \
    -e DATABASE_HOST="database_ip" \
    -e DATABASE_PASS="database_password" \
    -e APP_TOKEN="token_para_acesso_do_mk" \
    -e DOMAIN="seu_dominio" \
    -e SENDGRID_API_KEY="sendgrid_token" \ 
    bytecom/sicoba

#### Run in production as service
    DATABASE_HOST="DATABASE_HOST" \
    DATABASE_PASS="DATABASE_PASS" \
    APP_TOKEN="APP_TOKEN" \
    DOMAIN="DOMAIN" \
    SENDGRID_API_KEY="SENDGRID_API_KEY" \
    docker stack deploy -c docker-compose.yml bytecom-sicoba
    
#### baixar imagem gerada pelo gitlab
    docker login registry.gitlab.com -u username -p deploy-token
    docker pull registry.gitlab.com/bytecom/sicoba
    
    # para executar essa imagem basta usar o comando acima 
    # alterando o nome da imagem 
    # de: bytecom/sicoba para: registry.gitlab.com/bytecom/sicoba 
    
    
