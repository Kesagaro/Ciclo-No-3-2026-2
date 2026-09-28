# slotMachine — DOPO-POOB · Ciclo 3 · 2026-2

> Tercer ciclo del simulador de tragamonedas inspirado en el **Problem I – Slot Machine** de la ICPC World Finals 2025.  
> En este ciclo se implementa la resolución automática del problema de la maratón y su simulación visual.

---

## Autores

| Nombre | Correo |
|---|---|
| Kevin Garzón Romero | kevin.garzon-r@mail.escuelaing.edu.co |
| Daniel Blanco Salazar | daniel.blanco-s@mail.escuelaing.edu.co |

---

## Descripción del Ciclo 3

Los ciclos 1 y 2 construyeron el simulador gráfico de la tragamonedas.  
En este ciclo se agregan dos capacidades nuevas:

1. **Resolver el problema** (`solve`): dado `n`, encontrar automáticamente los giros necesarios para que todas las ruedas muestren el mismo símbolo (jackpot), usando solo las operaciones permitidas.
2. **Simular la solución** (`simulate`): ejecutar el mismo algoritmo en una máquina visible para que el usuario pueda verlo animado en pantalla.

---

## Requisitos funcionales del Ciclo 3

| # | Caso de uso | Método | Estado |
|---|---|---|---|
| 13 | Crear máquina con n ruedas y n símbolos | `SlotMachine(n)` | [x] (máx. 50 ruedas) |
| 14 | Resolver el problema de la maratón | `SlotMachineContest.solve(n)` | [ ] Implementado, **no alcanza el jackpot** |
| 15 | Simular la solución visualmente | `SlotMachineContest.simulate(n)` | [ ] Implementado, **no alcanza el jackpot** |

> **Bloqueo actual:** `SlotMachine.distinctSymbols()` devuelve el número de símbolos definidos (siempre `n`), no los distintos visibles. El algoritmo depende de ese conteo, así que nunca detecta que una rueda quedó alineada. Detalle en [`fallos.md`](fallos.md).

---

## Cambios respecto al Ciclo 2

### `SlotMachine.java` — Constructor nuevo

Se agregó el constructor `SlotMachine(int n)` que crea una máquina completa con `n` ruedas y `n` símbolos de colores distintos, cada rueda en una posición aleatoria. La máquina queda en modo invisible.

También se agregó internamente una paleta de 50 colores CSS predefinidos para cubrir máquinas de hasta 50 ruedas.

### `SlotMachineContest.java` — Clase nueva

Contiene la lógica para resolver y simular el problema:

- **`solve(n)`** — retorna la secuencia de acciones `{rueda, pasos}` para alcanzar el jackpot. La máquina es invisible. Solo usa: `SlotMachine(n)`, `spin(rueda, pasos)` y `distinctSymbols()`.
- **`simulate(n)`** — ejecuta el mismo algoritmo en una máquina visible con animación. Al terminar, oculta la máquina.

---

## Algoritmo de solve

### Diseño previsto

La rueda 1 se toma como referencia fija. Para cada rueda de la 2 a la n:

1. Se gira un paso a la vez durante `n` pasos (vuelta completa).
2. Tras cada paso, se consulta `distinctSymbols()`.
3. Se anota en qué paso se obtuvo el menor número de símbolos distintos.
4. Si en algún paso se llega a 1 símbolo distinto, se retorna inmediatamente (jackpot).
5. Tras la vuelta completa, se avanza la rueda al mejor paso encontrado.

El número de acciones es del orden de n²: como máximo `n²−1`, es decir 2499 para `n=50` (límite: 10 000).

### Comportamiento real hoy

Como `distinctSymbols()` siempre devuelve `n`, las condiciones `d == 1` y `d < bestDistinct` nunca se cumplen. Cada rueda da una vuelta completa de `n` pasos y vuelve a su posición inicial; `solve(n)` devuelve `n·(n−1)` acciones `{rueda, 1}` (n=5 → 20) y la máquina no llega a jackpot. `simulate(n)` anima esos mismos giros y cierra la ventana sin banner.

### Limitaciones adicionales

- Aun corrigiendo `distinctSymbols()` (contar colores distintos de `configuration()`), el algoritmo toma la primera posición que mejora estrictamente; una coincidencia con una rueda no alineada produce el mismo descenso y puede elegir mal. No hay reintento ni prueba que lo verifique.
- `solve` y `simulate` no validan `n < 2`.
- Con `n > 50` la paleta se repite y `addSymbol` rechaza los colores duplicados.
- `Canvas` solo pinta un conjunto fijo de nombres CSS (más `#rrggbb`); el resto sale negro.

---

## Estructura del proyecto (Ciclo 3)

```
Ciclo3/
├── Documents/
│   ├── README_Ciclo3.md           ← Este archivo
│   ├── README.MD                  ← README de los Ciclos 1 y 2
│   ├── documentacion.md           ← Guía técnica detallada del Ciclo 3
│   ├── fallos.md                  ← Fallos conocidos del código
│   ├── diagramas.md               ← Explicación de los diagramas
│   ├── diagramCode.md             ← Relación diagramas ↔ código
│   ├── DOPO-I03-2026-02.md        ← Documento de requisitos
│   ├── BlancoS-GarzonR.txt        ← URL del repositorio para Moodle
│   └── Guia-Retrospectiva.md      ← Guía de retrospectiva
└── slotMachine/
    ├── SlotMachine.java               ← Modificado: nuevo constructor SlotMachine(n)
    ├── SlotMachineContest.java        ← NUEVO: solve(n) y simulate(n)
    ├── SlotMachineContestTest.java    ← NUEVO: 12 pruebas propias del Ciclo 3
    ├── SlotMachineContestCTest.java   ← NUEVO: 2 pruebas compartidas del Ciclo 3
    ├── Wheel.java                     ← Sin cambios
    ├── Symbol.java                    ← Sin cambios
    ├── SlotMachineView.java           ← Sin cambios
    ├── Canvas.java                    ← Sin cambios
    ├── SlotMachineC2Test.java         ← Sin cambios (Ciclo 2)
    └── SlotMachineCC2Test.java        ← Sin cambios (Ciclo 2)
```

---

## Cómo ejecutar

### Requisitos previos

- **Java SE 8** o superior.
- **BlueJ 5.x**.

### Probar solve

1. Abrir el proyecto en BlueJ y compilar todo.
2. En el Object Bench, crear un objeto: clic derecho sobre `SlotMachineContest` → `new SlotMachineContest()`.
3. Llamar `solve(5)` sobre el objeto.
4. BlueJ mostrará el arreglo de acciones. Hoy son 20 acciones `{rueda, 1}` que no producen jackpot.

### Probar simulate

1. Sobre el mismo objeto, llamar `simulate(5)`.
2. Se abre una ventana con 5 ruedas y las ruedas 2 a 5 giran una vuelta completa cada una. **Hoy no aparece el banner de jackpot** (comportamiento previsto pendiente de corregir).

### Ejecutar pruebas unitarias

1. Crear un objeto de `SlotMachineContestTest`.
2. Llamar cada método que empiece por `should` con `assert` activo (`-ea`).
3. Las 2 pruebas de `simulate` abren la ventana. Ninguna prueba comprueba el jackpot, por lo que pasan con el fallo actual.

---

## Mini-ciclos del Ciclo 3 (8 – 19 septiembre)

| # | Descripción | Criterio de completitud | Estado |
|---|---|---|---|
| 1 | **Análisis y planificación** — Lectura del DOPO, revisión de los ciclos anteriores, diseño del algoritmo y definición de los 7 mini-ciclos. | Plan documentado y aprobado. | [x] |
| 2 | **Constructor `SlotMachine(n)`** — Crea n ruedas y n símbolos con colores distintos e inicialización aleatoria. | La máquina se crea correctamente y `distinctSymbols()` retorna n en la mayoría de casos. | [x] |
| 3 | **`solve(n)`** — Implementa el algoritmo de alineación rueda por rueda. | Retorna una secuencia no vacía de acciones válidas dentro del límite de 10 000. | [~] Cumple el criterio escrito, pero la secuencia no lleva al jackpot |
| 4 | **`simulate(n)`** — Misma lógica que `solve` pero con la máquina visible y animada. | La ventana se abre, las ruedas giran con animación y el jackpot se muestra. | [ ] Anima, pero el jackpot no se muestra |
| 5 | **`SlotMachineContestTest`** — 12 pruebas unitarias propias del Ciclo 3. | Todos los casos pasan sin errores con `assert` activo. | [~] Pasan, pero no verifican el jackpot |
| 6 | **`SlotMachineContestCTest`** — 2 pruebas compartidas siguiendo la convención de nombres. | Las 2 pruebas pasan y están listas para compartir. | [~] Pasan, pero no verifican el jackpot |
| 7 | **Integración y entrega** — Revisión de comentarios, documentación, publicación en Git y entrega en Moodle. | Repositorio actualizado antes del 19 de septiembre. | [ ] Documentación actualizada; `.txt` de Moodle creado; falta corregir el fallo y republicar |
| 8 | **Corrección de `distinctSymbols()` y pruebas de jackpot** — Contar colores distintos visibles y agregar pruebas que apliquen la solución y comprueben `isJackpot()`. | `solve` y `simulate` llegan a jackpot en pruebas con varios `n`. | [ ] Pendiente |

---

## Retrospectiva

### Ciclo 3

**1. ¿Cuáles fueron los mini-ciclos definidos? Justifíquenlos.**

Se definieron 7 mini-ciclos organizados por responsabilidad incremental: análisis, constructor con inicialización aleatoria, algoritmo de resolución, simulación visual, pruebas propias, pruebas compartidas e integración final. Esta estructura permitió probar cada pieza de forma aislada antes de integrarla con las demás, reduciendo el riesgo de romper lo que ya funcionaba.

**2. ¿Cuál es el estado actual del proyecto en términos de mini-ciclos? ¿Por qué?**

Los mini-ciclos 1 y 2 están completos. Los mini-ciclos 3 a 6 tienen código y pruebas, pero `solve` y `simulate` no alcanzan el jackpot por el fallo de `distinctSymbols()`, y las pruebas no lo detectan. El mini-ciclo 7 está a medias (documentación y `.txt` listos; falta corregir y republicar) y se agregó el mini-ciclo 8 para la corrección. Los requisitos 14 y 15 siguen sin cumplirse.

**3. ¿Cuál fue el tiempo total invertido por cada uno de ustedes? (Horas/Hombre)**

| Integrante | Horas |
|---|---|
| Kevin Garzón Romero | ~14 h |
| Daniel Blanco Salazar | ~14 h |
| **Total** | **~28 h** |

**4. ¿Cuál consideran fue el mayor logro? ¿Por qué?**

Plantear el algoritmo usando únicamente `distinctSymbols()` como señal de retroalimentación, sin acceso al estado interno de la máquina, y separar las responsabilidades entre `SlotMachine` y `SlotMachineContest` respetando las reglas de diseño del DOPO. El algoritmo aún no funciona en la práctica (ver punto 5).

**5. ¿Cuál consideran que fue el mayor problema técnico? ¿Qué hicieron para resolverlo?**

El mayor problema fue que `distinctSymbols()` devuelve el número de símbolos definidos (siempre `n`) en lugar de los distintos visibles. Con ese valor el algoritmo de `solve` nunca detecta una rueda alineada y no llega al jackpot; las pruebas no lo detectaron porque solo validan la forma del arreglo. Se documentó con traza en `fallos.md`. La corrección (contar colores distintos de `configuration()`) y las pruebas que comprueben `isJackpot()` quedan pendientes. Un problema secundario es la ambigüedad por empates entre posiciones, que puede llevar a elegir mal incluso con el conteo corregido.

**6. ¿Qué hicieron bien como equipo? ¿Qué se comprometen a hacer para mejorar los resultados?**

La comunicación durante el ciclo fue fluida y la planificación con mini-ciclos nos ayudó a mantener el foco. También logramos cumplir con todas las reglas de diseño del DOPO sin mezclar responsabilidades entre `SlotMachine` y `SlotMachineContest`. Falló que las pruebas solo validaran la forma del resultado y no el objetivo (jackpot), lo que ocultó el fallo principal. Para mejorar, nos comprometemos a escribir primero pruebas que comprueben el resultado real (desarrollo guiado por pruebas) y a revisar el comportamiento de cada método que usa el algoritmo antes de darlo por terminado.

**7. Considerando las prácticas XP incluidas en los laboratorios, ¿cuál fue la más útil? ¿Por qué?**

La práctica de **diseño simple e incremental** fue la más útil en este ciclo. En lugar de tratar de construir el algoritmo completo desde el principio, lo construimos paso a paso: primero el constructor, luego el algoritmo de una sola rueda, luego el ciclo completo, y finalmente la simulación visual. Eso nos permitió verificar cada paso antes de avanzar.

**8. ¿Qué referencias usaron? ¿Cuál fue la más útil? Incluyan citas con estándares adecuados.**

| # | Referencia | Uso |
|---|---|---|
| 1 | ICPC Foundation. (2025). *ICPC World Finals 2025 — Problem I: Slot Machine*. https://icpc.global | Comprensión del problema de la maratón que se debía resolver. |
| 2 | Oracle. (s.f.). *java.util.Random (Java SE 8)*. https://docs.oracle.com/javase/8/docs/api/java/util/Random.html | Inicialización aleatoria de ruedas en el constructor `SlotMachine(n)`. |
| 3 | Oracle. (s.f.). *ArrayList (Java SE 8)*. https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html | Construcción dinámica de la secuencia de acciones en `solve`. |
| 4 | Kolling, M. & Barnes, D. J. (2016). *Objects First with Java* (6.ª ed.). Pearson. | Referencia de diseño OO y pruebas con BlueJ. |
| 5 | Google DeepMind. (2026). *Antigravity (Gemini 2.5 Pro)* [IA]. https://deepmind.google | Asistente utilizado para resolver dudas sobre el algoritmo y la implementación. |

La más útil fue **la documentación del problema ICPC**, ya que definió con precisión las operaciones permitidas y el límite de 10 000 acciones que guiaron todo el diseño.

---

## Tecnologías

| Tecnología | Uso |
|---|---|
| **Java SE 8** (BlueJ) | Lenguaje y entorno de desarrollo |
| **java.util.Random** | Inicialización aleatoria del constructor `SlotMachine(n)` |
| **java.util.ArrayList** | Almacenamiento dinámico de acciones en `solve` |
| **javax.swing / java.awt** | Ventana gráfica y animaciones (heredado de Ciclos 1 y 2) |
| **Astah** | Diagramas de clases actualizados para el Ciclo 3 |
| **Git** | Control de versiones del proyecto (URL en `BlancoS-GarzonR.txt`) |
| **Google DeepMind Antigravity** | Asistente de IA para dudas técnicas |

