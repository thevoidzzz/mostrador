# Cuatro capas, con la entrega única en el servicio

El mostrador tiene dos escrituras, registrar y entregar. La segunda solo vale si el pedido guardado sigue en recibido. El controlador traduce HTTP y pone el nombre estable en el `problem+json`. El servicio decide el registro único y la entrega única, porque es donde se ven juntos el pedido guardado y la operación nueva. El repositorio persiste y busca por código. La base queda detrás del repositorio.

Opciones descartadas: la regla en el controlador, atada al transporte; la regla escondida en el ciclo de vida de la fila; un solo paquete donde la petición y el dato no tienen frontera.

Consecuencia: la tabla se llama `pickup_order`, porque `order` es palabra reservada en Postgres.
