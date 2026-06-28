

# NetScan Pro

## Programación Básica para Redes

## Resumen Técnico del Proyecto

NetScan Pro es una herramienta de diagnóstico de redes de grado profesional para Android, desarrollada íntegramente en Java. Se distingue por su estética inspirada en consolas de administración y por sus potentes capacidades de análisis de infraestructura a alto rendimiento.

## Funcionalidades Principales

- **Barrido de Subred (Subnet Sweep)**: Auditoría de alta velocidad de las 254 direcciones IP de una subred /24 utilizando un `ExecutorService` con 50 hilos en paralelo.
- **Resolución de Nombres (Hostname)**: Motor de descubrimiento que captura los nombres de los dispositivos en la red local cuando la infraestructura lo permite (ej: "Servidor-Admin", "Laptop-Oficina").
- **Monitor de Latencia Dinámico**: Seguimiento en tiempo real con gráficas de rendimiento. Sistema de codificación por colores:
  - Verde: < 50 ms
  - Amarillo: < 150 ms
  - Rojo: > 150 ms
- **Registro Histórico**: Persistencia local mediante SQLite para almacenar auditorías pasadas con fecha y cantidad de hosts activos.
- **Exportación de Reportes**: Generación automática de reportes detallados en formato `.txt` guardados directamente en la carpeta de Descargas del dispositivo.

## Modo Administrativo (Administrative Mode)

La aplicación incluye un entorno visual alternativo diseñado para usuarios avanzados:

- **Activación**: Mediante un patrón de interacción específico en el título principal.
- **Transformación visual**: Cambio del esquema de color de Gris Linux (`#D1D1D1`) a Verde de Alta Visibilidad (`#39d353`).

### Elementos Inmersivos
- Prefijo de comando `user@android:~$` en las pantallas de diagnóstico.
- Cabecera con información del kernel y tiempo de actividad (`uptime`).
- Ventana de créditos interactiva que simula comandos de terminal (`ls`, `cat`, `vim`, etc.).
- **Salida del modo**: Simulación del comando `exit` mediante presión prolongada, restaurando el entorno estándar.

## Arquitectura de Archivos

### Red y Lógica
- `NetworkUtils.java` — Detección de IP y subred.
- `ScanActivity.java` — Gestión de hilos, escaneo y resolución de nombres.
- `DiagActivity.java` — Bucle de monitoreo y generación de gráficas.

### Interfaz y Estética
- `MainActivity.java` — Nodo central de navegación y control del entorno visual.
- `themes.xml` — Definiciones globales de estilo y tipografía.

### Persistencia
- `ScanDatabase.java` — Implementación de la base de datos SQLite.

---

## Autores
- Arcos Alexander
- Valdivia Roger
- Mansillas Jhordan
- Gonzales Bruno
- Portugal Aldeir

## Docente
- Olanda Claudio
