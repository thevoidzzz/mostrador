# El precio queda como texto decimal fijo

La carta tiene dos precios y este mostrador no calcula con ellos. El pedido guarda y publica el texto `4.50` o `6.00`, en una columna de texto.

Opciones descartadas: `double`, que no conserva el decimal escrito; `BigDecimal`, cuya escala puede imprimir `4.5`; una columna numérica, que el driver puede devolver sin el cero final.
