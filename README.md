# NetScan Pro - Herramienta de Diagnóstico de Redes

NetScan Pro es una aplicación nativa para Android desarrollada en **Java**, diseñada para administradores de sistemas y profesionales de redes. Ofrece una interfaz técnica inspirada en consolas Linux y capacidades de análisis de infraestructura de alto rendimiento.

## 🚀 Características Principales

### 1. Exploración de Subred (Subnet Sweep)
*   **Escaneo Paralelo**: Utiliza un `ExecutorService` con 50 hilos concurrentes para auditar una subred /24 en segundos.
*   **Identificación Avanzada**: Resuelve direcciones IP y captura el `hostname` (nombre del dispositivo) mediante consultas DNS locales dinámicas.
*   **Exportación de Datos**: Genera reportes técnicos detallados en formato `.txt` almacenados en el directorio público de Descargas.

### 2. Monitor de Latencia (Ping Monitor)
*   **Telemetría en Tiempo Real**: Visualización dinámica mediante `LineChart` (MPAndroidChart) para detectar micro-cortes o congestión.
*   **Codificación por Colores (Semáforo)**:
    *   🟢 **Óptimo**: < 50ms
    *   🟡 **Moderado**: 50ms - 150ms
    *   🔴 **Crítico/Timeout**: > 150ms

### 3. Gestión de Auditorías (Historial)
*   **Persistencia Local**: Base de datos **SQLite** integrada para el registro histórico de escaneos realizados.
*   **Control Total**: Permite visualizar y realizar limpiezas periódicas de los registros de auditoría.

## 🛠️ Detalles Técnicos y Arquitectura

### Interfaz de Usuario (UI/UX)
*   **Aesthetic Linux Console**: Esquema de colores Gris Plata (#D1D1D1) sobre Negro Profundo (#0d1117) para maximizar el contraste.
*   **Tipografía**: Optimizada para lectura de logs y datos técnicos.
*   **Iconografía Adaptativa**: Sistema 100% vectorial (Vector Drawables) para asegurar nitidez absoluta en cualquier densidad de pantalla (xxxhdpi).

### Sistema de Atajos de Visualización (Administrative Mode)
La herramienta incluye una capa avanzada de visualización para operarios de red:
*   **Activación**: Patrón de interacción técnico mediante múltiples taps en la cabecera principal.
*   **Persistencia**: Estado global gestionado mediante `SharedPreferences`.
*   **Transformación**: Cambio dinámico del entorno al color verde hacker y desbloqueo de consola de créditos interactiva con comandos shell (`ls`, `cat`, `vim`, `exit`).

## 📁 Estructura del Proyecto

*   `NetworkUtils.java`: Motor de detección de infraestructura local.
*   `ScanDatabase.java`: Gestor de persistencia SQLite.
*   `HostAdapter.java`: Adaptador optimizado para listas de alta densidad.
*   `MainActivity.java`: Controlador de navegación y lógica de entorno.

---
**Proyecto Final de Programación Móvil para Redes - 2026**
*Desarrollado con estándares de ingeniería de software para entornos de red profesionales.*
