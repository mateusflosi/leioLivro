package Model;

public class UsuarioRanking extends UsuarioLogado{
    private int colocacao;
    private int pontos;
    
    public void setColocacao(int colocacao) {
        this.colocacao = colocacao;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }
    
    public int getColocacao(){
        return colocacao;
    }
    
    public int getPontos(){
        return pontos;
    }
}