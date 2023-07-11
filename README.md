# Order Management API

This is a simple Order Management API project.

## Prerequisites

Make sure you have the following tools installed:

- Docker
- Docker Compose

## Getting Started

To run the Order Management API locally, follow these steps:

1. Clone the repository:

   ```bash
   git clone https://github.com/your-username/OrderManagementAPI.git
   ```
   
2. Navigate to the project directory:

   ```bash
   cd OrderManagementAPI
   ```
   
3. Open the src/main/resources/application.properties file and modify the following properties:
   ```properties
   spring.mail.username=your_mail_username
   spring.mail.password=your_mail_password
   ```

4. Build and start the Docker containers using Docker Compose:

    ```bash
    docker-compose up -d
   ```
This command will build the Docker images and start the containers for the API, database, and Swagger UI.

Once the containers are up and running, you can access the API documentation using Swagger UI:

Open your web browser and go to: http://localhost:8080/swagger-ui.html

You will see the Swagger UI interface with the available routes and their descriptions. You can test the API endpoints directly from the Swagger UI.

To stop the containers, run the following command:

```bash
docker-compose down
```

## API Endpoints
The following are the available API endpoints:

GET /orders: Get all orders.

GET /orders/{id}: Get an order by ID.

POST /orders: Create a new order.

PUT /orders/{id}: Update an existing order.

DELETE /orders/{id}: Delete an order.

GET /items: Get all items.

GET /items/{id}: Get an item by ID.

POST /items: Create a new item.

PUT /items/{id}: Update an existing item.

DELETE /items/{id}: Delete an item.

GET /users: Get all users.

GET /users/{id}: Get a user by ID.

POST /users: Create a new user.

PUT /users/{id}: Update an existing user.

DELETE /users/{id}: Delete a user.

GET /stock: Get all stock movements.

GET /stock/{id}: Get a stock movement by ID.

POST /stock: Create a new stock movement.

PUT /stock/{id}: Update an existing stock movement.

DELETE /stock/{id}: Delete a stock movement.

You can use the Swagger UI interface mentioned earlier to test these endpoints.
