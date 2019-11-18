package com.mwaysolution.sapMock.rest;


import com.mwaysolution.sapMock.model.User;
import com.mwaysolution.sapMock.model.UserRegistrationStatus;
import com.mwaysolution.sapMock.service.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;

@Controller
public class GuiUserController {

    @Value("${syncItem.register.url}")
    private String registerUrl;

    @Value("${syncItem.unregister.url}")
    private String unregisterUrl;

    @Value("${syncItem.hostname}")
    private String hostname;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserServiceImpl userService;

    private static final Logger logger = LogManager.getLogger(GuiUserController.class);

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
        User user = userService.getById(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<User> entity = new HttpEntity<>(user, headers);
        if (restTemplate.exchange(
                hostname + registerUrl +
                        "?username=" + user.getSapUsername() +
                        "&email=" + user.getExchangeUsername() +
                        "&domain=" + user.getExchangeDomain() +
                        "&timezone=" + user.getTimeZone(),
                HttpMethod.GET, entity, String.class).getStatusCodeValue() == 200) {
            logger.info("User " + user.getSapUsername().toUpperCase() + " was registered!");
            user.setRegistrationStatus(UserRegistrationStatus.REGISTERED);
            userService.save(user);
        } else
            logger.error("Something went wrong. User " + user.getSapUsername().toUpperCase() + " wasn't registered!");
        return "redirect:/gui/users";
    }

    @RequestMapping("/gui/users/unregister/{id}")
    public String unregister(@PathVariable("id") Integer id) {
        User user = userService.getById(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<User> entity = new HttpEntity<>(user, headers);
        if (restTemplate.exchange(
                hostname + unregisterUrl +
                        "?username=" + user.getSapUsername(),
                HttpMethod.GET, entity, String.class).getStatusCodeValue() == 200) {
            logger.info("User " + user.getSapUsername().toUpperCase() + " was unregistered!");
            user.setRegistrationStatus(UserRegistrationStatus.UNREGISTERED);
            userService.save(user);
        } else
            logger.error("Something went wrong. User " + user.getSapUsername().toUpperCase() + " wasn't unregistered!");
        return "redirect:/gui/users";
    }
}
