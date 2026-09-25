# CSE 2102 — Lab 2: Factory Pattern

A pizza store for UConn game night, built with the Java factory pattern and
unit tested with JUnit 4.

## Contents

```
.
├── c/c/                          source code (package c)
│   ├── Pizza.java                interface every pizza implements
│   ├── PizzaState.java           ORDERED → PREPARED → BAKED → CUT → BOXED
│   ├── AbstractPizza.java        shared prepare/bake/cut/box; enforces the order
│   ├── CheesePizza.java
│   ├── GreekPizza.java
│   ├── PepperoniPizza.java
│   ├── GlutenFreePizza.java
│   ├── PizzaFactory.java         the factory: order string → new Pizza
│   └── PizzaStore.java           has a PizzaFactory; main class (the scenario)
├── u/u/                          unit tests (package u)
│   ├── PizzaFactoryTests.java
│   ├── PizzaStoreTests.java
│   └── PizzaContractTests.java   same tests run against every pizza type
├── junit-4.13.2.jar
├── hamcrest-core-1.3.jar
├── LAB_REPORT.md
├── AI_PROMPT_LOG.md
└── Lab 2 Working Proof.mov       video of the system running
```

## Requirements

- JDK 14 or later (developed against OpenJDK 24). `PizzaFactory` uses a
  switch expression, which became a standard part of Java in version 14.
- The two JAR files above, included in this directory

## Build

From the project root:

```
javac -cp junit-4.13.2.jar -d out c/c/*.java u/u/*.java
```

This compiles the source code and the tests into `out/`, using the package
layout (`out/c/` and `out/u/`).

## Run

```
java -cp out c.PizzaStore
```

Expected output:

```
=== Husky Pizza, Storrs CT: UConn game night ===

Order 1 for the pep band: "cheese"
  Preparing Cheese Pizza (thin New Haven-style crust, crushed tomato sauce, mozzarella, grated pecorino)
  Baking 10 minutes at 650 degrees F in the coal-fired oven
  Cutting into 8 triangle slices
  Boxing Cheese Pizza

Order 2 for the student section: "greek"
  Preparing Greek Pizza (thick pan crust, oregano tomato sauce, feta, kalamata olives, red onion, spinach)
  Baking 18 minutes at 475 degrees F in an oiled pan
  Cutting into 8 triangle slices
  Boxing Greek Pizza

Order 3 for a dorm floor: "pepperoni"
  Preparing Pepperoni Pizza (thin New Haven-style crust, crushed tomato sauce, mozzarella, pepperoni)
  Baking 10 minutes at 650 degrees F in the coal-fired oven
  Cutting into 8 triangle slices
  Boxing Pepperoni Pizza

Order 4 for a student with celiac disease: "gluten-free"
  Preparing Gluten-Free Pizza (rice-flour gluten-free crust, crushed tomato sauce, mozzarella, fresh basil)
  Baking 14 minutes at 450 degrees F on a separate gluten-free tray
  Cutting into 6 squares with the dedicated gluten-free cutter
  Boxing Gluten-Free Pizza

Order 5 for a visiting fan: "pineapple"
  Sorry, we can't make that: Unknown pizza type: pineapple

Order 6 for someone who hit Enter too fast: ""
  Sorry, we can't make that: No pizza type was given

=== Game night totals: 4 pizzas boxed, 2 orders turned away ===
```

## Test

Run all three test classes:

```
java -cp "junit-4.13.2.jar:hamcrest-core-1.3.jar:out" org.junit.runner.JUnitCore u.PizzaFactoryTests u.PizzaStoreTests u.PizzaContractTests
```

All 42 tests should pass, and the run ends with `OK (42 tests)`. Several
tests make real pizzas, so their preparation steps are printed while the
tests run. That output is expected, and the tests don't check it.

## Running in IntelliJ

1. Mark `c` as **Sources Root** and `u` as **Test Sources Root**. Right-click
   each one and choose Mark Directory as.
2. Add both JARs as a library: right-click each JAR, then choose Add as Library.
3. Run the scenario with the green arrow next to `main` in `PizzaStore.java`.
   Run the tests by right-clicking `u` and choosing Run 'All Tests'.

## Notes on the classpath

Classpath entries are separated by `:` on macOS and Linux, and by `;` on
Windows. `junit-4.13.2.jar` provides the annotations, the assertions, and the
`JUnitCore` runner. `hamcrest-core-1.3.jar` provides the matchers that JUnit
depends on. Both must be on the classpath when the tests run, along with `out`.
# 2102-Lab-2
