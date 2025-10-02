# Sistema Bancário Simulado (Spring Boot + MySQL)

**Descrição**  
Projeto educacional que simula um sistema bancário simples com contas, transações (depósito, saque, transferência) e relatórios. Backend em **Spring Boot (Java 17)** e persistência em **MySQL**.

---

## Funcionalidades
- Criar conta com saldo inicial
- Listar contas e visualizar detalhes
- Depositar e sacar valores
- Transferir entre contas
- Extrato (transações por conta)
- Relatório resumo de contas

---

## Arquitetura (resumo)
Camadas:
- Controller (REST API) — endpoints para operações e relatórios
- Service — regras de negócio, transações ACID
- Repository (JPA) — persistência com Hibernate
- Model — Account, Transaction, TransactionType
- DTOs — requisições/respostas para a API

Fluxo simplificado:
```
Client -> Controller -> Service (@Transactional, lock PESSIMISTIC_WRITE) -> Repository -> MySQL
```

---

## Requisitos
- Java 17
- Maven
- Docker & Docker Compose (opcional, recomendado)
- VSCode / IDE de sua preferência
- Portas: app 8080, Adminer (opcional) 8081

---

## Como rodar (passo a passo)

1. **Clonar / criar o repositório**
   ```bash
   git clone <seu-repo>
   cd bank-system
   ```

2. **(Opcional) Subir MySQL com Docker Compose**
   ```bash
   docker compose up -d
   ```
   - MySQL: `root` / `root`, banco `bankdb`
   - Adminer: http://localhost:8081

3. **Build e run**
   - Com Maven:
     ```bash
     mvn clean package
     mvn spring-boot:run
     ```
   - Ou com JAR:
     ```bash
     mvn clean package
     java -jar target/bank-system-0.0.1-SNAPSHOT.jar
     ```

4. **API Docs (Swagger/OpenAPI)**
   - Após rodar: `http://localhost:8080/swagger-ui.html` (ou `/api-docs` para JSON)

---

## Endpoints principais (exemplos `curl`)

- Criar conta:
  ```bash
  curl -X POST http://localhost:8080/api/accounts             -H "Content-Type: application/json"             -d '{"holderName":"Alice","initialBalance":1000.00}'
  ```

- Listar contas:
  ```bash
  curl http://localhost:8080/api/accounts
  ```

- Depositar:
  ```bash
  curl -X POST http://localhost:8080/api/accounts/1/deposit             -H "Content-Type: application/json"             -d '{"amount":100.00,"description":"Depósito"}'
  ```

- Sacar:
  ```bash
  curl -X POST http://localhost:8080/api/accounts/1/withdraw             -H "Content-Type: application/json"             -d '{"amount":50.00,"description":"Saque"}'
  ```

- Transferir:
  ```bash
  curl -X POST http://localhost:8080/api/accounts/transfer             -H "Content-Type: application/json"             -d '{"fromAccountId":1,"toAccountId":2,"amount":200.00,"description":"Transfer"}'
  ```

- Extrato:
  ```bash
  curl http://localhost:8080/api/accounts/1/transactions
  ```

- Relatório resumo de contas:
  ```bash
  curl http://localhost:8080/api/reports/accounts-summary
  ```

---

## Estrutura do repositório
```
bank-system/
├── Dockerfile
├── docker-compose.yml
├── pom.xml
├── src/
└── docs/
    └── prints/
```
Coloque prints/screenshots em `docs/prints/` e referencie no README com:
```md
![Print - cadastro de conta](docs/prints/print01.png)
```

---

## Prints / como gerar e incluir
1. Abra o app rodando e use o Postman ou navegador para acessar endpoints/Swagger.
2. Faça capturas de tela (Windows: `Win+Shift+S`, macOS: `Cmd+Shift+4`).
3. Salve em `docs/prints/print01.png` e adicione ao repositório:
   ```bash
   git add docs/prints/print01.png
   git commit -m "Add print cadastro de conta"
   git push
   ```

---

## Observações importantes
- **Segurança**: não há autenticação; não usar em produção sem proteger endpoints.
- **Concorrência**: operações financeiras usam lock pessimista para reduzir condições de corrida; para alto volume considere estratégias especiais.
- **Validação**: o exemplo é didático — adicione validação (Bean Validation) conforme necessário.

---

## Próximos passos sugeridos
- Adicionar testes unitários e integração (Spring Boot Test)
- Adicionar autenticação (JWT)
- Criar frontend (React) para interação
- Dockerizar a aplicação (imagem + compose com DB)

---

## Autor
Paulo Henrique dos Santos Brito
