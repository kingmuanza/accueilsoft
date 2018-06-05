package soft.accueil.servlets;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import soft.accueil.dao.EmployeDAO;
import soft.accueil.dao.UtilisateurDAO;
import soft.accueil.dao.UtilisateurProfilDAO;
import soft.accueil.entities.Employe;
import soft.accueil.entities.Utilisateur;
import soft.accueil.entities.UtilisateurProfil;

@WebServlet(name = "FormNouveauUtilisateurServlet", urlPatterns = {"/FormNouveauUtilisateurServlet"})
public class FormNouveauUtilisateurServlet extends HttpServlet {

    UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
    EmployeDAO employeDAO = new EmployeDAO();
    UtilisateurProfilDAO utilisateurProfilDAO = new UtilisateurProfilDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("employes", employeDAO.getall());
        request.setAttribute("utilisateurProfils", utilisateurProfilDAO.getall());
        this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/form/nouvel_utilisateur.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String login = request.getParameter("login");
            String passe = request.getParameter("passe");
            String confirmation = request.getParameter("confirmation");
            String employe = request.getParameter("employe");
            Employe e = null;
            if (employe != null && !employe.isEmpty()) {
                int idemploye = Integer.parseInt(employe);
                e = employeDAO.get(idemploye);
            }
            String profil = request.getParameter("profil");
            UtilisateurProfil up = null;
            if (profil != null && !profil.isEmpty()) {
                int idprofil = Integer.parseInt(profil);
                up = utilisateurProfilDAO.get(idprofil);
            }

            if (passe.equals(confirmation)) {

                Utilisateur u = new Utilisateur();
                u.setLogin(login);
                u.setEmploye(e);
                u.setPasse(hashPasse(passe));
                u.setUtilisateurProfil(up);

                utilisateurDAO.ajouter(u);

                response.sendRedirect("start");

            } else {
                response.sendRedirect("start?passenotsame=true");
            }
        } catch (Exception ex) {
            response.sendRedirect("start?loginused=true");
        }

        
    }
    
    public String hashPasse(String plaintext) {
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(plaintext.getBytes());
            byte[] digest = m.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            String hashtext = bigInt.toString(16);
// Now we need to zero pad it if you actually want the full 32 chars.
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(FormUtilisateurServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return plaintext;
    }


}
