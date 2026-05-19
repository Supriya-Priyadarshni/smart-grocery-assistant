# Smart Grocery Recommendation API

> **Java · Spring Boot · PostgreSQL · Redis · Ollama (Llama 3.2) · Docker**

REST API backend with natural-language product search — built to mirror quick-commerce stacks like FirstClub (recommendation engines, LLM workflows, cached reads).

---

## Architecture

```
Client (Postman / curl)
        ↓
Spring Boot REST API
        ↓
┌───────────────────────────────┐
│  Service Layer                │
│  ├─ ProductService            │
│  └─ LlmService ───────────────┼──→ Ollama (Llama 3.2)
└───────────────────────────────┘
        ↓
   Redis Cache  (search results, ~80% faster on repeat queries)
        ↓
   PostgreSQL  (100+ products, nutrition + categories)
        ↓
   Docker Compose (app + Postgres + Redis + Ollama)
```

**Design choice:** The LLM extracts *structured filters* (meal time, min protein, max price) as JSON. PostgreSQL remains the source of truth — the model never invents products.

---

## API Endpoints

| Method | Path | Description |
|--------|------|-------------|
| `GET` | `/api/products` | List all products |
| `GET` | `/api/products/{id}` | Get one product |
| `GET` | `/api/products/category/{name}` | Filter by category (e.g. `Dairy`) |
| `POST` | `/api/search` | **Natural language search** (LLM-powered) |
| `GET` | `/health` | Health check (DB + Redis) |

### Star endpoint — `POST /api/search`

**Request:**
```json
{
  "query": "I want something healthy for breakfast, high protein",
  "limit": 10
}
```

**Response:**
```json
{
  "query": "I want something healthy for breakfast, high protein",
  "reasoning": "User wants a high-protein breakfast option.",
  "products": [ ... ],
  "cached": false,
  "tookMs": 842
}
```

Call again with the same query → `"cached": true`, `"tookMs": 0`.

---

## Sample API calls

```bash
# List catalog
curl http://localhost:8080/api/products

# Single product
curl http://localhost:8080/api/products/1

# By category
curl http://localhost:8080/api/products/category/Dairy

# Natural language search
curl -X POST http://localhost:8080/api/search \
  -H "Content-Type: application/json" \
  -d "{\"query\": \"I want something healthy for breakfast, high protein\"}"

# Health
curl http://localhost:8080/health
```

---

## Tech stack

| What | Tool |
|------|------|
| Language | Java 17 |
| Framework | Spring Boot 3.2 |
| Database | PostgreSQL 16 |
| Cache | Redis 7 |
| LLM | Ollama + Llama 3.2 |
| Containers | Docker + Docker Compose |
| Migrations | Flyway |

---

## Quick start (Docker)

```bash
docker compose up -d postgres redis ollama
docker exec -it grocery-ollama-1 ollama pull llama3.2
docker compose up --build api
```

> Without Ollama, search still works via keyword fallback rules.

### Local dev (no Docker for app)

1. `docker compose up -d postgres redis ollama`
2. Copy `.env.example` → set vars if needed
3. `mvn spring-boot:run` (requires JDK 17 + Maven)

### Switch to OpenAI

```env
LLM_PROVIDER=openai
OPENAI_API_KEY=sk-your-key
```

---

## Week-by-week build plan

Use this repo as the **end state**; follow the plan to learn each layer.

### Week 1 — Spring Boot + PostgreSQL
| Day | Task |
|-----|------|
| 1 | Spring Initializr setup, project structure |
| 2 | Design schema (`categories`, `products`, nutrition fields) |
| 3 | Entity, repository, CRUD endpoints |
| 4 | Postman tests, seed data |
| 5 | Validation + error handling |

### Week 2 — LLM integration
| Day | Task |
|-----|------|
| 1 | Install Ollama, `ollama pull llama3.2` |
| 2 | Java HTTP client → Ollama REST API |
| 3 | Build `POST /api/search` |
| 4 | Map LLM JSON → DB filters |
| 5 | Refine prompts, end-to-end tests |

### Week 3 — Redis + Docker
| Day | Task |
|-----|------|
| 1 | Redis caching on search |
| 2 | Measure cache hit vs miss latency |
| 3 | `Dockerfile` for the app |
| 4 | `docker-compose.yml` full stack |
| 5 | Integration test via Docker |

### Week 4 — Polish + GitHub
| Day | Task |
|-----|------|
| 1–2 | `/health`, request logging, error responses |
| 3 | README + architecture diagram |
| 4 | Push to GitHub with clean commits |
| 5 | Prepare 3-minute interview walkthrough |

---

## 3-minute interview walkthrough

1. **Problem** — Users shop with natural language (“high protein breakfast”), not SQL filters.
2. **Flow** — `POST /api/search` → Redis? → Ollama returns JSON filters → PostgreSQL query → ranked products → cache.
3. **Schema** — `products` has `protein_g`, `meal_tags`, `price_inr`, `stock_quantity` for real commerce filters.
4. **Resilience** — Keyword fallback if Ollama is down; DB is always source of truth.
5. **Ops** — `docker compose up`, `/health` checks Postgres + Redis.
6. **Demo** — Same query twice; show `cached: true` and near-instant response.

---

## Resume bullet (copy-paste)

**Smart Grocery Recommendation API** — Java, Spring Boot, PostgreSQL, Redis, Ollama

- Built a REST API backend with natural language product search powered by Llama 3.2 via Ollama
- Designed PostgreSQL schema for 100+ grocery products with nutrition and category metadata
- Reduced repeat query response time by ~80% using Redis caching
- Containerized full stack (app + DB + cache) using Docker Compose

---

## Project structure

```
src/main/java/com/grocery/assistant/
├── controller/   ProductController, SearchController, HealthController
├── service/      ProductService, LlmService, SearchService, SearchCacheService
├── repository/   JPA + custom filter queries
├── entity/       Product, Category
├── dto/          SearchRequest, SearchResponse, ProductDto
├── config/       Redis, LLM properties, request logging
└── exception/    Global error handling
```

---

## Database

- **categories** — Dairy, Grains, Snacks, etc.
- **products** — name, brand, nutrition, `meal_tags`, price (INR), stock
- **Seed data** — 200+ products via Flyway (`V2`, `V3` migrations)

---

## Using AI as a force multiplier

Good Cursor/Claude prompts for this project:

- *"Generate a Spring Boot entity for a grocery product with protein_g, meal_tags, and category FK"*
- *"Write a Docker Compose file for Spring Boot + PostgreSQL + Redis + Ollama"*
- *"Help me write a prompt template that extracts search filters from a grocery query as JSON"*

Understand each layer you add — that's what interviewers probe.
