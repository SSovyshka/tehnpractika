/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sim.data.domain.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
public class FnDateSearchOnly {
    @JsonFormat(pattern="dd-MM-yyyy",timezone = "Europe/Kiev")
    @Temporal(TemporalType.TIMESTAMP)
    @Getter @Setter private Date date_in;
}
