package Model;

public class UsuarioLogado {
    private String nome;
    private String login;
    
    public void setNome(String nome){
        this.nome = nome;
    }
    
    public void setLogin(String login){
        this.login = login;
    }
    
    public String getNome(){
        return this.nome;
    }
    
    public String getLogin(){
        return this.login;
    }
}