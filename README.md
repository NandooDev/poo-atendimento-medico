# Atendimento Médico — 1ª entrega

Projeto Java 17 com Maven para o banco MySQL do sistema de atendimento médico. Nesta etapa, o programa conecta ao banco, lê usuários e consultas via JDBC e apresenta os registros como objetos de domínio.

O `banco/schema.sql` é compatível com o schema de `atendimento-medico`: ambos usam o banco `atendimento_medico` e as mesmas tabelas, colunas, tipos, ENUMs e restrições. A carga de dados Java usa registros fictícios próprios, sem IDs fixos, para coexistir com os dados iniciais da API.

## Requisitos

- Java 17 ou superior
- Maven
- MySQL 8

## Preparar o banco

Na raiz do projeto, execute os scripts no MySQL:

```bash
mysql -u root -p < banco/schema.sql
mysql -u root -p atendimento_medico < banco/dados.sql
```

Os dados são fictícios e podem ser carregados novamente sem duplicar registros.

## Configurar e executar

Copie `src/main/resources/db.properties.example` para `src/main/resources/db.properties`, informe usuário e senha do MySQL e execute:

```bash
mvn compile exec:java
```

O arquivo `db.properties` contém credenciais locais e é ignorado pelo Git. O programa informa se a conexão foi estabelecida; em caso de falha, exibe uma mensagem sem revelar credenciais nem detalhes internos do SQL.

## Estrutura

- `banco/schema.sql` e `banco/dados.sql`: banco e dados iniciais no contrato solicitado.
- `docs/diagrama-classes.png`: diagrama UML das classes da primeira entrega.
- `src/main/java/br/edu/unifacisa/atendimento/model`: classes de domínio e enums.
- `src/main/java/br/edu/unifacisa/atendimento/db/ConexaoBD.java`: conexão JDBC baseada em properties.
- `src/main/java/br/edu/unifacisa/atendimento/Main.java`: leitura com `PreparedStatement` e fechamento de recursos com try-with-resources.
