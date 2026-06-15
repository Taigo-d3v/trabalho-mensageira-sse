# Projeto Mensageira SSE

Este projeto foi desenvolvido como parte de um estudo sobre Server-Sent Events (SSE) utilizando o framework Spring Boot. A aplicação permite a troca de mensagens em tempo real entre múltiplos clientes conectados.

**Autor:** Tiago Godinho Freitas

## Tecnologias Utilizadas

- **Backend:** Java 21, Spring Boot 4.1.0 (ou compatível)
- **Frontend:** HTML5, CSS3, JavaScript (Vanilla)
- **Comunicação:** Server-Sent Events (SSE)

## Estrutura do Projeto

- `com.trabalho.mensageira_sse.controller`: Contém o `MessageController` que gerencia os endpoints de inscrição e envio.
- `com.trabalho.mensageira_sse.service`: Contém o `SseService` responsável pelo gerenciamento dos `SseEmitter` e broadcast de mensagens.
- `com.trabalho.mensageira_sse.model`: Contém a classe `Message` que representa os dados trafegados.
- `src/main/resources/static`: Contém o frontend da aplicação.

## Como Executar

### Pré-requisitos
- JDK 21 ou superior.
- Maven (incluso no Eclipse).

### Passo a Passo
1. Importe o projeto no Eclipse como um projeto Maven existente.
2. Aguarde o download das dependências.
3. Execute a classe `MensageiraSseApplication.java` como um "Java Application" ou use o plugin do Spring Boot.
4. Abra o navegador no endereço: `http://localhost:8080`
5. Para testar o tempo real, abra duas ou mais abas do navegador no mesmo endereço.
6. Digite seu nome, uma mensagem e clique em "Enviar". Você verá a mensagem aparecer instantaneamente em todas as abas.

## Funcionamento do SSE
O SSE permite que o servidor envie dados de forma assíncrona para o cliente através de uma conexão HTTP persistente. Diferente do WebSocket, o SSE é unidirecional (Servidor -> Cliente), o que o torna mais simples e eficiente para casos de uso como notificações e feeds de mensagens, onde o envio de dados do cliente pode ser feito via requisições POST padrão.
