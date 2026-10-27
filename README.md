# RSLV

## Vision

> This project is a backend API containing fitness exercises.
> Every exercise contains information about it, a gif on how it is performed and instructions on how it is performed.
> The goal is to provide enough data for a beginner to understand and execute without risk of injury.

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

Describe the overall structure of the system.

Example:

This project is built as a layered backend architecture using:

- Controller layer (REST endpoints)
- Service layer (business logic and Validation)
- DAO layer (database access)

Technologies used:

- Java
- Javalin
- JPA / Hibernate
- PostgreSQL
- Maven
- JWT authentication

---

## Architecture Diagram

(Optional but recommended)

You can include a simple diagram showing the main components.

Example:
client - rest controller - service - repository - database

Or include an image if you created a diagram.

---

## Key Design Decisions

Describe important technical decisions you made.

Examples:

- Why you chose your project structure
- How authentication works
- How errors are handled
- Why you chose specific libraries or frameworks

Example:

**Authentication**

Authentication is implemented using JWT tokens.  
Users log in and receive a token which must be included in subsequent API requests.

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

Briefly describe the main entities in your system.

### User

Represents a registered user in the system.

Fields:

- id
- username
- password
- role

### Activity

Represents a recorded workout activity.

Fields:

- id
- userId
- date
- description

---

# API Documentation

Describe the most important endpoints.

## Example Endpoints

### Create User

# User Stories

List the most important user stories for the project.

Example:

- As a user, I want to register an account.
- As a user, I want to log in and receive a token.
- As a user, I want to record a workout activity.
- As a user, I want to view my activity history.

---

# Development Notes

(Optional section)

Here you can briefly describe important challenges or lessons learned during development.

Examples:

- Handling authentication with JWT
- Designing the database schema
- Structuring the service layer
- Testing the API
