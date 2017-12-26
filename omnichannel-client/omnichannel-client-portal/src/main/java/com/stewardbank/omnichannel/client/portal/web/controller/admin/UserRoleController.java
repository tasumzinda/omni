/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.stewardbank.omnichannel.client.portal.web.controller.admin;

import com.stewardbank.omnichannel.client.portal.util.AppMessage;
import com.stewardbank.omnichannel.client.portal.util.MessageType;
import com.stewardbank.omnichannel.client.portal.web.controller.BaseController;
import com.stewardbank.omnichannel.client.portal.web.validator.UserRoleValidator;
import com.stewardbank.omnichannel.business.domain.UserRole;
import com.stewardbank.omnichannel.business.service.UserRoleService;
import com.stewardbank.omnichannel.business.util.dto.ItemDeleteDTO;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Tasu Muzinda
 */
@Controller
@RequestMapping("/admin/user-role")
public class UserRoleController extends BaseController{

    @Resource
    private UserRoleService userRoleService;
    @Resource
    private UserRoleValidator validator;

    private String setUpModel(ModelMap model, UserRole item) {
        model.addAttribute("pageTitle", appPrefix + "Create/ Edit User Role");
        model.addAttribute("item", item);
        model.addAttribute("itemDelete", "index?type=3");
        return "admin/userRoleForm";
    }

    @RequestMapping(value = "/item.form", method = RequestMethod.GET)
    public String userRoleForm(ModelMap model, @RequestParam(required = false) String id) {
        model.addAttribute("message", new AppMessage.MessageBuilder().build());
        UserRole p = new UserRole();
        if (id != null) {
            p = userRoleService.get(id);
        }
        return setUpModel(model, p);
    }

    @RequestMapping(value = "/item.form", method = RequestMethod.POST)
    public String saveUserRole(@ModelAttribute("item") @Valid UserRole incentive, BindingResult result, ModelMap model) {
        validator.validate(incentive, result);
        model.addAttribute("message", new AppMessage.MessageBuilder().build());
        if (result.hasErrors()) {
            model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Data entry error has occurred").messageType(MessageType.ERROR).build());
            return setUpModel(model, incentive);
        }
        userRoleService.save(incentive);
        return "redirect:item.list?type=1";
    }

    @RequestMapping(value = {"/item.list", "/"}, method = RequestMethod.GET)
    public String userRoleList(ModelMap model, @RequestParam(required = false) Integer type) {
        model.addAttribute("message", new AppMessage.MessageBuilder().build());
        model.addAttribute("pageTitle", appPrefix + "User Role List");
        model.addAttribute("items", userRoleService.getAll());
        if (type != null) {
            model.addAttribute("message", getMessage(type));
        }
        return "admin/userRoleList";
    }
    
    @RequestMapping(value = "item.delete", method = RequestMethod.GET)
    public String getUserDeleteForm(@RequestParam("id") String id, ModelMap model) {
        UserRole userRole = userRoleService.get(id);
        ItemDeleteDTO dto = new ItemDeleteDTO(id, userRole.getName(), "user.list?type=3");
        model.addAttribute("item", dto);
        model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Are you sure you want to delete this record").messageType(MessageType.WARNING).build());
        model.addAttribute("pageTitle", appPrefix + "Delete " + userRole.getName() + " User");
        return "admin/deleteItem";
    }

    @RequestMapping(value = "item.delete", method = RequestMethod.POST)
    public String deleteUser(@Valid ItemDeleteDTO dto) {
        userRoleService.delete(userRoleService.get(dto.getId()));
        return "redirect:user.list?type=2";
    }
}
