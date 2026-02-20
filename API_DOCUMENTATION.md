# Train Ticket Booking API Documentation

Base URL: `http://localhost:8080`

## Authentication Endpoints

### 1. User Sign Up
Creates a new user account with hashed password.

**Endpoint:** `POST /api/auth/signup`

**Request Body:**
```json
{
  "username": "string",
  "password": "string"
}
```

**Success Response (200):**
```json
{
  "success": true,
  "message": "User registered successfully",
  "data": "user_abc12345"
}
```

**Error Response (400/500):**
```json
{
  "success": false,
  "message": "Registration failed",
  "data": null
}
```

---

### 2. User Login
Authenticates user credentials.

**Endpoint:** `POST /api/auth/login`

**Request Body:**
```json
{
  "username": "string",
  "password": "string"
}
```

**Success Response (200):**
```json
{
  "success": true,
  "message": "Login successful",
  "data": "username"
}
```

**Error Response (401):**
```json
{
  "success": false,
  "message": "Invalid credentials",
  "data": null
}
```

---

## Train Endpoints

### 3. Search Trains
Search for trains between source and destination cities.

**Endpoint:** `GET /api/trains/search`

**Query Parameters:**
- `source` (required): Source city name
- `destination` (required): Destination city name

**Example:** `/api/trains/search?source=CityA&destination=CityB`

**Success Response (200):**
```json
{
  "success": true,
  "message": "Trains found",
  "data": [
    {
      "train_id": "T123",
      "train_number": "EXP123",
      "train_name": "Express Line",
      "departure_time": "10:00 AM",
      "arrival_time": "02:00 PM",
      "seats": [[0,0,0],[0,0,0]],
      "stations": ["CityA", "CityC", "CityB"],
      "station_times": {
        "CityA": "10:00 AM",
        "CityC": "12:00 PM",
        "CityB": "02:00 PM"
      }
    }
  ]
}
```

---

### 4. Get Train by ID
Retrieve detailed information about a specific train.

**Endpoint:** `GET /api/trains/{trainId}`

**Path Parameters:**
- `trainId`: Train identifier

**Success Response (200):**
```json
{
  "success": true,
  "message": "Train found",
  "data": {
    "train_id": "T123",
    "train_number": "EXP123",
    "train_name": "Express Line",
    ...
  }
}
```

**Error Response (404):**
Train not found

---

### 5. Get Available Seats
Get list of available seats for a train.

**Endpoint:** `GET /api/trains/{trainId}/seats`

**Path Parameters:**
- `trainId`: Train identifier

**Success Response (200):**
```json
{
  "success": true,
  "message": "Available seats",
  "data": [
    [0, 0],
    [0, 1],
    [1, 0],
    [1, 2]
  ]
}
```

**Note:** Each array represents [row, column] of available seat.

---

## Booking Endpoints

### 6. Book Ticket
Book a train ticket with seat selection.

**Endpoint:** `POST /api/bookings/book`

**Request Body:**
```json
{
  "userId": "user_abc12345",
  "trainId": "T123",
  "source": "CityA",
  "destination": "CityB",
  "row": 0,
  "col": 0
}
```

**Success Response (200):**
```json
{
  "success": true,
  "message": "Ticket booked successfully",
  "data": "ticket_xyz78901"
}
```

**Error Response (400):**
```json
{
  "success": false,
  "message": "Booking failed. Seat may be unavailable",
  "data": null
}
```

---

### 7. Get User Bookings
Retrieve all bookings for a specific user.

**Endpoint:** `GET /api/bookings/user/{userId}`

**Path Parameters:**
- `userId`: User identifier

**Success Response (200):**
```json
{
  "success": true,
  "message": "Bookings retrieved",
  "data": [
    {
      "ticket_id": "ticket_xyz78901",
      "user_id": "user_abc12345",
      "source": "CityA",
      "destination": "CityB",
      "date_of_journey": "2024-01-15T10:00:00.000+00:00",
      "train": {
        "train_id": "T123",
        "train_number": "EXP123",
        "train_name": "Express Line",
        ...
      }
    }
  ]
}
```

---

### 8. Cancel Ticket
Cancel a booked ticket and free up the seat.

**Endpoint:** `DELETE /api/bookings/cancel`

**Query Parameters:**
- `ticketId` (required): Ticket identifier
- `userId` (required): User identifier
- `trainId` (required): Train identifier
- `row` (required): Seat row number
- `col` (required): Seat column number

**Example:** `/api/bookings/cancel?ticketId=ticket_xyz78901&userId=user_abc12345&trainId=T123&row=0&col=0`

**Success Response (200):**
```json
{
  "success": true,
  "message": "Ticket cancelled successfully",
  "data": "ticket_xyz78901"
}
```

**Error Response (400):**
```json
{
  "success": false,
  "message": "Cancellation failed",
  "data": null
}
```

---

## Response Format

All API endpoints follow a standard response format:

```json
{
  "success": boolean,
  "message": "string",
  "data": object | null
}
```

## HTTP Status Codes

- `200 OK`: Request successful
- `400 Bad Request`: Invalid request parameters
- `401 Unauthorized`: Authentication failed
- `404 Not Found`: Resource not found
- `500 Internal Server Error`: Server error

## Data Models

### Seat Representation
- `0`: Available seat
- `1`: Booked seat
- Seats are represented as 2D array: `[[0,1,0],[1,0,0]]`
- Row and column indices start from 0

## Testing

Import the `postman_collection.json` file into Postman for easy API testing.

## CORS

CORS is enabled for all origins (`*`). Configure in production for specific domains.
