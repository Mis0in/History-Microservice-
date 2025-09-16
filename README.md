# Transaction Service

Микросервис для просмотра истории операций с поддержкой пагинации и фильтрации.

## Технологии
- Java 21 + Spring Boot 3
- PostgreSQL
- Docker + Docker Compose
- Spring Data JPA
- OpenCSV

## API Endpoints

### Получить транзакции
**GET /api/transactions**

**Параметры:**
- `customerId` - фильтр по ID клиента
- `startDate`, `endDate` - фильтр по дате
- `page`, `size` - пагинация

## Запуск

```bash
docker-compose up --build
