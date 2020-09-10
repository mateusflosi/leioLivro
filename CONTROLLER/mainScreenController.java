package Controller;

import Model.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "mainScreenController", urlPatterns = {"/carregar"})
public class mainScreenController extends HttpServlet {
    
    protected void process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LivrosDAO l = new LivrosDAO();
        request.setAttribute("livros", l.getLivros());
        request.getRequestDispatcher("index.jsp").forward(request,response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Object o = request.getSession().getAttribute("usuario");
        request.setAttribute("usuario", o);
        if(o == null) request.setAttribute("head", new headOff());
        else request.setAttribute("head", new headOn());
        process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("head", new headOn());
        request.setAttribute("usuario", request.getSession().getAttribute("usuario"));
        process(request, response);
    }
}
