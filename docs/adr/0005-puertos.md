# La API en el 8080 y Postgres en el 5435

La API escucha en el puerto 8080, en un solo proceso. Postgres se publica en el 5435 del anfitrión y sigue en el 5432 dentro de la red de Compose. El 5432 del anfitrión queda libre.

Opciones descartadas: publicar Postgres en el 5432 del anfitrión, encima de una instalación que ya use ese puerto; elegir el puerto de la API en cada arranque, con un contrato local distinto cada vez.
