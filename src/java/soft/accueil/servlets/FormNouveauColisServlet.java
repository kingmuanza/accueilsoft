package soft.accueil.servlets;

import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import soft.accueil.dao.ColisDAO;
import soft.accueil.dao.EmployeDAO;
import soft.accueil.entities.Colis;
import soft.accueil.entities.Employe;

@WebServlet(name = "FormNouveauColisServlet", urlPatterns = {"/FormNouveauColisServlet"})
public class FormNouveauColisServlet extends HttpServlet {

    EmployeDAO employeDAO = new EmployeDAO();
    ColisDAO colisDAO = new ColisDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("employes", employeDAO.getall());
        this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/form/nouveau_colis.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");
        String action = request.getParameter("action");
        if (action != null && !action.isEmpty()) {
            if(action.equals("colis_recu")){
                int i = Integer.parseInt(id);
                Colis colis = colisDAO.get(i);
                colis.setStatut("livré");
                colisDAO.modifier(colis);
            }
        } else {
            String nom = request.getParameter("nom");
            String piece = request.getParameter("piece");
            String numeropiece = request.getParameter("numeropiece");
            String numerocolis = request.getParameter("numerocolis");
            String employe = request.getParameter("employe");
            int idemploye = 1;
            idemploye = Integer.parseInt(employe);
            Employe e = employeDAO.get(idemploye);
            String description = request.getParameter("description");

            Colis colis = new Colis();
            colis.setBureau(e.getBureau());
            colis.setDateDepot(new Date());
            colis.setDescription(description);
            colis.setEmploye(e);
            colis.setNoms(nom);
            colis.setPiece(piece);
            colis.setNumerocolis(numerocolis);
            colis.setNumeropiece(numeropiece);

            colisDAO.ajouter(colis);
        }

        response.sendRedirect("start");

    }

}
