# 🚂 Train Ticket Booking System - REST API Backend

A comprehensive Spring Boot REST API backend for train ticket booking with user authentication, real-time seat management, and persistent storage.

---

## 📋 Table of Contents

- [Problem Statement](#-problem-statement)
- [Solution Overview](#-solution-overview)
- [System Architecture](#-system-architecture)
- [Technology Stack](#-technology-stack)
- [Features](#-features)
- [API Endpoints](#-api-endpoints)
- [Workflow Diagrams](#-workflow-diagrams)
- [Project Structure](#-project-structure)
- [Getting Started](#-getting-started)
- [API Documentation](#-api-documentation)
- [Testing](#-testing)
- [Deployment](#-deployment)
- [Future Enhancements](#-future-enhancements)

---

## 🎯 Problem Statement

### Business Challenge

Traditional train ticket booking systems face several challenges:

1. **Manual Booking Process**: Time-consuming counter-based ticket booking
2. **Seat Availability**: No real-time visibility of available seats
3. **User Management**: Difficulty in tracking user bookings and history
4. **Scalability**: Limited capacity to handle multiple concurrent bookings
5. **Data Persistence**: Need for reliable storage of user and booking data
6. **Security**: Secure handling of user credentials and personal information

### Technical Requirements

- Multi-user support with secure authentication
- Real-time seat availability checking
- Concurrent booking handling
- Train search across multiple routes
- Booking history management
- RESTful API for frontend/mobile integration

---

## 💡 Solution Overview

### How We Solved It

Our solution provides a **REST API backend** that addresses all the challenges:

```
┌─────────────────────────────────────────────────────────────┐
│                    SOLUTION APPROACH                         │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  1. RESTful API Architecture                                │
│     → Stateless, scalable, platform-independent             │
│                                                              │
│  2. Secure Authentication                                   │
│     → BCrypt password hashing                               │
│     → No plain-text password storage                        │
│                                                              │
│  3. Real-time Seat Management                               │
│     → 2D matrix representation of seats                     │
│     → Instant availability updates                          │
│                                                              │
│  4. JSON-based Persistence                                  │
│     → Lightweight data storage                              │
│     → Easy to migrate to database later                     │
│                                                              │
│  5. Modular Architecture                                    │
│     → Separation of concerns (MVC pattern)                  │
│     → Easy to maintain and extend                           │
│                                                              │
└─────────────────────────────────────────────────────────────┘
```

---

## 🏗️ System Architecture

### High-Level Architecture

```
┌──────────────────────────────────────────────────────────────────┐
│                         CLIENT LAYER                              │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐          │
│  │   Web App    │  │  Mobile App  │  │   Postman    │          │
│  │  (React/Vue) │  │ (iOS/Android)│  │   (Testing)  │          │
│  └──────┬───────┘  └──────┬───────┘  └──────┬───────┘          │
│         │                  │                  │                   │
│         └──────────────────┴──────────────────┘                   │
│                            │                                      │
│                    HTTP/REST API                                  │
│                            │                                      │
└────────────────────────────┼──────────────────────────────────────┘
                             │
┌────────────────────────────┼──────────────────────────────────────┐
│                    SPRING BOOT APPLICATION                        │
│                            │                                      │
│  ┌─────────────────────────▼────────────────────────────┐        │
│  │              CONTROLLER LAYER                        │        │
│  │  ┌──────────────┐ ┌──────────────┐ ┌─────────────┐ │        │
│  │  │    Auth      │ │    Train     │ │   Booking   │ │        │
│  │  │  Controller  │ │  Controller  │ │ Controller  │ │        │
│  │  └──────┬───────┘ └──────┬───────┘ └──────┬──────┘ │        │
│  └─────────┼────────────────┼────────────────┼────────┘        │
│            │                │                │                   │
│  ┌─────────▼────────────────▼────────────────▼────────┐        │
│  │                 SERVICE LAYER                       │        │
│  │  ┌──────────────────────┐  ┌──────────────────┐   │        │
│  │  │ UserBookingService   │  │   TrainService   │   │        │
│  │  │  - Login/Signup      │  │  - Search trains │   │        │
│  │  │  - Book ticket       │  │  - Seat mgmt     │   │        │
│  │  │  - Cancel ticket     │  │  - Availability  │   │        │
│  │  └──────────┬───────────┘  └────────┬─────────┘   │        │
│  └─────────────┼──────────────────────┼──────────────┘        │
│                │                      │                         │
│  ┌─────────────▼──────────────────────▼──────────────┐        │
│  │              ENTITY/MODEL LAYER                    │        │
│  │  ┌──────┐  ┌────────┐  ┌────────┐  ┌──────────┐  │        │
│  │  │ User │  │ Ticket │  │ Train  │  │   DTOs   │  │        │
│  │  └──────┘  └────────┘  └────────┘  └──────────┘  │        │
│  └────────────────────────┬───────────────────────────┘        │
│                           │                                     │
└───────────────────────────┼─────────────────────────────────────┘
                            │
┌───────────────────────────▼─────────────────────────────────────┐
│                    DATA PERSISTENCE LAYER                        │
│  ┌──────────────────────┐      ┌──────────────────────┐        │
│  │     user.json        │      │     train.json       │        │
│  │  - User accounts     │      │  - Train schedules   │        │
│  │  - Hashed passwords  │      │  - Seat availability │        │
│  │  - Booking history   │      │  - Routes & timing   │        │
│  └──────────────────────┘      └──────────────────────┘        │
└──────────────────────────────────────────────────────────────────┘
```

### Architecture Layers Explained

#### 1. **Controller Layer** (API Endpoints)
- Handles HTTP requests/responses
- Input validation
- Routes requests to appropriate services
- Returns standardized JSON responses

#### 2. **Service Layer** (Business Logic)
- Core business logic implementation
- Data processing and validation
- Interaction with data layer
- Transaction management

#### 3. **Entity/Model Layer** (Data Models)
- Domain object definitions
- JSON serialization/deserialization
- Data transfer objects (DTOs)

#### 4. **Persistence Layer** (Data Storage)
- JSON file-based storage
- CRUD operations
- Data consistency management

---

## 🛠️ Technology Stack

| Layer | Technology | Purpose |
|-------|-----------|---------|
| **Framework** | Spring Boot 3.2.0 | REST API framework |
| **Language** | Java 21 | Programming language |
| **Build Tool** | Gradle 9.2.1 | Dependency management |
| **JSON Processing** | Jackson 2.15.2 | Serialization/Deserialization |
| **Security** | jBCrypt 0.4 | Password hashing |
| **API Testing** | Postman | Endpoint testing |
| **Containerization** | Docker | Deployment |

---

## ✨ Features

### Core Functionality

✅ **User Authentication**
- Secure signup with password hashing
- Login with credential validation
- BCrypt encryption (10 salt rounds)

✅ **Train Search**
- Search by source and destination
- Multi-station route support
- Real-time availability

✅ **Seat Management**
- 2D matrix representation
- Real-time availability checking
- Automatic updates on booking/cancellation

✅ **Booking System**
- Book tickets with seat selection
- View complete booking history
- Cancel tickets with seat release

✅ **API Features**
- RESTful architecture
- Standard JSON responses
- CORS enabled
- Comprehensive error handling

---

## 🔌 API Endpoints

### Authentication Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/signup` | Register new user |
| POST | `/api/auth/login` | Authenticate user |

### Train Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/trains/search` | Search trains by route |
| GET | `/api/trains/{trainId}` | Get train details |
| GET | `/api/trains/{trainId}/seats` | Get available seats |

### Booking Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/bookings/book` | Book a ticket |
| GET | `/api/bookings/user/{userId}` | Get user bookings |
| DELETE | `/api/bookings/cancel` | Cancel a ticket |

---

## 📊 Workflow Diagrams

### 1. User Registration & Login Flow

```
┌─────────┐                                              ┌──────────┐
│ Client  │                                              │  Server  │
└────┬────┘                                              └────┬─────┘
     │                                                        │
     │  POST /api/auth/signup                                │
     │  { username, password }                               │
     ├──────────────────────────────────────────────────────>│
     │                                                        │
     │                                    Hash password       │
     │                                    with BCrypt         │
     │                                    ┌──────────┐        │
     │                                    │ BCrypt   │        │
     │                                    │ Hashing  │        │
     │                                    └────┬─────┘        │
     │                                         │              │
     │                                    Generate userId     │
     │                                    Save to user.json   │
     │                                         │              │
     │  { success: true, userId }              │              │
     │<────────────────────────────────────────┼──────────────┤
     │                                         │              │
     │  POST /api/auth/login                   │              │
     │  { username, password }                 │              │
     ├─────────────────────────────────────────┼─────────────>│
     │                                         │              │
     │                                    Load user data      │
     │                                    Verify password     │
     │                                    ┌──────────┐        │
     │                                    │ BCrypt   │        │
     │                                    │ Verify   │        │
     │                                    └────┬─────┘        │
     │                                         │              │
     │  { success: true, username }            │              │
     │<────────────────────────────────────────┼──────────────┤
     │                                         │              │
```

### 2. Train Search & Booking Flow

```
┌─────────┐                                              ┌──────────┐
│ Client  │                                              │  Server  │
└────┬────┘                                              └────┬─────┘
     │                                                        │
     │  GET /api/trains/search?source=A&destination=B        │
     ├──────────────────────────────────────────────────────>│
     │                                                        │
     │                                    Load train.json    │
     │                                    Filter by route    │
     │                                    Check stations     │
     │                                         │              │
     │  { trains: [...] }                      │              │
     │<────────────────────────────────────────┼──────────────┤
     │                                         │              │
     │  GET /api/trains/T123/seats             │              │
     ├─────────────────────────────────────────┼─────────────>│
     │                                         │              │
     │                                    Get seat matrix    │
     │                                    Find available     │
     │                                    (value = 0)        │
     │                                         │              │
     │  { seats: [[0,0], [0,1], ...] }         │              │
     │<────────────────────────────────────────┼──────────────┤
     │                                         │              │
     │  POST /api/bookings/book                │              │
     │  { userId, trainId, row, col, ... }     │              │
     ├─────────────────────────────────────────┼─────────────>│
     │                                         │              │
     │                                    Check seat         │
     │                                    availability        │
     │                                         │              │
     │                                    ┌────▼─────┐       │
     │                                    │ Available?│       │
     │                                    └────┬─────┘       │
     │                                         │              │
     │                                    Mark seat as 1     │
     │                                    Create ticket      │
     │                                    Update user.json   │
     │                                    Update train.json  │
     │                                         │              │
     │  { success: true, ticketId }            │              │
     │<────────────────────────────────────────┼──────────────┤
     │                                         │              │
```

### 3. Booking Cancellation Flow

```
┌─────────┐                                              ┌──────────┐
│ Client  │                                              │  Server  │
└────┬────┘                                              └────┬─────┘
     │                                                        │
     │  DELETE /api/bookings/cancel                          │
     │  ?ticketId=T001&userId=U001&trainId=TR123&row=0&col=0 │
     ├──────────────────────────────────────────────────────>│
     │                                                        │
     │                                    Load user data     │
     │                                    Find ticket        │
     │                                         │              │
     │                                    ┌────▼─────┐       │
     │                                    │  Ticket  │       │
     │                                    │  Found?  │       │
     │                                    └────┬─────┘       │
     │                                         │              │
     │                                    Mark seat as 0     │
     │                                    Remove from user   │
     │                                    Update train.json  │
     │                                    Update user.json   │
     │                                         │              │
     │  { success: true, ticketId }            │              │
     │<────────────────────────────────────────┼──────────────┤
     │                                         │              │
```

### 4. Data Flow Architecture

```
┌──────────────────────────────────────────────────────────────┐
│                      REQUEST FLOW                             │
└──────────────────────────────────────────────────────────────┘

HTTP Request
     │
     ▼
┌─────────────────┐
│   Controller    │  ← Receives HTTP request
│  - Validation   │  ← Validates input
│  - Routing      │  ← Routes to service
└────────┬────────┘
         │
         ▼
┌─────────────────┐
│    Service      │  ← Business logic
│  - Processing   │  ← Data manipulation
│  - Validation   │  ← Business rules
└────────┬────────┘
         │
         ▼
┌─────────────────┐
│  JSON Storage   │  ← Read/Write data
│  - user.json    │  ← Persistence
│  - train.json   │  ← File operations
└────────┬────────┘
         │
         ▼
┌─────────────────┐
│    Response     │  ← Format response
│  - ApiResponse  │  ← Standard format
│  - HTTP Status  │  ← Status codes
└────────┬────────┘
         │
         ▼
    JSON Response
```

---

## 📁 Project Structure

```
train-ticket-booking-system/
│
├── app/
│   ├── src/main/java/ticket/booking/
│   │   │
│   │   ├── TicketBookingApplication.java    # 🚀 Spring Boot Entry Point
│   │   │
│   │   ├── controller/                      # 🎮 REST Controllers
│   │   │   ├── AuthController.java          #    - Signup/Login
│   │   │   ├── TrainController.java         #    - Train search
│   │   │   └── BookingController.java       #    - Booking management
│   │   │
│   │   ├── dto/                             # 📦 Data Transfer Objects
│   │   │   ├── LoginRequest.java            #    - Login payload
│   │   │   ├── SignUpRequest.java           #    - Signup payload
│   │   │   ├── BookingRequest.java          #    - Booking payload
│   │   │   └── ApiResponse.java             #    - Standard response
│   │   │
│   │   ├── entities/                        # 🏛️ Domain Models
│   │   │   ├── User.java                    #    - User entity
│   │   │   ├── Train.java                   #    - Train entity
│   │   │   └── Ticket.java                  #    - Ticket entity
│   │   │
│   │   ├── services/                        # ⚙️ Business Logic
│   │   │   ├── UserBookingService.java      #    - User operations
│   │   │   └── TrainService.java            #    - Train operations
│   │   │
│   │   ├── Utils/                           # 🔧 Utilities
│   │   │   └── UserServicesUtil.java        #    - Password hashing
│   │   │
│   │   └── localDB/                         # 💾 JSON Storage
│   │       ├── user.json                    #    - User data
│   │       └── train.json                   #    - Train data
│   │
│   ├── src/main/resources/
│   │   └── application.properties           # ⚙️ Configuration
│   │
│   └── build.gradle                         # 📦 Dependencies
│
├── README.md                                # 📖 This file
├── API_DOCUMENTATION.md                     # 📚 API Reference
├── QUICK_START.md                           # 🚀 Quick Start Guide
├── PROJECT_SUMMARY.md                       # 📋 Project Overview
├── postman_collection.json                  # 🧪 API Tests
├── Dockerfile                               # 🐳 Docker Build
├── docker-compose.yml                       # 🐳 Docker Compose
└── .gitignore                               # 🚫 Git Ignore

Total: 20+ files | 1500+ lines of code
```

---

## 🚀 Getting Started

### Prerequisites

- **Java 21** or higher
- **Gradle** (included via wrapper)
- **Docker** (optional, for containerized deployment)

### Installation

1. **Clone the repository**
```bash
git clone <repository-url>
cd train-ticket-booking-system
```

2. **Build the project**
```bash
./gradlew build
```

3. **Run the application**
```bash
./gradlew bootRun
```

4. **Access the API**
```
http://localhost:8080
```

### Quick Test

```bash
# 1. Sign up a new user
curl -X POST http://localhost:8080/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"test123"}'

# 2. Search for trains
curl "http://localhost:8080/api/trains/search?source=CityA&destination=CityB"

# 3. Check available seats
curl "http://localhost:8080/api/trains/T123/seats"
```

---

## 📚 API Documentation

### Standard Response Format

All endpoints return a consistent JSON structure:

```json
{
  "success": true,
  "message": "Operation successful",
  "data": { ... }
}
```

### HTTP Status Codes

| Code | Description |
|------|-------------|
| 200 | Success |
| 400 | Bad Request |
| 401 | Unauthorized |
| 404 | Not Found |
| 500 | Internal Server Error |

### Example: Book a Ticket

**Request:**
```bash
POST /api/bookings/book
Content-Type: application/json

{
  "userId": "user_abc123",
  "trainId": "T123",
  "source": "CityA",
  "destination": "CityB",
  "row": 0,
  "col": 0
}
```

**Response:**
```json
{
  "success": true,
  "message": "Ticket booked successfully",
  "data": "ticket_xyz789"
}
```

For complete API documentation, see [API_DOCUMENTATION.md](API_DOCUMENTATION.md)

---

## 🧪 Testing

### Using Postman

1. Import `postman_collection.json`
2. All 8 endpoints are pre-configured
3. Update variables as needed
4. Run requests sequentially

### Using cURL

See [QUICK_START.md](QUICK_START.md) for cURL examples

### Manual Testing Flow

1. **Sign up** → Get userId
2. **Login** → Verify credentials
3. **Search trains** → Get trainId
4. **Check seats** → Select seat
5. **Book ticket** → Get ticketId
6. **View bookings** → Verify booking
7. **Cancel ticket** → Confirm cancellation

---

## 🐳 Deployment

### Local Deployment

```bash
./gradlew bootRun
```

### JAR Deployment

```bash
./gradlew build
java -jar app/build/libs/*.jar
```

### Docker Deployment

```bash
# Build and run
docker-compose up

# Run in background
docker-compose up -d

# Stop
docker-compose down
```

### Production Considerations

- Configure CORS for specific domains
- Use environment variables for configuration
- Implement JWT authentication
- Migrate to database (PostgreSQL/MySQL)
- Add logging and monitoring
- Implement rate limiting

---

## 🔮 Future Enhancements

### Phase 1: Security & Authentication
- [ ] JWT token-based authentication
- [ ] Refresh token mechanism
- [ ] Role-based access control (Admin/User)
- [ ] API rate limiting

### Phase 2: Database Integration
- [ ] PostgreSQL/MySQL integration
- [ ] JPA/Hibernate ORM
- [ ] Database migrations (Flyway/Liquibase)
- [ ] Connection pooling

### Phase 3: Advanced Features
- [ ] Payment gateway integration
- [ ] Email/SMS notifications
- [ ] Seat reservation timeout
- [ ] Waiting list management
- [ ] Dynamic pricing

### Phase 4: Performance & Scalability
- [ ] Redis caching
- [ ] WebSocket for real-time updates
- [ ] Load balancing
- [ ] Microservices architecture

### Phase 5: Monitoring & Analytics
- [ ] Swagger/OpenAPI documentation
- [ ] Application monitoring (Prometheus/Grafana)
- [ ] Logging (ELK stack)
- [ ] Analytics dashboard

---

## 📄 License

This project is open source and available under the MIT License.

---

## 👥 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

---

## 📞 Support

For issues or questions:
- Check [API_DOCUMENTATION.md](API_DOCUMENTATION.md)
- Review [QUICK_START.md](QUICK_START.md)
- See [PROJECT_SUMMARY.md](PROJECT_SUMMARY.md)

---

## 🎉 Acknowledgments

Built with Spring Boot, Java, and modern REST API best practices.

---

**Made with ❤️ for seamless train ticket booking**
