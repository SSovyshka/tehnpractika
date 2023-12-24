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
    public class MenuTree {
    @Getter @Setter private String label;
    @Getter @Setter private String icon;
    @Getter @Setter private ArrayList<Child> items = new ArrayList<>();
}
