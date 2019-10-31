package com.mwaysolution.sapMock.rest;


import com.mwaysolution.sapMock.model.User;
import com.mwaysolution.sapMock.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.time.ZonedDateTime;
import java.util.List;

@Controller
public class GuiController {

    private ZonedDateTime creationDate;

    @Autowired
    private UserService userService;

    @RequestMapping("/gui/users")
    public String viewHomePage(Model model) {
        List<User> listUsers = userService.findAll();
        model.addAttribute("listUsers", listUsers);

        return "index";
    }

    @RequestMapping("/gui/createUser")
    public String createUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);

        return "createUser";
    }

    @RequestMapping(value = "/gui/save", method = RequestMethod.POST)
    public String saveUser(@ModelAttribute("user") User user) {
        user.setCreationDate(ZonedDateTime.now());
        user.setModificationDate(ZonedDateTime.now());
        if (!user.getSapUsername().equals("") && !user.getExchangeUsername().equals("")
                && !user.getExchangeDomain().equals("") && !user.getTimeZone().equals(""))
            userService.save(user);

        return "redirect:/gui/users";
    }

    @RequestMapping("/gui/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        userService.delete(userService.findById(id));

        return "redirect:/gui/users";
    }

    @RequestMapping("/gui/edit/{id}")
    public ModelAndView updateUserPage(@PathVariable("id") Integer id) {
        ModelAndView model = new ModelAndView("updateUser");

        User user = userService.findById(id);
        creationDate = user.getCreationDate();
        model.addObject("user", user);

        return model;
    }

    @RequestMapping(value = "/gui/update/{id}", method = RequestMethod.POST)
    public String updateUser(@PathVariable("id") Integer id, @ModelAttribute("user") User user) {
        user.setCreationDate(creationDate);
        user.setModificationDate(ZonedDateTime.now());
        BeanUtils.copyProperties(user, userService.findById(id), "id");
        if (!user.getSapUsername().equals("") && !user.getExchangeUsername().equals("")
                && !user.getExchangeDomain().equals("") && !user.getTimeZone().equals(""))
            userService.save(userService.findById(id));

        return "redirect:/gui/users";
    }
}
