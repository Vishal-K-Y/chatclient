# Chat Client — Spring AI + Qdrant RAG

**A Spring Boot chat API that pairs OpenRouter LLMs with Qdrant vector search and local embeddings — ingest documents, run semantic search, and chat over your own knowledge base.**

Built with **Spring AI**, **Qdrant**, and **HuggingFace Text Embeddings Inference** (BAAI/bge-small-en-v1.5). Talk to a free OpenRouter model, embed text into a vector store, query similar content, and use RAG to answer questions grounded in ingested PDF data.

---

## Prerequisites

- **Java 21**
- **Docker** & **Docker Compose**
- An [OpenRouter](https://openrouter.ai/) API key

---

## Configuration

`application.properties` is gitignored so your API key stays local. Create it at `src/main/resources/application.properties`:

```properties
spring.application.name=chatclient

spring.ai.openai.api-key=<YOUR_OPENROUTER_API_KEY>

# OpenRouter Base URL
spring.ai.openai.base-url=https://openrouter.ai/api/v1
# OpenRouter Model
spring.ai.openai.chat.options.model=nvidia/nemotron-3-nano-omni-30b-a3b-reasoning:free
spring.ai.openai.chat.options.temperature=0.2

spring.ai.openai.embedding.base-url=http://localhost:8081/v1
spring.ai.openai.embedding.options.model=BAAI/bge-small-en-v1.5

spring.ai.vectorstore.qdrant.collection-name=my_multimodal_embeddings
spring.ai.vectorstore.qdrant.host=localhost
spring.ai.vectorstore.qdrant.port=6334

#for logging
logging.level.org.springframework.ai.chat.client=DEBUG
```

Replace `<YOUR_OPENROUTER_API_KEY>` with your key from [OpenRouter](https://openrouter.ai/keys).

---

## Run the project

### 1. Start infrastructure (Qdrant + embedding server)

```bash
docker compose up -d
```

This starts:
- **Qdrant** on ports `6333` (HTTP) and `6334` (gRPC)
- **Text Embeddings Inference** on port `8081` (model: `BAAI/bge-small-en-v1.5`)

Wait until both containers are healthy before continuing.

### 2. Start the Spring Boot application

```bash
./mvnw spring-boot:run
```

On Windows:

```bash
mvnw.cmd spring-boot:run
```

The API runs at `http://localhost:8080`.

### 3. Seed the vector database (one-time)

The vector store starts empty. Run these tests **once** to ingest data before using semantic search or RAG.

#### 3.1 Dummy data for semantic search

In `ChatclientApplicationTests`, uncomment `@Test` on `testInsertDocumentsToVectorStore`, then run that test:

```java
//running this test will ingest the data in vector store
@Test
void testInsertDocumentsToVectorStore(){
```

This loads sample IT knowledge-base documents into Qdrant. After it succeeds, try:

```bash
curl "http://localhost:8080/searchQuery?query=VPN%20password%20error"
```

#### 3.2 PDF data for RAG chat

Place your PDF at `src/main/resources/general-concept.pdf` (or update the path in `RAGService`). In `RAGServiceTest`, uncomment `@Test` on `testIngest`, then run it:

```java
@Test
public void testIngest() {
    ragService.ingestPDFInVectorStore();
}
```

After ingestion, ask questions via the RAG endpoint:

```bash
curl -X POST http://localhost:8080/chatWithData -H "Content-Type: text/plain" -d "What is covered in the document?"
```

---

## API endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/` | Welcome message |
| `POST` | `/chat` | Chat with the LLM (plain text body) |
| `GET` | `/searchQuery?query=...` | Semantic similarity search over ingested documents |
| `POST` | `/chatWithData` | RAG chat grounded in ingested PDF context |

---

## Project structure

```
src/main/java/com/coding/ai/chatclient/
├── controller/ChatController.java    # REST endpoints
├── service/AIChatService.java        # LLM chat, embeddings, similarity search
├── service/RAGService.java           # PDF ingestion & RAG prompts
└── configuration/AIConfig.java       # Spring AI beans
```

---

## Tech stack

- Spring Boot 4.1 · Spring AI 2.0
- OpenRouter (chat) · TEI (embeddings) · Qdrant (vector store)
- Java 21 · Lombok
