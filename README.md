# Locadora Katchow

Um sistema de apoio ao gerenciamento de estoque e de clientes para uma Locadora de automóveis de pequeno e médio porte.

## Pré-Requisitos

* Java 24
* Apache Maven 3.6.3
* PostgresSQL

## Instalação de Dependências

```shell
git clone https://github.com/angellusj/locadora-katchow.git
cd locadora-katchow/locadorakatchow
mvn clean
mvn -X install
```

## Criação e configuração do banco de dados

Em 'locadorakatchowDB.sql' disponibilizamos um script para a criação do banco de dados d
a aplicação. Após a criação do banco com o script, em resources/db.properties en
tre com as informações do bando de dados.

```propeties
db.url=jdbc:postgresql://localhost:5432/locadorakatchowDB
db.username=postgres
db.password=<sua senha aqui>
```

## Execução

Após a instalação das dependências e criação do banco, basta executar a classe 
*App.java*
