package soft.accueil.servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import soft.accueil.dao.VisiteDAO;
import soft.accueil.entities.Utilisateur;
import soft.accueil.entities.Visite;

@WebServlet(name = "FormNouvelleVisiteServlet", urlPatterns = {"/FormNouvelleVisiteServlet"})
public class FormNouvelleVisiteServlet extends HttpServlet {
    
    VisiteDAO visiteDAO = new VisiteDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/form/nouvelle_visite.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        HttpSession httpSession = request.getSession();
        Utilisateur utilisateur = null;
        utilisateur = (Utilisateur) httpSession.getAttribute("utilisateur");

        if (utilisateur != null) {

            String nom = request.getParameter("nom");
            String commentaire = request.getParameter("commentaire");
            String entreprise = request.getParameter("entreprise");
            String motif = request.getParameter("motif");
            if (!(motif != null && !motif.isEmpty())) {
                motif = "Non spécifié ";
            }
            String date_arrivee = request.getParameter("date_arrivee");
            String heure = request.getParameter("heure");
            Date heure_arrivee = null;
            try {
                heure_arrivee = sdf.parse(date_arrivee + " " + heure);
                //Fin Paramètres
            } catch (ParseException ex) {
            }

            Visite visite = new Visite();
            visite.setCommentaire(commentaire);
            visite.setEmploye(utilisateur.getEmploye());
            visite.setHeureAttendue(heure_arrivee);
            visite.setMotif(motif);
            visite.setEntreprise(entreprise);
            visite.setNomPersonneAttendue(nom);
            visite.setStatut("Créé");
            
            visiteDAO.ajouter(visite);
            
            response.sendRedirect("start");

        }else{
            response.sendRedirect("index.htm");
        }
    }

}
