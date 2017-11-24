# Extra/plus
1) Si no hay empleados disponibles se encolan algunas y luego se comienza a rechazar llamdas.

2) En este momento, rechazo las llamadas, pero tengo un RejectionHandler que podra modificar para ir limpiando la cola de workers y eliminar las más viejas.

3) Hay tests unitarios para probar como rechazan tareas o que pasa si una se demora más de lo deseado. Se hicieron en groovy por comodidad

4) No veo necesaria tanta documentación por se sencillo


Prueba realizada:

hey -n 20 -c 20 'http://localhost:8080/call/123123123'
Summary:
  Total:	0.4852 secs
  Slowest:	0.4813 secs
  Fastest:	0.4517 secs
  Average:	0.4709 secs
  Requests/sec:	41.2211
  Total data:	128 bytes
  Size/request:	6 bytes

Response time histogram:
  0.452 [1]	|∎∎∎∎∎∎∎
  0.455 [0]	|
  0.458 [0]	|
  0.461 [0]	|
  0.464 [2]	|∎∎∎∎∎∎∎∎∎∎∎∎∎
  0.466 [2]	|∎∎∎∎∎∎∎∎∎∎∎∎∎
  0.469 [1]	|∎∎∎∎∎∎∎
  0.472 [6]	|∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎
  0.475 [3]	|∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎
  0.478 [3]	|∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎
  0.481 [2]	|∎∎∎∎∎∎∎∎∎∎∎∎∎

Latency distribution:
  10% in 0.4626 secs
  25% in 0.4666 secs
  50% in 0.4724 secs
  75% in 0.4759 secs
  90% in 0.4812 secs
  95% in 0.4813 secs

Details (average, fastest, slowest):
  DNS+dialup:	 0.0065 secs, 0.0047 secs, 0.0094 secs
  DNS-lookup:	 0.0048 secs, 0.0031 secs, 0.0067 secs
  req write:	 0.0004 secs, 0.0000 secs, 0.0023 secs
  resp wait:	 0.4638 secs, 0.4455 secs, 0.4757 secs
  resp read:	 0.0001 secs, 0.0000 secs, 0.0002 secs

Status code distribution:
  [200]	12 responses
  [429]	8 responses
