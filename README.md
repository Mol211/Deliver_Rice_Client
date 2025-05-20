# Deliver_Rice
## Proyecto Final de Victor Molins Martinez (FPGS DAM)
### Aplicación Mobil basada en un E-Commerce que consume su propio Backend para ofrecer un servicio de Delivery basado en un restaurante local especializado en Arroces de calidad.

- [Descripcion](#Descripcion)
- [Características](#Características)
- [Tecnologías y Herramientas](#Tecnologías_y_Herramientas)
- [Instalación](#Instalación)


## 📝 Descripcion
El proyecto consiste en una aplicación Mobil basada en una plataforma E-Commerce que consume su propiop Backend y ofrece un servicio de Delivery basado en un restaurante local especializado en Arroces de Calidad. Una alternativa local para todo aquel que quiera tener comida de calidad al servicio de comida rápida.

## ✨ Características
- Registro y Login del Usuario en una base de datos local.
- Diferenciar los usuarios según sus roles (Administrador, Cliente y Repartidor)
- El usuario puede crear, eliminar y modificar sus direcciones.
- El administrador puede crear, eliminar y modificar productos y actualizar disponibilidad de stock.
- Al realizar el pedido el usuario puede elegir distintos tipos de pago o direcciones que tenga asociada a su cuenta.
- La aplicación permite el control total del ciclo de vida del pedido a través de sus interfaces gráficas, cada Rol tiene una funcionalidad:
    - Cliente: Escoge productos del catálogo, los introduce en la cesta, crea el Pedido.
    - Administrador: Confirma el pedido, asigna un pedido a un repartidor.
    - Repartidor: Marca el pedido como completado al entregarlo al cliente.

## 🛠️ Tecnologías y Herramientas para la aplicación Android
- Java 21 (JDK)
- SDK 33
- Librerías:
    - Retrofit y kHttp para consumir nuestro backend.
    - Glide para el manejo de archivos de imágenes
    - Dagger Hilt para la inyección de dependencias en nuestra aplicación.
- Patrón de diseño MVVM.
- Layout XML

## Instalación

### Requisitos de la instalación:

    - JDK 21
    - Android Studio (versión utilizada para el proyecto: Android Studio Ladybug Feature Drop | 2024.2.2 Patch 1)
    - Min SDK 33.
1. Seguir las instrucciones del Readme de la aplicación `Servidor Deliver-Rice` para:
    - Iniciar nuestro puerto MySql.
    - Iniciar el Backend.
    - Importar el script .sql que contiene la base de datos que se encuentra en el zip del proyecto, `bd_service_deliveryrice.sql`
3. Iniciar nuestro emulador Android con el SDK 33 mínimo o conectar nuestro Dispositivo Físico.
4. Buscar el archivo `app-debug.apk` en la ruta `app < build < outputs < apk < debug` y arrastrar a nuestro dispositivo o emulador para iniciar la instalación.

### Credenciales para iniciar la apliación:

| Email | Password |
|----------|----------|
| admin@admin.com    | admin   
| repartidor@repartidor.com    | repartidor   
| cliente@cliente.com    | cliente   






