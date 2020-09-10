package Model;

public class LivroExibicao extends LivroLista{
    private int paginas;
    private String sinopse;
    private String estilo;
    private String autor;
    
    
    public void setPaginas(int paginas){
        this.paginas = paginas;
    }
    
    public void setSinopse(String sinopse){
        this.sinopse = sinopse;
    }
    
    public void setEstilo(String estilo){
        this.estilo = estilo;
    }
    
    public void setAutor(String autor){
        this.autor = autor;
    }
    
    public int getPaginas(){
        return paginas;
    }
    
    public String getSinopse(){
        return sinopse;
    }
    
    public String getEstilo(){
        return estilo;
    }
    
    public String getAutor(){
        return autor;
    }
}