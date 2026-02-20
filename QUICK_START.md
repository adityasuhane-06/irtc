# Quick Start Guide

## Prerequisites
- Java 21 or higher
- Gradle (included via wrapper)

## Setup & Run

### 1. Build the project
```bash
./gradlew build
```

### 2. Start the server
```bash
./gradlew bootRun
```

The API will be available at `http://localhost:8080`

## Quick Test

### 1. Create a user
```bash
curl -X POST http://localhost:8080/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"test123"}'
```

### 2. Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"test123"}'
```

### 3. Search trains
```bash
curl "http://localhost:8080/api/trains/search?source=CityA&destination=CityB"
```

### 4. Book a ticket
```bash
curl -X POST http://localhost:8080/api/bookings/book \
  -H "Content-Type: application/json" \
  -d '{
    "userId":"user_abc12345",
    "trainId":"T123",
    "source":"CityA",
    "destination":"CityB",
    "row":0,
    "col":0
  }'
```

## Using Postman

1. Import `postman_collection.json` into Postman
2. All endpoints are pre-configured
3. Update variables as needed

## Project Structure

```
├── app/
│   ├── src/main/java/ticket/booking/
│   │   ├── TicketBookingApplication.java  # Main Spring Boot app
│   │   ├── controller/                    # REST controllers
│   │   ├── dto/                           # Data transfer objects
│   │   ├── entities/                      # Domain models
│   │   ├── services/                      # Business logic
│   │   └── Utils/                         # Utilities
│   └── src/main/resources/
│       └── application.properties         # Configuration
├── README.md                              # Full documentation
├── API_DOCUMENTATION.md                   # API reference
└── postman_collection.json                # Postman collection
```

## Available Endpoints

### Authentication
- `POST /api/auth/signup` - Register new user
- `POST /api/auth/login` - Login user

### Trains
- `GET /api/trains/search` - Search trains
- `GET /api/trains/{trainId}` - Get train details
- `GET /api/trains/{trainId}/seats` - Get available seats

### Bookings
- `POST /api/bookings/book` - Book ticket
- `GET /api/bookings/user/{userId}` - Get user bookings
- `DELETE /api/bookings/cancel` - Cancel ticket

## Troubleshooting

### Port already in use
Change port in `app/src/main/resources/application.properties`:
```properties
server.port=8081
```

### Build fails
Clean and rebuild:
```bash
./gradlew clean build
```

### JSON files not found
Ensure these files exist:
- `app/src/main/java/ticket/booking/localDB/user.json`
- `app/src/main/java/ticket/booking/localDB/train.json`

## Next Steps

1. Review `API_DOCUMENTATION.md` for detailed API reference
2. Test all endpoints using Postman collection
3. Integrate with your frontend application
4. Configure CORS for production in controllers

## Support

For issues or questions, refer to:
- `README.md` - Complete documentation
- `API_DOCUMENTATION.md` - API reference
