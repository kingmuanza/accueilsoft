package soft.accueil.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.HibernateException;
import soft.accueil.dao.BureauDAO;
import soft.accueil.entities.Bureau;

@WebServlet(name = "FormBureauServlet", urlPatterns = {"/FormBureauServlet"})
public class FormBureauServlet extends HttpServlet {

    BureauDAO bureauDAO = new BureauDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        if (id != null && !id.isEmpty()) {
            int i = Integer.parseInt(id);
            request.setAttribute("bureau", bureauDAO.get(i));
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/form/nouveau_bureau.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException  {
        String action = request.getParameter("action");
        String id = request.getParameter("id");
        try {
            if (action != null && !action.isEmpty()) {
                if (action.equals("supprimer")) {

                    if (id != null && !id.isEmpty()) {
                        int i = Integer.parseInt(id);
                        Bureau bureau = bureauDAO.get(i);
                        bureauDAO.supprimer(bureau);
                        response.sendRedirect("start?success=true");
                    }

                }
            } else {
                String code = request.getParameter("code");
                String libelle = request.getParameter("libelle");
                String batiment = request.getParameter("batiment");
                String numeroPorte = request.getParameter("numeroPorte");
                String etage = request.getParameter("etage");
                Bureau bureau = new Bureau();
                if (id != null && !id.isEmpty()) {
                    int i = Integer.parseInt(id);
                    bureau = bureauDAO.get(i);
                }
                bureau.setCode(code);
                bureau.setLibelle(libelle);
                bureau.setEtage(etage);
                bureau.setNumeroPorte(numeroPorte);
                if (id != null && !id.isEmpty()) {
                    bureauDAO.modifier(bureau);
                } else {
                    bureauDAO.ajouter(bureau);
                }
                response.sendRedirect("start?success=true");
            }
        } catch (IOException | NumberFormatException | HibernateException e) {
            System.out.println(e);
            response.sendRedirect("start?objectUsed=true");
        } finally{
            //response.sendRedirect("start?objectUsed=true");
        }
    }

}
