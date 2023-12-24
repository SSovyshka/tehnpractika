/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sim.data.domain.utils;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Jason
 */
@NoArgsConstructor
@AllArgsConstructor
public class FnUUIDReturnOnly {
    @Getter
    @Setter
    private UUID result;
}
