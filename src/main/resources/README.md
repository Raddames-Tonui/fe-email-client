# 📧 Email HTML Templates - Documentation & Best Practices

This README serves as a comprehensive guide for the HTML email templates used in our system. It includes details about structure, design philosophy, compatibility strategies, and best practices to ensure consistent rendering across email clients.

---

## 📌 Overview

These templates are designed to be:

* **Cross-client compatible** (Outlook, Gmail, Apple Mail, Yahoo, etc.)
* **Responsive on mobile and desktop**
* **Visually clean and professional**
* **Easily extendable** (with Thymeleaf, Freemarker, or other engines)

---

## ✅ Templates Included

1. **Account Statements**
2. **Trial Expiry Notifications**
3. **Welcome Emails**
4. **Newsletters** (future addition)

Each template follows the same structure and practices described below.

---

## 🛠️ Technical Foundations

### 📄 DOCTYPE & Syntax

* Uses `<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">`
* Ensures maximum support in legacy clients like Outlook

### 🧱 Layout Structure

* **Fully table-based layout** using nested `<table>` elements
* Avoids modern layout methods like `<div>`, `flex`, `grid`

### 🎨 CSS Styling

* All styles are **inline**: e.g. `style="font-size: 16px; color: #333;"`
* Media queries included within `<style>` tags (for mobile optimization)
* Fonts are email-safe with fallbacks: `font-family: Arial, sans-serif;`

### 📩 Images

* Images must use full URLs (no local references)
* Always include `alt` text and set dimensions
* Avoid base64 images if possible

### 🔘 Buttons

* CTAs use styled `<a>` tags with `display: inline-block`
* Examples: `style="background-color: #1976d2; padding: 10px 20px; color: white;"`

### 🔒 Accessibility

* Uses sufficient contrast ratios
* Uses clear, readable text
* Provides alternative texts for images

---

## 🔁 Common Corrections & Why They Matter

| Issue                   | Correction Made                       | Reason                                        |
| ----------------------- | ------------------------------------- | --------------------------------------------- |
| Use of semantic tags    | Replaced with `<table>` + `<td>`      | Outlook doesn't recognize semantic tags       |
| CSS in `<style>` only   | Moved all styles inline               | Some clients strip out `<style>` tags         |
| Modern layout tags used | Replaced with table layout            | Reliable rendering in all clients             |
| Missing button styling  | Converted `<div>`/`<button>` to `<a>` | Styled `<a>` is most compatible               |
| No image alt text       | Added `alt` attributes                | Accessibility and fallback for blocked images |

---

## 🧠 Key Concepts to Understand

### 1. Tables over Divs

Email clients—especially Outlook—render HTML using **MS Word’s engine**, which breaks modern layouts. Tables are predictable and widely supported.

### 2. Inline CSS

Inline CSS ensures styles are applied consistently across all platforms, including those that strip `<style>` tags or block external CSS.

### 3. CTA Buttons

Always use styled `<a>` tags for clickable buttons. `button` elements have inconsistent rendering in Outlook and Yahoo.

### 4. Semantic Simplicity

Avoid using:

* `<section>`, `<header>`, `<footer>`
* `<article>`, `<h1>` to `<h6>`

Use `<table>`, `<td>`, and styled `<p>` instead.

### 5. Responsive Design with Media Queries

Use media queries to adapt padding, width, and font-size on mobile:

```css
@media only screen and (max-width: 600px) {
  .container { width: 100% !important; }
  .mobile-text-center { text-align: center !important; }
}
```

---

## 🧪 Tools to Test Your Templates

* **Litmus** – Test rendering on 90+ clients
* **Email on Acid** – Similar to Litmus, includes accessibility checks
* **Can I Email** – Check HTML/CSS feature support in email clients
* **MJML** – Write markup that compiles into compatible HTML

---

## 🔧 Developer Notes

* These templates are structured for use in:

    * **JavaMail API**
    * **Spring Boot MailSender**
    * **Freemarker / Thymeleaf** (for dynamic rendering)

* Suggested directory structure:

```
/resources
 └── templates
     ├── account-statement.html
     ├── trial-expiry.html
     └── welcome-email.html
```

* Use placeholders for dynamic fields, e.g., `${username}`, `${expiryDate}`

---

## 📦 Next Steps

* [ ] Convert static templates to Freemarker-compatible versions
* [ ] Create reusable layout components (`header`, `footer`)
* [ ] Add email tracking via pixels or tracking links (optional)
