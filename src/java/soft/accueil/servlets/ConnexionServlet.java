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
import soft.accueil.dao.UtilisateurDAO;
import soft.accueil.entities.Utilisateur;

@WebServlet(name = "ConnexionServlet", urlPatterns = {"/ConnexionServlet"})
public class ConnexionServlet extends HttpServlet {

    UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        //System.out.println(httpSession.getAttribute("error"));
        String login = request.getParameter("login");
        String passe = request.getParameter("passe");
        Utilisateur utilisateur = null;
        utilisateur = utilisateurDAO.get(login, hashPasse(passe));
        if (utilisateur != null) {
            httpSession.setAttribute("utilisateur", utilisateur);
            httpSession.setAttribute("error", null);
            httpSession.removeAttribute("error");
            response.sendRedirect("start");
        } else {
            httpSession.setAttribute("error", "Login ou mot de passe incorrect");
            response.sendRedirect("index.htm");
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
