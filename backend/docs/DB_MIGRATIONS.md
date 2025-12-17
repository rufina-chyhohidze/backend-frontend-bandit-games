Database migration helper

What I added:

- `be.kdg.banditgames.config` — a Spring `ApplicationRunner` component that runs on startup and ensures the `event_publication.serialized_event` column in the PostgreSQL database is `text` (not `varchar(255)`). It checks the `information_schema.columns` and, if necessary, runs:

    ALTER TABLE event_publication ALTER COLUMN serialized_event TYPE text;

Why:

- The application previously failed when saving large serialized events because the column was limited to 255 characters.

Notes and rollbacks:

- To revert, remove the class `DatabaseMigrationsRunner` from the project or disable it, then manually run `ALTER TABLE event_publication ALTER COLUMN serialized_event TYPE varchar(255);` (only if you know no values exceed 255 characters).

- Alternatively, if you use Flyway/liquibase migrations, you can convert this change into a proper migration script in your migration folder (e.g., V2__increase_serialized_event.sql) with the same ALTER TABLE statement.

Suggested JPA/entity change:

- If you manage the schema via JPA, mark the entity field that maps to `serialized_event` with `@Lob` and `@Column(columnDefinition = "text")` so Hibernate will use an appropriate SQL type.

Testing:

- The project was built locally (`gradlew.bat build -x test`) to ensure compilation.


