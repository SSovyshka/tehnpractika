package sim.data.pg.tables;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "filial", schema = "dictionary")
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries({
    @NamedQuery(name = "Filial.findAll", query = "SELECT t FROM Filial t")
})
@Data
public class Filial implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Filial ID", required = false, example = "")
    private Integer filialid;

    @Schema(description = "Filial name", required = true, example = "Smile face")
    private String filial;

}
