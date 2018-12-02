#### Build
    docker build -t bytecom/sicoba:2 -t bytecom/sicoba:latest .

#### Create a network to connect app with database
    docker network create --driver bridge postgres-network
    
#### Database in docker
    docker pull postgres
    docker run --name bytecom-postgres --network postgres-network -e POSTGRES_PASSWORD=postgres \
        -p 5432:5432 \
        -v "$PWD/docker-entrypoint-initdb.d/init-user-db.sh":/docker-entrypoint-initdb.d/init-user-db.sh \
        -d postgres

#### Database via psql
    docker run -it --rm --link bytecom-postgres postgres psql -h bytecom -U bytecom


#### Run in development    
    docker run -p 8080:8080 bytecom/sicoba --spring.datasource.url=jdbc:postgresql://10.77.5.81:5432/bytecom?user=bytecom\&password=bytecom
    
#### Run in production    
    heroku pg:backups:download --app appname