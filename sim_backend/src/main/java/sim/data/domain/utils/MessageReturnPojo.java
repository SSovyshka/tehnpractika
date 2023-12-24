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
 * @author dit51
 */
@NoArgsConstructor
@AllArgsConstructor
public class MessageReturnPojo{

    @Getter @Setter private Integer id;
    @Getter @Setter private String message;
    
}
