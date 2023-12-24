package sim.data.pg.tables.dbo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(
        schema = "dbo",
        name = "contract"
)
@NamedQueries({
        @NamedQuery(name = "Contract.findAll", query = "SELECT t FROM Contract t"),
})
@Data
public class Contract implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer contractid;

    private String tabnumber;
    private String firstname;
    private String secondname;
    private Integer officialrankid;
    private String phoneorg;
    private Integer simcardid;
    private Integer tariffnameid;
    private Integer tariffnametariffid;

}
