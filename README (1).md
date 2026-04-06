# 💰 Finance Dashboard API



A **Spring Boot backend application** that manages financial transactions using **JWT authentication** and **role-based access control (RBAC)**.
It also provides **dashboard analytics** such as income, expenses, category insights, and trends.

---

# 🚀 Features

## 🔐 Authentication & Authorization

* JWT-based login system
* Role-based access:

  * **Viewer** → View own data, update own
  * **Analyst** → View all data
  * **Admin** → Full access

---

## 📊 Transaction Management

* Create transaction
* View transactions
* Update transaction
* Soft delete transaction
* Filter by:

  * Type
  * Category
  * Date range

---

## 📈 Dashboard Analytics

* Total income
* Total expenses
* Net balance
* Category-wise totals
* Monthly trends
* Recent transactions

---


---

# 🏗️ Project Structure



```
src/main/java/com/zorvyn/
│
├── Modules/
│   ├── Auth/           # Authentication & JWT (login/signup)
│   ├── Dashboard/      # Dashboard APIs (analytics)
│   ├── Report/         # Transaction management
│   ├── User/           # User management
│   ├── Security/       # JWT filter & user details
│   └── Shared/         # Enums, exceptions, utilities
│
├── AssignmentApplication.java   # Main Spring Boot app
├── DataInitializer.java         # Initial data setup (optional)
│
├── resources/
│   ├── application.properties   # Configurations
│
└── test/
```

---

# ⚙️ Tech Stack

* Java 17
* Spring Boot
* Spring Security (JWT)
* Spring Data JPA
* MySQL
* Lombok
* Postman

---

# 🔑 API Documentation

---

# 🔐 AUTH APIs

## 1. Signup

**POST** `/auth/signup`

### Request

```json
{
  "name": "Hardik",
  "email": "hardik@gmail.com",
  "password": "123456",
  "role": "VIEWER"
}
```

### Response

```json
{
  "message": "User registered successfully"
}
```

---

## 2. Login

**POST** `/auth/login`

### Request

```json
{
  "email": "hardik@gmail.com",
  "password": "123456"
}
```

### Response

```json
{
  "token": "jwt_token_here"
}
```

---

# 👤 USER APIs (Admin Only)

## 1. Get All Users

**GET** `/users`

---

## 2. Get User by ID

**GET** `/users/{id}`

---

## 3. Update User

**PUT** `/users/{id}`

---

## 4. Delete User

**DELETE** `/users/{id}`

---

# 📊 TRANSACTION APIs

## 1. Create Transaction

**POST** `/transactions`

### Request

```json
{
  "amount": 5000,
  "type": "INCOME",
  "category": "Salary",
  "date": "2025-04-06",
  "notes": "Monthly salary"
}
```

### Response

```json
{
  "id": 1,
  "amount": 5000,
  "type": "INCOME",
  "category": "Salary",
  "date": "2025-04-06",
  "notes": "Monthly salary",
  "createdByName": "Admin"
}
```

---

## 2. Get All Transactions

**GET** `/transactions`

### Role Behavior

* Viewer → Own transactions
* Analyst → All transactions
* Admin → All transactions

---

## 3. Get Transaction by ID

**GET** `/transactions/{id}`

---

## 4. Update Transaction

**PUT** `/transactions/{id}`

### Role Rules

* Viewer → Own only
* Admin → All

---

## 5. Delete Transaction

**DELETE** `/transactions/{id}`

### Rule

* Admin only
* Soft delete (not removed from DB)

---

## 6. Filter Transactions

**GET** `/transactions/filter`

### Query Params

```
type=INCOME
category=Food
from=2025-01-01
to=2025-12-31
```

---

# 📊 DASHBOARD API

## Get Dashboard Data

**GET** `/dashboard`

### Response

```json
{
  "totalIncome": 15000,
  "totalExpense": 3000,
  "netBalance": 12000,
  "categoryTotals": {
    "Food": 1500,
    "Salary": 15000
  },
  "monthlyTrends": {
    "APRIL": 18000
  },
  "recentTransactions": []
}
```

---



---

# 🔐 Authorization Rules

| Role    | Create | View | Update | Delete |
| ------- | ------ | ---- | ------ | ------ |
| Viewer  | ✅      | Own  | Own    | ❌      |
| Analyst | ✅      | All  | ❌      | ❌      |
| Admin   | ✅      | All  | All    | ✅      |

---

# 🔒 Security

All APIs require JWT token:

```
Authorization: Bearer <token>
```

---

# 🧪 Testing

Use Postman:

1. Login → get token
2. Add token in headers
3. Call APIs

---

# 📄 Documentation Link

(Add your Postman Documentation link here)

---

# 🧠 Key Concepts

* Layered Architecture
* DTO Pattern
* Soft Delete
* RBAC (Role-Based Access Control)
* Aggregation & Analytics

---

# 🚀 Future Improvements

* Pagination
* Swagger documentation
* Caching
* Frontend UI

---

# 👨‍💻 Author

Tanishq Mahey

---



## API Reference

#### Get all items

```http
  GET /api/items
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `api_key` | `string` | **Required**. Your API key |

#### Get item

```http
  GET /api/items/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of item to fetch |

#### add(num1, num2)

Takes two numbers and returns the sum.

