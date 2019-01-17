/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import entity.Product;
import entity.Customer;
import entity.Purchase;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import session.ProductFacade;
import session.CustomerFacade;
import session.PurchaseFacade;
import util.EncriptPass;
import util.PageReturner;

/**
 *
 * @author pupil
 */
@WebServlet(name = "Shop", urlPatterns = {
    "/newProduct",
    "/addProduct",
    "/newCustomer",
    "/addCustomer",
    "/showProduct",
    "/showCustomer",
    "/listAll",
    "/buyProduct",
    "/showBuyProduct",
    "/listBuyProduct",
    "/deleteProduct",

    
})

public class Shop extends HttpServlet {

    @EJB
    ProductFacade productFacade;
    @EJB
    CustomerFacade customerFacade;
    @EJB
    PurchaseFacade purchaseFacade;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF8");
        Product product=null;
        String path = request.getServletPath();
       if(null != path)switch (path) {
                case "/newProduct":
                    request.getRequestDispatcher(PageReturner.getPage("newProduct")).forward(request, response);
                    break;
                case "/addProduct":
                    String  name = request.getParameter("name");
                    String price = request.getParameter("price");
                    String count = request.getParameter("count");
                     product = new Product( name, new Integer(price), new Integer(count));
                    productFacade.create(product);
                    request.setAttribute("product", product);
                    request.getRequestDispatcher(PageReturner.getPage("welcome")).forward(request, response);
                    break;
                case "/showProduct": {
                    List<Product> listProducts = productFacade.findExistingProduct();
                    request.setAttribute("listProducts", listProducts);
                    request.getRequestDispatcher(PageReturner.getPage("listProduct")).forward(request, response);
                    break;
                }

                case "/newCustomer": {
                    request.getRequestDispatcher(PageReturner.getPage("newCustomer")).forward(request, response);
                    break;
                }
                case "/addCustomer": {
                    name = request.getParameter("name");
                    String surname = request.getParameter("surname");
                    String money = request.getParameter("money");
                    String login = request.getParameter("login");
                    String password1 = request.getParameter("password1");
                    String password2 = request.getParameter("password2");
                    if (!password1.equals(password2)) {
                        request.setAttribute("info", "Неправильный логин или пароль");
                        request.getRequestDispatcher(PageReturner.getPage("welcome")).forward(request, response);
                        break;
                    }
                    EncriptPass ep = new EncriptPass();
                    String salts = ep.createSalts();
                    String encriptPass = ep.setEncriptPass(password1, salts);
                    Customer customer = new Customer(name, surname,new Integer(money), login, encriptPass, salts);

                    customerFacade.create(customer);
                    request.setAttribute("customer", customer);
                   // request.setAttribute("product", new Product());
                    request.getRequestDispatcher(PageReturner.getPage("welcome")).forward(request, response);
                    break;
                }
                case "/listAll":{
                   request.setAttribute("listProducts", productFacade.findAll());//findActived(true));
                   request.setAttribute("listCustomer", customerFacade.findAll());
                   request.getRequestDispatcher(PageReturner.getPage("buyProduct")).forward(request, response);
                   break;
               }
                case "/showCustomer": {
                    List<Customer> listCustomers = customerFacade.findAll();
                    request.setAttribute("listCustomers", listCustomers);
                    request.getRequestDispatcher(PageReturner.getPage("listCustomer")).forward(request, response);
                    break;
                }

                    case "/purchase":{
                   request.setAttribute("listProducts", productFacade.findAll());
                   request.setAttribute("listCustomers", customerFacade.findAll());
                   request.getRequestDispatcher(PageReturner.getPage("listBuyProduct")).forward(request, response);
                   break;
               }
                case "/showBuyProduct": {
                    List<Purchase> buyProducts = purchaseFacade.findAll();
                    request.setAttribute("buyProducts", buyProducts);
                    request.getRequestDispatcher(PageReturner.getPage("listBuyProduct")).forward(request, response);
                    break;
                }
                case "/buyProduct":{
                   String selectedProduct = request.getParameter("selectedProduct");
                   String selectedCustomer = request.getParameter("selectedCustomer");
                   String quantity = request.getParameter("quantity");
                   product = productFacade.find(new Long(selectedProduct));
                   Customer customer = customerFacade.find(new Long(selectedCustomer));
                   
                   Calendar c = new GregorianCalendar();
                           
//                  Если      количество продукта  минус quantity больше 0  то проверяем кол-во денег у пользователя 
//                  Если кол-во денег у пользователя больше чем кол-во пользователя минус quantity * продукт price больше или равно 0 то попроизводим прокупку и списание 
                    
                    if ((customer.getMoney() - (new Integer(quantity) * product.getPrice())) >= 0) {
                        if(product.getCount()-new Integer(quantity)>=0){
                            product.setCount(product.getCount() - new Integer(quantity));
                            customer.setMoney(customer.getMoney() - new Integer(quantity)*product.getPrice());
                            Purchase purchase = new Purchase (product, customer, c.getTime(), new Integer(quantity));
                            purchaseFacade.create(purchase);
                             List<Purchase> buyProducts = purchaseFacade.findAll();
                             request.setAttribute("buyProducts", buyProducts);
                             request.getRequestDispatcher("/listBuyProduct.jsp").forward(request, response);
                             break;
                        }else {
                            List<Purchase> buyProducts = purchaseFacade.findAll();
                            request.setAttribute("buyProducts", buyProducts);
                            request.setAttribute("info", "Не хватает товара!");
                            request.getRequestDispatcher("/listBuyProduct.jsp").forward(request, response); 
                            break;
                        }
                    } else {
                        List<Purchase> buyProducts = purchaseFacade.findAll();
                      request.setAttribute("buyProducts", buyProducts);
                      request.setAttribute("info", "Не хватает денег!");
                      request.getRequestDispatcher("/listBuyProduct.jsp").forward(request, response); 
                      break;
                    }      
                }
                

                default:
                    request.getRequestDispatcher(PageReturner.getPage("welcome")).forward(request, response);
                    break;
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
