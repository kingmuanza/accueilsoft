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
import javax.servlet.http.HttpSession;
import org.hibernate.HibernateException;
import soft.accueil.dao.EmployeDAO;
import soft.accueil.dao.UtilisateurDAO;
import soft.accueil.dao.UtilisateurProfilDAO;
import soft.accueil.entities.Employe;
import soft.accueil.entities.Utilisateur;
import soft.accueil.entities.UtilisateurProfil;

@WebServlet(name = "FormUtilisateurServlet", urlPatterns = {"/FormUtilisateurServlet"})
public class FormUtilisateurServlet extends HttpServlet {

    UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
    EmployeDAO employeDAO = new EmployeDAO();
    UtilisateurProfilDAO utilisateurProfilDAO = new UtilisateurProfilDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        if (id != null && !id.isEmpty()) {
            int i = Integer.parseInt(id);
            Utilisateur u = utilisateurDAO.get(i);
            request.setAttribute("u", u);
        }
        request.setAttribute("employes", employeDAO.getall());
        request.setAttribute("utilisateurProfils", utilisateurProfilDAO.getall());
        this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/utilisateur.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        Utilisateur utilisateur = null;
        utilisateur = (Utilisateur) httpSession.getAttribute("utilisateur");
        if (utilisateur != null) {

            String action = request.getParameter("action");
            if (action != null && !action.isEmpty() && action.equals("supprimer")) {
                String id = request.getParameter("id");
                if (id != null && !id.isEmpty()) {
                    try {
                        int i = Integer.parseInt(id);
                        Utilisateur u = utilisateurDAO.get(i);
                        utilisateurDAO.supprimer(u);
                        response.sendRedirect("start?success=true");
                    } catch (IOException | NumberFormatException | HibernateException e) {
                        System.out.println(e);
                        response.sendRedirect("start?objectUsed=true");
                    }
                }
            } else {
                try {

                    String login = request.getParameter("login");
                    String ancien = request.getParameter("ancien");
                    String passe = request.getParameter("passe");
                    String confirmation = request.getParameter("confirmation");
                    String employe = request.getParameter("employe");
                    Employe e = null;
                    if (employe != null && !employe.isEmpty()) {
                        int idemploye = Integer.parseInt(employe);
                        e = employeDAO.get(idemploye);
                    } else {
                        if (utilisateur.getEmploye() != null) {
                            e = employeDAO.get(utilisateur.getEmploye().getIdemploye());
                        }
                    }
                    String profil = request.getParameter("profil");
                    UtilisateurProfil up = null;
                    if (profil != null && !profil.isEmpty()) {
                        int idprofil = Integer.parseInt(profil);
                        up = utilisateurProfilDAO.get(idprofil);
                    } else {
                        if (utilisateur.getUtilisateurProfil() != null) {
                            up = utilisateurProfilDAO.get(utilisateur.getUtilisateurProfil().getIdutilisateurProfil());
                        }
                    }

                    if (passe.equals(confirmation)) {
                        String id = request.getParameter("id");
                        Utilisateur u = new Utilisateur();
                        if (id != null && !id.isEmpty()) {
                            int i = Integer.parseInt(id);
                            u = utilisateurDAO.get(i);
                            if (ancien!=null && !ancien.isEmpty() && hashPasse(ancien).equals(u.getPasse())) {
                                u.setLogin(login);
                                u.setEmploye(e);
                                u.setPasse(hashPasse(passe));
                                u.setUtilisateurProfil(up);

                                utilisateurDAO.modifier(u);

                                response.sendRedirect("start?success=true");
                            } 
                            if (ancien==null || ancien.isEmpty()) {
                                u.setLogin(login);
                                u.setEmploye(e);
                                u.setPasse(hashPasse(passe));
                                u.setUtilisateurProfil(up);

                                utilisateurDAO.modifier(u);
                                response.sendRedirect("start?success=true");
                            }

                        }

                    } else {
                        response.sendRedirect("start?passenotsame=true");
                    }
                } catch (Exception ex) {
                    System.out.println(ex);
                    response.sendRedirect("start?loginused=true");
                }
            }

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
