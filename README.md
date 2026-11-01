# Proyecto de Testing Unitario con TestNG

Proyecto de automatización de pruebas en **Java 17** con **Maven** y **TestNG**, que valida datos leídos desde archivos **Excel** y **JSON**. Incluye logging con **Log4j2**, listeners de TestNG, *data providers*, agrupación de tests (smoke / regresión) y reportes con **Allure** y **Surefire**.

Desarrollado como parte del curso *Master Class de Appium 2 con Java absolutamente desde 0* (Blass Academy, Udemy).

---

## Tecnologías

| Herramienta | Versión | Uso |
|---|---|---|
| Java | 17 | Lenguaje |
| Maven (wrapper incluido) | — | Build y dependencias |
| TestNG | 7.12.0 | Framework de testing |
| Poiji | 5.4.0 | Mapeo de hojas Excel a objetos Java |
| Jackson Databind | 3.2.3 | Deserialización de JSON a objetos Java |
| Log4j2 | 2.26.1 | Logs en archivos |
| Allure TestNG | 3.0.1 | Reporte de resultados |
| Allure Maven plugin | 3.1.0 | Generación y servidor del reporte |
| AspectJ Weaver | 1.9.7 | Agente requerido por Allure |
| Maven Surefire | 3.6.0 | Ejecución de tests |

---

## Estructura del proyecto

```
proyectoTestUnitario
├── src
│   ├── main
│   │   ├── java
│   │   │   ├── modelos        # POJOs: Monstruo, User, Address, Company, Geo
│   │   │   └── reader         # ExcelReader (Poiji) y JsonReader (Jackson)
│   │   └── resources
│   │       ├── data           # dataExcel.xlsx y user.json (datos de prueba)
│   │       └── log4j2.xml     # Configuración de logs
│   └── test
│       ├── java
│       │   ├── data           # CustomData: DataProvider de TestNG
│       │   ├── listeners      # TestListeners y SuiteListeners
│       │   ├── principal      # Clases de test: ExcelMonstruoTests, JsonUserTests
│       │   └── utilities      # BaseTest y Logs (wrapper de Log4j2)
│       └── resources
│           ├── allure.properties
│           └── logs           # informativeLogs.log, debugLogs.log, traceLogs.log
├── runSuite.sh                # Ejecuta la suite de regresión + reporte Surefire
├── openSurfireReport.sh       # Abre el reporte Surefire
├── mvnw / mvnw.cmd            # Maven wrapper (Linux-Mac / Windows)
└── pom.xml
```

---

## Qué se prueba

### `ExcelMonstruoTests`: datos desde Excel
Lee la hoja `monstruos` de `dataExcel.xlsx` y la mapea a una lista de `Monstruo` con Poiji.

| Test | Validación | Severidad | Grupos |
|---|---|---|---|
| `primerTest` | El nombre del 1er monstruo es `TOLOSA` | Blocker | regresion, smoke |
| `segundoTest` | La lista tiene 14 elementos | Critical | regresion, smoke |
| `tercerTest` | El nivel del 3er monstruo es 22 | Minor | regresion, smoke |
| `cuartoTest` | Todos los campos del último monstruo (soft asserts) | Normal | regresion, smoke |
| `quintoTest` | Edad > 0 y nivel < 100 para **cada** monstruo (DataProvider) | Trivial | regresion |

### `JsonUserTests`: datos desde JSON
Lee `user.json` y lo deserializa a un objeto `User` (con `Address`, `Geo` y `Company` anidados) usando Jackson.

| Test | Validación | Severidad | Grupos |
|---|---|---|---|
| `primerTest` | El `id` es positivo | Critical | regresion, smoke |
| `segundoTest` | `address.geo.lng` es 71.7478 | Minor | regresion, smoke |
| `tercerTest` | `company.bs` tiene más de 10 caracteres | Critical | regresion, smoke |
| `cuartoTest` | Nombre, id, username y website (soft asserts) | Blocker | regresion, smoke |

En total, la suite de regresión ejecuta **22 casos** (9 tests, uno de ellos parametrizado con los 14 monstruos).

---

## Conceptos aplicados

- **Herencia de configuración:** todas las clases de test extienden `BaseTest`, que inicializa un `SoftAssert` nuevo antes de cada test y registra los listeners.
- **Hard vs. soft asserts:** `Assert` corta el test en el primer fallo; `SoftAssert` acumula todas las validaciones y las reporta juntas con `assertAll()`.
- **Data Driven Testing:** `CustomData` expone un `@DataProvider` que alimenta `quintoTest` con cada fila del Excel.
- **Grupos:** los tests se etiquetan como `smoke` y/o `regresion` para poder ejecutar subconjuntos.
- **Listeners:** `TestListeners` y `SuiteListeners` registran en los logs el inicio, éxito, fallo y fin de cada test y suite.
- **Logging por nivel:** Log4j2 escribe tres archivos (`info`, `debug`, `trace`) en `src/test/resources/logs`, que se sobrescriben en cada ejecución.
- **Reportes con metadata:** `@Description` y `@Severity` de Allure documentan cada test en el reporte.

---

## Requisitos

- **JDK 17** o superior
- **Git** (en Windows incluye **Git Bash**, recomendado para usar los scripts `.sh`)
- No hace falta instalar Maven: el proyecto incluye el **Maven wrapper**

---

## Cómo ejecutarlo

### Linux / Mac / Git Bash en Windows

```bash
# Ejecutar todos los tests
./mvnw clean test

# Ejecutar solo un grupo
./mvnw clean test -Dgroups="smoke"
./mvnw clean test -Dgroups="regresion"

# Suite de regresión + reporte Surefire (script del proyecto)
chmod +x runSuite.sh openSurfireReport.sh   # solo la primera vez
./runSuite.sh
./openSurfireReport.sh
```

### Windows con PowerShell o CMD

Los scripts `.sh` no se ejecutan en PowerShell (y `chmod` no existe en Windows). Usá directamente los comandos equivalentes con `mvnw.cmd`:

```powershell
# Ejecutar todos los tests
.\mvnw.cmd clean test

# Ejecutar solo un grupo
.\mvnw.cmd clean test "-Dgroups=smoke"

# Equivalente a runSuite.sh
.\mvnw.cmd clean test "-Dgroups=regresion" surefire-report:report

# Equivalente a openSurfireReport.sh
start target\reports\surefire.html
```

> **Tip para IntelliJ en Windows:** en *File → Settings → Tools → Terminal → Shell path* poné `C:\Program Files\Git\bin\bash.exe` para que la terminal integrada use Git Bash y los scripts funcionen igual que en Mac/Linux.

---

## Reporte de Allure

Los resultados se guardan en `target/allure-results` (configurado en `src/test/resources/allure.properties`). Primero se ejecutan los tests y después se genera el reporte:

```bash
./mvnw clean test
./mvnw allure:serve
```

En PowerShell:

```powershell
.\mvnw.cmd clean test
.\mvnw.cmd allure:serve
```

`allure:serve` arma el reporte y lo abre en el navegador. La primera vez descarga Allure automáticamente (en la carpeta `.allure`).

---

## Notas y problemas comunes

- **`allure.properties` debe estar directamente en `src/test/resources`.** Si está en una subcarpeta, Allure lo ignora y guarda los resultados en `allure-results` (raíz), y `allure:serve` no los encuentra.
- **`@Description` debe importarse de `io.qameta.allure`.** IntelliJ suele sugerir `jdk.jfr.Description`, que Allure ignora.
- **Ejecutar `clean` antes de generar el reporte** evita que Allure mezcle resultados de corridas anteriores (aparecen como *retries*).
- **`testFailureIgnore=true`** en Surefire hace que el build no se corte si falla un test, para poder generar igual los reportes.
- **AspectJ y Java 17:** la versión 1.9.7 está pensada hasta Java 16. Si el agente da errores al ejecutar, subir `aspectj.version` a una versión más reciente (por ejemplo `1.9.22.1`) en el `pom.xml`.

---

## Autor

**Gustavo**: proyecto de práctica de automatización de pruebas con Java, TestNG y Allure.
