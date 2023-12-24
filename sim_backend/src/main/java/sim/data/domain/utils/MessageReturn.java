/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sim.data.domain.utils;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author dit51
 */
@Entity
public class MessageReturn implements Serializable{

    @Id
    private Integer id;
    private String message;

    public MessageReturn(Integer id, String message) {
        this.id = id;
        this.message = message;
    }
    
    public MessageReturn() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    
}
