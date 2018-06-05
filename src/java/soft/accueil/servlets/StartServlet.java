package soft.accueil.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import soft.accueil.dao.BureauDAO;
import soft.accueil.dao.ColisDAO;
import soft.accueil.dao.EmployeDAO;
import soft.accueil.dao.EntreeBadgeDAO;
import soft.accueil.dao.EntreeDAO;
import soft.accueil.dao.MessageDAO;
import soft.accueil.dao.PieceOublieeDAO;
import soft.accueil.dao.PosteDAO;
import soft.accueil.dao.UrgenceDAO;
import soft.accueil.dao.UtilisateurDAO;
import soft.accueil.dao.UtilisateurProfilFonctionnaliteDAO;
import soft.accueil.dao.VisiteDAO;
import soft.accueil.entities.Entree;
import soft.accueil.entities.Utilisateur;
import soft.accueil.entities.Visite;

@WebServlet(name = "StartServlet", urlPatterns = {"/start"})
public class StartServlet extends HttpServlet {

    UtilisateurProfilFonctionnaliteDAO upfDAO = new UtilisateurProfilFonctionnaliteDAO();
    EntreeDAO entreeDAO = new EntreeDAO();
    EntreeBadgeDAO entreeBadgeDAO = new EntreeBadgeDAO();
    BureauDAO bureauDAO = new BureauDAO();
    EmployeDAO employeDAO = new EmployeDAO();
    VisiteDAO visiteDAO = new VisiteDAO();
    PieceOublieeDAO pieceOublieeDAO = new PieceOublieeDAO();
    UrgenceDAO urgenceDAO = new UrgenceDAO();
    MessageDAO messageDAO = new MessageDAO();
    ColisDAO colisDAO = new ColisDAO();
    PosteDAO posteDAO = new PosteDAO();
    UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession httpSession = request.getSession();
        Utilisateur utilisateur = null;
        utilisateur = (Utilisateur) httpSession.getAttribute("utilisateur");
        String page = "/WEB-INF/jsp/start.jsp";

        if (utilisateur != null) {

            notificateur(request, response);
            request.setAttribute("upfDAO", upfDAO);
            if (utilisateur.getEmploye() != null) {
                request.setAttribute("employe", employeDAO.get(utilisateur.getEmploye().getIdemploye()));
                if (employeDAO.get(utilisateur.getEmploye().getIdemploye()).getImageblob() != null) {
                    byte[] blob = employeDAO.get(utilisateur.getEmploye().getIdemploye()).getImageblob();
                    String str = "";
                    //read bytes from ByteArrayInputStream using read method
                    for (byte b : blob) {
                        str = str + (char) b;
                    }
                    request.setAttribute("profil", str);
                } else {
                    request.setAttribute("profil", "");
                }
                request.setAttribute("messages", messageDAO.getallMyMessagesReceive(utilisateur.getEmploye()));
                request.setAttribute("messages_nonlus", messageDAO.getallMyUnreadReceive(utilisateur.getEmploye()));
            }

            if (upfDAO.hasAccess(utilisateur, "003")) { // Agent d'accueil
                List<Entree> entrees = entreeDAO.getActuAll();
                request.setAttribute("entrees", entrees);
                request.setAttribute("urgences", urgenceDAO.getall());
                Date date = new Date();
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                cal.set(Calendar.MILLISECOND, 0);
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.HOUR_OF_DAY, 0);
                Date debut = cal.getTime();
                cal.add(Calendar.DATE, 1);
                Date fin = cal.getTime();
                request.setAttribute("premierEntree", debut);
                request.setAttribute("derniereEntree", fin);
                request.setAttribute("entreeBadges", entreeBadgeDAO.getallNonUtilise());
                request.setAttribute("entreeBadgesAll", entreeBadgeDAO.getall());
                request.setAttribute("pieces", pieceOublieeDAO.getall());
                List<Visite> visites = visiteDAO.getallActif();
                request.setAttribute("visites", visites);
                request.setAttribute("bureaux", bureauDAO.getall());
                request.setAttribute("postes", posteDAO.getall());
                request.setAttribute("employes", employeDAO.getall());
                request.setAttribute("coliss", colisDAO.getAllUnreceived());
                page = "/WEB-INF/jsp/start_guerite.jsp";
            }
            if (upfDAO.hasAccess(utilisateur, "002") && utilisateur.getEmploye()!=null) { // Employe
                List<Entree> entreesEmploye = entreeDAO.getAllOfActual(utilisateur.getEmploye());
                List<Visite> visitesEmploye = visiteDAO.getAllOfActual(utilisateur.getEmploye());
                request.setAttribute("mesEntrees", entreesEmploye);
                request.setAttribute("mesVisites", visitesEmploye);
                request.setAttribute("coliss", colisDAO.getAllByEmploye(utilisateur.getEmploye()));
                request.setAttribute("coliss_nonrecu", colisDAO.getAllUnreceivedByEmploye(utilisateur.getEmploye()));
                page = "/WEB-INF/jsp/start_employe.jsp";
            }
            if (upfDAO.hasAccess(utilisateur, "001")) { // Guerite
                request.setAttribute("utilisateurs", utilisateurDAO.getall());
                page = "/WEB-INF/jsp/start.jsp";
            }
            request.setAttribute("temps", new Date().getTime());
            this.getServletContext().getRequestDispatcher(page).forward(request, response);

        } else {
            response.sendRedirect("index.htm");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    public void notificateur(HttpServletRequest request, HttpServletResponse response) {
        String loginused = request.getParameter("loginused");
        if (loginused != null && !loginused.isEmpty()) {
            request.setAttribute("error", "Le login est déjà pris ! Veuillez en choisir un autre");
        }
        String passenotsame = request.getParameter("passenotsame");
        if (passenotsame != null && !passenotsame.isEmpty()) {
            request.setAttribute("error", "Les mots de passe ne sont pas identiques");
        }
        String passefalse = request.getParameter("passefalse");
        if (passefalse != null && !passefalse.isEmpty()) {
            request.setAttribute("error", "Le mot de passe est incorrect. Impossible de mettre à jour");
        }
        String messageenvoye = request.getParameter("messageenvoye");
        if (messageenvoye != null && !messageenvoye.isEmpty()) {
            request.setAttribute("notification", "Message envoyé avec succès");
        }
        String success = request.getParameter("success");
        if (success != null && !success.isEmpty()) {
            request.setAttribute("notification", "Changement effectué avec succès !");
        }
        String objectUsed = request.getParameter("objectUsed");
        if (objectUsed != null && !objectUsed.isEmpty()) {
            request.setAttribute("error", "Impossible de supprimer l'entité car elle est utilisée par une autre !");
        }
    }

}
