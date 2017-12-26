/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.stewardbank.omnichannel.client.rest.resource;

import com.stewardbank.omnichannel.business.domain.Customer;
import com.stewardbank.omnichannel.business.service.CustomerService;
import com.stewardbank.omnichannel.business.util.DateUtil;
import com.stewardbank.omnichannel.business.util.HttpStatus;
import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author Tasu Muzinda
 */
@Component
@Path("/mobile/customer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {

    @Resource
    private CustomerService customerService;
    Logger logger = LoggerFactory.getLogger(CustomerResource.class);
    
    @POST
    @Path("/save")
    public HttpStatus saveCustomer(Customer customer){
        try{
            customer.setDateCreated(DateUtil.getDateFromRest(customer.getDate()));
            if(customerService.exists(customer)){
                return HttpStatus.CONFLICT;
            }
            customerService.save(customer);
        }catch(Exception e){
           logger.info("Excepion occured");
           return HttpStatus.ERROR;
        }
        return HttpStatus.OK;
    }
}
