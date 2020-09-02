## Greeting-api

## Introduction

The greeting-api is a simple example. I use **spring-boot-starter-web**, **spring-boot-starter-data-mongodb** and **swagger**.
For the deployment, **Docker** is uses.

It is necessary to specify that API has been realized in contract-first with **Apicurio Studio**.

## Contract first & openapi-generator-maven-plugin

With **Apicurio Studio**, i have write the contract. The **openapi-generator-maven-plugin** is the plugin that generates
contract interface and DTO.

## Api implementation

I implement the interface contract with the controller named **GreetingController**. This controller implement **GreetingsApi** interface.

## MongoDB & Spring-boot-starter-data-mongodb

I use **mongodb** for the persistence and **spring-boot-starter-data-mongodb** for the interaction with mongodb.

## Documentation with Swagger

The documentation has been created with **Apicurio Studio**. But **Swagger-ui** allows to present the API with the differents endpoints.

## The docker-maven-plugin for the creation of docker image

When you launch that :

	mvn clean package -P docker
	
The profile named **docker** is enabled. The **docker-maven-plugin** is launched and the docker image will be generated.

	docker images | grep greeting-api

## Starting up the greeting-api

Let's go, starting up the greeting-api :

	mvn clean package
	
	docker-compose up --build --detach
	
	docker-compose logs -f
	
When all is launched, for your information :

	docker-compose ps
	
You can see, for example, the logs of selected container :

	docker-compose logs -f greeting-api
	
## Greeting creation

	curl -X POST -H "Content-Type: application/json" -d '{"subject": "Luke listen to me","message": "May the force be with you", "firstname":"Luke","name": "Skywalker"}' "http://localhost:8080/api/v1/greetings" -s | jq
	
Result :

	{
		"id": "5f4f57b93d0a737c6d59c421", 
		"message": "May the force be with you", 
		"firstname": "Luke", 
		"name": "Skywalker", 
		"creationDate": "2020-09-02T08:28:41.203Z", 
		"version": "1", 
		"subject": "Luke listen to me"
	}
	
## Find a greeting by name and firstname

	curl -X GET "http://localhost:8080/api/v1/greetings?name=Skywalker&firstname=Luke" -s | jq
	
Result :

	{
		"id": "5f4f57b93d0a737c6d59c421",
		"message": "May the force be with you",
		"firstname": "Luke",
		"name": "Skywalker",
		"creationDate": "2020-09-02T08:20:38.792Z",
		"version": "1",
		"subject": "Luke listen to me"
	}
	
## Delete a greeting

	curl -X DELETE "http://localhost:8080/api/v1/greetings/5f4f57b93d0a737c6d59c421" -s | jq
	
## Stopping up the greeting-api

	docker-compose down
	
## Test the greeting-api

	./test-em-all.bash start stop
