package Model;

public class botaoCadastroOff implements botaoCadastroConfig {

    @Override
    public String getText() {
        return "Faça login para marcar esse livro";
    }

    @Override
    public String getAction() {
        return "login.jsp";
    }
    
}
