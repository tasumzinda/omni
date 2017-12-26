/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.stewardbank.omnichannel.business.service;

import com.stewardbank.omnichannel.business.domain.Customer;
import com.stewardbank.omnichannel.business.domain.User;

/**
 *
 * @author Tasu Muzinda
 * 
 */
public interface CustomerService extends GenericService<Customer>{
    public Customer getByIdNumber(String idNumber);
    
    public Customer getByCardNumber(Long cardNumber);
    
    public Boolean exists(Customer customer);
    
    public int getCount(User user);
}
