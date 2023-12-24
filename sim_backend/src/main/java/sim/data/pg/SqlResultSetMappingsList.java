/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sim.data.pg;

import sim.data.domain.utils.MessageReturnPojo;
import java.io.Serializable;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author dit51
 */
@Entity
@SqlResultSetMappings({
    
    @SqlResultSetMapping(
            name = "MessageReturnMapping",
            classes = @ConstructorResult(
                    targetClass = MessageReturnPojo.class,
                    columns = {
                        @ColumnResult(name = "id", type = Integer.class),
                        @ColumnResult(name = "message", type = String.class)
                    }
            )
    )
        
})
@NoArgsConstructor
@AllArgsConstructor
public class SqlResultSetMappingsList implements Serializable {

    @Id
    @Getter
    @Setter
    Long id;
}
