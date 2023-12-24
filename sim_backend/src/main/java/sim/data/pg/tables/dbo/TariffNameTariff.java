package sim.data.pg.tables.dbo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(
        schema = "dbo",
        name = "tariffnametariff"
)
@Data
public class TariffNameTariff implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "tariffnameid")
    private Long tariffnameid;

    @Column(name = "tariffplanname")
    private String tariffplanname;

    @Column(name = "tariffnametariffid")
    private Integer tariffnametariffid;

    @Column(name = "tariffid")
    private Integer tariffid;

    @OneToOne
    @JoinColumn(name = "tariffnameid", referencedColumnName = "tariffnameid", insertable = false, updatable = false)
    private TariffName tariffName;

    @OneToOne
    @JoinColumn(name = "tariffid", referencedColumnName = "tariffid", insertable = false, updatable = false)
    private Tariff tariff;


}
