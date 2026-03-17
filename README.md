# Ecommerce Backend API

A RESTful backend service for an Ecommerce application built using Java Spring Boot and MySQL.
This backend provides APIs for user authentication, product management, cart operations, and order processing.

## Project Overview

This backend is designed using Spring Boot layered architecture to maintain clean and scalable code structure.

The system supports two roles:

Admin → Manage products and users

User → Browse products, manage cart, place orders

## Tech Stack
Technology	Purpose
Java 17	Programming Language
Spring Boot	Backend Framework
Spring Data JPA	ORM & Database Access
Spring Security	Authentication & Authorization
Hibernate	ORM Implementation
MySQL	Database
Maven	Dependency Management
REST APIs	Communication
## Project Structure
src/main/java/com/ecommerce

```text
src
└── main
    └── java
        └── com
            └── ecommerce
                ├── controller
                ├── service
                ├── repository
                ├── entity
                ├── config
                └── EcommerceApplication.java
```
## Features
## User Features

User Registration

Secure Login

View Products

Add Products to Cart

Remove Products from Cart

Place Orders

## Admin Features

Add New Products

Update Product Details

Delete Products

View All Users

Manage Orders

## Installation & Setup
1️ Clone the Repository.

-git clone https://github.com/TejasPatil-1947/Ecommerce.git

2 Navigate to Project Directory

-cd ecommerce-backend

3️ Configure Database.

Update application.properties

&nbsp;&nbsp;&nbsp;-spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce

&nbsp;&nbsp;&nbsp;-spring.datasource.username=root

&nbsp;&nbsp;&nbsp;-spring.datasource.password=password

&nbsp;&nbsp;&nbsp;-spring.jpa.hibernate.ddl-auto=update

&nbsp;&nbsp;&nbsp;-spring.jpa.show-sql=true

4️ Run the Application

Using Maven:

&nbsp;&nbsp;&nbsp;-mvn spring-boot:run

Or run the main class:

EcommerceApplication.java

## Base API URL
&npsb;&npsb;&npsb;-http://localhost:8080/

## API Endpoints
Authentication APIs
| Method | Endpoint         | Description       |
| ------ | ---------------- | ----------------- |
| POST   | `/auth/register` | Register new user |
| POST   | `/auth/login`    | Login user        |

Product APIs
| Method | Endpoint         | Description                  |
| ------ | ---------------- | ---------------------------- |
| GET    | `/products`      | Get all products             |
| GET    | `/products/{id}` | Get product by ID            |
| POST   | `/products`      | Add new product (Admin only) |
| PUT    | `/products/{id}` | Update product               |
| DELETE | `/products/{id}` | Delete product               |

Cart APIs
| Method | Endpoint            | Description         |
| ------ | ------------------- | ------------------- |
| POST   | `/cart/add`         | Add product to cart |
| GET    | `/cart/{userId}`    | Get user cart       |
| DELETE | `/cart/remove/{id}` | Remove cart item    |

Order APIs
| Method | Endpoint                                  | Description                                | Access |
| ------ | ----------------------------------------- | ------------------------------------------ | ------ |
| POST   | `/order/create/{userId}`                  | Create order for user and initiate payment | User   |
| POST   | `/order/verify`                           | Verify Razorpay payment after transaction  | User   |
| GET    | `/order/{orderId}`                        | Get order details by order ID              | User   |
| GET    | `/order/user/{userId}`                    | Get all orders of a specific user          | User   |
| GET    | `/order/allOrders`                        | Get all orders in the system               | Admin  |
| PUT    | `/order/update/{orderId}?status={status}` | Update order status                        | Admin  |
| DELETE | `/order/delete/{orderId}`                 | Delete an order                            | Admin  |

Category APIs
| Method | Endpoint                             | Description                                               | Access |
| ------ | ------------------------------------ | --------------------------------------------------------- | ------ |
| POST   | `/category/create`                   | Create a new category                                     | Admin  |
| GET    | `/category/{id}`                     | Get category by ID                                        | Public |
| GET    | `/category/`                         | Get all categories                                        | Public |
| GET    | `/category/name?name={name}`         | Get category by name                                      | Public |
| PUT    | `/category/deactivate/{oldC}/{newC}` | Deactivate category and move products to another category | Admin  |
| PUT    | `/category/activate/{id}`            | Activate a category                                       | Admin  |

Wishlist APIs
| Method | Endpoint                                | Description                  | Access |
| ------ | --------------------------------------- | ---------------------------- | ------ |
| POST   | `/wishlist/add/{userId}/{productId}`    | Add product to wishlist      | User   |
| GET    | `/wishlist/user/{userId}`               | Get wishlist of user         | User   |
| DELETE | `/wishlist/remove/{userId}/{productId}` | Remove product from wishlist | User   |

User APIs
| Method | Endpoint                      | Description         | Access |
| ------ | ----------------------------- | ------------------- | ------ |
| GET    | `/user/{userId}`              | Get user by ID      | User   |
| GET    | `/user/`                      | Get all users       | Admin  |
| GET    | `/user/byEmail?email={email}` | Get user by email   | Admin  |
| PUT    | `/user/update/{userId}`       | Update user details | User   |
| DELETE | `/user/delete/{userId}`       | Delete user         | Admin  |

## Security

The application uses Spring Security for authentication and authorization.

Role Based Access:

&nbsp;&nbsp;&nbsp;-ROLE_ADMIN

&nbsp;&nbsp;&nbsp;-ROLE_USER

## API Testing

You can test APIs using:

&nbsp;&nbsp;&nbsp;-Postman

&nbsp;&nbsp;&nbsp;-Thunder Client

&nbsp;&nbsp;&nbsp;-Swagger

## Deployment

Backend deployed on Railway

Example:

ecommerce-production-744f.up.railway.app
## Author

Tejas Patil

Java Backend Developer
Spring Boot | REST APIs | MySQL

## Support

If you found this project helpful:

⭐ Star the repository
🍴 Fork the project
