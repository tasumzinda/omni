/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.stewardbank.omnichannel.business.util.dto;

import java.io.Serializable;

/**
 *
 * @author Tasu Muzinda
 */
public class NameIdDTO implements Serializable{

    private String name;
    private String id;

    public NameIdDTO() {
    }

    public NameIdDTO(String name, String id) {
        this.name = name;
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
