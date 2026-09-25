# Lab 2 Report — Factory Pattern

**Name:** Alexander Ryan
**Course:** CSE 2102, Software Engineering — Prof. Bradford

## 1. Objective

The objective of this lab was to build a pizza factory using the Java factory
pattern and to run it through an interesting scenario. There are four types of
pizza (Cheese, Greek, Pepperoni, and GlutenFree), and making any of them takes
four steps: `prepare()`, `bake()`, `cut()`, and `box()`. The factory had to be
run from a Pizza Store that has a Pizza Factory, and the design had to
illustrate the Liskov Substitution Principle. The work had to be unit tested
with JUnit 4, with the source code and the tests in separate directories.

## 2. Design and Architecture

```
               Pizza  (interface)
                 ▲
           AbstractPizza  (final prepare/bake/cut/box + hook methods)
   ┌───────────┬─────┴───────┬─────────────────┐
CheesePizza  GreekPizza  PepperoniPizza  GlutenFreePizza

PizzaStore ──has a──► PizzaFactory ──creates──► Pizza
```

`Pizza` is the product interface: `getName()`, `getState()`, and the four
steps. As in the slides' Notification example, all code outside the pizza
classes depends only on this interface. `PizzaState` is an enum that tracks
each pizza's progress: ORDERED → PREPARED → BAKED → CUT → BOXED.

`AbstractPizza` implements the interface once for all four pizzas. It stores
the name, dough, sauce, and toppings, and implements the four steps. Each
concrete pizza is a small subclass whose constructor passes in its ingredients.

`PizzaFactory` is the single point of construction. Its `createPizza(String type)`
method turns an order string into a new pizza, and it is the only production
code that names the concrete classes.

`PizzaStore` has a `PizzaFactory`, which it receives through its constructor.
`orderPizza(type)` asks the factory for a pizza, then runs the four steps.
Like `NotificationService` in the slides, `PizzaStore` also holds `main`.
`main` runs the scenario: game night at Husky Pizza, a made-up pizzeria in
Storrs. Six orders come in. Four are made, including a gluten-free pizza for a
student with celiac disease. Two are turned away: a "pineapple" order, and an
empty order from someone who hit Enter too fast.

As in Lab 1, the source code is in `c/c` (package `c`) and the unit tests are
in `u/u` (package `u`).

## 3. Implementation Details

**Factory pattern.** `createPizza` trims and lowercases the order before its
`switch`, so " Cheese " and "GREEK" both work. It also accepts the handout's
spellings, "Peperoni" and "GlutenFree". An unknown type throws
`IllegalArgumentException` with the message "Unknown pizza type: …", which the
scenario prints. Null or blank input throws the same exception. The slide's
factory returns `null` for that case instead, but returning `null` only moves
the failure: the store would crash later, at `pizza.prepare()`, far from the
real mistake. `PizzaStore` still checks for `null`, because it accepts any
`PizzaFactory`, including subclasses.

**Template Method.** The four steps in `AbstractPizza` are `final`. Each step
calls `advance(required, next)` before doing anything else. That method throws
`IllegalStateException` if the pizza is in the wrong state, and otherwise
moves it forward one step. Subclasses change behavior only through two
protected hook methods, `bakeInstructions()` and `cutInstructions()`.
`GreekPizza` bakes in an oiled pan, and `GlutenFreePizza` bakes on a separate
tray and is cut with a dedicated cutter.

**Liskov Substitution.** Every `Pizza` follows the same contract:
- It starts ORDERED.
- The steps run in order, and each one advances the state by one.
- A step called out of order throws `IllegalStateException`.
- After `box()`, the pizza is BOXED.

All four pizzas follow this contract, so `orderPizza` treats them the same
way, with no `instanceof` checks. `GlutenFreePizza` is the clearest example:
it behaves differently from the others, but no code that uses a `Pizza` needs
to know.

The tempting violation is a gluten-free pizza whose crust "crumbles", so its
`cut()` throws `UnsupportedOperationException`. That adds a precondition the
base type doesn't have, so code written for `Pizza` would crash. This was
tested by planting that bug in a scratch copy, which first required removing
`final`. The scenario crashed at order 4, and only the gluten-free tests
failed. In the real code, `final` makes this violation a compile error. The
same principle applies to the factory: the store works with any `PizzaFactory`
subclass, and one test relies on that.

## 4. Test Strategy and Edge Cases

There are 42 tests in three classes. As in Lab 1, a `@Before` method creates
fresh objects before each test, so no test depends on another.

- `PizzaFactoryTests` (12 tests): each order string produces exactly the right
  class. The tests use `assertEquals` on `getClass()`, which is stricter than
  `instanceof`. Every order also gets a new object.
- `PizzaStoreTests` (6 tests): an ordered pizza comes back BOXED and of the
  requested type, and bad orders are rejected. One test gives the store an
  anonymous `PizzaFactory` subclass that always returns a `GlutenFreePizza`.
  This proves the store uses the factory it was given.
- `PizzaContractTests` (6 tests × 4 types = 24): the Liskov test. It is written
  only against `Pizza`, and JUnit's `Parameterized` runner runs it once for
  each pizza type. It checks the starting state, each step's transition, and
  the illegal sequences. If one type broke the contract, only that type's
  results would fail.

Edge cases covered:
- orders that are `null`, `""`, or `"   "`
- unknown types
- mixed case and extra spaces, and the handout's spellings
- `"PEPPERONI"` under a Turkish locale (see Technology below)
- baking before preparing, which must also leave the state unchanged
- boxing the same pizza twice
- a factory that returns `null`

Two mutation checks were run on scratch copies to confirm the tests can catch
real bugs. Removing `Locale.ROOT` made only the Turkish-locale test fail.
Making `GlutenFreePizza.cut()` throw made only the four tests that involve
gluten-free pizza fail.

## 5. Conclusion and Analysis

The system meets the requirements. It has four pizza types, a factory run by
a store that owns it, a scenario, and 42 passing tests. One of the test
classes checks Liskov substitution directly. Adding a pizza takes one new
class and one new `case`.

That `case` is the main weakness. A simple factory is not fully open/closed,
because every new pizza means editing the `switch`. A map from names to
constructors would avoid that, and so would the Factory Method pattern, with
one store subclass per region.

The pizzas also print directly to `System.out`. This means the tests can't
check the printed text, and the test runs are noisy. Passing a `PrintStream`
into the pizzas would fix both problems.

Finally, pizza types are plain strings. That fits a scenario where customers
type in their orders, but an enum would catch typos at compile time.

## AI Disclosure

**AI system used:** Claude Code (Anthropic), running the Claude Opus 5.5 model.

**How it was used:**
- Claude read the handout, the slides, and my Lab 1 project, and proposed the
  design.
- It wrote all the Java code other than the PizzaFactory class, and it peer-reviewed my unit tests.
- It ran the mutation checks.
- It wrote the README and a draft of this report.

**AI-generated code:** Each file starts with a comment saying so, and `AI_PROMPT_LOG.md` has a file-by-file table.

**Prompts:** Located in AI_PROMPT_LOG.md

## Technology not covered in class

- **JUnit `Parameterized` runner.** `@RunWith(Parameterized.class)` tells JUnit
  to run the whole test class once for each row returned by the `@Parameters`
  method. Each row is passed to the test class's constructor; here, a row is a
  pizza type string. The setting `name = "{0}"` labels the results, for
  example `startsOrderedTest[greek]`.
- **`assertThrows` with lambdas.** JUnit 4.13 added this assertion. For example,
  `assertThrows(IllegalStateException.class, () -> pizza.box())` passes only if
  that exact call throws that exception. It returns the exception, so the test
  can also check the message. Unlike `@Test(expected = …)`, it can't pass by
  accident because an earlier setup line threw.
- **`Locale.ROOT`.** `toLowerCase()` normally follows the computer's language
  settings. In Turkish, a capital I lowercase to a dotless "ı", so
  "PEPPERONI" would become "pepperoni" and wouldn't match any case.
  `Locale.ROOT` makes the conversion the same on every machine.
- **`final` methods and hook methods (Template Method pattern).** A `final`
  method can't be overridden, so the base class controls the order of the
  steps. Subclasses change the details by overriding `protected` methods that
  the base class calls.
- **Varargs.** `String... toppings` lets each pizza pass any number of
  toppings. Inside the method, `toppings` is an array.
- **Anonymous subclasses.** `new PizzaFactory() { … }` defines and creates a
  one-off subclass inline. The tests use this to swap in a different factory.
- **Mutation checking.** Planting a bug on purpose to confirm that a test
  fails. This shows the tests would catch that kind of bug.
