#!/bin/bash

clear

falhou() {
    echo -e "\033[0;31m [ FALHOU ] \033[m"
}

ok() {
    echo -e "\033[0;32m [ OK ] \033[m"
}

echo -ne "Verificando se o docker está rodando... "
if ! docker info >/dev/null 2>&1; then
    falhou
    echo "O docker não está rodando."
    exit 1
else
    ok
fi

echo -ne "Verificando se o container do PostgreSQL está rodando... "
if [ "$( docker container inspect -f '{{.State.Status}}' postgres-sistema 2>/dev/null)" == "running" ]; then
    ok
else
    falhou
    echo -ne "O container do PostgreSQL não está rodando. Vou tentar iniciá-lo... "
    docker-compose -f docker/docker-compose.yml up -d 1>/dev/null 2>/dev/null
    if [ $? -eq 0 ]; then
        ok
    else
        falhou
        echo "Não pode iniciar o container do PostgreSQL."
        exit 1
    fi
fi

sleep 20

echo -ne "Iniciando eureka-server... "
nohup java -jar eureka-server/target/eureka-server-0.0.1.jar > .eureka-server.log 2>&1 &
ok
echo $! > .eureka-server.pid 

sleep 5

echo -ne "Iniciando config-server... "
nohup java -jar config-server/target/config-server-0.0.1.jar > .config-server.log 2>&1 &
ok
echo $! > .config-server.pid 

echo -ne "Iniciando gateway-server... "
nohup java -jar gateway-server/target/gateway-server-0.0.1.jar > .gateway-server.log 2>&1 &
ok
echo $! > .gateway-server.pid 

sleep 5

echo -ne "Iniciando autenticacao-service... "
nohup java -jar autenticacao-service/target/autenticacao-service-0.0.1.jar > .autenticacao-service.log 2>&1 &
ok
echo $! > .autenticacao-service.pid 

echo -ne "Iniciando monitoramento... "
nohup java -jar monitoramento/target/monitoramento-0.0.1.jar > .monitoramento.log 2>&1 &
ok
echo $! > .monitoramento.pid 