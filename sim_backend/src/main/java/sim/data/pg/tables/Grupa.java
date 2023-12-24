package sim.data.pg.tables;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;


@Entity
@Table(name = "grupa", schema = "dictionary")
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries({
        @NamedQuery(name = "Grupa.findAll", query = "SELECT t FROM Grupa t"),
})
@Data
public class Grupa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Group ID", required = false, example = "")
    private BigInteger grupaid;

    @Schema(description = "Group name", example = "Funny group, yohoo", required = true)
    private String grupa;
}
