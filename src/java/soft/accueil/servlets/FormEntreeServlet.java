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
import soft.accueil.dao.BureauDAO;
import soft.accueil.dao.EmployeDAO;
import soft.accueil.dao.EntreeBadgeDAO;
import soft.accueil.dao.EntreeDAO;
import soft.accueil.entities.Bureau;
import soft.accueil.entities.Employe;
import soft.accueil.entities.Entree;
import soft.accueil.entities.EntreeBadge;

@WebServlet(name = "FormEntreeServlet", urlPatterns = {"/FormEntreeServlet"})
public class FormEntreeServlet extends HttpServlet {

    EntreeBadgeDAO entreeBadgeDAO = new EntreeBadgeDAO();
    EntreeDAO entreeDAO = new EntreeDAO();
    BureauDAO bureauDAO = new BureauDAO();
    EmployeDAO employeDAO = new EmployeDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("entreeBadges", entreeBadgeDAO.getallNonUtilise());
        request.setAttribute("entreeBadgesAll", entreeBadgeDAO.getall());
        request.setAttribute("bureaux", bureauDAO.getall());
        request.setAttribute("employes", employeDAO.getall());
        this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/form/entree.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        HttpSession httpSession = request.getSession();

        //Récupération de l'action
        String action = request.getParameter("action");
        if (action != null && !action.isEmpty()) {
            if (action.equals("sortie")) {
                String id = request.getParameter("id");
                System.out.println("ID de l'entrée : " + id);
                if (id != null && !id.isEmpty()) {
                    Entree entree = entreeDAO.get(Integer.parseInt(id));
                    entree.setHeureSortie(new Date());
                    if (entreeDAO.modifier(entree)) {
                        if (entree.getEntreeBadge() != null) {
                            EntreeBadge eb = entreeBadgeDAO.get(entree.getEntreeBadge().getIdentreeBadge());
                            eb.setStatut(null);
                            entreeBadgeDAO.modifier(eb);
                        }
                        httpSession.setAttribute("suppression", true);
                    } else {
                        httpSession.setAttribute("suppression", false);
                    }
                }
            }
        } else {
            String id = request.getParameter("identree");
            //Paramètres
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
            if (idemploye != null && !idemploye.isEmpty()) {
                employe = employeDAO.get(Integer.parseInt(idemploye));
                bureau = bureauDAO.get(employe.getBureau().getIdbureau());
            }

            //Fin Paramètres
            if (id != null && !id.isEmpty()) {
                Entree entree = entreeDAO.get(Integer.parseInt(id));
                if (entree.getEntreeBadge() != null) {
                    EntreeBadge eb = entreeBadgeDAO.get(entree.getEntreeBadge().getIdentreeBadge());
                    eb.setStatut(null);
                    entreeBadgeDAO.modifier(eb);
                }
                entree.setBureau(bureau);
                entree.setEmploye(employe);
                entree.setEntreeBadge(entreeBadge);
                entree.setNomComplet(nom);
                entree.setMotif(motif);
                entree.setPiece(piece);
                entree.setNumeroDePiece(numero);
                entree.setEntreprise(entreprise);

                if (entreeDAO.modifier(entree)) {
                    httpSession.setAttribute("modification", true);
                    if (entreeBadge != null) {
                        EntreeBadge eb = entreeBadgeDAO.get(entree.getEntreeBadge().getIdentreeBadge());
                        eb.setStatut("Utilisé");
                        entreeBadgeDAO.modifier(eb);
                    }
                } else {
                    httpSession.setAttribute("modification", false);
                }
            } else {
                Entree entree = new Entree();
                entree.setBureau(bureau);
                entree.setEmploye(employe);
                entree.setEntreeBadge(entreeBadge);
                entree.setNomComplet(nom);
                entree.setMotif(motif);
                entree.setPiece(piece);
                entree.setNumeroDePiece(numero);
                entree.setEntreprise(entreprise);
                entree.setHeureDentree(new Date());
                if (entreeDAO.ajouter(entree)) {
                    httpSession.setAttribute("creation", true);
                    if (entreeBadge != null) {
                        EntreeBadge eb = entreeBadgeDAO.get(entree.getEntreeBadge().getIdentreeBadge());
                        eb.setStatut("Utilisé");
                        entreeBadgeDAO.modifier(eb);
                    }

                } else {
                    httpSession.setAttribute("creation", false);
                }
            }
        }

        response.sendRedirect("start");
    }


}
