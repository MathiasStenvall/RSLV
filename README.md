# RSLV

## Vision

This project is a backend API containing fitness exercises.
Every exercise contains information about it, a gif on how it is performed and instructions on how it is performed.
The goal is to provide enough data for a beginner to understand and execute exercises while minimizing risk of injury.

---

## Links

Deployed application (optional):  
https://rslv.mstenvall.dk/api/v1

Source code repository:  
https://github.com/MathiasStenvall/RSLV/tree/main/src

Portfolio website:  
https://mathiasstenvall.github.io/portfolio/

---

# Architecture

## System Overview

This project's structure is divided into several layers, each having a single clear purpose:

- Controller layer (REST endpoints)
- Service layer (security, services and validation)
- DAO layer (database access)

Technologies used:

- Java
- Javalin
- JPA / Hibernate
- PostgreSQL
- Maven
- JWT authentication
- Lombok
- JUnit
- Hamcrest
- REST-assured
- Jackson
- SLF4J
- BCrypt

---

## Architecture Diagram

The overall flow of this project goes as follows:

A client sends a request to an endpoint, which the corresponding controller handles. Depending on the endpoint, the request will be validated and will require JWT authentication and role authorization.
If the validations pass, the controller uses the corresponding DAO class to access the database and perform the desired operation. 
The client then receives an HTTP response, which contains the result message or an error message.

---

## Key Design Decisions

While decisions of all sizes were made under the development, some of the key decisions include:

**Structure**
Classes are separated by responsibility.
This improves modularity and maintainability, and gives a better overview of the project.

**Database**
Exercise information is sometimes shared, and I didn't want to flood my database tables with duplicates.
For this reason my exercise class has several ManyToMany relations. This keeps the tables clean, at the cost of having more tables (connecting tables).

**Security**
Authentication is implemented with JWT tokens, and authorization is based on the user's role.
Whenever a user logs in, they receive a time-limited token based on their role. If they have a valid token with the required permissions, they can access endpoints that modify data.

**Exceptions**
Exceptions are handled with an exception mapper.
This class contains the most thrown errors, alongside a catch-all for unexpected exceptions.
Each exception is logged, and an error message is returned to the client.

**Test**
Tests are covered with an HTTP request file, JUnit and REST-assured.
These tests verify expected behaviour, and run automatically in the CI/CD pipeline.
In order to completely separate my tests classes from my deployed project, they use their own "EntityManagerFactory" and "secret".

---

# Data Model

## ERD

Insert your Entity Relationship Diagram here.

Example entities:

- User
- Activity
- Workout
- Exercise

You may include:

- an image
- a link to a diagram
- or a textual explanation

---

## Important Entities

The two main classes in this project are:

### User

Represents a registered user in the system.

Fields:

- id
- name
- age
- role
- email
- password
- phoneNumber
- sex
- height
- currentWeight
- signupDate

### Exercise

Represents an exercise in the system.

Fields:

- id
- exerciseId
- name
- gifUrl
- bodyParts
- targetMuscles
- secondaryMuscles
- equipments
- instructions

---

# API Documentation

Describe the most important endpoints.

## Example Endpoints

### Create User

# User Stories

The main user stories for this project are:

- As a user, I want to register an account.
- As a user, I want to log in and receive a token.
- As a user, I want browse exercises with an id-based pagination.
- As a user, I want to search a specific exercise by id.
- As an admin, I want to add a new exercise to the database.
- As an admin, I want to change an existing exercise.
- As an admin, I want to delete an existing exercise from the database.

---

# Development Notes

(Optional section)

Here you can briefly describe important challenges or lessons learned during development.

Examples:

- Handling authentication with JWT
- Designing the database schema
- Structuring the service layer
- Testing the API
