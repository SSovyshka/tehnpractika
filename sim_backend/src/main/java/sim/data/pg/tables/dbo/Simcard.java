package sim.data.pg.tables.dbo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sim.data.pg.tables.dictionary.Filial;
import sim.data.pg.tables.dictionary.Grupa;
import sim.data.pg.tables.dictionary.OfficialRank;

import javax.persistence.*;
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
    private Integer tariffnametariffid;
    private Integer addressid;
    private Integer contractid;
    private String du;
    private String mp;
    private String note1;

    @ManyToOne
    @JoinColumn(name = "filialid", referencedColumnName = "filialid", insertable = false, updatable = false)
    private Filial filial;

    @ManyToOne
    @JoinColumn(name = "officialrankid", referencedColumnName = "officialrankid", insertable = false, updatable = false)
    private OfficialRank officialRank;

    @ManyToOne
    @JoinColumn(name = "grupaid", referencedColumnName = "grupaid", insertable = false, updatable = false)
    private Grupa grupa;

    @ManyToOne
    @JoinColumn(name = "tariffnametariffid", referencedColumnName = "tariffnametariffid", insertable = false, updatable = false)
    private TariffNameTariff tariffNameTariff;

    @ManyToOne
    @JoinColumn(name = "contractid", referencedColumnName = "contractid", insertable = false, updatable = false)
    private Contract contract;

}
