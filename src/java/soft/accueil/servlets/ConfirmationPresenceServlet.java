package soft.accueil.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import soft.accueil.dao.EntreeDAO;
import soft.accueil.entities.Entree;

@WebServlet(name="ConfirmationPresenceServlet", urlPatterns={"/ConfirmationPresenceServlet"})
public class ConfirmationPresenceServlet extends HttpServlet {
   
    EntreeDAO entreeDAO = new EntreeDAO();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String id = request.getParameter("id");
        if(id!=null && !id.isEmpty()){
            int i = Integer.parseInt(id);
            Entree e = entreeDAO.get(i);
            request.setAttribute("entree", e);
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/form/confirmation_presence.jsp").forward(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
    }

    
}
