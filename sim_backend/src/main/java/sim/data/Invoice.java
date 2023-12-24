package sim.data;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Invoice {

    private String invoicePoint;
    private BigDecimal num1;
    private BigDecimal num2;
    private BigDecimal num3;

    @Override
    public String toString() {
        return "Invoice{" + "invoicePoint=" + invoicePoint + ", num1=" + num1 + ", num2=" + num2 + ", num3=" + num3 + '}';
    }

}
