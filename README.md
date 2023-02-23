
# Challenge search app!!

Aplicación android para buscar productos y ver su detalle, la información se carga desde las APIs que mercado libre tiene disponibles para la comunidad, desarrollada bajo la arquitectura MVVM que hace referencia a una forma de organizar el código de nuestra aplicación, mejora y facilita la intervención del código en caso de que se presente algún error o debamos realizarle mantenimiento al software.
 
## Funcionalidades

La app cuenta con las siguientes funcionalidades:
 1. Campo de búsqueda. 
 2. Visualización breve de resultados de la búsqueda con imagen y titulo.
2. Visualización de resultados de la búsqueda con mas detalle. 
3. Detalle total del un producto. 

Casos de uso:

[![Screenshot-2023-02-20-at-9-48-16-PM.png](https://i.postimg.cc/4d2tb6J7/Screenshot-2023-02-20-at-9-48-16-PM.png)](https://postimg.cc/47tmXhHs)


## Estructura

Se desarrollo bajo la arquitectura MVVM que se compone por tres capas:

- **Modelo** : contiene los datos de la aplicación. No puede hablar directamente con la vista. Generalmente, se recomienda exponer los datos a ViewModel a través de Observables.

- **Vista** : representa la interfaz de usuario. Observa el ViewModel.

- **ViewModel** Actúa como enlace entre el Modelo y la Vista. Es responsable de transformar los datos del Modelo. Proporciona flujos de datos a la vista. También usa ganchos o devoluciones de llamada para actualizar la Vista.

[![Screenshot-2023-02-22-at-11-55-30-PM.png](https://i.postimg.cc/0Nnnj03B/Screenshot-2023-02-22-at-11-55-30-PM.png)](https://postimg.cc/jWWPkPRH)

Construida con el componente navigation, que utiliza un gráfico de navegación para administrar el aspecto de la app:

[![Screenshot-2023-02-23-at-12-04-22-AM.png](https://i.postimg.cc/sgQCpvym/Screenshot-2023-02-23-at-12-04-22-AM.png)](https://postimg.cc/CRg20MRn)

 
## Test

Resultado de pruebas:

[![Screenshot-2023-02-22-at-11-39-05-PM.png](https://i.postimg.cc/R0gw5y1F/Screenshot-2023-02-22-at-11-39-05-PM.png)](https://postimg.cc/4KhYp8Sk)

## Firebase crashlytics

Se implementa en el proyecto crashlytics para capturar los crash que tenga la app y poder tener una mayor información y control de los mismos, adicional se agrega Firebase.crashlytics.recordException(e) para obtener cualquier error inesperado que ocurra al obtener la información de las APIs.

Ejemplo:

[![Screenshot-2023-02-21-at-11-27-55-PM.png](https://i.postimg.cc/zf3VYp4x/Screenshot-2023-02-21-at-11-27-55-PM.png)](https://postimg.cc/Mc2Wy06B)


## Android Profiler

Resultado de pruebas en Android Profiler, que ayuda a identificar fugas y pérdidas de memoria que puedan generar inestabilidades, fallas e incluso bloqueos en la app:

[![Screenshot-2023-02-21-at-2-33-43-PM.png](https://i.postimg.cc/bJwktCjf/Screenshot-2023-02-21-at-2-33-43-PM.png)](https://postimg.cc/sBbMFJXT)


## Resumen

- Introducción.
- Tipo de arquitectura utilizada.
- Resultados test.
- Firebase crashlytics.
-  Android Profiler.
