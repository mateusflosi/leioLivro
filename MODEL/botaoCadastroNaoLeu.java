package Model;

public class botaoCadastroNaoLeu implements botaoCadastroConfig {

    @Override
    public String getText() {
        return "Marcar esse livro como lido";
    }

    @Override
    public String getAction() {
        return "marcaLivro";
    }
    
}
