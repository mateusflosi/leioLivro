package Model;

public class botaoRelatorioOn implements botaoRelatorioConfig {

    @Override
    public String getText(){
        return "Veja sua pontuação";
    }
    
    @Override
    public String getAction() {
        return "relatorio";
    }
}
