package Model;

public class headOn implements headConfig{
    
    @Override
    public String getFirst() {
        return "Sair";
    }

    @Override
    public String getLinkFirst() {
        return "sair";
    }

    @Override
    public String getSecond() {
        return "Cadastro de Livro";
    }

    @Override
    public String getLinkSecond() {
        return "cadastroLivro.jsp";
    }
    
}
