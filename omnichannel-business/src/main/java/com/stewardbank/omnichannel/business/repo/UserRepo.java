/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.stewardbank.omnichannel.business.repo;

import com.stewardbank.omnichannel.business.domain.User;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Tasu Muzinda
 */
public interface UserRepo extends CrudRepository<User, String> {

    public User findByUserName(String userName);
    
    @Override
    public List<User> findAll();
    
    public User findById(String id);
}
