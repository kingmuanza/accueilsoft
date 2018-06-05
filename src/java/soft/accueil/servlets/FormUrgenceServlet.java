package soft.accueil.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import soft.accueil.dao.UrgenceDAO;
import soft.accueil.entities.Urgence;
import soft.accueil.entities.Utilisateur;

@WebServlet(name = "FormUrgenceServlet", urlPatterns = {"/FormUrgenceServlet"})
public class FormUrgenceServlet extends HttpServlet {

    UrgenceDAO urgenceDAO = new UrgenceDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession httpSession = request.getSession();
        Utilisateur utilisateur = null;
        utilisateur = (Utilisateur) httpSession.getAttribute("utilisateur");

        if (utilisateur != null) {
            this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/form/urgence.jsp").forward(request, response);

        } else {
            response.sendRedirect("index.htm");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        Utilisateur utilisateur = null;
        utilisateur = (Utilisateur) httpSession.getAttribute("utilisateur");

        if (utilisateur != null) {
            String id = request.getParameter("id");
            if (id != null && !id.isEmpty()) {
                int i = Integer.parseInt(id);
                Urgence urgence = urgenceDAO.get(i);
                urgence.setResolue(true);
                urgenceDAO.modifier(urgence);
            } else {
                Urgence urgence = new Urgence();
                urgence.setDate(new Date());
                urgence.setEmploye(utilisateur.getEmploye());
                urgence.setResolue(false);
                urgence.setStatut(null);
                urgenceDAO.ajouter(urgence);
            }

        }
        response.sendRedirect("start");
    }

}
