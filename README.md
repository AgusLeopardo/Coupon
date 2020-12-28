# Coupon
Ejercicio Cupón ML

El requerimiento del ejercicio era que, dada una lista de items y un valor de un cupón, se devuelvan los items *unicos* cuya suma no excediera el monto del cupón, junto con el monto total utilizado. No importaba la cantidad de items.

Para el calculo de la mejor utilización del cupón, se utilizó una funcion recursiva. Continua la recursividad hasta llegar al caso en el que la suma de precios sea mayor al valor del cupón, o bien cuando la suma era igual al valor del cupón, ya que no hay posibilidad de mejoría.

La estructura resultante sería, por ej:

![Ejemplo](https://i.gyazo.com/48309cb64409c70c10fd18c03982e30c.png)

# Utilizacion de la API

La API expone un solo endpoint: 
**POST /coupon/**

* Acepta y devuelve application/json.
* Request body
  * List<String> - Lista de IDs de productos.
  * Float - Valor del cupon, no negativo.
* Response body
  * List<String> - IDs de productos seleccionados.
  * Float - Monto de utilización del cupón.
  
## Hosteado en Heroku
URL: https://coupon-exercise.herokuapp.com/coupon/

Ejemplo usando Insomnia.

![Insomnia](https://i.gyazo.com/5f9e968eda38672b0ab959e560e23c8d.png)
