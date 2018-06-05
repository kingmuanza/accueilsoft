package soft.accueil.servlets;

import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import soft.accueil.dao.BureauDAO;
import soft.accueil.dao.EmployeDAO;
import soft.accueil.dao.EntreeBadgeDAO;
import soft.accueil.dao.EntreeDAO;
import soft.accueil.entities.Bureau;
import soft.accueil.entities.Employe;
import soft.accueil.entities.Entree;
import soft.accueil.entities.EntreeBadge;

@WebServlet(name = "EntreeServlet", urlPatterns = {"/EntreeServlet"})
public class EntreeServlet extends HttpServlet {

    EntreeDAO entreeDAO = new EntreeDAO();
    EntreeBadgeDAO entreeBadgeDAO = new EntreeBadgeDAO();
    BureauDAO bureauDAO = new BureauDAO();
    EmployeDAO employeDAO = new EmployeDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        if (id != null && !id.isEmpty()) {
            int i = Integer.parseInt(id);
            Entree entree = entreeDAO.get(i);
            request.setAttribute("entree", entree);
        }
        request.setAttribute("entreeBadgesAll", entreeBadgeDAO.getall());
        request.setAttribute("bureaux", bureauDAO.getall());
        request.setAttribute("employes", employeDAO.getall());
        this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/entree.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("On est quand mm dans la bonne servlet là");
        String action = request.getParameter("action");
        System.out.println("Action : " + action);
        String id = request.getParameter("id");
        if (id != null && !id.isEmpty()) {
            int i = Integer.parseInt(id);
            Entree entree = entreeDAO.get(i);
            if (action != null && !action.isEmpty() && "sortie".equals(action)) {
                System.out.println(request.getParameter("muanza"));
                entree.setHeureSortie(new Date());
                entreeDAO.modifier(entree);
                if (entree.getEntreeBadge() != null) {
                    EntreeBadge eb = entreeBadgeDAO.get(entree.getEntreeBadge().getIdentreeBadge());
                    eb.setStatut(null);
                    entreeBadgeDAO.modifier(eb);
                }

            }
            if (action != null && !action.isEmpty() && "confirmation_entree".equals(action)) {
                System.out.println("confirmation_entree");
                entree.setHeureEntreeBureau(new Date());
                entreeDAO.modifier(entree);
            }
            if (action != null && !action.isEmpty() && "confirmation_sortie".equals(action)) {
                System.out.println("confirmation_sortie");
                entree.setHeureSortieBureau(new Date());
                entreeDAO.modifier(entree);
            }
            if (action != null && !action.isEmpty() && "modifier".equals(action)) {
                String nom = request.getParameter("nom");
                String piece = request.getParameter("piece");
                String numero = request.getParameter("numero");
                String motif = request.getParameter("motif");
                String entreprise = request.getParameter("entreprise");
                if (!(motif != null && !motif.isEmpty())) {
                    motif = "Non spécifié ";
                }
                String idbadge = request.getParameter("badge");
                EntreeBadge entreeBadge = null;
                if (idbadge != null && !idbadge.isEmpty()) {
                    entreeBadge = entreeBadgeDAO.get(Integer.parseInt(idbadge));
                }
                String idbureau = request.getParameter("bureau");
                Bureau bureau = null;
                if (idbureau != null && !idbureau.isEmpty()) {
                    bureau = bureauDAO.get(Integer.parseInt(idbureau));
                }
                String idemploye = request.getParameter("employe");
                Employe employe = null;
                employe = employeDAO.get(Integer.parseInt(idemploye));

                entree.setBureau(bureau);
                entree.setEmploye(employe);
                entree.setEntreeBadge(entreeBadge);
                entree.setNomComplet(nom);
                entree.setMotif(motif);
                entree.setPiece(piece);
                entree.setNumeroDePiece(numero);
                entree.setEntreprise(entreprise);

                entreeDAO.modifier(entree);
            }
        }
        response.sendRedirect("start");
    }

}
