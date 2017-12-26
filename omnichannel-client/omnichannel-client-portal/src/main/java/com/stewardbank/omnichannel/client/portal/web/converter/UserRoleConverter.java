/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.stewardbank.omnichannel.client.portal.web.converter;

import com.stewardbank.omnichannel.business.domain.UserRole;
import com.stewardbank.omnichannel.business.service.UserRoleService;
import javax.annotation.Resource;
import org.springframework.core.convert.converter.Converter;

/**
 *
 * @author Tasu Muzinda
 */
public class UserRoleConverter implements Converter<String, UserRole>{

    @Resource
    private UserRoleService userRoleService;

    @Override
    public UserRole convert(String s) {
        if(s.equals("")) return null;
        return userRoleService.get(s);
    }   
}
