/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.stewardbank.omnichannel.client.portal.web.validator;

import com.stewardbank.omnichannel.business.domain.UserRole;
import com.stewardbank.omnichannel.business.service.UserRoleService;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author Tasu Muzinda
 */
@Component
public class UserRoleValidator implements Validator{

    @Resource
    private UserRoleService userRoleService;

    @Override
    public boolean supports(Class<?> type) {
        return UserRole.class.equals(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserRole current = (UserRole) o;
        UserRole old = null;
        ValidationUtils.rejectIfEmpty(errors, "name", "field.empty");
        if(current.getId() != null){
            old = userRoleService.get(current.getId());
        }
        if(userRoleService.checkDuplicate(current, old)){
            errors.rejectValue("name", "item.duplicate");
        }
    }
}
