package com.mwaysolution.sapMock.rest;


import com.mwaysolution.sapMock.model.User;
import com.mwaysolution.sapMock.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class GuiUserController {

    @Autowired
    private UserServiceImpl userService;

    @RequestMapping("/gui/users")
    public String viewHomePage(Model model) {
        List<User> listUsers = userService.get();
        model.addAttribute("listUsers", listUsers);

        return "indexUser";
    }

    @RequestMapping("/gui/createUser")
    public String createUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);

        return "createUser";
    }

    @RequestMapping(value = "/gui/users/save", method = RequestMethod.POST)
    public String saveUser(@ModelAttribute("user") User user) {
        userService.save(user);

        return "redirect:/gui/users";
    }

    @RequestMapping("/gui/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        userService.deleteById(id);

        return "redirect:/gui/users";
    }

    @RequestMapping("/gui/users/edit/{id}")
    public ModelAndView updateUserPage(@PathVariable("id") Integer id) {
        ModelAndView model = new ModelAndView("updateUser");

        User user = userService.getById(id);
        model.addObject("user", user);

        return model;
    }

    @RequestMapping(value = "/gui/users/update", method = RequestMethod.POST)
    public String updateUser(@ModelAttribute("user") User user) {
        userService.save(user);

        return "redirect:/gui/users";
    }

    @RequestMapping("/gui/users/register/{id}")
    public String register(@PathVariable("id") Integer id) {
        userService.register(userService.getById(id));

        return "redirect:/gui/users";
    }

    @RequestMapping("/gui/users/unregister/{id}")
    public String unregister(@PathVariable("id") Integer id) {
        userService.unregister(userService.getById(id));

        return "redirect:/gui/users";
    }
}
