/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package secure;

import static com.sun.faces.facelets.util.Path.context;
import entity.Customer;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import session.RoleFacade;
import session.UserRolesFacade;
/**
 *
 * @author Anastasia
 */
public class SecureLogic {
      private UserRolesFacade userRolesFacade;
private RoleFacade roleFacade;
    public SecureLogic() {
       Context context;
        try {
           context =  new InitialContext();
           this.userRolesFacade=(UserRolesFacade)context.lookup("java:module/UserRolesFacade");
            this.roleFacade = (RoleFacade) context.lookup("java:module/RoleFacade");
        } catch (NamingException ex) {
            Logger.getLogger(SecureLogic.class.getName()).log(Level.SEVERE, "Не удалось найти Bin", ex);
        }
    }

    public void addRoleToUser(UserRoles ur) {
        if (ur.getRole().getName().equals("ADMIN")) {
            this.deleteRoleToUser(ur.getCustomer());
            userRolesFacade.create(ur);
            Role addNewRole = roleFacade.findRoleByName("USER");
            UserRoles addedNewRoles = new UserRoles(ur.getCustomer(), addNewRole);
            userRolesFacade.create(addedNewRoles);
        } else if  (ur.getRole().getName().equals("USER")){
                
                userRolesFacade.create(ur);
            }
        
    }

    public void deleteRoleToUser(Customer user) {
        List<UserRoles> deleteUserRoles = userRolesFacade.findByUser(user);
        int n = deleteUserRoles.size();
        for (int i = 0; i < n; i++) {
            userRolesFacade.remove(deleteUserRoles.get(i));
        }
    }
    public String getRole(Customer regUser){
        if (regUser==null){
          return null;
        }
        List<UserRoles> listUserRoles = userRolesFacade.findByUser(regUser);
//        List<Role>listRoles = roleFacade.findAll();
//        int n = userRoles.size();
        int n = listUserRoles.size();
        
        for(int i=0;i<n;i++){
            if( "ADMIN".equals(listUserRoles.get(i).getRole().getName())){
                return listUserRoles.get(i).getRole().getName();
            }
        }
       for(int i=0;i<n;i++){
            if("USER".equals(listUserRoles.get(i).getRole().getName())){
                return listUserRoles.get(i).getRole().getName();
            }
        }
 
   return null;
   }
}
