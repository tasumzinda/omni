/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stewardbank.omnichannel.client.portal.web.controller;

import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.stewardbank.omnichannel.client.portal.util.AppMessage;
import com.stewardbank.omnichannel.client.portal.util.MessageType;

/**
 *
 * @author Tasunungurwa Muzinda
 */
@Controller
public class IndexController extends BaseController{
    
    
    @RequestMapping(value = "/login")
    public String getLogin(ModelMap model) {
        model.addAttribute("pageTitle", appPrefix+"Login");
        return "login";
    }

    @RequestMapping(value = "/loginfailed")
    public String getloginFailed() {
        return "redirect:loginerror";
    }
    
    @RequestMapping(value = "/loginerror", method = RequestMethod.GET)
    public String getRedirectError(ModelMap model) {
        model.addAttribute("pageTitle", appPrefix+"Access Denied");
        model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Error wrong username/ password").messageType(MessageType.ERROR).build());
        return "login";
    }
    
    @RequestMapping(value = "/session-exists", method = RequestMethod.GET)
    public String getLoginExists(ModelMap map){
        map.addAttribute("pageTitle", appPrefix + "Access Denied");
        map.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Error: You can not login multiple times").messageType(MessageType.ERROR).build());
        return "login";
    }

    @RequestMapping(value = "/success")
    public String getSuccess(ModelMap model) {
        model.addAttribute("message", "Access accepted");
        return "index";
    }

    @RequestMapping(value = "/logout")
    public String logout(ModelMap model, HttpSession httpSession) {
        model.addAttribute("message", "Access Failed");
        httpSession.invalidate();
        return "redirect:login";

    }
    
    @RequestMapping(value = "/acceessdenied")
    public String accessDenied(ModelMap model, HttpSession httpSession) {
        model.addAttribute("pageTitle", appPrefix+"Access Denied");
        return "accessdenied";
    }
}
