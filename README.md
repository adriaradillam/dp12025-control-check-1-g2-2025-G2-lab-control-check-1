# 🧩 Control Check 1 – Group G2 (English Version)

> ### ⚠️ You can find the [Spanish version here](Control%20Check%201%20-%20G2.pdf)

## 🧾 Statement

In this exercise, you will add chess problem management functionality within a web-based chess application.

A `ChessProblem` class is provided, representing a chess challenge or puzzle that users of the application can solve. Each problem is based on a specific chessboard position (`ChessBoard`) and a defined goal (e.g., *“checkmate in two moves”*). Each problem has one or more possible solutions represented by the `ChessProblemSolution` class, consisting of a final board (`solutionBoard`) and an expected move count (`expectedMoveCount`). Problems can also have a *source* (e.g., a book or publication), represented by `ProblemSource`. Attempts by users (`User`) to solve problems are represented by the `ChessProblemAttempt` class.

The following UML diagram describes the classes and relationships:

![Class diagram](https://kroki.io/plantuml/svg/eNqVVW1P2zAQ_p5fYbEPwEZR-mHTxABBC5OQOglR-AFX-9J4OHZmO7Bu2n_fOWlSJ0uR1i-W7-W557k7p1fOg_VVoZJDtoCNqXyiMPPMG2blOvdMSIvcS6MT9yx1CRYKxhU4d-29lavK4x03eil_IUujEJeDMK9Sr1kGymHkEaVk049pkkuBzHm0aPymxObOpeUKEyKz7DyTFTgUjBtlbAA8snS7CAwLKL-wlaqQrqAsgtgEW4nieMiW_U4YmwF_XltTaTEPYOfnD7c3l5fsXZZlaZaGAGMF2r6T85R-Y9mzxdNtnZ5mn7JsmN56U_j4mUPyJ4i6aH9sHkih21mShua9NSuFxdJUliNrSQTyH5a-lq-hwOgKlc-NjQwCHbeyrEe2s9KMPHB_W4BUgUtTbZ6jc11JVYWkN4suDAd1Ax4fZUHTopZ7FNc-uFbGKATNCqllASpONh5duN9pj2u0DH-WtFMovpkXnFND_Tgj1jVxjIrUYTmzTPJK-c0CXzCuuTYw1DkzYMUAsyVdKzH2sbJ6RGZlLWofnMvwWuLE7_Cjwl2he4k0tphuvdvxHFRvWqVxX6XCuDtkegD9PN4TenVYlP7t1vTpu5Zz35zRnFy-q_LkqHaMVpFhsGwlBb7Skg-3-QEVhN1xuSzjnT5s95lNLlnT_6P09HQaHu_0OOkN-yA4DtiEIg_ojEZ2xlbhDCVb-RR0r2BDjAOebvBOeu4t7D7_kM0Jjaag1sugo0-tzQoM9Y5h3bAzVtY8BlrIfT6ZtBljYJTYGPaWIoz3b3eje7L79MYB_yG4Sxso7mkcF9BL3avAbaNmrZLBZ49yerU6UeE8TvrRMcl_-73j6UKh7UcgNKx-qkfTHWxEcTvADrEJJqhwuuQKtaD_zL91FU74)

Classes marked in **red** are to be mapped as JPA entities; **blue** classes are already mapped but will be used in this control.


## 🧪 Instructions

Your first task in this control will be to clone the repository. If you are using the classroom computers, you will need to use a GitHub authentication token as a password. A help document explaining how to configure this token is included in the repository itself. Next, you should import the project into your preferred development environment and start working on the exercises listed below. When importing the project, you may initially see compilation errors — do not worry. These errors will gradually disappear as you implement the required exercises for this control.

The functionality implemented in each of the exercises will be validated by unit tests. Tests can be run via your IDE or using:

```bash
./mvnw test
```

Each exercise is worth **2 points**. The number of test cases vary from one exercise to the other. Your score for each exercise depends on the percentage of passing tests. For instance:

| Test Success | Score |
|---------------|--------|
| 100% | 2.0 |
| 50% | 1.0 |
| 10% | 0.2 |


### 📤 Submission

The submission of the control check will be carried out in **two steps:**
1.	**Submit the activity in the EV platform associated with the control check, providing as text the URL of your personal repository.** You can do this at the beginning of the session.
2.	**Perform a single git push to your individual repository** (the one containing this document) once you have completed all the exercises.

Remember that **you must push your work before logging out of the computer and leaving the classroom;** otherwise, your attempt will be graded as not submitted.


### ⚠️ Important Notes

1. **Do not change class names, method names, return types, or parameters.**  
   Tests rely on exact signatures.
2. **Do not modify unit tests.**  
   Local edits to unit tests will be ignored; your repo will be re-tested from scratch.
3. As long as exercises remain incomplete, **some tests will fail** — this is expected. 
   Only projects with a grade of 10 points run all tests without failing.
4. **Git usage is part of your grade.** You cannot ask for support in using git or GitHub.
5. Projects that **do not compile or fail to start Spring** will receive **0 points**.
6. Except for **Exercise 5** (depends on 4), exercises are **independent**. You can solve them in any order.

---

## 🧩 Test 1 – Modify the `ChessProblemAttemptService` so that it does not allow saving invalid games (business rule) (2 points)

Modify the `save` method in the game management service (`ChessProblemAttemptService`) so that it throws an `UnfeasibleAttemptException` if an attempt is saved that violates any of the following business rules:

- A player cannot have another **active attempt** (i.e., an attempt where `finish == null`) for the same problem, except if the attempt is the one being saved.  
  *(You can use the helper method in `ChessProblemAttemptRepository`.)*
- The start time must be **before** the finish time (if `finish` is not null).

The method must be **transactional** and roll back if the exception is thrown.



## 🧩 Test 2 – Creation of `ChessProblem` Service and Repository, and `ChessProblemAttempt` Controller

### Part A – Service and Repository (1 point)
Modify the `ChessProblemService` class and the `ChessProblemRepository` interface so that they function as a Spring business logic service and a Spring Data repository, respectively. 

In addition, you must provide an implementation for the service methods using the repository, which should be injected by Spring. This implementation must include the following methods:

| Method | Description |
|--------|--------------|
| `getProblemsByDifficultyAndNumPieces(Integer difficulty, Integer numPieces)` | All problems with difficulty ≥ given value and fewer pieces than `numPieces`. |
| `getAll()` | All problems. |
| `getById()` | Problem by ID. |
| `delete()` | Delete a problem. |

All these methods must be **transactional at the method level**, not at the class level.

The repository must implement any queries required to support these operations.


### Part B – `ChessProblemAttemptController` (1 point)
Configure the `ChessProblemAttemptController` class as a REST controller exposing:

| Method | Endpoint | Description |
|--------|-----------|-------------|
| `GET` | `/api/v1/attempts` | Returns all attempts |
| `GET` | `/api/v1/attempts/{id}` | Returns an attempt by ID, or `404 NOT FOUND` |

Access must be **restricted to users with authority `"PLAYER"`**. Other users must receive **403 FORBIDDEN**.



## 🧩 Test 3 – React Component for Attempt Listing

Modify `frontend/src/attempts/index.js` to render attempts fetched from `/api/v1/attempts`.

**If attempts exist:**  
Render a table with these columns (in order):  
→ **Name**, **Start**, **Finish**, **Player**, **Problem**, **Difficulty**

**If no attempts exist:**  
Render an `<h1>` with the text `NO ATTEMPTS` instead of the table (no empty table must be rendered).

To run this test and check its result, go to the frontend folder and execute the command:

```bash
npm test
```
Then press “a” in the Jest command menu to run all tests.

Note that you must have previously executed the command:
```bash
npm install
```
at least once, so that all required Node libraries are installed.



## 🧩 Test 4 – Entities `ChessProblemSolution` and `ProblemSource`

### Part A – Entities and Repositories (1 point)

Map both classes as JPA entities with the following validation constraints:

#### `ChessProblemSolution`
| Field | Constraints | Description |
|--------|-------------|-------------|
| `id` | Primary key (Integer) | Primary key |
| `name` | Required (String), 3–50 chars | Name of the solution |
| `createdAt` | Required (`LocalDateTime`) | Moment when the solution was created |
| `minimal` | Required (boolean) | Indicates whether the solution is the minimal (shortest) solution |
| `notes` | Optional (String), ≤ 4000 chars | Additional comments |
| `expectedMoveCount` | Optional (Integer), 1–15 inclusive | Number of expected movements

#### `ProblemSource`
| Field | Constraints | Description |
|--------|-------------|-------------|
| `id` | Primary key (Integer) | Primary key |
| `name` | Required (String), 3–50 chars | Name of the source |
| `author` | Required (String), 3–100 chars | Author of the source |
| `description` | Optional (String), ≤ 1000 chars | Description |
| `contactEmail` | Optional (String), valid email format | Email of the author |

Modify the interfaces `ChessProblemSolutionRepository` and `ProblemSourceRepository`, located in the same package, so that they extend `CrudRepository`. Do not forget to specify their type parameters.


### Part B – Relationships (1 point)

Remove all `@Transient` annotations and set up in those attributes the following relationships:

| Relationship | 
|---------------|
| `ChessProblemSolution → ChessProblem` | 
| `ChessProblemSolution → ChessBoard` | 
| `ProblemSource → ChessProblem` | 

In all cases, you must ensure that the relationships correctly reflect the cardinalities shown in the UML diagram. For example, some attributes may be nullable because their cardinality is 0..n, while others must not be null because their navigable end of the relationship has a cardinality of 1..n.



## 🧩 Test 5 – Database Initialization

Extend the database initialization script with:

### ProblemSource 1
```text
id: 801
name: "Classic Puzzle Compendium"
author: "H. Steinitz"
description: "A curated set of elegant checkmate studies published in 1895."
contactEmail: "steinitz@classicpuzzles.org"
```

### ProblemSource 2
```text
id: 802
name: "Modern Engine Analysis 2025"
author: "DeepEval AI"
description: "Computer-validated solutions using latest neural networks."
contactEmail: "analysis@deepeval.ai"
```

### ChessProblemSolution 1
```text
id: 901
name: "Canonical Mate in 1 (line A)"
createdAt: 2025-01-10 09:11:00
minimal: true
notes: "Immediate mate with Qh5# from the starting position."
expectedMoveCount: 1
```

### ChessProblemSolution 2
```text
id: 902
name: "Canonical Mate in 2 (line B)"
createdAt: 2025-01-10 09:21:00
minimal: true
notes: "Two-move forcing sequence ending in mate with Qd5#."
expectedMoveCount: 2
```

### Relationships
- ProblemSource 801 → Problems 1 & 3  
- ProblemSource 802 → Problems 2, 4, & 5  
- ChessProblemSolution 901 → Problem 1, Board 910  
- ChessProblemSolution 902 → Problem 3, Board 911  

> ⚠️ **Remember:** insert order matters in the initialization script when defining relationships.
