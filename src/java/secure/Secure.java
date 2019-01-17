/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package secure;

import entity.Customer;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import session.CustomerFacade;
import session.RoleFacade;
import session.UserRolesFacade;
import util.EncriptPass;
import util.PageReturner;

/**
 *
 * @author Anastasia
 */
@WebServlet(loadOnStartup = 1,name = "Secure", urlPatterns = {
   
    "/login",
    "/logout",
    "/showLogin",
    "/editUserRoles",
    "/addUserRole",
    "/changeUserRole"})
public class secure extends HttpServlet {
    
    @EJB
    RoleFacade roleFacade;
    @EJB
    CustomerFacade customerFacade;
    @EJB
    UserRolesFacade userRolesFacade;

    @Override
    public void init(ServletConfig config) throws ServletException {
       List<Customer> listCustomer = null;
           listCustomer = customerFacade.findAll(); 
        if (listCustomer.isEmpty()) {
            EncriptPass ep = new EncriptPass();
            String salts = ep.createSalts();
            String encriptPass = ep.setEncriptPass("admin", salts);
            Customer customer = new Customer("Anastassia", "Mitrjagina", 0, "admin", encriptPass, salts);
            customerFacade.create(customer);
            Role role = new Role();
            role.setName("ADMIN");
            roleFacade.create(role);
            UserRoles ur = new UserRoles();
            ur.setCustomer(customer);
            ur.setRole(role);
            userRolesFacade.create(ur);
            role.setName("USER");
            roleFacade.create(role);
            ur.setCustomer(customer);
            ur.setRole(role);
            userRolesFacade.create(ur);
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF8");
        String path = request.getServletPath();
        HttpSession session = request.getSession(false);
        Customer regUser = null;
        if (session != null) {
            try {
                regUser = (Customer) session.getAttribute("regUser");
            } catch (Exception e) {
                regUser = null;
            }
        }
        SecureLogic sl = new SecureLogic();

        if (null != path) {
            switch (path) {
                case "/showLogin":
                    request.getRequestDispatcher(PageReturner.getPage("showLogin")).forward(request, response);
                    break;
                case "/login":
                    String login = request.getParameter("login");
                    String password = request.getParameter("password");//по логину найти user
                    request.setAttribute("info", "Нет такого пользователя");
                    regUser = customerFacade.findByLogin(login);
                    if (regUser == null) {

                        request.getRequestDispatcher(PageReturner.getPage("showLogin")).forward(request, response);
                        break;
                    }
                    EncriptPass ep = new EncriptPass();
//                    EncriptPass ep = new EncriptPass();
                    String salts = regUser.getSalts();
                    String encriptPass = ep.setEncriptPass(password, salts);

                    if (encriptPass.equals(regUser.getPassword())) {
                        session = request.getSession(true);//создаем сессию
                        session.setAttribute("regUser", regUser);
                        request.setAttribute("info", "Приветствую Вас ! \n" + regUser.getLogin() + " \n Вы успешно вошли в систему!");
                        request.getRequestDispatcher(PageReturner.getPage("welcome")).forward(request, response);
                        break;
                    }

                    request.getRequestDispatcher(PageReturner.getPage("showLogin")).forward(request, response);
                    break;

                case "/addUserRole":
                    String roleId = request.getParameter("role");
                    String userId = request.getParameter("user");
                    Role role = roleFacade.find(new Long(roleId));
                    Customer user = customerFacade.find(new Long(userId));
                    UserRoles ur = new UserRoles(user, role);
                    sl.addRoleToUser(ur);
                    Map<Customer, String> mapUsers = new HashMap<>();//мар состоит из множества у которых есть уникальные имена Entry 
                    List<Customer> listUsers = customerFacade.findAll();
                    int n = listUsers.size();
                    for (int i = 0; i < n; i++) {
                        mapUsers.put(listUsers.get(i), sl.getRole(listUsers.get(i)));//из листа ридера и передает гетридера
                    }
                    request.setAttribute("mapUsers", mapUsers);
                    List<Role> listRoles = roleFacade.findAll();
                    request.setAttribute("listRoles", listRoles);
                    request.getRequestDispatcher(PageReturner.getPage("editUserRoles")).forward(request, response);
                    break;
                case "/logout":
                    if (session != null) {
                        session.invalidate();
                        request.setAttribute("info", "До свидания! \n" + regUser.getLogin() + " \n Вы успешно вышли из системы систему!");
                    }
                    request.getRequestDispatcher(PageReturner.getPage("showLogin")).forward(request, response);
                    break;
                case "/editUserRoles":
                    if (!"ADMIN".equals(sl.getRole(regUser))) {
                        request.getRequestDispatcher(PageReturner.getPage("showLogin")).forward(request, response);
                        break;
                    }

                    mapUsers = new HashMap<>();//мар состоит из множества у которых есть уникальные имена Entry 
                    listUsers = customerFacade.findAll();
                    n = listUsers.size();
                    for (int i = 0; i < n; i++) {
                        mapUsers.put(listUsers.get(i), sl.getRole(listUsers.get(i)));//из листа ридера и передает гетридера
                    }
                    request.setAttribute("mapUsers", mapUsers);
                    listRoles = roleFacade.findAll();
                    request.setAttribute("listRoles", listRoles);
                    request.getRequestDispatcher(PageReturner.getPage("editUserRoles")).forward(request, response);
                    break;
                case "/changeUserRole":
                    if (!"ADMIN".equals(sl.getRole(regUser))) {
                        request.getRequestDispatcher(PageReturner.getPage("showLogin")).forward(request, response);
                        break;
                    }
                    String setButton = request.getParameter("setButton");
                    String deleteButton = request.getParameter("deleteButton");
                    userId = request.getParameter("user");
                    roleId = request.getParameter("role");
                    Customer customer = customerFacade.find(new Long(userId));
                    Role roleToUser = roleFacade.find(new Long(roleId));
                    ur = new UserRoles(customer, roleToUser);
                    if (setButton != null) {
                        sl.addRoleToUser(ur);
                    }
                    if (setButton != null) {
                        sl.deleteRoleToUser(ur.getCustomer());
                    }
                    mapUsers = new HashMap<>();
                    listUsers = customerFacade.findAll();
                    n = listUsers.size();
                    for (int i = 0; i < n; i++) {
                        mapUsers.put(listUsers.get(i), sl.getRole(listUsers.get(i)));
                    }
                    request.setAttribute("mapUsers", mapUsers);
                    List<Role> newlistRoles = roleFacade.findAll();
                    request.setAttribute("listRoles", newlistRoles);
                    request.getRequestDispatcher(PageReturner.getPage("editUserRoles")).forward(request, response);
                    break;
                default:
                    request.getRequestDispatcher(PageReturner.getPage("welcome")).forward(request, response);
                    break;
            }
        }
        

}
    @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
        public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}