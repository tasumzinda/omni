/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.stewardbank.omnichannel.business.service;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Tasu Muzinda
 * @param <T>
 */
public interface GenericService<T> extends Serializable{

    public List<T> getAll();
    
    public T get(String id);
    
    public T save(T t);
    
    public Boolean checkDuplicate(T current, T old);
    
    public void delete(T t);
}
