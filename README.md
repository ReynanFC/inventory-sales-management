# Inventory Sales Management

Backend system for **inventory and sales management**, built with **Spring Boot**.

The project provides a REST API responsible for managing products, sales, and inventory operations, following good backend architecture practices.

---

# 📚 Table of Contents

- [About the Project](#about-the-project)
- [Technologies](#technologies)
- [Requirements](#requirements)
- [Database Configuration](#database-configuration)
- [Running the Project](#running-the-project)
- [API Documentation](#api-documentation)
- [Project Structure](#project-structure)
- [UML Diagram](#uml-diagram)

---

# 📖 About the Project

This project was created to simulate a **real inventory and sales management system**, focusing on backend architecture and best development practices.

The API handles:

- Product management
- Inventory control
- Sales registration
- Data persistence
- Database versioning

---

# 🛠 Technologies

The project was built using the following technologies:

- **Java 21**
- **Spring Boot**
- **Spring Data JPA**
- **MySQL**
- **Flyway (Database Versioning)**
- **MapStruct (Object Mapping)**
- **OpenAPI / Swagger (API Documentation)**
- **Maven**

---

# 📋 Requirements

Before running the project, make sure you have installed:

- **Java 21**
- **Maven**
- **MySQL Server**

---

# 🗄 Database Configuration

This project uses **MySQL** as the database.

You have two options:

### Option 1 — Create the same database used in the project

Create a database with the name:

```sql
CREATE DATABASE inventory_sales;
```
### Option 2 — Change the database name in the project

Open the file:

```plaintext
src/main/resources/application.yml
```

## And modify the database configuration:

```plaintext
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/inventory_sales
    username: YOUR_USERNAME
    password: YOUR_PASSWORD
```
You can change:

- database name

- username

- password

according to your local MySQL configuration.

## ⚠️ Database Naming Note

In this project, the database attributes were created using **uppercase names**.

This happened because the schema was initially created this way, and it was only noticed when the project was already close to completion and fully mapped using **JPA/Hibernate**.

Although the project works correctly, the recommended best practice is to use **camelCase** (or snake_case) for database columns to keep naming conventions consistent and improve readability.

Because of this, the following configuration was added in `application.yml`:

```yaml
spring:
  jpa:
    hibernate:
      naming:
        physical-strategy: org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
```

This configuration tells Hibernate to use the exact names defined in the entities, preventing it from automatically converting attribute names to another naming strategy.
# 🚀 Running the Project

**Clone the repository:**

```plaintext
git clone https://github.com/your-username/inventory-sales-management.git
```
**Enter the project folder:**
```plaintext
cd inventory-sales-management
```
**Build the project:**
```plaintext
mvn clean install
```
**Run the application:**
```plaintext
mvn spring-boot:run
```
The API will start at: [http://localhost:8080]

# 📄 API Documentation

Swagger UI is available at: [http://localhost:8080/swagger-ui/index.html]

**Through Swagger you can:**

- View all endpoints

- Test API requests

- Inspect request and response models

# 📁 Project Structure

The project follows a layered architecture commonly used in Spring Boot applications.

```plaintext
src
 └─ main
     └─ java
         └─ com.project.inventory
             ├─ controller
             │
             ├─ service
             │
             ├─ repository
             │
             ├─ entity
             │
             ├─ dto
             │
             ├─ mapper
             │
             └─ exception

     └─ resources
         ├─ application.yml
         └─ db
             └─ migration (Flyway migrations)
```
## Layers

**Controller:** responsible for handling HTTP requests and returning responses.

**Service:** contains the business logic of the application.

**Repository:** handles database access using Spring Data JPA.

**DTO:** Objects used for API input and output.

**Mapper:** uses MapStruct to convert between Entity ↔ DTO.

**Exception:** centralized exception handling for the API.

# 📊 UML Diagram

The following UML diagram represents the main entities and relationships used in the system.
<img width="1500" height="604" alt="uml" src="https://github.com/user-attachments/assets/4e2312ad-1cdd-478c-8ede-36f41ca875b3" />

# 👨‍💻 Author

Developed for study and backend architecture practice using Spring Boot.