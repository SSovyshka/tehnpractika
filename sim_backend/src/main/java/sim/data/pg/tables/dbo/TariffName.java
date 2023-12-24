package sim.data.pg.tables.dbo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

@Entity
@Table(
        schema = "dbo",
        name = "tariffname"
)
@Data
public class TariffName implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tariffnameid;
    private String tariffname;
}
