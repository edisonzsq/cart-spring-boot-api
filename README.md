# Spring Boot Lesson

## Quick Start

To run the project:

Step 1: CD to the project root directory `cart_api`

Step 2: Run `./mvnw spring-boot:run` command

## Lesson Overview (20 Feb 2023, Mon)

1. Generate a Spring Project with the web dependency added
1. Annotations Explanations:
    - @SpringBootApplication
    - @RestController
    - @RequestMapping
    - @RequestBody
    - @RequestResponse

By the end of the class, we would have created:

1. GET /products
1. GET /products/{id}
1. POST /products

## Standard REST API

|Path|Verb|Has Body|Purpose|
|-|-|-|-|
|/entities|GET|NO|Get all entities (usually with pagination and sorting query parameters)|
|/entities/{id}|GET|NO|Get one entity by id|
|/entities|POST|Yes|Create an entity|
|/entities/{id}|PUT|Yes|Update an entity|
|/entities/{id}|DELETE|No|Delete an entity|

For more info - [Link](https://www.geeksforgeeks.org/rest-api-architectural-constraints/)