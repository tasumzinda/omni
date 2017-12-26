/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stewardbank.omnichannel.business.service.impl;

import com.stewardbank.omnichannel.business.domain.Customer;
import com.stewardbank.omnichannel.business.domain.User;
import com.stewardbank.omnichannel.business.repo.CustomerRepo;
import com.stewardbank.omnichannel.business.service.CustomerService;
import com.stewardbank.omnichannel.business.service.UserService;
import com.stewardbank.omnichannel.business.util.UUIDGen;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class CustomerServiceImpl implements CustomerService {

    @Resource
    private CustomerRepo customerRepo;
    @Resource
    private UserService userService;

    @Override
    public Customer get(String id) {
        if (id == null) {
            throw new IllegalStateException("Item does not exist");
        }
        return customerRepo.findById(id);
    }

    @Override
    public Customer getByIdNumber(String idNumber) {
        return customerRepo.findByIdNumber(idNumber);
    }

    @Override
    public Customer getByCardNumber(Long cardNumber) {
        return customerRepo.findByCardNumber(cardNumber);
    }

    @Override
    public Customer save(Customer customer) {
        if (customer.getId() == null) {
            if (customer.getCreatedBy() == null) {
                customer.setCreatedBy(userService.getCurrentUser());
            }
            if (customer.getDateCreated() == null) {
                customer.setDateCreated(new Date());
            }
            if (customer.getTimeCreated() == null) {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                customer.setTimeCreated(sdf.format(System.currentTimeMillis()));
            }
            customer.setId(UUIDGen.createUUID());
            return customerRepo.save(customer);
        }
        customer.setModifiedBy(userService.getCurrentUser());
        customer.setDateModified(new Date());
        return customerRepo.save(customer);
    }

    @Override
    public List<Customer> getAll() {
        return customerRepo.findAll();
    }

    @Override
    public Boolean checkDuplicate(Customer current, Customer old) {
        if (current.getId() != null) {
            /**
             * @param current is in existence
             */
            if (!current.getIdNumber().equalsIgnoreCase(old.getIdNumber())) {
                if (getByIdNumber(current.getIdNumber()) != null) {
                    return true;
                }
            }

        } else if (current.getId() == null) {
            /**
             * @param current is new
             */
            if (getByIdNumber(current.getIdNumber()) != null) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void delete(Customer t) {
        if (t.getId() == null) {
            throw new IllegalStateException("Item to be deleted is in an inconsistent state");
        }
        customerRepo.save(t);
    }

    @Override
    public Boolean exists(Customer customer) {
        return getByIdNumber(customer.getIdNumber()) != null;
    }

    @Override
    public int getCount(User user) {
        return customerRepo.findByCreatedBy(user).size();
    }

    public void sendSMS(Customer customer) {
        try {
            String recipient = "+263" + customer.getPhoneNumber();
            String message = "Account successfully opened. Your account number is " + customer.getCardNumber();
            String username = "admin";
            String password = "abc123";
            String originator = "+440987654321";
            String requestUrl = "http://127.0.0.1:9501/api?action=sendmessage&"
                    + "username=" + URLEncoder.encode(username, "UTF-8")
                    + "&password=" + URLEncoder.encode(password, "UTF-8")
                    + "&recipient=" + URLEncoder.encode(recipient, "UTF-8")
                    + "&messagetype=SMS:TEXT"
                    + "&messagedata=" + URLEncoder.encode(message, "UTF-8")
                    + "&originator=" + URLEncoder.encode(originator, "UTF-8")
                    + "&serviceprovider=GSMModem1"
                    + "&responseformat=html";
            URL url = new URL(requestUrl);
            HttpURLConnection uc = (HttpURLConnection) url.openConnection();
            System.out.println(uc.getResponseMessage());
            uc.disconnect();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}
