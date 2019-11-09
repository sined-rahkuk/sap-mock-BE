package com.mwaysolution.sapMock.rest;


import com.mwaysolution.sapMock.model.Appointment;
import com.mwaysolution.sapMock.model.User;
import com.mwaysolution.sapMock.service.AppointmentServiceImpl;
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
public class GuiAppointmentController {

    @Autowired
    private AppointmentServiceImpl appointmentService;

    @Autowired
    private UserServiceImpl userService;

    private User user;

    @RequestMapping("/gui/appointments")
    public String viewHomePage(Model model) {
        List<Appointment> listAppointments = appointmentService.get();
        model.addAttribute("listAppointments", listAppointments);

        return "indexAppointment";
    }

    @RequestMapping("/gui/appointments/create/{id}")
    public String createAppointment(Model model, @PathVariable("id") Integer id) {
        Appointment appointment = new Appointment();
        user = userService.getById(id);
        appointment.setUser(user);
        model.addAttribute("appointment", appointment);

        return "createAppointment";
    }

    @RequestMapping(value = "/gui/appointments/save", method = RequestMethod.POST)
    public String saveAppointment(@ModelAttribute("appointment") Appointment appointment) {
        appointment.setUser(user);
        appointmentService.save(appointment);

        return "redirect:/gui/appointments";
    }

    @RequestMapping("/gui/appointments/delete/{id}")
    public String deleteAppointment(@PathVariable("id") String id) {
        appointmentService.deleteById(id);

        return "redirect:/gui/appointments";
    }

    @RequestMapping("/gui/appointments/edit/{id}")
    public ModelAndView updateAppointmentPage(@PathVariable("id") String id) {
        ModelAndView model = new ModelAndView("updateAppointment");

        Appointment appointment = appointmentService.getById(id);
        model.addObject("appointment", appointment);

        return model;
    }

    @RequestMapping(value = "/gui/appointments/update", method = RequestMethod.POST)
    public String updateAppointment(@ModelAttribute("appointment") Appointment appointment) {
        appointmentService.save(appointment);

        return "redirect:/gui/appointments";
    }
}
