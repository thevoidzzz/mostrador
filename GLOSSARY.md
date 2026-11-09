# Mostrador

El mostrador anota pedidos para llevar de una carta fija y los deja listos para una entrega.

## Language

**Pedido**:
Anotación para llevar con un código, un ítem de la carta, el precio de ese ítem y un estado.
_Avoid_: orden, ticket, venta, cuenta

**Código**:
Identidad de un pedido, elegida al anotarlo. Un código pertenece a un solo pedido.
_Avoid_: id, folio, número de orden

**Ítem**:
Producto de la carta que un pedido pide.
_Avoid_: sku, plato, producto suelto

**Carta**:
Lista fija de ítems que el mostrador acepta.
_Avoid_: menú del día, catálogo, inventario

**Café**:
Ítem de la carta. Se escribe `coffee`. Su precio es 4.50.
_Avoid_: espresso, americano

**Sándwich**:
Ítem de la carta. Se escribe `sandwich`. Su precio es 6.00.
_Avoid_: bocadillo, emparedado

**Precio**:
Cantidad de un ítem de la carta, escrita con dos decimales fijos. El pedido lleva el precio de su ítem.
_Avoid_: importe calculado, tarifa, monto

**Estado**:
Situación de un pedido. Es recibido o entregado.
_Avoid_: fase, etapa, situación de pago

**Recibido**:
Estado de un pedido anotado que todavía no se entregó. Se escribe `received`.
_Avoid_: pendiente, en cola, pagado

**Entregado**:
Estado de un pedido que ya se entregó. Se escribe `delivered`.
_Avoid_: cerrado, completado, servido

**Registro**:
Anotación de un pedido bajo un código. Cada código tiene un solo registro.
_Avoid_: alta, checkout, compra

**Entrega**:
Paso de un pedido recibido a entregado. Cada pedido tiene una sola entrega.
_Avoid_: despacho, envío, servicio
