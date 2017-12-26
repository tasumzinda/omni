/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.stewardbank.omnichannel.client.portal.web.validator;

import com.stewardbank.omnichannel.business.domain.Customer;
import com.stewardbank.omnichannel.business.domain.User;
import com.stewardbank.omnichannel.business.service.CustomerService;
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
public class CustomerValidator implements Validator{

    @Resource
    private CustomerService customerService;

    @Override
    public boolean supports(Class<?> type) {
        return Customer.class.equals(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Customer customer = (Customer) o;
        Customer old = null;
        ValidationUtils.rejectIfEmpty(errors, "firstName", "field.empty");
        ValidationUtils.rejectIfEmpty(errors, "lastName", "field.empty");
        ValidationUtils.rejectIfEmpty(errors, "idNumber", "field.empty");
        ValidationUtils.rejectIfEmpty(errors, "address", "field.empty");
        ValidationUtils.rejectIfEmpty(errors, "cardNumber", "field.empty");
        ValidationUtils.rejectIfEmpty(errors, "phoneNumber", "field.empty");
        if (customer.getId() != null) {
            old = customerService.get(customer.getId());
        }
        if (customerService.checkDuplicate(customer, old)) {
            errors.rejectValue("name", "item.duplicate");
        }
    }
}
