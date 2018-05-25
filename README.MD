# poc-jackson

## Endpoint que utiliza um DTO genérico e usa o atributo @class para indicar qual a classe converter.
curl -d '{ "animal" : { "@class" : "com.andersonlfeitosa.jackson.Dog", "name" : "Anderson DOG", "barkVolume" : 2.0 }}' -H "Content-Type: application/json" -X POST http://localhost:8080/api1

## Endpoint que utiliza um DTO genérico e usa o atributo @class para indicar qual a classe converter.
curl -d '{ "animal" : { "name" : "Anderson DOG", "barkVolume" : 2.0 }}' -H "Content-Type: application/json" -X POST http://localhost:8080/api1

## Endpoint que utiliza um DTO especifico que herda propriedades do DTO genérico
curl -d '{ "name" : "anderson", "myProperty" : "myValue" }' -H "Content-Type: application/json" -X POST http://localhost:8080/api2
