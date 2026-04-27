# 🚀 ResumeForge – AI Resume Optimizer

An end-to-end AI-powered resume optimization system that generates ATS-friendly resumes tailored to job descriptions using **RAG (Retrieval-Augmented Generation)**, **LLMs**, and **deterministic skill matching**.

---

## 🌟 Live Demo

👉 [Live Application](https://resumeforge-vsba.onrender.com/swagger-ui/index.html)

---

## 🧠 What It Does

ResumeForge takes:

* A user profile (skills, projects, experience)
* A job description

And generates:

* 📄 Tailored resume (JSON)
* 📑 Downloadable PDF resume
* 🎯 Job match score
* ⚠️ Missing skills analysis

---

## ⚙️ Key Features

### 🔥 AI Resume Generation

* RAG pipeline with vector search
* ATS-friendly structured output
* Context-aware resume tailoring

### 📑 PDF Resume Generation

* Converts structured resume into formatted PDF
* Clean layout with sections (skills, experience, projects)
* No storage — generated on demand

### 🧠 Job Description Understanding

* Extracts structured data:

  * Role
  * Skills
  * Responsibilities
  * Keywords

### 📊 Job Match Analysis

* Deterministic matching algorithm
* Match score calculation
* Missing skills detection

### ⚡ Performance Optimized

* Multi-model pipeline:

  * Lightweight model → preprocessing
  * Strong model → final output
* Latency optimized from **~90s → ~14s**

### 🗄️ Vector Search (RAG)

* Uses embeddings + PGVector
* Semantic retrieval of user context

---

## 🏗️ Architecture

User Input (Job Description)
↓
LLM (JD Extraction)
↓
Structured Job Data
↓
Skill Matching Engine (Java)
↓
Match Score + Missing Skills
↓
Vector Retrieval (PGVector)
↓
LLM (Resume Generation)
↓
Structured Resume → PDF

---

## 🛠️ Tech Stack

### Backend

* Java 21
* Spring Boot
* Spring AI

### AI / ML

* OpenAI / OpenRouter (LLMs)
* Embedding APIs
* RAG (Retrieval-Augmented Generation)

### Database

* PostgreSQL
* PGVector

### DevOps

* Docker
* GitHub Actions (CI/CD)
* Render (Deployment)
* Docker Hub

### PDF

* OpenPDF (or iText equivalent)

---

## ⚡ API Endpoints

### Generate Resume

 [Generate Resume](https://resumeforge-vsba.onrender.com/swagger-ui/index.html)

---

### Download Resume as PDF

[Download Resume](https://resumeforge-vsba.onrender.com/swagger-ui/index.html)

---

## 🧪 Run Locally

---

### 🥇 1. Clone Repository

git clone https://github.com/YOUR_USERNAME/resumeforge.git
cd resumeforge

---

### 🥈 2. Configure Environment Variables

Create `.env` or set in system:

#### 🔑 LLM Configuration (Choose One)

**Option A: OpenAI / OpenRouter**

```
#openai/openrouter api key
openai-api-key=<YOUR API KEY>
```

---

**Option B: Local Models (Ollama)**

comment out open ai dependency in pom.xml
uncomment out ollama dependency in pom.xml

Install ollama locally and create account on ollama 

Chat Model configuation
```
spring.ai.ollama.chat.options.model=ministral-3:3b-cloud
```
Embedding configuation
```
spring.ai.ollama.base-url=http://localhost:11434
spring.ai.ollama.embedding.options.model=nomic-embed-text:latest
```

### 🧠 Jwt configuration

generate an 256 bit alph numeric key for jwt token generation

```
jwt-security-key=<256 bit key>
```

---

### 🗄️ Database (PostgreSQL + PGVector)

Run Postgres with PgVector locally on docker By running this image  

[Ankane Vector](https://hub.docker.com/r/ankane/pgvector/tags)

```
docker pull ankane/pgvector
```

Database Configuration

```
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/resumeforge
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=yourpassword
```

### 🥉 3. Run Application

./mvnw spring-boot:run

---

## 🐳 Run with Docker

---

### Pull Image

docker pull salim84dev/resumeforge:latest

---

### Run Container

docker run -p 8080:8080 -e openai-api-key=<api-key> -e database_url=<database-url> -e database_username=<database-username> -e database_password=<database-password> -e jwt-security-key=<jwt-security-key>  --name <container-name> resumeforge:latest

---

## 🔄 CI/CD

* GitHub Actions for build & test
* Docker image pushed to Docker Hub
* Auto-deployed on Render

---

## 🧱 System Design Highlights

* Hybrid AI + deterministic architecture
* Multi-model orchestration
* Skill normalization & relational mapping
* Cost & latency optimization
* Clean separation of concerns

---

## 📊 Matching Algorithm

* Extract job skills (LLM)
* Normalize user skills (DB)
* Compute:

  * Match Score = matched / total
  * Missing Skills = difference
* Feed into LLM for adaptive resume generation

---

## 📌 Future Improvements

* Skill recommendation engine
* ATS keyword highlighting
* Frontend UI
* Resume templates
* Authentication system

---

## 👨‍💻 Author

**Salim Saudagar**

* 📧 [salimsaudagar84@gmail.com](mailto:salimsaudagar84@gmail.com)
* 🔗 https://linkedin.com/in/salim-saudagar
* 📍 Maharashtra, India

---

⭐ If you found this useful, consider starring the repo!
