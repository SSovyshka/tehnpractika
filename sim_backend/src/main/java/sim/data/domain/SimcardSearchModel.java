package sim.data.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SimcardSearchModel {

    private Integer simcardid;
    private String phonenumber;
    private Integer grupaid;
    private Integer filialid;
    @JsonFormat(pattern = "dd-MM-yyyy",timezone = "Europe/Kiev")
    private Date periodbegin;
    @JsonFormat(pattern = "dd-MM-yyyy",timezone = "Europe/Kiev")
    private Date periodend;
    private Integer officialrankid;
    private Integer tariffnametarifid;
    private Integer addressid;
    private String du;
    private String mp;
    private String note1;
}
