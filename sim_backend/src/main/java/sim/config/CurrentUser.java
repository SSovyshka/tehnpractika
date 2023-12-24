/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sim.config;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author dit51
 */
@NoArgsConstructor
@AllArgsConstructor
public class CurrentUser {
    @Getter @Setter private String uname;
    @Getter @Setter private String ufio;
    @Getter @Setter private String ipaddr;
    @Getter @Setter private String jsonLog;
    @Getter @Setter private String methodLog;
    @Getter @Setter private String apiLog;
    @Getter @Setter private String userfullname;
    @Getter @Setter private String userid;
    @Getter @Setter private Integer pernr;
    @Getter @Setter private UUID ldapid; 
}
