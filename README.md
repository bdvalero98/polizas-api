# API de Gestión de Pólizas

API REST para gestionar pólizas de arrendamiento (Individual y Colectiva) y sus riesgos,
con un mock del CORE transaccional.

## Requisitos
- Java 21
- Maven 3.8+ (o el wrapper `mvnw` incluido)

## Cómo ejecutar
```bash
mvn spring-boot:run
```
La API queda en `http://localhost:8080`. Usa una base H2 en memoria que se carga con datos de ejemplo:
- Póliza 1: INDIVIDUAL, con 1 riesgo.
- Póliza 2: COLECTIVA, con 2 riesgos.

## Seguridad
Todas las peticiones requieren el header `x-api-key: 123456`. Sin él responde 401.
La llave se configura en `application.properties` (`security.api-key`).

## Endpoints
| Método | Ruta | Descripción |
|---|---|---|
| GET | `/polizas?tipo=&estado=` | Lista pólizas; ambos filtros son opcionales |
| GET | `/polizas/{id}/riesgos` | Riesgos de una póliza |
| POST | `/polizas/{id}/renovar` | Incrementa canon y prima según IPC; estado RENOVADA. Body opcional `{"ipc": 0.05}` |
| POST | `/polizas/{id}/cancelar` | Cancela la póliza y todos sus riesgos |
| POST | `/polizas/{id}/riesgos` | Agrega un riesgo (solo pólizas COLECTIVA) |
| POST | `/riesgos/{id}/cancelar` | Cancela un riesgo |
| POST | `/core-mock/evento` | Mock del CORE: registra el evento en logs |

## Reglas de negocio
- Una póliza individual solo puede tener 1 riesgo.
- No se puede renovar una póliza cancelada.
- Cancelar una póliza cancela todos sus riesgos.
- Agregar un riesgo exige que la póliza sea COLECTIVA y no esté cancelada.
- Toda operación que modifica estado notifica al CORE (evento `ACTUALIZACION`).
  Si el CORE falla, la operación no se revierte y el error queda en el log.

## Códigos de respuesta
| Código | Significado |
|---|---|
| 200 / 201 | Operación exitosa |
| 400 | Solicitud inválida |
| 401 | `x-api-key` ausente o incorrecta |
| 404 | Póliza o riesgo no encontrado |
| 422 | Regla de negocio violada |

## Decisiones de diseño
- Las reglas viven en las entidades (modelo de dominio rico).
- El CORE se accede mediante un puerto (`CoreGateway`) y un adaptador (`CoreMockGateway`).
- El IPC por defecto se configura en `negocio.ipc-por-defecto` y puede enviarse en cada renovación.
- Prima = canon mensual × meses de vigencia.

## Pruebas manuales
Importar `postman/polizas-api.postman_collection.json` en Postman.
Reiniciar la aplicación antes de ejecutar la colección completa, porque los datos están en memoria.