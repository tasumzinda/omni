/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.stewardbank.omnichannel.client.portal.web.controller.admin;

import com.stewardbank.omnichannel.client.portal.util.AppMessage;
import com.stewardbank.omnichannel.client.portal.util.MessageType;
import com.stewardbank.omnichannel.client.portal.web.controller.BaseController;
import com.stewardbank.omnichannel.client.portal.web.validator.UserValidator;
import com.stewardbank.omnichannel.business.domain.User;
import com.stewardbank.omnichannel.business.service.UserRoleService;
import com.stewardbank.omnichannel.business.service.UserService;
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
@RequestMapping("/admin/user")
public class UserController extends BaseController{

    @Resource
    private UserService userService;
    @Resource
    private UserValidator userValidator;
    @Resource
    private UserRoleService userRoleService;

    private String setUpModel(ModelMap model, User item) {
        model.addAttribute("pageTitle", appPrefix + "Create/ Edit User");
        model.addAttribute("roles", userRoleService.getAll());
        model.addAttribute("item", item);
        model.addAttribute("itemDelete", "user.list?type=3");
        return "admin/userForm";
    }

    @RequestMapping(value = "/user.form", method = RequestMethod.GET)
    public String userForm(ModelMap model, @RequestParam(required = false) String id) {
        model.addAttribute("message", new AppMessage.MessageBuilder().build());
        User p = new User();
        if (id != null) {
            p = userService.get(id);
        }
        return setUpModel(model, p);
    }

    @RequestMapping(value = "/user.form", method = RequestMethod.POST)
    public String saveUser(@ModelAttribute("item") @Valid User user, BindingResult result, ModelMap model) {
        userValidator.validate(user, result);
        model.addAttribute("message", new AppMessage.MessageBuilder().build());
        if (result.hasErrors()) {
            model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Data entry error has occurred").messageType(MessageType.ERROR).build());
            return setUpModel(model, user);
        }
        userService.save(user);
        return "redirect:user.list?type=1";
    }

    @RequestMapping(value = "/reload-form", method = RequestMethod.POST)
    public String reloadForm(ModelMap model, @ModelAttribute("item") @Valid User user) {
        return setUpModel(model, user);
    }

    @RequestMapping(value = {"/user.list", "/"}, method = RequestMethod.GET)
    public String userList(ModelMap model, @RequestParam(required = false) Integer type) {
        model.addAttribute("message", new AppMessage.MessageBuilder().build());
        model.addAttribute("pageTitle", appPrefix + "User List");
        model.addAttribute("items", userService.getAll());
        if (type != null) {
            model.addAttribute("message", getMessage(type));
        }
        return "admin/userList";
    }

    @RequestMapping(value = "user.delete", method = RequestMethod.GET)
    public String getUserDeleteForm(@RequestParam("id") String id, ModelMap model) {
        User user = userService.get(id);
        ItemDeleteDTO dto = new ItemDeleteDTO(id, user.getUserName(), "user.list?type=3");
        model.addAttribute("item", dto);
        model.addAttribute("message", new AppMessage.MessageBuilder(Boolean.TRUE).message("Are you sure you want to delete this record").messageType(MessageType.WARNING).build());
        model.addAttribute("pageTitle", appPrefix + "Delete " + user.getUserName() + " User");
        return "admin/deleteItem";
    }

    @RequestMapping(value = "user.delete", method = RequestMethod.POST)
    public String deleteUser(@Valid ItemDeleteDTO dto) {
        userService.delete(userService.get(dto.getId()));
        return "redirect:user.list?type=2";
    }

}
