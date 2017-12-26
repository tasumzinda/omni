/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stewardbank.omnichannel.client.portal.web.controller;

import com.stewardbank.omnichannel.business.domain.User;
import com.stewardbank.omnichannel.business.service.UserService;
import com.stewardbank.omnichannel.client.portal.util.AppMessage;
import com.stewardbank.omnichannel.client.portal.util.MessageType;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 *
 * @author Tasunungurwa Muzinda
 */
abstract public class BaseController {

    @Resource
    private UserService userService;

    public String appPrefix = "Steward Bank::";

    @ModelAttribute("currentuser")
    public User getUserName() {
        return userService.getCurrentUser();
    }
    public AppMessage getMessage(Integer type) {
        switch (type) {
            case 1:
                return new AppMessage.MessageBuilder(Boolean.TRUE).message("Record saved").messageType(MessageType.MESSAGE).build();
            case 2:
                return new AppMessage.MessageBuilder(Boolean.TRUE).message("Record deleted").messageType(MessageType.MESSAGE).build();
            case 3:
                return new AppMessage.MessageBuilder(Boolean.TRUE).message("Operation cancelled").messageType(MessageType.MESSAGE).build();
            default:
                throw new IllegalArgumentException("Parameter provided not recognised :" + type);
        }
    }
    
    /*public void forceDownLoad(Workbook workbook, String name, HttpServletResponse response) {
        try {
            //Write the workbook in file system
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "filename=" + name + ".xls");
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            System.err.println("Error occured writing file");
        }
    }*/
}
