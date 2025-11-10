// Coloque este arquivo na pasta raiz, acima dos pacotes
import factory.ProdutoFactory;
import model.Produto;
import repository.EstoqueRepository;
import service.AtualizadorEstoque;
import service.EnviadorEmail;
import service.LojaService;
import view.TerminalView;

public class Main {
    public static void main(String[] args) {
        // 1. Configurar o sistema
        LojaService lojaService = new LojaService();
        EstoqueRepository estoque = EstoqueRepository.getInstancia(); // Pega o Singleton
        ProdutoFactory factory = new ProdutoFactory();

        // 2. Registrar os Observadores no Serviço
        // O LojaService não sabe o que eles fazem, apenas que eles existem
        lojaService.registrarObservador(new AtualizadorEstoque());
        lojaService.registrarObservador(new EnviadorEmail());

        // 3. Popular o estoque inicial usando a Factory
        estoque.adicionarProduto(factory.criarProduto("CAMISETA", "Camiseta Lisa Branca", 49.90, "M", 10));
        estoque.adicionarProduto(factory.criarProduto("CAMISETA", "Camiseta Estampada Rock", 79.90, "G", 5));
        estoque.adicionarProduto(factory.criarProduto("CALCA", "Calça Jeans Skinny", 129.90, "42", 8));
        estoque.adicionarProduto(factory.criarProduto("CALCA", "Calça Moletom Cinza", 99.90, "M", 12));

        // 4. Iniciar a interface do usuário
        TerminalView view = new TerminalView(lojaService);
        view.iniciar();
    }
}