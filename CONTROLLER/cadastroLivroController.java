package Controller;

import Model.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "cadastroLivroController", urlPatterns = {"/cadastroLivro"})
public class cadastroLivroController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LivrosDAO l = new LivrosDAO();
        String titulo = (String) request.getParameter("titulo");
        String autor = (String) request.getParameter("autor");
        String imagem = (String) request.getParameter("imagem");
        String sinopse = (String) request.getParameter("sinopse");
        String estilo = (String) request.getParameter("estilo");
        String paginas = (String) request.getParameter("paginas");
        
        try{
            l.cadastrar(titulo, autor, imagem, sinopse, estilo, paginas);
            request.getRequestDispatcher("carregar").forward(request,response);
        }
        catch(Exception e){
            request.setAttribute("erro", e.getMessage());
            request.getRequestDispatcher("cadastroLivro.jsp").forward(request,response);
        }
    }
}
