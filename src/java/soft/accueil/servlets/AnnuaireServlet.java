package soft.accueil.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import soft.accueil.dao.EmployeDAO;

@WebServlet(name="AnnuaireServlet", urlPatterns={"/AnnuaireServlet"})
public class AnnuaireServlet extends HttpServlet {
    
    EmployeDAO employeDAO = new EmployeDAO();
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        request.setAttribute("employes", employeDAO.getall());
        this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/annuaire.jsp").forward(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
    }

    
}
