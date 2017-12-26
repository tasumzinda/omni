/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.stewardbank.omnichannel.business.util;

/**
 *
 * @author Tasu Muzinda
 */
public enum HttpStatus {

    OK(1), CONFLICT(2), ERROR(3);
    
    private final Integer status;

    private HttpStatus(Integer status) {
        this.status = status;
    }
    
    public Integer getStatus(){
        return status;
    }
}
