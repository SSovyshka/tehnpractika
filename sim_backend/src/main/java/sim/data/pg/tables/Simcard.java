package sim.data.pg.tables;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;


@Entity
@Table( schema = "dbo", name = "simcard" )
@Data
@NamedQueries({
        @NamedQuery(name = "Simcard.findAll", query = "SELECT t FROM Simcard t"),
        @NamedQuery(name = "Simcard.findById", query = "SELECT s FROM Simcard s WHERE s.simcardid = :simcardId")

})
public class Simcard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer simcardid;

    private String phonenumber;
    private Integer grupaid;
    private Integer filialid;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "dd-MM-yyyy",timezone = "Europe/Kiev")
    private Date periodbegin;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "dd-MM-yyyy",timezone = "Europe/Kiev")
    private Date periodend;

    private Integer officialrankid;
    private Integer tariffnametarifid;
    private Integer addressid;
    private String du;
    private String mp;
    private String note1;

    @ManyToOne
    @JoinColumn(name = "filialid", referencedColumnName = "filialid", insertable = false, updatable = false)
    private Filial filial;

}
