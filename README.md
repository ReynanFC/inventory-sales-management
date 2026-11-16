# Inventory Sales Management

REST API for **retail inventory and sales**, built with **Java 21 and Spring Boot**. The focus is on business rules and data consistency: every stock change is recorded as a movement, sales follow a status flow, and canceling a sale puts the items back in stock inside the same transaction.

---

## 📚 Table of Contents

- [Business Rules](#-business-rules)
- [Technologies](#-technologies)
- [Endpoints](#-endpoints)
- [Running the Project](#-running-the-project)
- [Project Structure](#-project-structure)
- [Database](#-database)
- [UML Diagram](#-uml-diagram)
- [Known Limitations and Next Steps](#-known-limitations-and-next-steps)

---

## 📏 Business Rules

- **Stock is never edited directly.** Every change is a `StockMovement` (`ENTRY` or `EXIT`), so each product keeps a full history that can be queried.
- **A sale cannot leave stock negative.** When a sale is created, each item generates an `EXIT` movement; if there is not enough stock, the whole sale is rolled back.
- **The price is frozen at sale time.** Each `SaleItem` stores the product price when the sale was made, so later price changes don't affect old sales.
- **Sales follow a status flow:** `PENDING → COMPLETED` or `PENDING → CANCELED`. Any other transition is rejected.
- **Canceling a sale returns the items to stock** through `ENTRY` movements, in the same transaction as the status change.
- **Errors are handled in one place** (`GlobalExceptionHandler`), with a standard error body for not-found resources, validation errors and stock errors.

---

## 🛠 Technologies

- **Java 21** and **Spring Boot 4**
- **Spring Data JPA / Hibernate**
- **MySQL**
- **Flyway** for database migrations
- **MapStruct** for Entity ↔ DTO mapping
- **Bean Validation** for request validation
- **OpenAPI / Swagger** for API documentation
- **Maven** (wrapper included)

---

## 🔌 Endpoints

Product, customer and sale lists are paginated (`?page=0&size=10`).

| Resource | Method | Path | Description |
|---|---|---|---|
| Categories | `GET` | `/api/categories` | List categories |
| | `GET` | `/api/categories/{id}` | Find by id |
| | `POST` | `/api/categories` | Create |
| | `PUT` | `/api/categories/{id}` | Update |
| | `DELETE` | `/api/categories/{id}` | Delete |
| Products | `GET` | `/api/products` | List products |
| | `GET` | `/api/products/{id}` | Product details |
| | `GET` | `/api/products/by-category?categoryId={id}` | Products in a category |
| | `GET` | `/api/products/{id}/stock-movements` | Stock movement history |
| | `POST` | `/api/products` | Create |
| | `POST` | `/api/products/{id}/stock` | Add stock (`ENTRY` movement) |
| | `PUT` | `/api/products/{id}` | Update |
| | `DELETE` | `/api/products/{id}` | Delete |
| Customers | `GET` | `/api/customers` | List customers |
| | `GET` | `/api/customers/{id}` | Find by id |
| | `GET` | `/api/customers/{id}/sales` | Sales of a customer |
| | `POST` | `/api/customers` | Create |
| | `PUT` | `/api/customers/{id}` | Update |
| | `DELETE` | `/api/customers/{id}` | Delete |
| Sales | `GET` | `/api/sales` | List sales |
| | `GET` | `/api/sales/{id}` | Find by id |
| | `POST` | `/api/sales` | Create a sale with items |
| | `PATCH` | `/api/sales/{id}` | Change status (complete or cancel) |

The full contract, with request and response models, is in Swagger once the app is running: <http://localhost:8080/swagger-ui/index.html>

---

## 🚀 Running the Project

### Requirements

- **Java 21**
- **MySQL 8**

### 1. Create the database

```sql
CREATE DATABASE inventory_sales;
```

Flyway creates the tables on startup.

### 2. Set the credentials

The database user and password come from environment variables:

```bash
export DB_USERNAME=root
export DB_PASSWORD=your_password
```

The connection URL is in `src/main/resources/application.yaml` (`jdbc:mysql://localhost:3306/inventory_sales`). Change it there if your MySQL runs on another host, port or database name.

### 3. Run

```bash
git clone https://github.com/ReynanFC/inventory-sales-management.git
cd inventory-sales-management
./mvnw spring-boot:run
```

The API starts at <http://localhost:8080>.

---

## 📁 Project Structure

Layered architecture:

```plaintext
src/main/java/com/reynan/inventorysalesmanagement
 ├─ controllers     HTTP layer: receives requests and returns responses
 ├─ services        business rules and transactions
 ├─ repository      database access with Spring Data JPA
 ├─ entities        JPA entities and enums (SaleStatus, MovementType)
 ├─ dtos            request and response objects
 ├─ mapper          MapStruct mappers (Entity ↔ DTO)
 └─ exceptions      custom exceptions and the global handler

src/main/resources
 ├─ application.yaml
 └─ db/migration    Flyway migrations (V1 to V4)
```

---

## 🗄 Database

The schema is versioned with Flyway, from `V1__create_tables.sql` to `V4__add_default_quantity_in_stock.sql`, and Hibernate runs with `ddl-auto: none`, so the migrations are the only source of truth for the schema.

**Note on column names:** the columns were created in uppercase, and this was only noticed when the entities were already mapped. To keep the names exactly as they are in the entities, the project uses:

```yaml
spring:
  jpa:
    hibernate:
      naming:
        physical-strategy: org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
```

In a new project I would use `snake_case` columns and keep Hibernate's default naming strategy.

---

## 📊 UML Diagram

<img width="1500" height="604" alt="UML diagram" src="https://github.com/user-attachments/assets/4e2312ad-1cdd-478c-8ede-36f41ca875b3" />

---

## 🧭 Known Limitations and Next Steps

- **Automated tests:** the project only has the default context test. Next step: unit tests for the sale status flow and stock rules, and integration tests with Testcontainers + MySQL.
- **Concurrency:** two sales for the same product at the same time could both pass the stock check. Next step: optimistic locking (`@Version`) or a pessimistic lock on the product row.
- **Docker Compose:** add a `compose.yaml` with MySQL so the project runs with one command.

---

## 👨‍💻 Author

**Reynan Ferreira** — [LinkedIn](https://linkedin.com/in/reynan-ferreira-correia-5518a0354) · [GitHub](https://github.com/ReynanFC)
