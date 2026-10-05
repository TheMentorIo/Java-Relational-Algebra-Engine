# JRA — Java Relational Algebra Engine

JRA is a Java-based relational algebra engine designed to process relational algebra expressions through a structured query-processing pipeline.

The project is being developed as a learning and systems-engineering project, with emphasis on **relational algebra, query representation, parsing, execution, and query optimization**.

## Project Architecture

The planned query-processing pipeline is:

```text
Relations
   ↓
Relational Algebra Expression
   ↓
Parser
   ↓
Expression Tree
   ├── Direct Execution ──────────→ Executor
   │
   └── Query Optimization
              ↓
        Optimized Expression Tree
              ↓
           Executor
              ↓
        Result Relation
```

The parser produces a common expression tree.

From that tree, JRA supports two execution paths:

* **Direct mode** — executes the parsed expression tree without optimization.
* **Optimization mode** — transforms the expression tree into an optimized tree and then executes it.

The optimizer is responsible for **transforming the query**, not executing it.

This separation allows the same query to be executed using both strategies and their results to be compared for correctness.

---

## Current Features

The project currently contains the foundational relational data model:

* `Attribute`
* `Schema`
* `Tuple`
* `Relation`

### Attribute

Represents an attribute/column in a relation.

Each attribute contains:

* attribute name
* Java `Class<?>` representing its value type
* value validation

Example:

```java
Attribute id = new Attribute("id", Integer.class);
Attribute name = new Attribute("name", String.class);
```

### Schema

Represents the ordered structure of a relation.

A schema:

* stores attributes in order
* prevents duplicate attribute names
* supports attribute lookup by name or index
* provides attribute indexes
* exposes a read-only attribute collection

Example:

```java
Schema schema = new Schema();

schema.addAttribute(new Attribute("id", Integer.class));
schema.addAttribute(new Attribute("name", String.class));
schema.addAttribute(new Attribute("year", Integer.class));
```

### Tuple

Represents one row of a relation.

A tuple:

* references its relation's schema
* stores its values
* validates values against the schema
* supports access by index or attribute name
* allows `null` values

Example:

```java
Tuple tuple = new Tuple(
    schema,
    Arrays.asList(1, "Ahmed", 4)
);
```

### Relation

Represents a relational-algebra relation consisting of:

* relation name
* schema
* set of tuples

`Relation` uses a `LinkedHashSet` for tuples, providing set semantics while maintaining deterministic iteration order.

Example:

```java
Relation students = new Relation("Student", schema);

students.addTuple(tuple);
```

---

## Planned Features

The project will be developed incrementally.

### 1. Relational Algebra Expression Model

Support for relational algebra operations such as:

* Selection
* Projection
* Rename
* Cartesian Product
* Join
* Union
* Difference

Expressions will be represented as an expression tree rather than being executed directly by the parser.

### 2. Expression Parser

A parser will convert textual relational algebra expressions into the internal expression tree.

Example concept:

```text
π[name](σ[year > 2](Student))
```

becomes a tree representing:

```text
Projection
    |
 Selection
    |
 Student
```

### 3. Query Execution

The executor will evaluate an expression tree and produce a `Relation`.

For example:

```text
Expression Tree
      ↓
   Executor
      ↓
Result Relation
```

### 4. Query Optimization

JRA will contain an optimizer capable of transforming a logical expression tree into an equivalent, more efficient tree.

Possible optimization techniques include:

* Selection pushdown
* Projection pushdown
* Predicate simplification
* Join ordering
* Elimination of unnecessary operations

The optimizer will preserve query semantics while attempting to reduce execution cost.

### 5. Direct vs Optimized Execution

JRA will support executing the same expression in two ways:

```text
Original Expression
       │
       ├──────────────→ Direct Executor
       │                       ↓
       │                 Direct Result
       │
       └→ Optimizer
              ↓
       Optimized Expression
              ↓
        Optimized Executor
              ↓
        Optimized Result
```

The results can then be compared to verify that optimization preserves correctness.

### 6. Testing and Evaluation

The project will include unit and integration tests covering:

* data-model correctness
* parser correctness
* expression-tree construction
* relational operators
* optimizer transformations
* direct execution
* optimized execution
* equivalence of optimized and non-optimized queries

---

## Project Structure

The current project structure is:

```text
JRA/
├── pom.xml
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── jra/
│   │               └── model/
│   │                   ├── Attribute.java
│   │                   ├── Schema.java
│   │                   ├── Tuple.java
│   │                   └── Relation.java
│   │
│   └── test/
│       └── java/
│           └── com/
│               └── jra/
│                   └── model/
│                       ├── AttributeTest.java
│                       ├── SchemaTest.java
│                       ├── TupleTest.java
│                       └── RelationTest.java
│
└── target/
```

As development continues, additional packages will be introduced for the parser, expression representation, execution, and optimization layers.

---

## Technology Stack

* **Java 21**
* **Maven**
* **JUnit 6**
* Git / GitHub

---

## Building the Project

Clone the repository and enter the project directory:

```bash
git clone <repository-url>
cd JRA
```

Compile the project:

```bash
mvn compile
```

Run the test suite:

```bash
mvn test
```

Perform a clean build and run all tests:

```bash
mvn clean test
```

---

## Development Philosophy

JRA is intentionally structured as a sequence of independent layers rather than one large implementation.

The main design principle is:

```text
Data Model
    ↓
Expression Representation
    ↓
Parser
    ↓
Execution
    ↓
Optimization
    ↓
Evaluation
```

Each layer should have a clear responsibility and should be testable independently.

In particular:

* The **model** represents relational data.
* The **parser** converts syntax into an internal representation.
* The **expression tree** represents what should be executed.
* The **executor** evaluates an expression tree.
* The **optimizer** transforms an expression tree into an equivalent optimized form.
* The **tests** verify both individual components and end-to-end correctness.

---

## Status

**Current stage:** Relational data model

Completed:

* [x] `Attribute`
* [x] `Schema`
* [x] `Tuple`
* [x] `Relation`
* [x] Unit tests for the current model
* [x] Maven/JUnit test setup

Next:

* [ ] Tuple equality and hashing
* [ ] Schema equality and hashing
* [ ] Relational algebra expression model
* [ ] Expression tree
* [ ] Parser
* [ ] Direct executor
* [ ] Query optimizer
* [ ] Optimized executor
* [ ] End-to-end evaluation

---

## License

This project is currently developed as a personal/academic project.
