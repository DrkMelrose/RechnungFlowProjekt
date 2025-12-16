# MiniERP – Lightweight ERP for Small Businesses

## Project Overview
**MiniERP** is a lightweight web application for small businesses (e.g. freelancers, craftsmen, small service providers)
that need a simple way to manage customers, offers, orders and invoices without relying on Excel/Word files
or complex enterprise ERP systems.

The project is developed as a learning-oriented but production-style Java/Spring Boot application.

---

## Target Audience
- Small businesses (1–10 employees)
- Freelancers / self-employed professionals
- Service providers with recurring invoices

---

## Core Features (Roadmap)
- Customer management (CRUD)
- Offer → Order → Invoice workflow
- Automatic calculations (Net / VAT / Gross)
- Invoice payments (including partial payments)
- Open invoices overview
- Export (CSV for accounting workflows, PDF invoices)

---

## Technology Stack
- Java 17
- Spring Boot 3
- REST API
- Spring Data JPA (Hibernate)
- PostgreSQL (later), H2 (initially)
- Bean Validation
- OpenAPI / Swagger
- Testing: JUnit (integration tests later)
- Deployment (later): Docker Compose