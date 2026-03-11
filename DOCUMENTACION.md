# Documentación del Sistema NanoLedgerAPI

Este documento explica el funcionamiento técnico y lógico de los componentes implementados en el sistema de contabilidad de partida doble.

## 1. Concepto de Partida Doble
El sistema sigue el principio contable de partida doble, donde cada transacción afecta al menos a dos cuentas: una se debita y otra se acredita. La suma de todas las entradas (`Entry`) en una transacción debe ser cero.

- **Account (Cuenta)**: Representa una entidad con un saldo (`balance`).
- **Transaction (Transacción)**: Es el "sobre" que agrupa los movimientos contables. Por ejemplo: "Pago de proveedor".
- **Entry (Asiento/Entrada)**: Es el movimiento individual de dinero dentro de una transacción.
  - Un **Débito** resta (en este modelo usamos signo negativo para simplificar la lógica de suma cero).
  - Un **Crédito** suma.

## 2. Flujo de una Transacción (`TransactionService`)

Cuando llamas al método `processTransaction(TransferRequest)` en el endpoint `POST /api/transactions`:

1. **Validación de Cuentas**:
   - Verifica que las IDs de origen y destino no sean iguales.
   - Verifica que ambas cuentas existan en la base de datos.
2. **Control de Saldo**: Se verifica que la cuenta de origen tenga fondos suficientes (`BigDecimal`). Si el saldo es insuficiente, se lanza `InsufficientBalanceException`.
3. **Atomicidad con `@Transactional`**: Toda la operación es atómica. Si falla la creación del segundo asiento contable o la actualización de los balances, **se deshacen todos los cambios automáticamente**.
4. **Balance de Partida Doble**: Antes de persistir, el sistema verifica internamente que la suma de los montos (Débito + Crédito) sea exactamente **cero**.
5. **Registro Contable**:
   - Se crea una `Transaction`.
   - Se crean dos `Entry`: una para el origen (negativa) y otra para el destino (positiva).

## 3. Manejo de Errores
Se ha implementado un `GlobalExceptionHandler` que captura errores de validación y de negocio, devolviendo respuestas JSON claras:
- `400 Bad Request`: Para saldos insuficientes, cuentas inexistentes o datos inválidos (monto negativo, campos nulos).
- `500 Internal Server Error`: Para errores inesperados del sistema.

El sistema permite trabajar en diferentes entornos sin cambiar el código:

- **Perfil `test` (H2)**: 
  - Usado por defecto.
  - Base de datos en memoria (se borra al apagar la aplicación).
  - Ideal para desarrollo rápido y pruebas unitarias.
  - Archivo: `application-test.yaml`.

- **Perfil `prod` (PostgreSQL)**:
  - Usado cuando conectas con Docker.
  - Los datos son persistentes (se guardan en un volumen de Docker).
  - Archivo: `application-prod.yaml`.

### Cómo usar Docker
1. **Levantar base de datos**: `docker-compose up -d`.
2. **Credenciales**: Usuario `postgres`, Clave `1234`, Base de datos `nanoledger`.
3. **Ejecutar App en producción**:
   ```bash
   ./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
   ```

## 4. Estructura de Archivos

- `model/`: Contiene las entidades JPA que definen las tablas.
- `repository/`: Interfaces para interactuar con la base de datos (guardar, buscar).
- `dto/`: Objetos de transferencia de datos con validaciones (`@Positive`, `@NotNull`).
- `service/`: Contiene la lógica de negocio (las reglas de cómo se mueve el dinero).
- `controller/`: Expone el servicio a través de una API REST.

---
**Nota**: Para probar la API puedes usar herramientas como Postman o cURL apuntando a `POST http://localhost:8080/api/transfers`.
