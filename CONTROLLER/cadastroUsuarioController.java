package Controller;

import Model.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "cadastroUsuarioController", urlPatterns = {"/cadastroUsuario"})
public class cadastroUsuarioController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UsuarioDAO u = new UsuarioDAO();
        RelatorioDAO r = new RelatorioDAO();
        String nome = (String) request.getParameter("nome");
        String login = (String) request.getParameter("login");
        String senha = (String) request.getParameter("senha");
        
        try{
            u.cadastrar(nome, login, senha);
            r.cadastrar(login);
            request.getRequestDispatcher("login.jsp").forward(request,response);
        }
        catch(Exception e){
            request.setAttribute("erro", e.getMessage());
            request.getRequestDispatcher("cadastroUsuario.jsp").forward(request,response);
        }
    }
}
