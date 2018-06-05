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

@WebServlet(name="VisiteServlet", urlPatterns={"/VisiteServlet"})
public class VisiteServlet extends HttpServlet {
   
    VisiteDAO visiteDAO = new VisiteDAO();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String id = request.getParameter("id");
        if(id!=null && !id.isEmpty()){
            int i = Integer.parseInt(id);
            Visite visite = visiteDAO.get(i);
            request.setAttribute("visite", visite);
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/visite.jsp").forward(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        HttpSession httpSession = request.getSession();
        Utilisateur utilisateur = null;
        utilisateur = (Utilisateur) httpSession.getAttribute("utilisateur");

        if (utilisateur != null) {
            String id = request.getParameter("id");
            String nom = request.getParameter("nom");
            String commentaire = request.getParameter("commentaire");
            String motif = request.getParameter("motif");
            if (!(motif != null && !motif.isEmpty())) {
                motif = "Non spécifié ";
            }
            String date_arrivee = request.getParameter("date_arrivee");
            String heure = request.getParameter("heure");
            String entreprise = request.getParameter("entreprise");
            Date heure_arrivee = null;
            try {
                heure_arrivee = sdf.parse(date_arrivee + " " + heure);
                //Fin Paramètres
            } catch (ParseException ex) {
            }
            int i = Integer.parseInt(id);
            Visite visite = visiteDAO.get(i);
            visite.setCommentaire(commentaire);
            visite.setEmploye(utilisateur.getEmploye());
            visite.setHeureAttendue(heure_arrivee);
            visite.setMotif(motif);
            visite.setEntreprise(entreprise);
            visite.setNomPersonneAttendue(nom);
            visite.setStatut("Créé");
            
            visiteDAO.modifier(visite);
            
            response.sendRedirect("start");

        }else{
            response.sendRedirect("index.htm");
        }
    }

    
}
