/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sim.data.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author dit51
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class InvoiceSearchModel {
    private String phonenumber;
    private Integer period;
}
