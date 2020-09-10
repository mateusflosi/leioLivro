package Model;

public class botaoCadastroLeu implements botaoCadastroConfig {

    @Override
    public String getText() {
        return "Você já leu esse livro";
    }

    @Override
    public String getAction() {
        return "carregar";
    }
    
}
