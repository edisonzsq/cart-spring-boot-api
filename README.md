# Spring Boot Lesson

## Quick Start

To run the project:

Step 1: CD to the project root directory `cart_api`

Step 2: Run `./mvnw spring-boot:run` command

## Lesson Overview (6 Mar 2023, Mon)

Lesson Coverage:
- Use of `@OneToOne` and `@JoinColumn` annotation
- Start implementing new set of APIs

## New APIs

|URL|Status|Response|
|-|-|-|
|GET /carts|200|A list of records in cart table|
||404|When no records are found in cart table|
|POST /carts/add/{productId}?quantity=1|200|<p>If `productId` does not exist, create a new record with `quantity` set to 1.</p><p>When `quantity` is provided, set the quantity of the specified `productId` to the specified value.</p><p>When `quantity` is not provided, increment the quantity of the specified `productId` by 1.</p>|
||400|When the specified `productId` does not exist in the product table.
|POST /carts/decrement/{productId}|200|<p>If the specified `productId` exist and the quantity is 1, delete the record from cart table.</p><p>If the specified `productId` exist and the quantity is greater than 1, decrement the quantity by 1.</p>|
||400|When the specified `productId` does not exist in the `cart` table|
|POST /carts/clear|200|If there are records in the cart table, delete all records in the cart table|
||304|If there are no records in the cart table, do nothing.|

