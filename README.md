# Extra/plus
1) Si no hay empleados disponibles se encolan algunas y luego se comienza a rechazar llamdas.

2) En este momento, rechazo las llamadas, pero tengo un RejectionHandler que podra modificar para ir limpiando la cola de workers y eliminar las más viejas.

3) Hay tests unitarios para probar como rechazan tareas o que pasa si una se demora más de lo deseado. Se hicieron en groovy por comodidad

4) No veo necesaria tanta documentación por se sencillo
