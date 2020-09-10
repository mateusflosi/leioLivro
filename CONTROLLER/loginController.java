package Controller;

import Model.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "loginController", urlPatterns = {"/login"})
public class loginController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UsuarioDAO u = new UsuarioDAO();
        String login = (String) request.getParameter("login");
        String senha = (String) request.getParameter("senha");
        
        try{
            request.getSession().setAttribute("usuario", u.autenticar(login, senha));
            request.setAttribute("login", login);
            request.getRequestDispatcher("carregar").forward(request,response);
        } catch(Exception ex){
            request.setAttribute("erro", ex.getMessage());
            request.getRequestDispatcher("login.jsp").forward(request,response);
        }       
    }
}
