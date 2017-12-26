/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.stewardbank.omnichannel.business.repo;

import com.stewardbank.omnichannel.business.domain.Customer;
import com.stewardbank.omnichannel.business.domain.User;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Tasu Muzinda
 */
public interface CustomerRepo extends CrudRepository<Customer, Long>{

    public Customer findByIdNumber(String idNumber);
    
    public Customer findByCardNumber(Long cardNumber);
    
    public Customer findById(String id);
    
    @Override
    public List<Customer> findAll();
    
    public List<Customer> findByCreatedBy(User createdBy);
}
