# H2 en el test de contrato

El test del contrato HTTP recorre el mapeo de la fila y tiene que correr sin un daemon y sin secretos. Usa H2 en memoria, solo en el alcance de test, en modo de compatibilidad con Postgres.

Opciones descartadas: Postgres dentro del test automático, que pide un servicio aparte y alarga la corrida; un doble del repositorio en ese mismo test, que no vería el mapeo de la fila.
