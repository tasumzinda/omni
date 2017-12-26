/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.stewardbank.omnichannel.business.util.dto;

/**
 *
 * @author Tasu Muzinda
 */
public class ItemDeleteDTO extends NameIdDTO{

    private String cancelRedirect;
    
    public ItemDeleteDTO(){
        
    }

    public ItemDeleteDTO(String id, String name,String cancelRedirect) {
        super(name, id);
        this.cancelRedirect = cancelRedirect;
    }

    public String getCancelRedirect() {
        return cancelRedirect;
    }

    public void setCancelRedirect(String cancelRedirect) {
        this.cancelRedirect = cancelRedirect;
    }
}
