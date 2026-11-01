# gimnasio-api

API REST para reservar los casilleros del gimnasio «Fuerza Viva». Ejercicio práctico 1.4: códigos de estado HTTP y validación de datos.

## Cómo ejecutar

```
./gradlew bootRun
./gradlew test
```

La API queda en `http://localhost:8080`.

## Endpoint

`POST /casilleros/{numero}/reservas`

```json
{"socioId": 1, "meses": 3}
```

## Diagrama de decisión

```mermaid
flowchart TD
    A["POST /casilleros/{numero}/reservas"] --> B{"¿El JSON se puede leer?"}
    B -- No --> R400["400 Solicitud incorrecta"]
    B -- Sí --> C{"¿socioId presente y<br/>meses entre 1 y 12?"}
    C -- No --> R422a["422 Datos no válidos<br/>(detalles por campo)"]
    C -- Sí --> D{"¿Casillero entre 1 y 30?"}
    D -- No --> R404["404 No encontrado"]
    D -- Sí --> E{"¿Existe el socio?"}
    E -- No --> R422b["422 Datos no válidos<br/>(campo socioId)"]
    E -- Sí --> F{"¿El casillero está libre?"}
    F -- No --> R409a["409 Conflicto<br/>casillero ya reservado"]
    F -- Sí --> G{"¿El socio no tiene casillero?"}
    G -- No --> R409b["409 Conflicto<br/>socio ya tiene casillero"]
    G -- Sí --> R201["201 Created<br/>Location: /casilleros/{numero}"]
```

## Autores

- Anthony (anthonyrs25)
- Jairo Arevalo (jiarevalo-hub)