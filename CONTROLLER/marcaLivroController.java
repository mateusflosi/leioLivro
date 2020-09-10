package Controller;

import Model.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "marcaLivroController", urlPatterns = {"/marcaLivro"})
public class marcaLivroController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UsuarioDAO uDAO = new UsuarioDAO();
        RelatorioDAO r = new RelatorioDAO();
        UsuarioLogado u = (UsuarioLogado) request.getSession().getAttribute("usuario");
        String titulo = (String) request.getParameter("titulo");
        String estilo = (String) request.getParameter("estilo");
        int paginas = Integer.parseInt(request.getParameter("paginas"));
        
        try{
            uDAO.marca(titulo, u.getLogin());
            uDAO.adicionaPontos(paginas, u.getLogin());
            r.adicionaLivro(estilo, u.getLogin());
            r.adicionaTrofeus(estilo, u.getLogin());
            request.getRequestDispatcher("carregar").forward(request,response);
        } catch(Exception e){
            throw new RuntimeException(e.getMessage());
        } 
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}
