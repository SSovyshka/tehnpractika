/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sim.data.domain.utils;

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
public class DropDownList {
    @Getter @Setter private String label;
    @Getter @Setter private String value;
}