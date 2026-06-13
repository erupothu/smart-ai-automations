# smart-users-custome-mcp

Independent Spring Boot Maven microservice for user management with:

- REST CRUD APIs for users
- H2 database for primary relational persistence via JPA
- MongoDB localhost integration for audit trail storage
- real MCP server over Streamable HTTP for user tools
- Swagger UI for API exploration

## Tech stack

- Java 17
- Spring Boot 3.3.5
- Spring Web
- Spring Data JPA
- Spring Data MongoDB
- H2 Database
- Maven

## Run locally

Make sure MongoDB is running on localhost.

The project uses `.env` with:

- `MONGODB_URI=mongodb://localhost:27017/smart_users_mcp`

Start the service with Maven, then open:

- `http://localhost:8082/swagger-ui.html`
- `http://localhost:8082/h2-console`
- `http://127.0.0.1:8082/mcp`

## REST APIs

- `GET /api/users`
- `GET /api/users/{id}`
- `POST /api/users`
- `PUT /api/users/{id}`
- `DELETE /api/users/{id}`

## MCP server

The service now exposes a real MCP server over Streamable HTTP at:

- `http://127.0.0.1:8082/mcp`

Published MCP tools:

- `list_users`
- `get_user_by_id`
- `create_user`
- `update_user`
- `delete_user`

## Sample request body

{
  "firstName": "Harish",
  "lastName": "Kumar",
  "email": "harish@example.com",
  "mobileNumber": "9876543210",
  "active": true
}
