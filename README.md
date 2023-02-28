# Spring Boot Lesson

## Quick Start

To run the project:

Step 1: CD to the project root directory `cart_api`

Step 2: Run `./mvnw spring-boot:run` command

## Lesson Overview (3 Mar 2023, Fri)

Lesson Coverage:

- Pros & Cons of Database Access Layer?
- Implement `@Entity` class which represents a table in database
- Implement an interface and extends `JpaRepository`
- Get started on implementing RESTful APIs

## Pros & Cons of DAL (Data Access Layer)

|Pros|Cons|
|-|-|
|Ability to switch database without changing codebase.|May face limitation in constructing complex query.|
|To overcome complex query, there are multiple approaches to query database. Some of them brings unique purpose to add value to developers by helping developer to focus on business logics.|To overcome complex query, there are multiple approaches to query database using code which can be confusing to new learners.|

### Three Way to Query DB

1. Using JpaRepository that automatically maps method name to DB query.
    ```java
    public List<Product> findByNameLike(String name); 
    public Optional<Product> findById(long id);
    ```
    More examples [here](https://javatute.com/jpa/how-to-write-custom-method-in-repository-in-spring-data-jpa/)

    Observation(s):
    - JpaRepository save developer a lot of time in constructing common queries.
    - There will be limitation when faced with multiple entity joins query


2. Using @Query notation and write JPQL
    ```java
    @Query("select p from Product p where p.price > ?1")
    public List<Product> findExpensiveProducts(float price);
    ```
    More info [here](https://www.baeldung.com/spring-data-jpa-query)

    Observation(s):
    - JPQL is Java Persistent Query Language, where it will be translated to the native query language by the DAL.
    - We should not write native query because it defeat the purpose of using DAL to provide a layer of abstraction that takes care of agnostic DB queries.


3. Using `CriteriaBuilder`
    ```java
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Product> cq = cb.createQuery(Product.class);

    Root<Product> product = cq.from(Product.class);
    Predicate expensiveProductPredicate = cb.greaterThan(product.get(price));
    Predicate nameLikePredicate = cb.like(product.get("name"), "%" + pName + "%");
    cq.where(expensiveProductPredicate, nameLikePredicate);

    TypedQuery<Product> query = em.createQuery(cq);
    return query.getResultList();
    ```
    More info [here](https://www.baeldung.com/spring-data-criteria-queries)

    Observation(s):
    - The code is complex to use
    - Yet, provide high flexibility in build queryies dynamically

## RESTful APIs

|Path|Verb|Has Request Body|Has Response Body|Purpose|
|-|-|-|-|-|
|/entities|GET|NO|YES|Get all entities (usually with pagination and sorting query parameters)|
|/entities/{id}|GET|NO|YES|Get one entity by id|
|/entities|POST|Yes|YES|Create an entity|
|/entities/{id}|PUT|YES|YES|Update an entity|
|/entities/{id}|DELETE|NO|NO|Delete an entity|