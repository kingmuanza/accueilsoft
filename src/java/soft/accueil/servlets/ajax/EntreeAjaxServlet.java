package soft.accueil.servlets.ajax;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import soft.accueil.dao.EntreeDAO;
import soft.accueil.entities.Entree;
import soft.accueil.entities.Utilisateur;

@WebServlet(name="EntreeAjaxServlet", urlPatterns={"/EntreeAjaxServlet"})
public class EntreeAjaxServlet extends HttpServlet {
   
    EntreeDAO entreeDAO = new EntreeDAO();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        HttpSession httpSession = request.getSession();
        Utilisateur utilisateur = null;
        utilisateur = (Utilisateur) httpSession.getAttribute("utilisateur");
        if (utilisateur != null) {
            List<Entree> entreesEmploye = entreeDAO.getAllOfActual(utilisateur.getEmploye());
            request.setAttribute("mesEntrees", entreesEmploye);            
        }
        
        this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/ajax/entrees.jsp").forward(request, response);
        
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
    }

    
}
