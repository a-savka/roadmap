# Дорожная карта — Запуск проекта

## Требования

- Java 17+
- Node.js 20.19+ или 22.12+
- npm 10+

## Первый запуск (после git clone)

```bash
# Frontendы
cd frontend && npm install
cd ../admin && npm install
```

## Запуск

### 1. Backend (порт 8081)

```bash
cd backend
./mvnw spring-boot:run
```

### 2. Frontend для мигранта (порт 5173)

```bash
cd frontend
npm run dev
```

### 3. Admin панель (порт 5174)

```bash
cd admin
npm run dev
```

## Доступ

| Сервис | URL | Логин |
|--------|-----|-------|
| Frontend | http://localhost:5173 | — |
| Admin | http://localhost:5174 | admin / admin |