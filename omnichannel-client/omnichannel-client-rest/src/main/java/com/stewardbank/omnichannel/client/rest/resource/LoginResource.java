/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.stewardbank.omnichannel.client.rest.resource;

import com.stewardbank.omnichannel.business.domain.User;
import com.stewardbank.omnichannel.business.service.UserService;
import com.stewardbank.omnichannel.client.rest.dto.UserDTO;
import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author Tasu Muzinda
 */
@Component
@Path("/mobile/login")
@Produces(MediaType.APPLICATION_JSON)
public class LoginResource {

    Logger logger = LoggerFactory.getLogger(LoginResource.class);
   @Resource
   private UserService userService;
   
   @GET
   @Path("/get-user")
   public UserDTO getUser(@QueryParam("userName") String userName){
       logger.debug("Test", userName);
       User user = userService.findByUserName(userName);
       return new UserDTO(user.getFirstName(), user.getLastName(), user.getUserName(), user.getId());
   }
}
