# NetScan Pro

[![Java](https://img.shields.io/badge/Java-17-orange)](https://www.java.com/)
[![Android](https://img.shields.io/badge/Android-14+-blue)](https://developer.android.com/)
[![License](https://img.shields.io/badge/License-MIT-green)](LICENSE)

## Programación Básica para Redes

**NetScan Pro** es una herramienta de diagnóstico de redes para Android, desarrollada íntegramente en **Java** con Android Studio.

---

## 📋 Tabla de Contenidos
- [Descripción](#resumen-técnico-del-proyecto)
- [Funcionalidades Principales](#funcionalidades-principales)
- [Capturas de Pantalla](#capturas-de-pantalla)
- [Tecnologías Utilizadas](#tecnologías-utilizadas)
- [Arquitectura](#arquitectura-del-proyecto)
- [Desarrollado por](#desarrollado-por)

---

## Resumen Técnico del Proyecto

NetScan Pro es una aplicación Android de diagnóstico de redes desarrollada en Java 17. Permite realizar escaneos de red, monitoreo de latencia y registro de resultados.

## Funcionalidades Principales

- **Barrido de Subred**: Escaneo rápido de direcciones IP en una subred /24 utilizando múltiples hilos.
- **Resolución de Nombres de Host**: Descubrimiento de nombres de dispositivos en la red local.
- **Monitor de Latencia**: Visualización en tiempo real con gráficas y código de colores.

## Capturas de Pantalla

<p align="center">
  <img src="screenshots/main_screen.jpg" width="350" alt="Pantalla Principal">
  <img src="screenshots/scan_screen.jpg" width="350" alt="Escaneo de Red">
  <img src="screenshots/latency_graph.jpg" width="350" alt="Monitor de Latencia">
</p>

## Tecnologías Utilizadas

- **Lenguaje**: Java 17
- **IDE**: Android Studio
- **Base de Datos**: SQLite
- **Concurrency**: ExecutorService
- **Interfaz**: XML Layouts

## Arquitectura del Proyecto
      app/
      ├── java/com/netscanpro/
      │   ├── MainActivity.java
      │   ├── ScanActivity.java
      │   ├── DiagActivity.java
      │   ├── NetworkUtils.java
      │   └── ScanDatabase.java
      └── res/
      └── values/themes.xml


## Desarrollado por:

- Arcos Alexander
- Valdivia Roger
- Mansillas Jhordan
- Gonzales Bruno
- Portugal Aldeir

---

**Licencia**  
Proyecto desarrollado con fines académicos bajo la licencia MIT.
