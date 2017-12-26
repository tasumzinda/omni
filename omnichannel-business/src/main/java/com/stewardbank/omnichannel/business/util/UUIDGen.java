/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.stewardbank.omnichannel.business.util;

import java.util.UUID;

/**
 *
 * @author Tasu Muzinda
 */
public class UUIDGen {

    private UUIDGen(){
        throw new IllegalStateException("Class can not be instantiated");
    }
    
    public static String createUUID(){
        return UUID.randomUUID().toString();
    }
}
