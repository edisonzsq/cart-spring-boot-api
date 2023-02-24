# Spring Boot Lesson

## Quick Start

To run the project:

Step 1: CD to the project root directory `cart_api`

Step 2: Run `./mvnw spring-boot:run` command

## Lesson Overview (24 Feb 2023, Fri)

Previously, we have
- Create a spring starter project with web dependency [here](https://start.spring.io/)
- Used several annotations to create RESTful API
```
@RestController
@RequestMapping
@RequestBody
@ResponseBody
@PathVariable
@Autowired
@Service
```

In this lesson, we will cover:

1. Configure a custom singleton object using `@Bean` annotation
1. The use of `@Value` annotation to inject environmental variable
1. Implementing a logger to capture all API Input

Essentially, there are three common ways to configure and inject Spring Bean/Value.
1. Using `@Component` or its children - @RestController, @Service, etcs..
1. Using `@Value` to inject value picked up from `application.properties` file.
1. Using `@Bean` on a method that returns a configured object.

Practically, we will create this endpoint:

|Path|Verb|Return Status|Remarks|
|-|-|-|-|
|/payment?payable=5.3|POST|200|Payment successful|
|||400|When payment is below minimum payable sum or negative value.

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
1. PUT /products/{id}
1. DELETE /products/{id}

## Standard REST API

|Path|Verb|Has Request Body|Has Response Body|Purpose|
|-|-|-|-|-|
|/entities|GET|NO|YES|Get all entities (usually with pagination and sorting query parameters)|
|/entities/{id}|GET|NO|YES|Get one entity by id|
|/entities|POST|Yes|YES|Create an entity|
|/entities/{id}|PUT|YES|YES|Update an entity|
|/entities/{id}|DELETE|NO|NO|Delete an entity|

For more info - [Link](https://www.geeksforgeeks.org/rest-api-architectural-constraints/)
