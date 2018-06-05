package soft.accueil.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import soft.accueil.dao.EntreeBadgeDAO;
import soft.accueil.dao.EntreeDAO;
import soft.accueil.dao.VisiteDAO;
import soft.accueil.entities.Entree;
import soft.accueil.entities.EntreeBadge;
import soft.accueil.entities.Visite;

@WebServlet(name = "VisiteToEntreeServlet", urlPatterns = {"/VisiteToEntreeServlet"})
public class VisiteToEntreeServlet extends HttpServlet {

    VisiteDAO visiteDAO = new VisiteDAO();
    EntreeDAO entreeDAO = new EntreeDAO();
    EntreeBadgeDAO entreeBadgeDAO = new EntreeBadgeDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession httpSession = request.getSession();

        String id = request.getParameter("id");
        if (id != null && !id.isEmpty()) {
            int i = Integer.parseInt(id);
            request.setAttribute("visite", visiteDAO.get(i));
        }
        request.setAttribute("entreeBadges", entreeBadgeDAO.getallNonUtilise());

        this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/form/visite_entree.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        HttpSession httpSession = request.getSession();

        //Récupération de l'action
        String action = request.getParameter("action");
        if (action != null && !action.isEmpty() && action.equals("supprimer")) {
            String id = request.getParameter("id");
            if (id != null && !id.isEmpty()) {
                if (true) {
                    httpSession.setAttribute("suppression", true);
                } else {
                    httpSession.setAttribute("suppression", false);
                }
            }
        } else {

            //Paramètres
            String idvisite = request.getParameter("idvisite");
            Entree entree = new Entree();
            Visite visite = null;
            if (idvisite != null && !idvisite.isEmpty()) {
                visite = visiteDAO.get(Integer.parseInt(idvisite));
                entree.setBureau(visite.getEmploye().getBureau());
                entree.setEmploye(visite.getEmploye());
                entree.setMotif(visite.getMotif());
                entree.setEntreprise(visite.getEntreprise());
                entree.setNomComplet(visite.getNomPersonneAttendue());
                entree.setVisite(visite);
            }

            String piece = request.getParameter("piece");
            String numero = request.getParameter("numero");
            String idbadge = request.getParameter("badge");
            EntreeBadge entreeBadge = null;
            if (idbadge != null && !idbadge.isEmpty()) {
                entreeBadge = entreeBadgeDAO.get(Integer.parseInt(idbadge));
                entree.setEntreeBadge(entreeBadge);
            }
            entree.setHeureDentree(new Date());
            entree.setNumeroDePiece(numero);
            entree.setPiece(piece);

            //Fin Paramètres
            if (entreeDAO.ajouter(entree)) {

            } else {
                httpSession.setAttribute("creation", false);
            }
            visite.setStatut("Venu");
            visiteDAO.modifier(visite);
        }

        response.sendRedirect("start");
    }

}
