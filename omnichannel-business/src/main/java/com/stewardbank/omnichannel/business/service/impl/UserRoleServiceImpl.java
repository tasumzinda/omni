/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.stewardbank.omnichannel.business.service.impl;

import com.stewardbank.omnichannel.business.domain.UserRole;
import com.stewardbank.omnichannel.business.repo.UserRoleRepo;
import com.stewardbank.omnichannel.business.service.UserRoleService;
import com.stewardbank.omnichannel.business.util.UUIDGen;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Tasu Muzinda
 */
@Repository
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class UserRoleServiceImpl implements UserRoleService{

    @Resource
    private UserRoleRepo userRoleRepo;
    
    @Override
    public UserRole getByName(String name){
        return userRoleRepo.findByName(name);
    }
    
    @Override
    public UserRole get(String id){
        return userRoleRepo.findById(id);
    }
    
    @Override
    public List<UserRole> getAll(){
        return userRoleRepo.findAll();
    }
    
    @Override
    public UserRole save(UserRole t){
        t.setId(UUIDGen.createUUID());
        return userRoleRepo.save(t);
    }
    
    @Override
    public Boolean checkDuplicate(UserRole current, UserRole old){
         if (current.getId() != null) {
            /**
             * @param current is in existence
             */
            if (!current.getName().equalsIgnoreCase(old.getName())) {
                if (getByName(current.getName()) != null) {
                    return true;
                }
            }

        } else if (current.getId() == null) {
            /**
             * @param current is new
             */
            if (getByName(current.getName()) != null) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void delete(UserRole t) {
        if (t.getId() == null) {
            throw new IllegalStateException("Item to be deleted is in an inconsistent state");
        }
        userRoleRepo.save(t);
    }
}
