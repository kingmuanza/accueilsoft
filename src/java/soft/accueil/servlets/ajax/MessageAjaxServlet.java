package soft.accueil.servlets.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import soft.accueil.dao.MessageDAO;
import soft.accueil.entities.Utilisateur;

@WebServlet(name="MessageAjaxServlet", urlPatterns={"/MessageAjaxServlet"})
public class MessageAjaxServlet extends HttpServlet {
   
    MessageDAO messageDAO = new MessageDAO();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        Utilisateur utilisateur = null;
        utilisateur = (Utilisateur) httpSession.getAttribute("utilisateur");
        if (utilisateur != null) {
            request.setAttribute("messages_nonlus", messageDAO.getallMyUnreadReceive(utilisateur.getEmploye()));
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/ajax/messages_nonlus.jsp").forward(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
    }

    
}
