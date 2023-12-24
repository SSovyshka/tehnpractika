package sim.data.pg.tables.dbo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(
        schema = "dbo",
        catalog = "tariff"
)
@Data
public class Tariff implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tariffid;

    private Integer price;

    private Integer simlimit;

    private Integer simlimitcalc;

    private Integer period;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "dd-MM-yyyy",timezone = "Europe/Kiev")
    private Date periodbegin;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "dd-MM-yyyy",timezone = "Europe/Kiev")
    private Date periodend;
}
