/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sim.config;

import java.util.UUID;
import org.springframework.util.Assert;

/**
 *
 * @author dit51
 */

public class UserContextHolder {
    
private static ThreadLocal<CurrentUser> userHolder = 
            new ThreadLocal<CurrentUser>();
	
   public static void setUser(CurrentUser currentUser) {
      Assert.notNull(currentUser, "currentUser cannot be null");
      userHolder.set(currentUser);
   }

   public static CurrentUser getUser() {
      return (CurrentUser) userHolder.get();
   }
   
   public static String getUserName(){
       return getUser().getUname();
   }

   public static String getIpaddr(){
       return getUser().getIpaddr();
   }

   public static String getApiLog(){
       return getUser().getApiLog();
   }

   public static String getJsonLog(){
       return getUser().getJsonLog();
   }

   public static String getMethodLog(){
       return getUser().getMethodLog();
   }

   public static String getUFio(){
       return getUser().getUfio();
   }
   
   public static String getUserfullname(){
       return getUser().getUserfullname();
   }
   
   public static String getUserid(){
       return getUser().getUserid();
   }
   
   public static Integer getPernr(){
       return getUser().getPernr();
   }
   
   public static UUID getLdapid(){
       return getUser().getLdapid();
   }
}
