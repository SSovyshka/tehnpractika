/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sim.data.pg.tables;

import java.io.Serializable;
import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author dit51
 */
//@Entity
//@Table(name = "zkonsol_dostup", schema = "connection_services")
//@NamedQueries({
//    @NamedQuery(name = "ZKonsolDostup.findAll", query = "SELECT t FROM ZKonsolDostup t")
//})
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter 
public class ZKonsolDostup implements Serializable{

    private static final long serialVersionUID = 1L;
    
////        @Id
//        @GeneratedValue(strategy = GenerationType.IDENTITY)
//        @Basic(optional = false)
        private BigInteger zkonsol_dostupid;
        private Integer bukrs;
        private String butxt;
        private Integer pernr;
        private String ename;
        private Boolean m1;
	private Boolean m2;
	private Boolean m3;
	private Boolean m4;
	private Boolean m5;
	private Boolean m6;
	private Boolean m7;
	private Boolean m8;
	private Boolean m9;
	private Boolean vptu;
	private Boolean vrp;
	private Boolean mall;
	private Boolean m10;
	private Boolean m12;
	private Boolean m14;
	private Boolean m15;
        private String email;
        private String mail_2;
        private String mail_3;
        private String mail_4;
        private String mail_5;
        private Boolean mail_6;
        private String mail_9;
        private Boolean mail_10;
        private String mail_11;
        private Boolean mail_12;
        private Boolean m11_1;
	private Boolean m11_2;
	private Boolean m11_3;
	private Boolean m11_4;


}
