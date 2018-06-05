package soft.accueil.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import soft.accueil.dao.EmployeDAO;
import soft.accueil.dao.MessageDAO;
import soft.accueil.dao.UtilisateurDAO;
import soft.accueil.dao.UtilisateurProfilFonctionnaliteDAO;
import soft.accueil.entities.Employe;
import soft.accueil.entities.Message;
import soft.accueil.entities.Utilisateur;

@WebServlet(name = "FormNouveauMessageServlet", urlPatterns = {"/FormNouveauMessageServlet"})
public class FormNouveauMessageServlet extends HttpServlet {

    EmployeDAO employeDAO = new EmployeDAO();
    MessageDAO messageDAO = new MessageDAO();
    UtilisateurProfilFonctionnaliteDAO upfDAO = new UtilisateurProfilFonctionnaliteDAO();
    UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession httpSession = request.getSession();
        Utilisateur utilisateur = null;
        utilisateur = (Utilisateur) httpSession.getAttribute("utilisateur");
        List<Employe> employes = new ArrayList<>();

        if (utilisateur != null) {
            request.setAttribute("upfDAO", upfDAO);
            if (upfDAO.hasAccess(utilisateur, "003")) { // Agent d'accueil
                employes.addAll(utilisateurDAO.getEmployes()) ;
            }
            if (upfDAO.hasAccess(utilisateur, "002")) { // Employé
                employes.addAll(utilisateurDAO.getAgents()) ;
            }
            String idemploye = request.getParameter("idemploye");
            if(idemploye!=null && !idemploye.isEmpty()){
                System.out.println("Employe enregistré");
                int i = Integer.parseInt(idemploye);
                request.setAttribute("employe", employeDAO.get(i));
            }
            request.setAttribute("employes", employes);
            this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/form/nouveau_message.jsp").forward(request, response);

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
            String action = request.getParameter("action");
            if (action != null && !action.isEmpty()) {
                if(action.equals("message_lu")){
                    System.out.println("message_lu");
                            
                    String id = request.getParameter("id");
                    int i = Integer.parseInt(id);
                    Message message = messageDAO.get(i);
                    message.setDateReception(new Date());
                    messageDAO.modifier(message);
                }
            } else {
                Employe e = null;
                String employe = request.getParameter("employe");
                if (employe != null && !employe.isEmpty()) {
                    int idemploye = Integer.parseInt((employe));
                    e = employeDAO.get(idemploye);
                }
                String contenu = request.getParameter("contenu");

                Message message = new Message();
                message.setContenu(contenu);
                message.setDateEnvoi(new Date());
                message.setDateReception(null);
                message.setEmployeByIdemetteur(utilisateur.getEmploye());
                message.setEmployeByIdrecepteur(e);

                messageDAO.ajouter(message);
            }

            response.sendRedirect("start?messageenvoye=true");

        }

    }

}
