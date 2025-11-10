# Projeto: Loja de Roupas (Terminal App)

Este é um sistema de pequeno porte para uma loja de roupas que funciona via terminal, desenvolvido em Java. O objetivo principal do projeto é demonstrar a aplicação prática de Padrões de Projeto e princípios SOLID em um contexto real.

## Entidades Principais

O sistema é construído em torno de 3 entidades principais:

1.  **`Produto`**: (Interface) Representa um item vendável. Implementado pelas classes concretas `Camiseta` e `Calca`.
2.  **`Pedido`**: Representa o carrinho de compras e, eventualmente, o pedido finalizado de um cliente. Contém uma lista de `Produto`s.
3.  **`EstoqueRepository`**: (Singleton) Representa o inventário da loja. É a fonte única de verdade sobre quais produtos estão disponíveis.

## Como Executar

1.  Compile todos os arquivos `.java` mantendo a estrutura de pacotes. Se estiver em um terminal na pasta raiz `LojaDeRoupasApp/`:
    ```bash
    javac main/*.java main/model/*.java main/factory/*.java main/repository/*.java main/service/*.java main/view/*.java
    ```
2.  Execute a classe `Main`:
    ```bash
    java Main
    ```
3.  Se estiver usando uma IDE (IntelliJ, Eclipse, VSCode), basta abrir o projeto e executar o arquivo `Main.java`.

## Padrões de Projeto e Princípios Aplicados

O sistema implementa 4 padrões de projeto de forma integrada.

### 1. Princípio SOLID: Single Responsibility Principle (SRP)

O Princípio da Responsabilidade Única (SRP) diz que uma classe deve ter apenas um motivo para mudar.

* **Problema:** Em um sistema pequeno, é tentador colocar toda a lógica (conexão de banco, regras de negócio, e interface de usuário) em uma única classe `Main`. Isso torna a manutenção impossível.
* **Por que foi escolhido:** O SRP é o princípio mais fundamental para criar código organizado e manutenível.
* **Como foi aplicado:** O projeto é dividido em pacotes, onde cada classe tem uma responsabilidade clara e única:
    * **`model`**: Contém apenas as classes de dados (`Produto`, `Pedido`). Elas não sabem como são salvas ou exibidas.
    * **`repository.EstoqueRepository`**: Sua *única* responsabilidade é gerenciar a coleção de produtos em memória. Não sabe sobre vendas ou UI.
    * **`factory.ProdutoFactory`**: Sua *única* responsabilidade é *criar* instâncias de `Produto`.
    * **`service.LojaService`**: Sua *única* responsabilidade é orquestrar a *lógica de negócio* de finalizar uma venda (e notificar interessados).
    * **`view.TerminalView`**: Sua *única* responsabilidade é exibir menus e ler a entrada do usuário no console.
* **Benefício:** Se quisermos mudar a interface de terminal para uma interface gráfica (GUI), **apenas** a classe `TerminalView` precisa ser modificada. O resto do sistema (serviços, repositórios, modelos) permanece intacto.

### 2. Padrão: Singleton

* **Problema:** O sistema precisa de um, e *apenas um*, local para gerenciar o inventário (`Estoque`). Se diferentes partes do código pudessem criar seus próprios "estoques", teríamos inconsistência de dados (um produto poderia ser vendido duas vezes).
* **Por que foi escolhido:** O Singleton garante que uma classe tenha apenas uma instância e fornece um ponto de acesso global a ela.
* **Como foi aplicado:** A classe **`repository.EstoqueRepository`** é um Singleton.
    * Seu construtor é `private`.
    * Ela fornece um método `public static synchronized EstoqueRepository getInstancia()`.
    * Tanto a `TerminalView` (para listar produtos) quanto o `AtualizadorEstoque` (para dar baixa) usam `EstoqueRepository.getInstancia()` para acessar *exatamente a mesma lista* de produtos.
* **Benefício:** Garante a consistência dos dados do inventário em toda a aplicação.

### 3. Padrão: Factory Method (usado como Simple Factory)

* **Problema:** A loja vende diferentes tipos de `Produto` (`Camiseta`, `Calca`). A classe `Main` (ou quem quer que inicialize o sistema) não deveria precisar saber *como* construir cada um (`new Camiseta(...)`, `new Calca(...)`). Se um novo tipo, como `Sapato`, for adicionado, teríamos que mudar o `Main`.
* **Por que foi escolhido:** O Factory centraliza a lógica de criação de objetos, desacoplando o "cliente" (código que *usa* o objeto) das classes "concretas" (como o objeto é *feito*).
* **Como foi aplicado:** A classe **`factory.ProdutoFactory`** possui um método `criarProduto(...)`. A classe `Main` usa esta fábrica para popular o estoque inicial, apenas informando o tipo ("CAMISETA" ou "CALCA") e os dados.
* **Benefício:** Extensibilidade. Para adicionar `Sapatos` à loja, basta criar a classe `Sapato` e adicionar um `else if` no `ProdutoFactory`. A classe `Main` e o resto do sistema não precisam de *nenhuma* alteração, pois eles programam para a interface `Produto`.

### 4. Padrão: Observer

* **Problema:** Quando um `Pedido` é finalizado, várias ações *não relacionadas* precisam acontecer:
    1.  O `Estoque` precisa ser atualizado (baixa de itens).
    2.  Um `Email` de confirmação precisa ser enviado ao cliente.
* **Por que foi escolhido:** O Observer define uma dependência um-para-muitos. Quando o objeto "Subject" (o `LojaService`) muda de estado (finaliza um pedido), todos os seus "Observers" (Estoque, Email) são notificados e atualizados automaticamente.
* **Como foi aplicado:**
    * **Subject:** O **`service.LojaService`** implementa a interface `Subject`. Ele mantém uma lista de `Observer`s.
    * **Observers:** As classes **`service.AtualizadorEstoque`** e **`service.EnviadorEmail`** implementam a interface `Observer`.
    * **Integração:** Na classe `Main`, nós "registramos" os observers no `LojaService`. Quando a `TerminalView` chama `lojaService.finalizarPedido()`, o serviço faz sua lógica e, no final, chama `notificarObservadores()`. Isso dispara o método `atualizar()` em *todos* os observadores registrados.
* **Benefício:** Desacoplamento total. O `LojaService` *não faz ideia* do que acontece depois que ele notifica. Ele não conhece o `Estoque` nem o `EnviadorEmail`. Se quisermos adicionar um notificador de SMS amanhã, basta criar a classe `EnviadorSMS` e registrá-la na `Main`. O `LojaService` não precisa ser alterado.
