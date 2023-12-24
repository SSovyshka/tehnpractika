/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sim.data.domain.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author jeyson
 */
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
    public class Child {

@Getter @Setter private String label;
@Getter @Setter private String icon;
@Getter @Setter private String routerLink;
@Getter @Setter private ArrayList<Child> items;
//@Getter @Setter private Boolean separator;

        public Child(String label, String icon, String routerLink) {
            this.label = label;
            this.icon = icon;
            this.routerLink = routerLink;
        }

        public Child(String label, String icon, ArrayList<Child> items) {
            this.label = label;
            this.icon = icon;
            this.items = items;
        }

     

    }