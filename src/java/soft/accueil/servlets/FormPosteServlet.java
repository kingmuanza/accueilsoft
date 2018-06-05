package soft.accueil.servlets;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.HibernateException;
import soft.accueil.dao.PosteDAO;
import soft.accueil.entities.Poste;

@WebServlet(name = "FormPosteServlet", urlPatterns = {"/FormPosteServlet"})
public class FormPosteServlet extends HttpServlet {

    PosteDAO posteDAO = new PosteDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        if (id != null && !id.isEmpty()) {
            int i = Integer.parseInt(id);
            request.setAttribute("poste", posteDAO.get(i));
        }
        request.setAttribute("postes", posteDAO.getall());
        this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/form/nouveau_poste.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String id = request.getParameter("id");

        if (action != null && !action.isEmpty()) {
            if (action.equals("supprimer")) {
                try {
                    if (id != null && !id.isEmpty()) {
                        int i = Integer.parseInt(id);
                        Poste poste = posteDAO.get(i);
                        posteDAO.supprimer(poste);
                        response.sendRedirect("start?success=true");
                    }
                }catch (IOException | NumberFormatException | HibernateException e) {
                    System.out.println(e);
                    response.sendRedirect("start?objectUsed=true");
                }
                //Handle errors for JDBC
                
            }
        } else {
            String code = request.getParameter("code");
            String libelle = request.getParameter("libelle");
            String parent = request.getParameter("parent");
            Poste posteParent = null;
            Poste poste = new Poste();
            if (parent != null && !parent.isEmpty()) {
                int idparent = Integer.parseInt(parent);
                posteParent = posteDAO.get(idparent);
            }
            if (id != null && !id.isEmpty()) {
                int idposte = Integer.parseInt(parent);
                poste = posteDAO.get(idposte);
            }
            poste.setCode(code);
            poste.setLibelle(libelle);
            poste.setPoste(posteParent);
            if (id != null && !id.isEmpty()) {
                posteDAO.modifier(poste);
            } else {
                posteDAO.ajouter(poste);
            }
            response.sendRedirect("start?success=true");
        }
        
    }

}
