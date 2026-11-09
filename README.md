# Mostrador

Mostrador records takeaway orders for a fixed menu. An order has a code, an item, and a status. It is registered once and delivered once. The orders are synthetic. There are no accounts, payments, or inventory.

## Menu

| Item | Price |
| --- | --- |
| coffee | 4.50 |
| sandwich | 6.00 |

A price is the exact text `4.50` or `6.00`.

## API

The API listens on port **8080**. Run a single process on that port.

`POST /orders` with a code and an item registers the order as `received`.

```bash
curl -sS -D - -H 'content-type: application/json' \
  -d '{"code":"A1","item":"coffee"}' \
  http://localhost:8080/orders
```

`201` body:

```json
{"code":"A1","item":"coffee","price":"4.50","status":"received"}
```

`POST /orders/{code}/delivery` marks that order `delivered` and returns `200`.

```bash
curl -sS -D - -X POST http://localhost:8080/orders/A1/delivery
```

Errors use `application/problem+json`. The `code` field is the name of the failure.

| Situation | HTTP | code |
| --- | --- | --- |
| Item off the menu | 400 | `unknown-item` |
| Blank code | 400 | `invalid-order` |
| Unknown order code | 404 | `unknown-order` |
| Code already registered | 409 | `already-registered` |
| Second delivery | 409 | `already-delivered` |

```json
{"type":"urn:mostrador:unknown-item","title":"unknown-item","status":400,"code":"unknown-item"}
```

## Tests

Java 21.

```bash
./mvnw test
```

The tests use an in-memory H2 database. They do not start Postgres.

## Local stack

Postgres 16 is published on host port **5435**. Host port 5432 stays free. Inside Compose, Postgres keeps its own port 5432. The database user and password are both `mostrador`. That database holds only synthetic orders on this machine.

```bash
docker compose up --build
docker compose down
```

`docker compose up --build` starts Postgres and the API. `docker compose down` stops the stack and removes those containers.

## Changes

A change enters through a pull request to `main`. The `test` check has to pass. `main` advances only by a merge commit.

Words, layers, and decisions: [GLOSSARY.md](GLOSSARY.md), [docs/architecture/arquitectura.md](docs/architecture/arquitectura.md), [docs/adr](docs/adr).
