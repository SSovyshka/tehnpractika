/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sim.utils;

import javax.persistence.Parameter;

/**
 *
 * @author dit51
 */
public class ParameterImp implements Parameter{
    
    private String name;
    private Integer position;
    private Class parameterType;

    public ParameterImp() {
    }

    
    public ParameterImp(String name, Integer position, Class parameterType) {
        this.name = name;
        this.position = position;
        this.parameterType = parameterType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public void setParameterType(Class parameterType) {
        this.parameterType = parameterType;
    }

    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public Integer getPosition() {
        return position;
    }

    @Override
    public Class getParameterType() {
        return parameterType;
    }
    
}
