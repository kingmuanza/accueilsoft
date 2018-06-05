package soft.accueil.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.HibernateException;
import soft.accueil.dao.BureauDAO;
import soft.accueil.dao.EmployeDAO;
import soft.accueil.dao.PosteDAO;
import soft.accueil.entities.Bureau;
import soft.accueil.entities.Employe;
import soft.accueil.entities.Poste;

@WebServlet(name = "EmployeServlet", urlPatterns = {"/EmployeServlet"})
public class EmployeServlet extends HttpServlet {

    EmployeDAO employeDAO = new EmployeDAO();
    BureauDAO bureauDAO = new BureauDAO();
    PosteDAO posteDAO = new PosteDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession httpSession = request.getSession();

        String id = request.getParameter("id");
        if (id != null && !id.isEmpty()) {
            int identity = Integer.parseInt(id);
            request.setAttribute("employe", employeDAO.get(identity));
            String str = "";
            if (employeDAO.get(identity).getImageblob() != null) {
                byte[] blob = employeDAO.get(identity).getImageblob();

//read bytes from ByteArrayInputStream using read method
                for (byte b : blob) {
                    str = str + (char) b;
                }

            }
            System.out.println(str);
            request.setAttribute("profil", str);
        }
        request.setAttribute("employes", employeDAO.getall());
        request.setAttribute("bureaux", bureauDAO.getall());
        request.setAttribute("postes", posteDAO.getall());

        this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/employe.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        HttpSession httpSession = request.getSession();

        String imageBlob = request.getParameter("imgblob");
        System.out.println(imageBlob);

        //Récupération de l'action
        String action = request.getParameter("action");
        if (action != null && !action.isEmpty() && action.equals("supprimer")) {
            String id = request.getParameter("id");
            if (id != null && !id.isEmpty()) {
                try {
                    int i = Integer.parseInt(id);
                    Employe employe = employeDAO.get(i);
                    employeDAO.supprimer(employe);
                    response.sendRedirect("start?success=true");
                } catch (IOException | NumberFormatException | HibernateException e) {
                    System.out.println(e);
                    response.sendRedirect("start?objectUsed=true");
                }
            }
        } else {
            String id = request.getParameter("id");
            //Paramètres
            String tel = request.getParameter("tel");
            String autre = request.getParameter("autre");
            String noms = request.getParameter("noms");
            String prenoms = request.getParameter("prenoms");
            String idbureau = request.getParameter("bureau");
            Bureau bureau = null;
            if (idbureau != null && !idbureau.isEmpty()) {
                bureau = bureauDAO.get(Integer.parseInt(idbureau));
            }
            String idposte = request.getParameter("poste");
            Poste poste = null;
            if (idposte != null && !idposte.isEmpty()) {
                poste = posteDAO.get(Integer.parseInt(idposte));
            }

            //Fin Paramètres
            if (id != null && !id.isEmpty()) {
                int i = Integer.parseInt(id);
                Employe employe = employeDAO.get(i);
                employe.setAutreContact(autre);
                employe.setBureau(bureau);
                employe.setNoms(noms);
                employe.setPrenoms(prenoms);
                employe.setPoste(poste);
                employe.setTelEntreprise(tel);
                byte[] myBytes = imageBlob.getBytes("UTF-8");;
                employe.setImageblob(myBytes);
                if (employeDAO.modifier(employe)) {
                    httpSession.setAttribute("modification", true);
                } else {
                    httpSession.setAttribute("modification", false);
                }
            } else {
                Employe employe = new Employe();
                employe.setAutreContact(autre);
                employe.setBureau(bureau);
                employe.setNoms(noms);
                employe.setPrenoms(prenoms);
                employe.setPoste(poste);
                employe.setTelEntreprise(tel);
                byte[] myBytes = imageBlob.getBytes("UTF-8");;
                employe.setImageblob(myBytes);
                if (employeDAO.ajouter(employe)) {
                    httpSession.setAttribute("creation", true);
                } else {
                    httpSession.setAttribute("creation", false);
                }
            }
            response.sendRedirect("start?success=true");
        }

    }

}
