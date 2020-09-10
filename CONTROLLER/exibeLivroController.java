package Controller;

import Model.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "exibeLivroController", urlPatterns = {"/exibeLivro"})
public class exibeLivroController extends HttpServlet {

    private void getBotao(HttpServletRequest request, HttpServletResponse response, String titulo)
            throws ServletException, IOException, Exception{
        UsuarioDAO uDAO = new UsuarioDAO();
        UsuarioLogado u = (UsuarioLogado) request.getSession().getAttribute("usuario");;
        
        if(uDAO.leu(titulo, u.getLogin())) request.setAttribute("botao", new botaoCadastroLeu());
        else request.setAttribute("botao", new botaoCadastroNaoLeu());
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LivrosDAO l = new LivrosDAO();
        String titulo = (String) request.getParameter("titulo");
        request.setAttribute("livro", l.getLivro(titulo));
        try {
            getBotao(request, response, titulo);
        } catch (Exception ex) {
            request.setAttribute("botao", new botaoCadastroOff());
        }
        request.getRequestDispatcher("exibeLivro.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}
