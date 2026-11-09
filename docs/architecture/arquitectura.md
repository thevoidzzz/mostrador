# Arquitectura

Mostrador anota pedidos para llevar de una carta fija. La carta tiene café a 4.50 y sándwich a 6.00. Un pedido tiene código, ítem, precio y estado. El estado es recibido o entregado.

Cada código se registra una vez. Cada pedido se entrega una vez. Un ítem ausente de la carta queda fuera del pedido. Un código que no identifica un pedido queda fuera de la entrega.

## Capas

El controlador traduce HTTP. Lee la petición, llama al servicio y escribe la respuesta. Cuando el servicio rechaza la operación, la respuesta es `problem+json` y nombra un código estable.

El servicio es dueño de las reglas que miran el pedido guardado. Ahí vive el registro único y la entrega única. El servicio pide el precio a la carta y rechaza un código en blanco.

El repositorio persiste el pedido y lo busca por código. La validez de la entrega no se decide en el repositorio.

La base de datos queda detrás del repositorio. El test del contrato usa H2 en memoria. El proceso en la máquina usa Postgres 16 cuando Compose está levantado.

## Registro

`POST /orders` entra con un código y un ítem. El servicio rechaza el código en blanco con `invalid-order`. Pide el precio a la carta. Un ítem ausente responde `unknown-item`. Un código que ya identifica un pedido responde `already-registered`. Si cabe, el pedido queda guardado en recibido, con el precio en el mismo texto de la carta.

## Entrega

`POST /orders/{code}/delivery` entra con un código. Si ese código no identifica un pedido, la respuesta es `unknown-order`. Si el estado ya es entregado, la respuesta es `already-delivered`. Si está recibido, el pedido queda guardado como entregado.

## Precio

La carta tiene dos textos, `4.50` y `6.00`. El pedido guarda ese texto. En este mostrador el precio no se suma ni se reparte.

## Alcance

Quedan fuera las cuentas de personas, los pagos, el inventario y la pantalla. Los pedidos son sintéticos. La API escucha en un solo proceso.

## Comprobación

La carta y el pedido recién anotado se comprueban sin el framework. El servicio se comprueba con un doble del repositorio. El contrato HTTP se comprueba con el controlador, el servicio y la persistencia sobre H2.
