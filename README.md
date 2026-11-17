# 🎾 Tennis Scoreboard

A Java-based web application for managing tennis matches, tracking scores in real time, and storing completed match results.

The application provides a RESTful API for match management and implements tennis scoring rules using a rich domain model.

## Tech Stack

- **Backend:** Java, Spring MVC
- **Database:** PostgreSQL, Hibernate ORM
- **Build Tool:** Gradle
- **Testing:** JUnit 5
- **Deployment:** Apache Tomcat

## Features

- Create new tennis matches between two players
- Track match scores in real time
- Automatically calculate points, games, and sets
- Support deuce, advantage, and tie-break rules
- Determine match winners automatically
- Store completed matches in PostgreSQL
- Retrieve match history with pagination and player filtering
- Handle concurrent match updates safely
- Validate requests and provide structured API error responses

## Architecture

The application follows a layered architecture with a clear separation of responsibilities:

- **Controller Layer:** Handles HTTP requests, input validation, and API responses.
- **Service Layer:** Coordinates match management, scoring operations, and persistence.
- **Domain Layer:** Encapsulates tennis scoring rules and match state transitions.
- **Persistence Layer:** Manages database operations using Hibernate ORM and PostgreSQL.

### Domain Model

The scoring system follows a rich domain model approach, where business logic is encapsulated within domain objects rather than concentrated in service classes.

Core domain concepts include:

- `Match` — manages the overall match state and determines the winner.
- `Set` — tracks games and determines when a set is completed.
- `Game` — handles point progression, deuce, and advantage.
- `TieBreak` — implements tie-break scoring rules.

Matches follow a best-of-three-sets format, with a tie-break played at 6–6.

### Match State Management

Active matches are stored in memory using thread-safe data structures, while completed matches are persisted in PostgreSQL.

This approach separates frequently updated match state from persistent historical records.

## REST API

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/matches` | Create a new match |
| `GET` | `/matches/{uuid}` | Retrieve the current match score |
| `POST` | `/matches/{uuid}/point` | Award a point to a player |
| `GET` | `/matches` | Retrieve completed matches |

### Create a Match

**POST** `/matches`

Request:

```json
{
  "firstPlayerName": "Roger Federer",
  "secondPlayerName": "Rafael Nadal"
}
```

Response — `201 Created`:

```json
{
  "id": "1d5e5fb4-5203-4933-8278-486f3d8db2ca"
}
```

### Award a Point

**POST** `/matches/{uuid}/point`

Request:

```json
{
  "name": "Roger Federer"
}
```

Response — `200 OK`:

```json
{
  "firstPlayer": {
    "name": "Roger Federer",
    "points": "40",
    "games": 2,
    "sets": 0,
    "tieBreakPoints": null
  },
  "secondPlayer": {
    "name": "Rafael Nadal",
    "points": "30",
    "games": 3,
    "sets": 0,
    "tieBreakPoints": null
  },
  "winnerName": null
}
```

### Retrieve Match History

**GET** `/matches?page=0&player_name=Federer`

Response — `200 OK`:

```json
{
  "matches": [
    {
      "firstPlayerName": "Roger Federer",
      "secondPlayerName": "Rafael Nadal",
      "winnerName": "Roger Federer"
    }
  ],
  "currentPage": 0,
  "totalPages": 1
}
```

## Database Design

The application uses PostgreSQL to store players and completed matches.

**Players**

| Column | Description |
|---|---|
| `id` | Primary key |
| `name` | Unique player name |

**Matches**

| Column | Description |
|---|---|
| `id` | Primary key |
| `player1` | Reference to the first player |
| `player2` | Reference to the second player |
| `winner` | Reference to the winning player |

Hibernate ORM manages entity mapping and database interactions.

## Testing

The application uses JUnit 5 to verify tennis scoring rules and match state transitions.

Test scenarios include:

- Standard point progression (0 → 15 → 30 → 40)
- Deuce and advantage handling
- Game completion
- Set completion
- Tie-break activation at 6–6
- Tie-break winner determination
- Match completion after winning two sets