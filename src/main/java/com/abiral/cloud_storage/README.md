# Cloud File Storage API

A REST API built with Java and Spring Boot for uploading, listing, and deleting files stored on AWS S3.

## Tech Stack
- Java 26
- Spring Boot 3.5
- AWS SDK v2
- AWS S3

## Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /files/upload | Upload a file to S3 |
| GET | /files/list | List all files in bucket |
| DELETE | /files/delete/{fileName} | Delete a file from S3 |

## Setup
1. Clone the repo
2. Set environment variables:
   - AWS_ACCESS_KEY
   - AWS_SECRET_KEY
   - AWS_BUCKET_NAME
3. Run: `mvn spring-boot:run`

## Security
Credentials are managed via environment variables. Never hardcode AWS keys.