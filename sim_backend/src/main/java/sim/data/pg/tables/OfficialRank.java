package sim.data.pg.tables;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "officialrank", schema = "dictionary")
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries({
        @NamedQuery(name = "OfficialRank.findAll", query = "SELECT t FROM OfficialRank t")
})
@Data
public class OfficialRank implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Schema(description = "Rank ID", required = false, example = "")
    private Integer officialrankid;

    @Schema(description = "Rank name", example = "So seriously rank", required = true)
    private String name;
}
