# **Prompt Log**

## Refactoring and Rebuilding Connect Four Using LLMs

This prompt log documents the iterative prompt strategy used with ChatGPT during the reconstruction of a legacy Connect Four game into a Python-based architecture, following modern software engineering best practices. Prompts evolved across multiple stages: backend redesign, testing, game service architecture, frontend integration, and gameplay logging preparation.

Link to the original game: https://balkarjun.github.io/ConnectFourAI/

---

## **1\. Establishing Project Context**

**Technique used:** *Context priming, grounding the model*  
At the beginning of the process, I provided ChatGPT with a detailed description of the project requirements:

* Rebuild Connect Four in Python

* Follow proper software engineering patterns

* Integrate future AI components

* Prepare for gameplay logging

* Migrate from a legacy non-Python implementation

This ensured ChatGPT had full situational awareness before generating any code. Throughout the conversation, context was reinforced, a prompt engineering technique known as recursive grounding.

---

## **2\. Incremental Code Generation and Refactoring**

**Techniques used:**

* *Step-by-step prompting*  
    
* *Decomposition prompting*

* *Code rewriting / refactor prompts*

* *Constrained prompt instructions*

I repeatedly asked ChatGPT to generate or modify isolated components instead of asking for the full system at once. For example:

* First, the `Engine` class

* Then, the `Board` class

* Then, FastAPI routes

* Then, the `GameService` architecture

* Later, gameplay event publishing

* Finally, test suites

Breaking the task into structured, incremental prompts kept the model focused on correctness and maintainability.

---

## **3\. Debugging Through LLM Interaction**

**Technique used:** *LLM as a debugging assistant*  
 When issues appeared, such as:

* animation glitches in the frontend,

* incorrect GameService design assumptions,

* failed test cases,

* API integration concerns

I pasted error messages and asked the model to diagnose problems.

The model compared expected vs. actual results and recommended both structural fixes and test corrections.

---

## **4\. Test Design and TDD Techniques**

**Techniques used:**

* *Behaviour-driven prompting* (“Write tests that verify X…”)

* *Adversarial prompting* (“What tests are missing?”)

* *Edge-case prompting*

* *Self-reflection prompting* (“Do these tests rely on incorrect assumptions?”)

I asked ChatGPT to generate:

* Engine-level tests

* API tests for FastAPI routes

* Service-level tests for `GameService`

When tests failed, I provided the stack traces. The model used this feedback to revise the code or update the tests.

This constitutes a feedback loop known as **prompt refinement through adversarial examples**, where incorrect outputs are used as new prompts to force correction.

---

## **5\. Game Service Architecture Redesign**

**Technique used:** *Correction prompting, clarifying ambiguous requirements*

At one stage, tests failed because the LLM assumed an incorrect internal GameService structure. I provided the *actual code*, then instructed:

“Rewrite the tests based on this exact implementation.”

This prompted a full regeneration of accurate test cases.  
This is a key example of **model disambiguation via explicit source-of-truth prompts**.

---

## **6\. Frontend Integration Strategy**

**Technique used:** *Decision prompting* and *Scenario prompting*

I explored different architectural approaches for embedding the Connect Four frontend into a React gaming platform, asking questions like:

* Should internal games mimic external game redirection?

* Can vanilla JS games live inside a React app?

* Do the files need to be converted to JSX?

ChatGPT evaluated architectural constraints and recommended a simple and maintainable solution:  
 **serve the vanilla HTML/JS game inside React via the `public/` directory and an `<iframe>` route**.  
 This method was chosen after prompting for trade-offs, demonstrating **multi-step reasoning prompts**.

---

## **7\. Addressing Gameplay Logging (RabbitMQ Events)**

**Techniques used:**

* *Forward planning prompting*

* *Architectural scaffolding prompting*

I asked where gameplay events such as `move_made` should be triggered in the backend. ChatGPT proposed:

* Publishing events inside the GameService’s move-handling logic

* Designing structured event schemas

* Planning for later analytics and AI integration

Though event publishing was temporarily removed for testing, the prompt strategy established a scalable architecture for later logging integration.

This demonstrates **prompt-driven system design**.

---

## **8\. UI Bugs and UX Refinement**

**Technique used:** *Symptom prompting* (“There is a glitch when pressing the button…”)  
 I described frontend symptoms without providing code.  
 ChatGPT proposed likely causes (e.g., button re-rendering, async state update race conditions), then suggested UI-focused fixes.

This shows another pattern: **LLM as a reasoning partner for exploratory debugging**.

---

# **Summary of Prompt Engineering Techniques Used**

| Technique | Purpose |
| ----- | ----- |
| **Context priming** | Ensured the LLM understood the project scope |
| **Decomposition prompting** | Broke the system into small, manageable components |
| **Step-by-step prompting** | Forced clear, sequential reasoning |
| **Constrained prompting** | Ensured compliance with required structure/patterns |
| **Adversarial prompting** | Caught errors, forced the LLM to correct itself |
| **Debugging prompts** | Used error outputs to refine the solution |
| **Scenario prompting** | Helped choose between architectural alternatives |
| **Decision prompting** | Generated comparative analyses for hosting strategies |
| **Self-reflection prompting** | Asked the model to evaluate its own outputs |
| **Correction prompting** | Replaced incorrect assumptions with actual ground truth |
| **Iterative refinement** | Improved code quality through multiple iterations |

