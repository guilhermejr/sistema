#!/bin/bash

clear

falhou() {
    echo -e "\033[0;31m [ FALHOU ] \033[m"
}

ok() {
    echo -e "\033[0;32m [ OK ] \033[m"
}

echo -ne "Parando monitoramento... "
kill -9 $(cat .monitoramento.pid) 1>/dev/null 2>/dev/null
if [ $? -eq 0 ]; then
    ok
else
    falhou
fi

echo -ne "Parando autenticacao-service... "
kill -9 $(cat .autenticacao-service.pid) 1>/dev/null 2>/dev/null
if [ $? -eq 0 ]; then
    ok
else
    falhou
fi

echo -ne "Parando gateway-server... "
kill -9 $(cat .gateway-server.pid) 1>/dev/null 2>/dev/null
if [ $? -eq 0 ]; then
    ok
else
    falhou
fi

echo -ne "Parando config-server... "
kill -9 $(cat .config-server.pid) 1>/dev/null 2>/dev/null
if [ $? -eq 0 ]; then
    ok
else
    falhou
fi

echo -ne "Parando eureka-server... "
kill -9 $(cat .eureka-server.pid) 1>/dev/null 2>/dev/null
if [ $? -eq 0 ]; then
    ok
else
    falhou
fi

echo -ne "Parando o container do PostgreSQL... "
if [ "$( docker container inspect -f '{{.State.Status}}' postgres-sistema 2>/dev/null)" == "running" ]; then
    docker-compose -f docker/docker-compose.yml down 1>/dev/null 2>/dev/null
    if [ $? -eq 0 ]; then
        ok
    else
        falhou
        exit 1
    fi
else
    falhou
    exit 1
fi