# Project Name

## Vision

Briefly describe the purpose of your project in 1–2 sentences.

Example:

> This project is a backend API for managing fitness activities.  
> The system allows users to track workouts, store exercise history, and analyse progress over time.

---

## Links

Deployed application (optional):  
<LINK>

Source code repository:  
<LINK>

Portfolio website:  
<LINK>

---

# Architecture

## System Overview

Describe the overall structure of the system.

Example:

This project is built as a layered backend architecture using:

- Controller layer (REST endpoints)
- Service layer (business logic)
- Repository layer (database access)

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
