# Almundo Call
Aplicación en SpringBoot para el manejo de llamadas, ejercicio propuesto como prueba técnica

##Aspectos generales de la aplicaión
1. Desarrollado con SpringBoot
2. Se intento seguir una arquitectura en cebolla
3. Se usa base de datos MongoDB
4. Uso de Repositorios de Spring data
5. Unit Test
6. cobertura del 86%
7. se limita el numero maximo de threads del servidowe web a 10 como se plantea en el ejercicio
8. Se crea una prieba de integración para verificar que se permite el llamado concurrente de 10 peticiones (AlmundoCallApplicationTests)
9. para el caso de que no existan empleados disponibles para atender las llamadas se retorna un codigo de error 404, para indicar al IVR que debe esperar un momento para volver a intentarlo o simplemente indicarle al cliente que lo intente de nuevo en unos minutos

##Clonar repositorio de GitHub
```json
git clone https://github.com/hectormao/almundoCall
```
##Construir el proyecto
1. limpiar el proyecto
```bash
mvn clean
```
2. establecer conexión a un MongoDB dispoinble: editar el archivo *application.properties* y establecer el host y el puerto al mongo en las propiedades
```bash
   vim src/main/resources/application.properties
```
editar el las propiedades con las que corresponden
```
spring.data.mongodb.host=172.17.0.2
spring.data.mongodb.port=27017
spring.data.mongodb.database=almundoCall
```
3. empaquetar el proyecto
```bash
mvn package
```
4. correr el proyecto
```
   java -jar target/almundoCall-0.0.1-SNAPSHOT.jar
```

## Endpoints
El proyecto expone un api rest con 2 metodos 
###Iniciar una llamada
/call/start
```bash
curl -X GET \
  http://127.0.0.1:8080/call/start \
  -H 'Cache-Control: no-cache' \
  -H 'Postman-Token: 2993d138-7288-491e-aa28-97428a837027'
```
* Json respuesta en caso de Exito
```json
{
    "attendant": {
        "id": "5b5283b657b93bba58796f47",
        "identity": "1234",
        "name": "First Employee",
        "rank": {
            "level": 1,
            "name": "operator"
        },
        "state": "BUSY"
    },
    "call": {
        "id": "5b5283e652e8d84448081d99",
        "employeeId": "5b5283b657b93bba58796f47",
        "startDate": "2018-07-21T00:52:54.692+0000",
        "finishDate": null,
        "notes": null
    },
    "message": "Your call is answered"
}
```
* Json respuesta en caso de Fallo
```json
{
    "timestamp": "2018-07-21T00:44:30.556+0000",
    "status": 404,
    "error": "Not Found",
    "message": "No Employees available, please try again in a few minutes",
    "path": "/call/start"
}
```
###Finalizar una llamada
/call/finish/{callId}
```bash
curl -X GET \
  http://127.0.0.1:8080/call/finish/5b5283e652e8d84448081d99 \
  -H 'Cache-Control: no-cache' \
  -H 'Postman-Token: cbe78a81-cd71-4e8e-b131-68594ae7536e'
```
* Objeto de respuesta en exito
```json
{
    "attendant": {
        "id": "5b5283b657b93bba58796f47",
        "identity": "1234",
        "name": "First Employee",
        "rank": {
            "level": 1,
            "name": "operator"
        },
        "state": "FREE"
    },
    "call": {
        "id": "5b5283e652e8d84448081d99",
        "employeeId": "5b5283b657b93bba58796f47",
        "startDate": "2018-07-21T00:52:54.692+0000",
        "finishDate": "2018-07-21T00:57:57.384+0000",
        "notes": null
    },
    "message": "your call was finished"
}

```
* Objeto de respuesta en fallo
```json
{
    "timestamp": "2018-07-21T00:43:33.011+0000",
    "status": 404,
    "error": "Not Found",
    "message": "The active call with id: [5b5283e652e8d84448081d99] not exists",
    "path": "/call/finish/5b5283e652e8d84448081d99"
}
```