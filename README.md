# AT_DSSB — Execução **LOCAL**

Sistema de cadastro de **Alunos**, **Disciplinas** e **Matrículas** com autenticação via **Keycloak** e persistência em **MongoDB**.  
Projeto em **Spring Boot (Java 21)** com testes usando **JUnit + Testcontainers**.

---

## Stacks
- **API:** Spring Boot 3, Spring Security (Resource Server JWT), Validation, Actuator, Spring Data MongoDB  
- **Auth:** Keycloak (OIDC)  
- **DB:** MongoDB  
- **Infra local:** Docker Compose (Mongo, Mongo Express, Keycloak)  
- **Build/Test:** Maven Wrapper, JUnit, Testcontainers, JaCoCo

---

## Pré-requisitos
- **Java 21 (JDK)** instalado (`java -version`)
- **Docker Desktop** rodando
- **Postman** para testar endpoints

> **Portas usadas:**  
> API `8080` · Keycloak `8081` · MongoDB `27017` · Mongo Express `8082`
