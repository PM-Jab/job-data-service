# Job Data Service

A Spring Boot REST API for querying salary survey data. Supports filtering by salary range, job title, and gender, with optional column selection and sorting.

---

## Tech Stack

- Java 17
- Spring Boot 4.0.3
- Spring Data JPA + Hibernate
- PostgreSQL 16
- Lombok
- Docker / Docker Compose

---

## Getting Started

### Run with Docker Compose (recommended)

```bash
docker compose up --build
```

This starts both the PostgreSQL database and the Spring Boot app.

| Service  | Port |
|----------|------|
| App      | 8080 |
| Postgres | 5432 |

### Run locally

Requires a running PostgreSQL instance with:

| Property | Value      |
|----------|------------|
| Database | `jobdb`    |
| Username | `jobuser`  |
| Password | `jobpassword` |

First running database on terminal

```bash
docker compose up postgres
```

Next inserting mock data

```bash
psql -h localhost -U jobuser -d jobdb -f src/main/resources/db/mock_data.sql
```

Lastly run local server

```bash
mvnw spring-boot:run
```

---

## API

### `GET /job_data`

Returns a list of job salary records. All parameters are optional.

#### Query Parameters

| Parameter       | Type   | Description                                      | Example                     |
|-----------------|--------|--------------------------------------------------|-----------------------------|
| `fields`        | string | Comma-separated columns to return                | `job_title,salary,gender`   |
| `salary[gte]`   | number | Minimum salary filter                            | `50000`                     |
| `salary[lte]`   | number | Maximum salary filter                            | `150000`                    |
| `job_title`     | string | Partial match on job title (case-insensitive)    | `Engineer`                  |
| `gender`        | string | Exact match: `Male` or `Female`                  | `Female`                    |
| `sort`          | string | Field to sort by (snake_case)                    | `job_title`                 |
| `sort_type`     | string | Sort direction: `ASC` or `DESC` (default: `ASC`) | `DESC`                      |

#### Available `fields` values

`timestamp`, `employer`, `location`, `job_title`, `years_at_employer`, `years_of_experience`, `salary`, `signing_bonus`, `annual_bonus`, `annual_stock_value_bonus`, `gender`, `additional_comments`

#### Example Requests

```
GET /job_data
GET /job_data?fields=job_title,salary,gender
GET /job_data?salary[gte]=100000&salary[lte]=200000
GET /job_data?job_title=Engineer&gender=Female
GET /job_data?salary[gte]=80000&fields=job_title,salary&sort=salary&sort_type=DESC
```

#### Success Response `200 OK`

```json
{
  "statusCode": "200",
  "message": "Success",
  "count": 2,
  "data": [
    {
      "Job Title": "Software Engineer",
      "Salary": "120000.0",
      "Gender": "Female"
    },
    {
      "Job Title": "Data Engineer",
      "Salary": "115000.0",
      "Gender": "Female"
    }
  ]
}
```

> Fields not requested are omitted from each record (they will not appear as `null`).

#### Error Response `400 Bad Request`

```json
{
  "statusCode": "400",
  "message": "Validation failed",
  "count": 0,
  "data": [
    "getJobs.gender: gender must be one of: Male, Female"
  ]
}
```

---

## Running Tests

```bash
./mvnw test
```

---

## Project Structure

```
src/
├── main/java/com/ata/job/
│   ├── constant/          # JobConstants, ErrorMapperConstants
│   ├── controller/        # JobController, GlobalExceptionHandler
│   ├── model/             # JobRequestParam, JobResponseBody, ApiResponse
│   ├── repository/        # JobRepository, JobSpecification, entity/Job
│   └── service/           # JobService
└── main/resources/
    ├── application.properties
    └── db/mock_data.sql   # ~3,700 rows from salary survey CSV
```
