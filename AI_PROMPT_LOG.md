# AI Prompt Log — Lab 2: Factory Pattern

**AI system used:** Claude Code (Anthropic), running the Claude Opus 5.5 model in the Claude desktop app with the "Learning" output style. Every prompt below comes from one session on September 24, 2026.

## Which code came from AI

| File | Origin |
|---|---|
| `c/c/Pizza.java` | AI-generated. I later removed its contract comment. |
| `c/c/PizzaState.java` | AI-generated |
| `c/c/AbstractPizza.java` | AI-generated |
| `c/c/CheesePizza.java`, `GreekPizza.java`, `PepperoniPizza.java`, `GlutenFreePizza.java` | AI-generated |
| `c/c/PizzaStore.java` | AI-generated, including the game-night scenario in `main` |
| `2102 Lab 2.iml`, `.idea/libraries/junit_4_13_2.xml` | AI-generated IntelliJ configuration |
| `junit-4.13.2.jar`, `hamcrest-core-1.3.jar` | Unmodified JUnit 4 jars, copied by Claude from my Lab 1 project |
| `README.md` | Written by Claude |
| `LAB_REPORT.md` | Drafted by Claude |

Each AI-generated Java file also starts with a comment saying so.

# 1.)
Attaching the lab handout (`LAB-02 (1).pdf`) and the Factory Pattern slides (`FactoryPatternJava (1).pdf`), I said: "I am working in IntelliJ, we need to complete this lab for class that's due tonight"

Claude read both PDFs, then read my Lab 1 project so that Lab 2 would follow the same conventions:
- source code in `c/c/` (package `c`) and unit tests in `u/u/` (package `u`)
- the JUnit jars at the project root
- plain `javac`/`java` commands instead of a build tool
- the same README, lab report, and prompt log formats

It proposed this design:

- A `Pizza` interface (`getName`, `getState`, `prepare`, `bake`, `cut`, `box`). The store and the factory depend only on this interface, following the slides' point about depending on a common interface.
- A `PizzaState` enum: ORDERED → PREPARED → BAKED → CUT → BOXED.
- An `AbstractPizza` base class whose four steps are `final` and enforce that order: calling a step out of order throws `IllegalStateException`. Subclasses change only the baking and cutting details, through protected "hook" methods (the Template Method pattern), so no subclass can skip or reorder a step.
- Four concrete pizzas. `GlutenFreePizza` bakes on a separate tray and cuts with a dedicated cutter, but still follows the same contract. It is the main Liskov substitution example.
- `PizzaFactory.createPizza(String)`, modeled on the `NotificationFactory` slide, with the body left for me to write.
- A `PizzaStore` that has a `PizzaFactory` (passed into its constructor), an `orderPizza` method, and a `main` method running a "UConn game night" scenario, including two orders it has to turn away.
- JUnit 4 tests, including a parameterized "contract" test that runs the same checks against every pizza type.

Claude also explained that the slide's factory returns `null` for empty input, which only moves the failure somewhere else: the program would crash later, at `pizza.prepare()`, far from the actual bad input.

# 2.)
I approved Claude's plan.

Claude then:
- copied the jars, added JUnit as an IntelliJ library scoped to tests only, and updated `2102 Lab 2.iml` to mark `u` as the test sources root
- wrote every class in `c/c/`, leaving only the body of `createPizza()` unfinished
- compiled with `javac -Xlint:all` (no warnings) and ran a throwaway smoke test, which is not part of my submission. It showed that all four pizzas end up BOXED, that out-of-order steps throw `IllegalStateException`, and that the store works with a substitute factory.

It explained three things along the way:
- Giving JUnit TEST scope stops production code in `c/` from importing it.
- One `advance()` check in `AbstractPizza` catches every out-of-order call, including repeating a step.
- Passing the factory into the store's constructor is what lets a different factory be substituted, which is Liskov substitution on the factory side.

Claude then asked me to write the body of `createPizza()` myself. It left these decisions to me:
- whether null or blank input returns `null` (like the slide) or throws `IllegalArgumentException`
- whether to normalize input with `trim().toLowerCase()`
- what messages to give for unknown types
- optionally, whether to accept the handout's spelling "peperoni"