# Postgres solo en Compose

El proceso en la máquina guarda en Postgres 16. Ese motor existe aquí solo dentro de Compose. El test automático no abre esa conexión. El usuario y la contraseña del contenedor viajan junto al servicio: la base guarda pedidos sintéticos y se queda en la máquina.

Opciones descartadas: H2 también para el proceso local, que es otro dialecto del que se usa al levantar el servicio; un Postgres instalado en el anfitrión, con el puerto y los datos pegados a la máquina.

Consecuencia: al arrancar el proceso, Hibernate crea la tabla `pickup_order`.
