# 📬 Email Template Sending in Java

This README outlines the **core concept** of sending dynamic HTML emails in Java using a **manual approach**, with additional placeholders for exploring **Thymeleaf** and **Freemarker** as alternative methods. The structure reflects a modular setup allowing experimentation and future integration.

---

## 📦 Project Structure

```
email-sender-java/
├── pom.xml
├── src/
│   └── main/
│       ├── java/
│       │   └── emailsender/
│       │       └── manual/               
│       │           ├── EnvLoader.java
│       │           ├── ManualEmailSender.java
│       │           ├── Main.java
│       │           └── TemplateLoader.java
│       └── resources/
│           ├── images/
│           │   └── tatua-logo.png               
│           └── templates/
│               ├── welcome.html             
│               ├── newsletter.html            
│               └── trial-expiry.html         
├── .env
```

---

## 🧠 Concept Summary

Add to `.env`:

```env
SENDER_EMAIL=youremail@example.com
SENDER_PASSWORD=yourpassword123
```

---

### 1. Manual String Injection

**How it works**:

* Loads HTML template as raw string.
* Replaces placeholders like `{{name}}` using `.replace()`.
* Embeds inline images with Content-ID (CID).

**Pros**:

* Lightweight and easy to understand.

**Cons**:

* No support for loops or conditionals.
* Prone to manual errors with large templates.

**Use case**:

* Simple onboarding emails, static marketing messages.

**Entry point**: `Main.java`

---

### 2. Thymeleaf (Concept Available)

**How it works**:

* Templates use `${variable}` syntax or `th:text` attributes.
* Rendered using `TemplateEngine`.

**Pros**:

* Intuitive and integrates seamlessly with Spring Boot.
* Clean HTML templates with dynamic rendering.

**Cons**:

* Requires more setup (TemplateEngine, Context).

**Use case**:

* Spring-based applications, rich onboarding flows.

**Entry point**: *To be implemented: `ThymeleafMain.java`*

---

### 3. Freemarker (Concept Available)

**How it works**:

* Templates written in `.ftl` format using `${}`, `#if`, `#list`, etc.
* Uses `Configuration` and `Template` classes to process variables.

**Pros**:

* Ideal for complex templating logic.
* Built-in support for loops, conditions, macros.

**Cons**:

* Slightly steeper learning curve.

**Use case**:

* Receipts, invoices, and dynamic reports.

**Entry point**: *To be implemented: `FreemarkerMain.java`*

---

## ✨ Recommended Usage

| Use Case                 | Recommended        |
| ------------------------ | ------------------ |
| Static templates         | Manual (for speed) |
| Spring Boot applications | Thymeleaf          |
| Logic-heavy emails       | Freemarker ✅       |

For a **scalable, reusable emailing service**, **Freemarker** offers the most flexibility and structure.

---

## 📚 Summary

This project offers a clean foundation for HTML email templating in Java. You can:

* Send styled emails with embedded images
* Swap templating engines easily
* Grow the project toward more advanced use cases (e.g., batch senders, queue-based systems)

Perfect for developers learning email automation or building custom notification systems.
