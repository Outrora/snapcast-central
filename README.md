# 📽️ SnapCast Central
![JAVA](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Banco](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![](https://img.shields.io/badge/Amazon_AWS-FF9900?style=for-the-badge&logo=amazonaws&logoColor=white)
![Quarkus](https://img.shields.io/badge/QUARKUS-009CAB?style=for-the-badge&logo=quarkus&logoColor=white)
![Kakfa](https://img.shields.io/badge/Apache_Kafka-231F20?style=for-the-badge&logo=apache-kafka&logoColor=white)

Este projeto é o backend central do SnapCast, responsável por orquestrar o processamento de vídeos, autenticação de usuários via AWS Cognito, geração de links temporários para download e integração com serviços de mensageira (Kafka).



## 💻 Como rodar em modo desenvolvimento

```shell script
./mvnw quarkus:dev
```

Acesse a Dev UI em: http://localhost:8080/q/dev/ 🚀

**Build e execução**
```shell script
./mvnw package
java -jar target/quarkus-app/quarkus-run.jar
```

### ⚙️ Variáveis de Ambiente

Crie um .env com as seguintes informações: 🛠️
```
AWS_REGION=
CLIENT_ID=
BUCKET=
USER_POOL_ID=
KAFKA_URL=
DB_KIND=
POSTGRES_USER=
POSTGRES_PASSWORD=
POSTGRES_URL=
EMAIL=
HOST_EMAIL=
SENHA_EMAIL=
```

### 🔗  Endpoints principais


| URL | Description |
| ----------- | ----------- |
|/login| 🔑  Autenticação e criação de usuário
|/buscar| 🔎 Busca de vídeos por status ou ID
|/link/{id}| ⏳ Geração de link temporário para download

### 🧪 Testes
Execute:
```shell script
./mvnw test
```

## Diagramas de Arquitetura

### C4
![C4](./doc/C4-SnapCast.png)

### Fluxo
![C4](./doc/Frame.jpg)
![C4](./doc/mermaid-diagram-2025-06-22-183030.png)

