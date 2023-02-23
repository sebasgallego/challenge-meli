# Challenge search app!!

Aplicación para buscar productos y ver su detalle, oteniendo la información desde las APIs que mercado libre tiene disponibles para la comunidad, desarrollada bajo la arquitectura MVVM que hace referencia a una forma de organizar el código de nuestra aplicación, la cual mejora y facilita la intervención del código en caso de que se presente algún error o debamos realizarle mantenimiento al software.
 
## Funcionalidades

La app cuenta con las siguientes funcionalidades:
 1. Campo de búsqueda. 
2. Visualización de resultados de la búsqueda. 
3. Detalle de un producto.

Casos de uso:

[![Screenshot-2023-02-20-at-9-48-16-PM.png](https://i.postimg.cc/4d2tb6J7/Screenshot-2023-02-20-at-9-48-16-PM.png)](https://postimg.cc/47tmXhHs)



## Arquitectura MVVM


MVVM se compone por tres capas:

-   **Modelo** : contiene los datos de la aplicación. No puede hablar directamente con la Vista. Generalmente, se recomienda exponer los datos a ViewModel a través de Observables.
-   **Vista** : representa la interfaz de usuario. Observa el ViewModel.
-   **ViewModel** : Actúa como enlace entre el Modelo y la Vista. Es responsable de transformar los datos del Modelo. Proporciona flujos de datos a la Vista. También usa ganchos o devoluciones de llamada para actualizar la Vista.

[![Screenshot-2023-02-21-at-10-47-42-PM.png](https://i.postimg.cc/jjJWpDYm/Screenshot-2023-02-21-at-10-47-42-PM.png)](https://postimg.cc/yJsY9Ydy)

## Test

Resultados de pruebas:

[![Screenshot-2023-02-21-at-10-58-58-PM.png](https://i.postimg.cc/qRV9L5GS/Screenshot-2023-02-21-at-10-58-58-PM.png)](https://postimg.cc/KKfqcQwD)

## Firebase crashlytics

Se implementa en el proyecto crashlytics para capturar los crash que tenga la app y poder tener una mayor información y control de los mismos, adicional de agrega Firebase.crashlytics.recordException(e) para obtener cualquier error inesperado que ocurra al obtener la información de APIs.

Ejemplo:

[![Screenshot-2023-02-21-at-11-27-55-PM.png](https://i.postimg.cc/zf3VYp4x/Screenshot-2023-02-21-at-11-27-55-PM.png)](https://postimg.cc/Mc2Wy06B)


## Android Profiler

Resultados de pruebas en Android Profiler que ayuda a identificar fugas y pérdidas de memoria que puedan generar inestabilidades, fallas e incluso bloqueos en la app:

[![Screenshot-2023-02-21-at-2-33-43-PM.png](https://i.postimg.cc/bJwktCjf/Screenshot-2023-02-21-at-2-33-43-PM.png)](https://postimg.cc/sBbMFJXT)


## Resumen

- Introducción.
- Tipo de arquitectura utilizada.
- Resultados test.
- Firebase crashlytics.
-  Android Profiler
