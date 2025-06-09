# 🍽️ Restaurant Finder

A simple REST API for searching the best-matched restaurants based on various search criteria. Built with Kotlin, Spring Boot, and Clean Architecture.

---

## 🚀 How to Run Locally

### ✅ Prerequisites

- JDK 21+
- [Gradle](https://gradle.org/install/) 8.14 or use the Gradle Wrapper
- Docker (optional)

---

### 🐳 Run with Docker Compose
To run the project with docker compose, execute the following command:
``` bash
    docker compose up --build
```

### 🐳 Run with Docker
First, build the Docker image:
``` bash
    docker build -t restaurant-app .
```
Then, run it:
``` bash
    docker run -p 8080:8080 restaurant-app
```

---

### 📦 Run with Gradle (locally)

``` bash
    ./gradlew bootRun
```
Application will be available at: http://localhost:8080

---

### 📂 CSV Files
The application expects two CSV files located in `src/main/resources/csv`:
- restaurant.csv
- cuisine.csv

These files are loaded into memory on application startup.

---

### 🌐 Static HTML Frontend
A simple static HTML file is included to allow users to query the API via a web interface.

#### Address
To access the frontend, access the address bellow when the application is running
```declarative
http://localhost:8080/index.html
```

#### Location
The file is located at:
```declarative
src/main/resources/static/index.html
```
#### Features
- Form to input search parameters (Name, Rating, Distance, Price, Cuisine)
- Fetches and display results from the `/restaurants/search` endpoint
- Fully static: no build tools needed, only HTML + JavaScript

---

### 📡 API Endpoints
#### 🔍 Search Restaurants
*GET* /restaurants/search

Query Parameters (all optional):

| Parameter        | Description                             | Example |
|------------------| --------------------------------------- | ------- |
| `name`           | Full or partial name of the restaurant  | `Mcd`   |
| `customerRating` | Minimum customer rating                 | `3`     |
| `distance`       | Maximum distance in miles               | `5`     |
| `price`          | Maximum price per person                | `20`    |
| `cuisine`        | Full or partial name of the cuisine     | `Chi`   |

Request Example:
```declarative
GET /restaurants/search?name=Chi&customerRating=3&distance=5&price=20
```
Example CURL:
```bash
  curl "http://localhost:8080/restaurants/search?name=Chi&customerRating=5&price=45&distance=5&cuisine=viet"
```

Response:
``` json
[
  {
    "name": "Chili Garden",
    "customerRating": 4,
    "distance": 2,
    "price": 18,
    "cuisine": "Chinese"
  },
  ...
]
```
- Returns up to 5 best-matched results
- Sorted by:
  1. Distance (ascending)
  2. Customer Rating (Descending)
  3. Price (ascending)

---

### 🧪 Run Tests
```bash
    ./gradlew test
```

---

### 🛠 Assumptions

- CSV files are well-formated and do not contain duplicates
- Each restaurant is associated with a single cuisine
- The search parameters have an "AND" relationship