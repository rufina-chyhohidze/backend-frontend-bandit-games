
## Database migration strategy (Flyway-based)

### What we use

We use **Flyway** as the **single source of truth for database schema migrations**.

All schema changes (table creation, column changes, seed data) are implemented as **versioned SQL migration scripts** under:

```
src/main/resources/db/migration
```

These migrations are executed automatically on application startup, **before JPA and Spring Modulith initialize**, ensuring the database schema is always in the expected state.

Hibernate is configured to **read/validate the schema only**, not modify it.

---

### What was added / changed

* A Flyway migration was added to ensure the `event_publication.serialized_event` column uses the PostgreSQL `TEXT` type instead of `VARCHAR(255)`.

Example migration (simplified):

```sql
ALTER TABLE event_publication
ALTER COLUMN serialized_event TYPE TEXT;
```

This migration is versioned (e.g. `V2__event_publication_use_text.sql`) and executed once by Flyway.

---

### Why this change is needed

* The application stores **serialized domain events** in the `event_publication` table (used by Spring Modulith).
* Serialized events can easily exceed 255 characters.
* When the column was defined as `VARCHAR(255)`, saving large events caused runtime failures.
* Using `TEXT` removes this limitation and is the correct PostgreSQL type for this use case.

---

### Why Flyway (and not runtime code)

We **do not** perform schema changes at runtime using:

* `ApplicationRunner`
* `ApplicationListener`
* custom startup SQL checks

Reasons:

* Runtime schema mutation is fragile and order-dependent
* Hibernate can override changes on restart
* Migrations become non-reproducible across environments

Using Flyway ensures:

* Schema changes are **explicit**
* Migrations are **versioned and repeatable**
* The same database structure is created in dev, test, and production
* Hibernate never silently overwrites changes

---

### Rollback / reverting the change

If the migration needs to be reverted:

1. Create a **new Flyway migration** (never modify an applied one), for example:

```sql
ALTER TABLE event_publication
ALTER COLUMN serialized_event TYPE VARCHAR(255);
```

2. Only apply this rollback if you are **certain** no existing values exceed 255 characters.

---

### JPA / Hibernate notes

* Hibernate is **not responsible** for schema creation or updates.
* Schema ownership belongs to Flyway.
* Hibernate runs in `none` mode to ensure mappings match the database.

---

### Testing / verification

* The project builds successfully locally.
* Flyway migrations run cleanly on startup.
* Large serialized events can now be persisted without errors.

---

### Summary

* Database schema is managed exclusively via Flyway
* `serialized_event` is correctly stored as `TEXT`
* No runtime schema mutation
* No Hibernate schema drift
* Safe across restarts and environments

This approach aligns with **production-grade database migration best practices**.

