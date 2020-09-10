package Model;

public class headOff implements headConfig {    
    @Override
    public String getFirst() {
        return "Login";
    }

    @Override
    public String getLinkFirst() {
        return "login.jsp";
    }

    @Override
    public String getSecond() {
        return "Cadastre-se";
    }

    @Override
    public String getLinkSecond() {
        return "cadastroUsuario.jsp";
    }
}