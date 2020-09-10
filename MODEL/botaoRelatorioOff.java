package Model;

public class botaoRelatorioOff implements botaoRelatorioConfig {

    @Override
    public String getText(){
        return "Faça login para ver sua pontuação";
    }
    
    @Override
    public String getAction() {
        return "login.jsp";
    }
    
}
