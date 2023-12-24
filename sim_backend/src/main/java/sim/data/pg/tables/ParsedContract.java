package sim.data.pg.tables;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import sim.data.Invoice;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(
        schema = "dbo",
        name = "invoices"
)
public class ParsedContract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer invoiceid;
    private String contractnumber;
    private String phonenumber;
    private String packagename;
    private String invoicename;
    private BigDecimal num1;
    private BigDecimal num2;
    private BigDecimal num3;
    private Integer period;

    @JsonIgnore
    @Transient
    private List<Invoice> invoiceList = new ArrayList<>();
    
    public void addInvoice(Invoice invoice) {
        invoiceList.add(invoice);
    }

    @JsonIgnore
    public boolean isNull() {
        return contractnumber == null && phonenumber == null
                && packagename == null && invoiceList.isEmpty();
    }

    @Override
    public String toString() {
        return "Contract number: " + contractnumber + "\n"
                + "Phone number: " + phonenumber + "\n"
                + "Package name: " + packagename + "\n"
                + "Invoices: " + invoiceList.toString();
    }
}
