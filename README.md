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
| 13 | Crear máquina con n ruedas y n símbolos | `SlotMachine(n)` | [x] |
| 14 | Resolver el problema de la maratón | `SlotMachineContest.solve(n)` | [x] |
| 15 | Simular la solución visualmente | `SlotMachineContest.simulate(n)` | [x] |

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

La rueda 1 se toma como referencia fija. Para cada rueda de la 2 a la n:

1. Se gira un paso a la vez durante `n` pasos (vuelta completa).
2. Tras cada paso, se consulta `distinctSymbols()`.
3. Se anota en qué paso se obtuvo el menor número de símbolos distintos.
4. Si en algún paso se llega a 1 símbolo distinto, se retorna inmediatamente (jackpot).
5. Tras la vuelta completa, se avanza la rueda al mejor paso encontrado.

El número de acciones es del orden de n², lo que para `n=50` equivale a aproximadamente 5000 acciones (límite: 10 000).

---

## Estructura del proyecto (Ciclo 3)

```
Ciclo3/
├── Documents/
│   ├── README.MD                  ← Este archivo
│   ├── documentacion.md           ← Guía técnica detallada del Ciclo 3
│   ├── DOPO-I03-2026-02.md        ← Documento de requisitos
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
4. BlueJ mostrará el arreglo de acciones resultante.

### Probar simulate

1. Sobre el mismo objeto, llamar `simulate(5)`.
2. Se abrirá una ventana con 5 ruedas. Cada rueda se alineará una a una hasta mostrar el banner dorado de jackpot.

### Ejecutar pruebas unitarias

1. Crear un objeto de `SlotMachineContestTest`.
2. Llamar cada método que empiece por `should` y verificar que no lanza errores (con `assert` activo).

---

## Mini-ciclos del Ciclo 3 (8 – 19 septiembre)

| # | Descripción | Criterio de completitud | Estado |
|---|---|---|---|
| 1 | **Análisis y planificación** — Lectura del DOPO, revisión de los ciclos anteriores, diseño del algoritmo y definición de los 7 mini-ciclos. | Plan documentado y aprobado. | [x] |
| 2 | **Constructor `SlotMachine(n)`** — Crea n ruedas y n símbolos con colores distintos e inicialización aleatoria. | La máquina se crea correctamente y `distinctSymbols()` retorna n en la mayoría de casos. | [x] |
| 3 | **`solve(n)`** — Implementa el algoritmo de alineación rueda por rueda. | Retorna una secuencia no vacía de acciones válidas dentro del límite de 10 000. | [x] |
| 4 | **`simulate(n)`** — Misma lógica que `solve` pero con la máquina visible y animada. | La ventana se abre, las ruedas giran con animación y el jackpot se muestra. | [x] |
| 5 | **`SlotMachineContestTest`** — 12 pruebas unitarias propias del Ciclo 3. | Todos los casos pasan sin errores con `assert` activo. | [x] |
| 6 | **`SlotMachineContestCTest`** — 2 pruebas compartidas siguiendo la convención de nombres. | Las 2 pruebas pasan y están listas para compartir. | [x] |
| 7 | **Integración y entrega** — Revisión de comentarios, documentación, publicación en Git y entrega en Moodle. | Repositorio actualizado antes del 19 de septiembre. | [ ] |

---

## Retrospectiva

### Ciclo 3

**1. ¿Cuáles fueron los mini-ciclos definidos? Justifíquenlos.**

Se definieron 7 mini-ciclos organizados por responsabilidad incremental: análisis, constructor con inicialización aleatoria, algoritmo de resolución, simulación visual, pruebas propias, pruebas compartidas e integración final. Esta estructura permitió probar cada pieza de forma aislada antes de integrarla con las demás, reduciendo el riesgo de romper lo que ya funcionaba.

**2. ¿Cuál es el estado actual del proyecto en términos de mini-ciclos? ¿Por qué?**

Los mini-ciclos del 1 al 6 están completados. El mini-ciclo 7 (integración y entrega final) está en proceso: el código está listo, pero falta actualizar el repositorio Git y generar el archivo `.txt` para Moodle. El avance es del ~85 %.

**3. ¿Cuál fue el tiempo total invertido por cada uno de ustedes? (Horas/Hombre)**

| Integrante | Horas |
|---|---|
| Kevin Garzón Romero | ~14 h |
| Daniel Blanco Salazar | ~14 h |
| **Total** | **~28 h** |

**4. ¿Cuál consideran fue el mayor logro? ¿Por qué?**

Diseñar e implementar el algoritmo de resolución usando únicamente `distinctSymbols()` como señal de retroalimentación fue el mayor logro. El reto estuvo en no poder ver los símbolos directamente y tener que inferir la alineación correcta solo a través del conteo de símbolos distintos, lo que requirió pensar el problema de forma diferente a como se resolvería si se tuviera acceso directo al estado interno de la máquina.

**5. ¿Cuál consideran que fue el mayor problema técnico? ¿Qué hicieron para resolverlo?**

El mayor problema fue la ambigüedad del algoritmo de `solve` cuando dos posiciones de una rueda producen el mismo número de símbolos distintos (empate). En algunos casos el algoritmo elige la posición incorrecta y no logra el jackpot en una sola pasada. Se documentó esta limitación con claridad en el código y en la documentación técnica. Para los casos de prueba generales con n ≥ 4 y configuraciones aleatorias, el algoritmo converge de manera confiable.

**6. ¿Qué hicieron bien como equipo? ¿Qué se comprometen a hacer para mejorar los resultados?**

La comunicación durante el ciclo fue fluida y la planificación con mini-ciclos nos ayudó a mantener el foco. También logramos cumplir con todas las reglas de diseño del DOPO sin mezclar responsabilidades entre `SlotMachine` y `SlotMachineContest`. Para mejorar, nos comprometemos a iniciar los ciclos con pruebas escritas antes del código (desarrollo guiado por pruebas) para detectar problemas de diseño más temprano.

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
| **Git** | Control de versiones del proyecto |
| **Google DeepMind Antigravity** | Asistente de IA para dudas técnicas |

