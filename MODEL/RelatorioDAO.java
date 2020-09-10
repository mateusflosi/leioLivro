package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelatorioDAO {
    
    public void cadastrar(String login) throws SQLException{
        try(Connection c = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/leioLivro","postgres","senha")){
          
            String sql = "INSERT INTO relatorio(login, conquistas, ficcao, romance, acao, comedia, epoca, investigacao, drama, terror, suspense, outros) VALUES (?, ?, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);";
            PreparedStatement stm = c.prepareStatement(sql);
            stm.setString(1, login);
            stm.setString(2, ",");
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Login ja existe", e);
        }
    }
    
    public void adicionaLivro(String estilo, String login) throws SQLException{
        try(Connection c = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/leioLivro","postgres","senha")){          
            String sql = "UPDATE relatorio SET " + estilo.toLowerCase() + "= " + estilo.toLowerCase() + " + 1 WHERE login=?";
            PreparedStatement stm = c.prepareStatement(sql);
            stm.setString(1, login);
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("erro na adição", e);
        }
    }

    public List<EstilosLidos> getEstilosLidos(String login) throws SQLException {
        List<EstilosLidos> lidos = new ArrayList<>();
        try(Connection c = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/leioLivro","postgres","senha")){          
            String sql = "SELECT * FROM relatorio WHERE login = ?";
            String[] estilos = getEstilos();
            int i;
            PreparedStatement stm = c.prepareStatement(sql);
            stm.setString(1, login);
            ResultSet rs = stm.executeQuery();
            rs.next();
            for(i=0;i<estilos.length;i++)
            {
                if(naoZerado(rs, estilos[i]))
                    lidos.add(preencheEstilosLidos(rs, estilos[i]));
            }
        } catch (SQLException e) {
            throw new SQLException("erro nos estilos", e);
        }
        return lidos;
        
    }

    private String[] getEstilos() {
        String[] estilos = new String[10];
        estilos[0] = "ficcao";
        estilos[1] = "romance";
        estilos[2] = "acao";
        estilos[3] = "comedia";
        estilos[4] = "epoca";
        estilos[5] = "investigacao";
        estilos[6] = "drama";
        estilos[7] = "terror";
        estilos[8] = "suspense";
        estilos[9] = "outros";
        return estilos;
    }

    private boolean naoZerado(ResultSet rs, String estilo) throws SQLException {
        return (rs.getInt(estilo) > 0);
    }

    private EstilosLidos preencheEstilosLidos(ResultSet rs, String estilo) throws SQLException {
        EstilosLidos e = new EstilosLidos();
        e.setEstilo(estilo);
        e.setQtd(rs.getInt(estilo));
        return e;
    }

    public List<String> getTrofeus(String login) throws Exception {
        try(Connection c = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/leioLivro","postgres","senha")){ 
            String sql = "SELECT conquistas FROM relatorio WHERE login=?";
            PreparedStatement stm = c.prepareStatement(sql);
            stm.setString(1, login);
            ResultSet rs = stm.executeQuery();
            rs.next();
            return vetToList(rs.getString("conquistas"));
        } catch (Exception e) {
            throw new Exception("Erro nos trofeus", e);
        }
    }

    private List<String> vetToList(String conquistas) {
        List<String> trofeus = new ArrayList<>();
        String vet[] = conquistas.split(",");
        int i;
        
        for(i=1; i<vet.length; i++){
            trofeus.add(vet[i]);
        }
        
        return trofeus;
    }

    public void adicionaTrofeus(String estilo, String login) throws Exception {
        try(Connection c = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/leioLivro","postgres","senha")){ 
            String sql = "SELECT * FROM relatorio WHERE login=?";
            PreparedStatement stm = c.prepareStatement(sql);
            stm.setString(1, login);
            ResultSet rs = stm.executeQuery();
            rs.next();
            if(rs.getInt(estilo) == 5) setConquistas(estilo, login);
        } catch (Exception e) {
            throw new Exception("Erro na adicao de trofeus", e);
        }
    }

    private void setConquistas(String estilo, String login) throws Exception {
        try(Connection c = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/leioLivro","postgres","senha")){ 
            String sql = "UPDATE relatorio SET conquistas=? WHERE login=?";
            PreparedStatement stm = c.prepareStatement(sql);
            stm.setString(1, getConquistas(login).concat("Leitor de " + estilo + ","));
            stm.setString(2, login);
            stm.executeUpdate();
        } catch (Exception e) {
            throw new Exception("Erro no setConquistas", e);
        }
    }

    private String getConquistas(String login) throws Exception {
        try(Connection c = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/leioLivro","postgres","senha")){ 
            String sql = "SELECT conquistas FROM relatorio WHERE login=?";
            PreparedStatement stm = c.prepareStatement(sql);
            stm.setString(1, login);
            ResultSet rs = stm.executeQuery();
            rs.next();
            return rs.getString("conquistas");
        } catch (Exception e) {
            throw new Exception("Erro no getConquistas", e);
        }
    }
}