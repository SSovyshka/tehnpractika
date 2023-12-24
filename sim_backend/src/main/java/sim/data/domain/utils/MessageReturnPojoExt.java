/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sim.data.domain.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author dit51
 */
@NoArgsConstructor
public class MessageReturnPojoExt extends MessageReturnPojo{
    @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss",timezone = "Europe/Kiev")
    @Getter @Setter Date executionDate;

    public MessageReturnPojoExt(Integer id, String message) {
        super(id, message);
    }

    public MessageReturnPojoExt(Integer id, String message, Date executionDate) {
        super(id, message);
        this.executionDate = executionDate;
    }
    
    
}
