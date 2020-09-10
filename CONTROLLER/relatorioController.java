package Controller;

import Model.*;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "relatorioController", urlPatterns = {"/relatorio"})
public class relatorioController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RelatorioDAO r = new RelatorioDAO();
        UsuarioDAO u = new UsuarioDAO();
        String login = (String) request.getParameter("login");
        request.setAttribute("login", login);
        
        try {
            request.setAttribute("estilosLidos", r.getEstilosLidos(login));
            request.setAttribute("trofeus", r.getTrofeus(login));
            request.setAttribute("livrosLidos", u.getLivros(login));
            request.setAttribute("pontos", u.getPontos(login));
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
        
        request.getRequestDispatcher("relatorio.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}
