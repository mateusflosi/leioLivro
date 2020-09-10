package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    
    static{
        try{
            Class.forName("org.postgresql.Driver");
        } catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    
    public UsuarioLogado autenticar(String login, String senhaDigitada) throws Exception{
        try(Connection c = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/leioLivro","postgres","senha")){
            String senhaBanco, sql = "SELECT * FROM usuario WHERE login=?;";
            PreparedStatement stm = c.prepareStatement(sql);
            stm.setString(1, login);
            ResultSet rs = stm.executeQuery();
            rs.next(); 
            senhaBanco = rs.getString("senha");
            if(senhaBanco.equals(senhaDigitada)) return preencheUsuarioLogado(rs);
            throw new Exception("Senha incorreta");
        } catch (SQLException e) {
            throw new SQLException("Usuario não existe", e);
        }
    }
    
    private UsuarioLogado preencheUsuarioLogado(ResultSet rs)
    {
        UsuarioLogado u = new UsuarioLogado();
        try {
            u.setNome(rs.getString("nome"));
            u.setLogin(rs.getString("login"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return u;
    }
    
    public void cadastrar(String nome, String login, String senha) throws Exception {
        try(Connection c = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/leioLivro","postgres","senha")){
            if(isVazio(nome, login, senha)) throw new Exception("Dados incompletos");
            
            String sql = "INSERT INTO usuario(nome, login, senha, livros, pontos) VALUES (?, ?, ?, ?, ?);";
            PreparedStatement stm = c.prepareStatement(sql);
            stm.setString(1, nome);
            stm.setString(2, login);
            stm.setString(3, senha);
            stm.setString(4, ",");
            stm.setInt(5, 0);
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Login ja existe", e);
        }
    }  

    private boolean isVazio(String nome, String login, String senha) {
        if(login.equals("")) return true;
        if(nome.equals("")) return true;
        return senha.equals("");
    }
    
    public void marca(String titulo, String login) throws Exception{
        try(Connection c = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/leioLivro","postgres","senha")){ 
            String sql = "UPDATE usuario SET livros=? WHERE login=?";
            PreparedStatement stm = c.prepareStatement(sql);
            stm.setString(1, livrosBanco(login).concat(titulo + ","));
            stm.setString(2, login);
            stm.executeUpdate();
        } catch (Exception e) {
            throw new Exception("Erro na marcaçao", e);
        }
    }

    private String livrosBanco(String login) throws Exception {
        try(Connection c = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/leioLivro","postgres","senha")){ 
            String sql = "SELECT livros FROM usuario WHERE login=?";
            PreparedStatement stm = c.prepareStatement(sql);
            stm.setString(1, login);
            ResultSet rs = stm.executeQuery();
            rs.next();
            return rs.getString("livros");
        } catch (Exception e) {
            throw new Exception("Erro no getLivros", e);
        }
    }

    public boolean leu(String titulo, String login) throws Exception {
        String[] livros = livrosBanco(login).split(",");
        return busca(titulo, livros);
    }

    private boolean busca(String titulo, String[] livros) {
        int i;
        
        for(i=0;i < livros.length; i++){
            if(livros[i].equals(titulo)) return true;
        }
        return false;
    }

    public List<UsuarioRanking> getRanking() throws Exception {
        List<UsuarioRanking> ranking = new ArrayList<>();
        int i = 0;
        try(Connection c = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/leioLivro","postgres","senha")){ 
            String sql = "SELECT * FROM usuario ORDER BY pontos DESC LIMIT 10;";
            PreparedStatement stm = c.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                ranking.add(preencheUsuarioRanking(rs,i));
                i++;
            }
        } catch (Exception e) {
            throw new Exception("Erro no ranking", e);
        }
        return ranking;
    }

    private UsuarioRanking preencheUsuarioRanking(ResultSet rs, int i) throws Exception {
        UsuarioRanking u = new UsuarioRanking();
        try{
            u.setColocacao(i+1);
            u.setNome(rs.getString("nome"));
            u.setLogin(rs.getString("login"));
            u.setPontos(rs.getInt("pontos"));
        }catch(Exception e){
            throw new Exception("Erro no preenchimento", e);
        }
        return u;
    }

    public void adicionaPontos(int paginas, String login) throws Exception {
        try(Connection c = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/leioLivro","postgres","senha")){ 
            String sql = "UPDATE usuario SET pontos = pontos + ? WHERE login=?";
            PreparedStatement stm = c.prepareStatement(sql);
            stm.setInt(1, pontosPorPag(paginas));
            stm.setString(2, login);
            stm.executeUpdate();
        } catch (Exception e) {
            throw new Exception("Erro na adicao de pontos", e);
        }
    }

    private int pontosPorPag(int paginas) {
        return paginas/100 + 1;
    }
    
    public int getPontos(String login) throws Exception{
        try(Connection c = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/leioLivro","postgres","senha")){ 
            String sql = "SELECT pontos FROM usuario WHERE login=?";
            PreparedStatement stm = c.prepareStatement(sql);
            stm.setString(1, login);
            ResultSet rs = stm.executeQuery();
            rs.next();
            return rs.getInt("pontos");
        } catch (Exception e) {
            throw new Exception("Erro na adicao de pontos", e);
        }
    }

    public List<String> getLivros(String login) throws Exception {
        List<String> retorno = new ArrayList<>();
        String[] livros = livrosBanco(login).split(",");
        int i;
        
        for(i=1; i<livros.length; i++){
            retorno.add(livros[i]);
        }
        
        return retorno;
    }
}