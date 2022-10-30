# reading-is-good

Simple backend for a book ordering application

[Swagger link](https://reading-is-good-be.herokuapp.com/swagger-ui/index.html#/)

[Postman collection](https://www.getpostman.com/collections/c445b54af7ef1ece190f)

[DockerHub](https://hub.docker.com/repository/docker/ozanokur/reading-is-good)

Endpoints are secured, so swagger will only work as documentation. It may take a while to open the swagger link because heroku will first initialize an instance.

## Design

3 Main models are used. Customer, Book and Order. There is a 1-to-N relationship between customers and orders, and a 1-to-N relationship between books and orders.

A customer can order an amount of books at a certain date, and that order will go through depending on conditions such as the stock of books, existence of books and customer etc.

## Tech stack

Java 11

Spring Boot

H2 database

Spring Security

Docker

## How to run

A deployed version exists [here](https://reading-is-good-be.herokuapp.com/swagger-ui/index.html#/) on heroku. 

Additionally the project is containerized and added to docker hub, can be found here: [DockerHub](https://hub.docker.com/repository/docker/ozanokur/reading-is-good)

To run the dockerized version, run the following command:

`
docker run -p 8080:8080 ozanokur/reading-is-good
`

### Notes

- Endpoints are secured, so before using them you must log in and generate a JWT. Valid login info can be found in the [Postman collection here](https://www.getpostman.com/collections/c445b54af7ef1ece190f). 
After generating a JWT, a header with the name of `Authorization` must be set with the generated value. This can also be found in the postman collection.
JWTs expire after 30 minutes, and there is no refresh token, so you must log in again in such a case.
