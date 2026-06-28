# NetScan Pro

[![Java](https://img.shields.io/badge/Java-17-orange)](https://www.java.com/)
[![Android](https://img.shields.io/badge/Android-Android_Studio-blue)](https://developer.android.com/studio)

## Programación Básica para Redes

**NetScan Pro** es una herramienta de diagnóstico de redes de grado profesional para Android, desarrollada íntegramente en **Java** con Android Studio. Destaca por su interfaz inspirada en consolas de administración y por sus potentes capacidades de análisis de infraestructura.

---

## 📋 Tabla de Contenidos
- [Descripción](#resumen-técnico-del-proyecto)
- [Funcionalidades](#funcionalidades-principales)
- [Modo Administrativo](#modo-administrativo)
- [Tecnologías Utilizadas](#tecnologías-utilizadas)
- [Requisitos](#requisitos)
- [Instalación](#instalación)
- [Arquitectura](#arquitectura-de-archivos)
- [Autores](#autores)
- [Docente](#docente)

---

## Resumen Técnico del Proyecto

NetScan Pro es una herramienta de diagnóstico de redes de grado profesional para Android, desarrollada íntegramente en Java. Se distingue por su estética inspirada en consolas de administración y por sus potentes capacidades de análisis de infraestructura a alto rendimiento.

## Funcionalidades Principales

- **Barrido de Subred (Subnet Sweep)**: Auditoría de alta velocidad de las 254 direcciones IP de una subred /24 utilizando un `ExecutorService` con 50 hilos en paralelo.
- **Resolución de Nombres (Hostname)**: Motor de descubrimiento que captura los nombres de los dispositivos en la red local cuando la infraestructura lo permite.
- **Monitor de Latencia Dinámico**: Seguimiento en tiempo real con gráficas y sistema de codificación por colores (Verde <50ms, Amarillo <150ms, Rojo >150ms).
- **Registro Histórico**: Persistencia local mediante SQLite.
- **Exportación de Reportes**: Reportes detallados en formato `.txt` guardados en Descargas.

## Modo Administrativo (Administrative Mode)

Entorno visual alternativo para usuarios avanzados con estética de terminal Linux.

- Activación mediante patrón en el título principal.
- Cambio de tema a Verde de Alta Visibilidad (`#39d353`).
- Simulación de comandos de terminal (`user@android:~$`, `ls`, `cat`, `vim`, etc.).
- Salida mediante presión prolongada (`exit`).

## Tecnologías Utilizadas

- **Lenguaje**: Java 17
- **IDE**: Android Studio
- **Base de Datos**: SQLite
- **Hilos**: ExecutorService (Multithreading)
- **Interfaz**: XML Layouts + Custom Themes
- **Gráficas**: Custom Views o MPAndroidChart (si aplica)

## Requisitos

- Android 8.0 (Oreo) o superior
- Permisos: `INTERNET`, `ACCESS_NETWORK_STATE`, `ACCESS_WIFI_STATE`

## Instalación

1. Clona este repositorio:
   ```bash
   git clone https://github.com/tuusuario/netscan-pro.git
