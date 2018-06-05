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
import soft.accueil.dao.BureauDAO;
import soft.accueil.dao.EmployeDAO;
import soft.accueil.dao.EntreeDAO;
import soft.accueil.dao.UtilisateurDAO;
import soft.accueil.entities.Utilisateur;

@WebServlet(name = "StatistiqueEmployeServlet", urlPatterns = {"/StatistiqueEmployeServlet"})
public class StatistiqueEmployeServlet extends HttpServlet {

    EntreeDAO entreeDAO = new EntreeDAO();
    UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
    BureauDAO bureauDAO = new BureauDAO();
    EmployeDAO employeDAO = new EmployeDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession httpSession = request.getSession();
        Utilisateur utilisateur = null;

        utilisateur = (Utilisateur) httpSession.getAttribute("utilisateur");

        if (utilisateur != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");

            String debut = request.getParameter("debut");
            String fin = request.getParameter("fin");
            request.setAttribute("entrees", entreeDAO.getallof(utilisateur));
            request.setAttribute("bureaux", bureauDAO.getall());
            request.setAttribute("employes", employeDAO.getall());
            request.setAttribute("premierEntree", entreeDAO.premiereEntree());
            request.setAttribute("derniereEntree", entreeDAO.derniereEntree());
            if (debut != null && !debut.isEmpty()) {
                Date datedebut = entreeDAO.premiereEntree();
                try {
                    datedebut = sdf.parse(debut);
                } catch (ParseException ex) {
                    datedebut = entreeDAO.premiereEntree();
                }
                datedebut.setHours(01);
                datedebut.setMinutes(59);
                request.setAttribute("dateDebut", sdf.format(datedebut));
                request.setAttribute("premierEntree", datedebut);
            } else {
                Date datedebut = entreeDAO.premiereEntree();
                datedebut.setHours(01);
                datedebut.setMinutes(59);
                request.setAttribute("dateDebut", sdf.format(entreeDAO.premiereEntree()));
                request.setAttribute("premierEntree", datedebut);
            }
            if (fin != null && !fin.isEmpty()) {
                Date datefin = entreeDAO.derniereEntree();
                try {
                    datefin = sdf.parse(fin);
                } catch (ParseException ex) {
                    datefin = entreeDAO.derniereEntree();
                }
                datefin.setHours(23);
                datefin.setMinutes(59);
                request.setAttribute("dateFin", sdf.format(datefin));
                request.setAttribute("derniereEntree", datefin);
            } else {
                Date datefin = entreeDAO.derniereEntree();
                datefin.setHours(23);
                datefin.setMinutes(59);
                request.setAttribute("dateFin", sdf.format(entreeDAO.derniereEntree()));
                request.setAttribute("derniereEntree", datefin);
            }

            this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/statistiques_employes.jsp").forward(request, response);
        } else {
            response.sendRedirect("index.htm");
        }

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
            String id = request.getParameter("id");
            //Paramètres

            //Fin Paramètres
            if (id != null && !id.isEmpty()) {
                if (true) {
                    httpSession.setAttribute("modification", true);
                } else {
                    httpSession.setAttribute("modification", false);
                }
            } else if (true) {
                httpSession.setAttribute("creation", true);
            } else {
                httpSession.setAttribute("creation", false);
            }
        }

        response.sendRedirect("HistoriqueServlet");
    }

}
