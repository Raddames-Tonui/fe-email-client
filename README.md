# 📬 Email Template Sending in Java

This README outlines the **full concept** of sending dynamic HTML emails in Java using three distinct approaches. It includes a **Java project tree structure**, use cases, and comparisons between **manual HTML loading**, **Thymeleaf**, and **Freemarker**. This is a learning-friendly setup where each method is independently runnable.

---

## 📦 Project Structure

```
email-sender-java/
├── pom.xml
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── emailsender/
│       │           ├── manual/                # Manual method using plain string
│       │           │   ├── ManualEmailSender.java
│       │           │   └── ManualMain.java
│       │           ├── thymeleaf/             # Thymeleaf template engine
│       │           │   ├── ThymeleafRenderer.java
│       │           │   └── ThymeleafMain.java
│       │           ├── freemarker/            # Freemarker template engine
│       │           │   ├── FreemarkerRenderer.java
│       │           │   └── FreemarkerMain.java
│       │           └── util/
│       │               └── EmailService.java  # Shared SMTP logic
│       └── resources/
│           └── email-templates/
│               ├── welcome.html               # For manual
│               ├── newsletter.html            # For manual
│               ├── trial-expiry.html          # For manual
│               ├── welcome-thymeleaf.html     # For Thymeleaf
│               ├── newsletter-thymeleaf.html
│               ├── trial-expiry-thymeleaf.html
│               ├── welcome.ftl                # For Freemarker
│               ├── newsletter.ftl
│               └── trial-expiry.ftl
```

---

## 🧠 Concept Summary

### 1. Manual String Injection

**How it works**:

* Load HTML as a raw string.
* Replace placeholders manually using `.replace("{{key}}", value)`.

**Pros**:

* Simple to implement.

**Cons**:

* Tedious and error-prone for large templates.
* No logic support (loops, conditionals).

**Use case**:

* Small static templates with 1-2 variables.

**Entry point**: `ManualMain.java`

---

### 2. Thymeleaf

**How it works**:

* HTML with `${variable}` or `th:text` attributes.
* Use `TemplateEngine` to render the page with variables.

**Pros**:

* Easy integration with Spring Boot.
* Good for structured HTML and readability.

**Cons**:

* Slightly heavier setup than manual.
* Less flexible for deeply nested logic.

**Use case**:

* Spring-based apps, marketing emails, dynamic user onboarding.

**Entry point**: `ThymeleafMain.java`

---

### 3. Freemarker

**How it works**:

* Uses `.ftl` templates with `${variable}`, `#if`, `#list`, etc.
* Loads and renders template via `Configuration` and `Template`.

**Pros**:

* Extremely flexible, supports logic, conditions, loops.
* Better suited for complex templating needs.

**Cons**:

* Slightly more complex syntax.

**Use case**:

* Invoices, receipts, complex conditional emails.

**Entry point**: `FreemarkerMain.java`

---

## ✨ Recommended Approach

| Use Case                    | Recommended        |
| --------------------------- | ------------------ |
| Simple templates            | Manual (for speed) |
| Spring Boot apps            | Thymeleaf          |
| Complex, logic-heavy emails | Freemarker ✅       |

If building a **scalable, reusable emailing service**, **Freemarker** is the most flexible and professional approach.

---



## 📚 Summary

This setup allows learners and teams to explore three different strategies within a single Java project. It supports modularity, learning, and experimentation with real-world email use cases — from onboarding to trial expiry, to newsletters.

