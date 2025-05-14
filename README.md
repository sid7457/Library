# 📚 Library Management System

A Spring Boot based RESTful web application to manage library resources such as Books, Authors, and Users. It supports role-based user management (Member, Librarian, Admin), book CRUD operations, and advanced filtering via REST APIs.

---

## 🛠️ Tech Stack

- **Java 21**
- **Spring Boot 3 / 6**
- **Spring Data JPA**
- **Hibernate Validator (Jakarta Validation)**
- **Lombok**
- **MySQL**
- **Swagger/OpenAPI**
- **Gradle (or Maven)**

---

## 📦 Features

- CRUD operations for:
    - Books
    - Authors
    - Users
- Role-based user model (`MEMBER`, `LIBRARIAN`, `ADMIN`)
- Filtering & Pagination for books
- Validation using Hibernate Validator
- Swagger UI for interactive API docs
- Basic Rate Limiting (via Filter)

---

## 🚀 Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/your-username/library-management.git
cd library-management
```

### Project Structure
```
src/main/java/com/example/Library/
├── controller/
├── service/
├── repository/
├── dto/
├── model/
└── config/
```
## Sample API Requests (cURL)
### Add Users (POST)
```
curl -X POST http://localhost:8080/api/users \
-H "Content-Type: application/json" \
-d '[
  {"name": "Alice", "email": "alice@example.com", "role": "MEMBER"},
  {"name": "Bob", "email": "bob@example.com", "role": "LIBRARIAN"}
]'
```
### Get Books with Filters (GET)
```
curl -X GET "http://localhost:8080/api/books?author=J.K.Rowling&publishedYear=2007&availabilityStatus=AVAILABLE&page=0&size=10"
```

### Add a Book (POST)
```
curl -X POST http://localhost:8080/api/books \
-H "Content-Type: application/json" \
-d '{
  "title": "Harry Potter and the Deathly Hallows",
  "isbn": "9780545010221",
  "publishedYear": 2007,
  "authorId": 1,
  "genre": "Fantasy",
  "description": "Final book in the Harry Potter series",
  "availabilityStatus": "AVAILABLE"
}'

```

### Update Book Availability (PATCH)
```
curl -X PATCH http://localhost:8080/api/books/1/availability \
-H "Content-Type: application/json" \
-d '{"availabilityStatus": "BORROWED"}'
```

### Delete a User (DELETE)
```
curl -X DELETE http://localhost:8080/api/users/2
```

## Swagger Documentation
http://localhost:8080/swagger-ui/index.html
