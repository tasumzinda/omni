/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.stewardbank.omnichannel.business.repo;

import com.stewardbank.omnichannel.business.domain.UserRole;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Tasu Muzinda
 */
public interface UserRoleRepo extends CrudRepository<UserRole, String>{

    @Override
    public List<UserRole> findAll();
    
    public UserRole findByName(String name);
    
    public UserRole findById(String id);
}
