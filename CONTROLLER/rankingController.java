package Controller;

import Model.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "rankingController", urlPatterns = {"/ranking"})
public class rankingController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UsuarioDAO uDAO = new UsuarioDAO();
        Object o = request.getSession().getAttribute("usuario");
        UsuarioLogado u;
        try {
            request.setAttribute("ranking",uDAO.getRanking());
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
        
        if(o == null) request.setAttribute("botao", new botaoRelatorioOff());
        else{
            u = (UsuarioLogado) o;
            request.setAttribute("login", u.getLogin());
            request.setAttribute("botao", new botaoRelatorioOn());
        }
        request.getRequestDispatcher("ranking.jsp").forward(request,response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}