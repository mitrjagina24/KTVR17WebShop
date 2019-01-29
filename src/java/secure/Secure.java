package secure;

import entity.Customer;
import entity.User;
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
import session.UserFacade;
import session.UserRolesFacade;
import util.EncriptPass;
import util.PageReturner;

@WebServlet(loadOnStartup = 1, name = "Secure", urlPatterns = {
    "/showLogin",
    "/login",
    "/logout",
    "/editUserRoles",
    "/addUserRole",
    "/changeUserRole"})
public class Secure extends HttpServlet {

    @EJB
    RoleFacade roleFacade;
    @EJB
    CustomerFacade customerFacade;
    @EJB
    UserRolesFacade userRolesFacade;
    @EJB
    UserFacade userFacade;

    @Override
    public void init(ServletConfig config) throws ServletException {
        List<Customer> listCustomer = customerFacade.findAll();
        if (listCustomer.isEmpty()) {
            EncriptPass ep = new EncriptPass();
            String salts = ep.createSalts();
            String encriptPass = ep.setEncriptPass("admin", salts);
            Customer customer = new Customer("Nikolai", "Nikolaevich", 300);
            customerFacade.create(customer);
            User user = new User("admin", encriptPass, salts, customer);
            userFacade.create(user);
            Role role = new Role();
            role.setName("ADMIN");
            roleFacade.create(role);
            UserRoles ur = new UserRoles();
            ur.setUser(user);
            ur.setRole(role);
            userRolesFacade.create(ur);
            role.setName("USER");
            roleFacade.create(role);
            ur.setUser(user);
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
        User regUser = null;
        if (session != null) {
            try {
                regUser = (User) session.getAttribute("regUser");
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
                    regUser = userFacade.findByLogin(login);
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
                    User user =userFacade.find(new Long(userId));
                    UserRoles ur = new UserRoles(user, role);
                    sl.addRoleToUser(ur);
                    Map<User, String> mapUsers = new HashMap<>();//мар состоит из множества у которых есть уникальные имена Entry 
                    List<User> listUsers = userFacade.findAll();
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
                    listUsers = userFacade.findAll();
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
                     user = userFacade.find(new Long(userId));
                    Role roleToUser = roleFacade.find(new Long(roleId));
                    ur = new UserRoles(user, roleToUser);
                    if (setButton != null) {
                        sl.addRoleToUser(ur);
                    }
                    if (setButton != null) {
                        sl.deleteRoleToUser(ur.getUser());
                    }
                    mapUsers = new HashMap<>();
                    listUsers = userFacade.findAll();
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

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
