package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LivrosDAO {
    
    static{
        try{
            Class.forName("org.postgresql.Driver");
        } catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public List<LivroLista> getLivros() {
        List<LivroLista> livro = new ArrayList<>();
        try(Connection c = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/leioLivro","postgres","senha")){
            String sql = "SELECT titulo, imagem FROM livro;";
            PreparedStatement stm = c.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                livro.add(preencheLivroLista(rs));
            }
        } catch(SQLException e){
            throw new RuntimeException("Não foi possivel fazer a conexão - getLivros");
        }
     
        return livro;
    }

    private LivroLista preencheLivroLista(ResultSet rs) {
        LivroLista l = new LivroLista();
        try{
            l.setImg(rs.getString("imagem"));
            l.setTitulo(rs.getString("titulo"));
        }catch(SQLException e){
            throw new RuntimeException("Não foi possivel fazer a conexão - preencheLivrosLista");
        }
        return l;
    }

    public LivroExibicao getLivro(String titulo){
        try(Connection c = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/leioLivro","postgres","senha")){
            String sql = "SELECT * FROM livro WHERE titulo=?;";
            PreparedStatement stm = c.prepareStatement(sql);
            stm.setString(1, titulo);
            ResultSet rs = stm.executeQuery();
            rs.next();
            return preencheLivroExibicao(rs);
        } catch(SQLException e){
            throw new RuntimeException("Não foi possivel fazer a conexão - getLivros");
        }
    }

    private LivroExibicao preencheLivroExibicao(ResultSet rs) {
        LivroExibicao livro = new LivroExibicao();
        try{
            livro.setImg(rs.getString("imagem"));
            livro.setTitulo(rs.getString("titulo"));
            livro.setAutor(rs.getString("autor"));
            livro.setEstilo(rs.getString("estilo"));
            livro.setPaginas(rs.getInt("paginas"));
            livro.setSinopse(rs.getString("sinopse"));

        }catch(SQLException e){
            throw new RuntimeException("Não foi possivel fazer a conexão - preencheLivrosLista");
        }
        return livro;
    }

    public void cadastrar(String titulo, String autor, String imagem,
            String sinopse, String estilo, String paginas) throws Exception{
        
        try(Connection c = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/leioLivro","postgres","senha")){
            
            String sql = "INSERT INTO livro(titulo, autor, imagem, sinopse, estilo, paginas) VALUES (?, ?, ?, ?, ?, ?);";
            PreparedStatement stm = c.prepareStatement(sql);
            stm.setString(1, titulo);
            stm.setString(2, autor);
            stm.setString(3, imagem);
            stm.setString(4, sinopse);
            stm.setString(5, estilo);
            stm.setInt(6, Integer.parseInt(paginas));
            stm.executeUpdate();
        } catch (Exception e) {
            throw new Exception("Titulo ja existente", e);
        }
    }
}