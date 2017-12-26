/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.stewardbank.omnichannel.business.service;

import com.stewardbank.omnichannel.business.domain.User;

/**
 *
 * @author Tasu Muzinda
 */
public interface UserService extends GenericService<User>{

    public User findByUserName(String name);

    public String getCurrentUsername();

    public User getCurrentUser();
    
    public User changePassword(User t);
}
