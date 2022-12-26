package com.msp.mvc.controller;

import com.msp.mvc.common.CommonConst;
import com.msp.mvc.common.ServiceUtil;
import com.msp.mvc.model.Employee;
import com.msp.mvc.service.ServicesEmployee;
import com.msp.mvc.service.ServicesEmployeeImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.msp.mvc.common.CommonConst.EMAIL_REGEX;
import static com.msp.mvc.common.CommonConst.FIELD_ERROR_MSG;


@Controller

public class democontroller {



    @Autowired
    ServicesEmployee servicesEmployee;

    //http://localhost:8080//

    @GetMapping("/")
    public String homePage(ModelMap modelMap) {
        modelMap.addAttribute("list", servicesEmployee.showAllEmployee());

        return "filejsp";
    }

    @GetMapping("/addnew")
    public String addnewEmployee(ModelMap modelMap) {
        Employee employee = new Employee();
        modelMap.addAttribute("newEmployee", employee);
        return "insert";
    }

    @PostMapping("/insert")
    public String insertEmployee(@ModelAttribute("newEmployee") Employee employee, ModelMap modelMap) {

        if (employee.getAge() < 1 || employee.getAge() >= 100) {

            String text3 = "Please provide a age";
            modelMap.addAttribute("errorAge", text3);
            return "insert";
        }

        employee.getEmail().matches(EMAIL_REGEX);

        Matcher matcher = ServiceUtil.pattern.matcher(employee.getEmail());
        boolean match = matcher.matches();
        modelMap.addAttribute(FIELD_ERROR_MSG, match);
        if (match == false) {
            String text = "email is not valid";
            modelMap.addAttribute("errorMsg", text);
            return CommonConst.ErrorMsg.EM_AGE_REQUIRED;
        }

        if (employee.getName().isEmpty()) {

            String text2 = "Please provide a name";
            modelMap.addAttribute("errorName", text2);
            return "insert";
        }

        servicesEmployee.insertEmployee(employee);
        return "redirect:/";

    }

    public static void main(String[] args) {
        boolean matches = "@gmail.com".matches(EMAIL_REGEX);
        boolean matches2 = "2@gmail.com".matches(EMAIL_REGEX);
        System.out.println(matches);
        System.out.println(matches2);
    }

//    @RequestMapping(value = "/findid/{id}",method = RequestMethod.POST)
//    public ModelMap findid(@PathVariable(value = "id") Long id, ModelMap modelMap){
//        modelMap.addAttribute("findId", servicesEmployee.findbyId(id));
//        return "findId";
//    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteEmployee(@PathVariable(value = "id") Long id) {
        servicesEmployee.deleteEmployee(id);
        return "redirect:/";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String updateEmployee(@PathVariable(value = "id") Long id, ModelMap modelMap) {
        Employee employee = servicesEmployee.updateEmployee(id);
        modelMap.addAttribute("employee", employee);
        return "update";
    }


    @GetMapping(path = "/findEmployee")
    public String findEmployee(ModelMap model) {
        Long id = 1L;
        model.addAttribute("id", id);
        return "findId";
    }

    @PostMapping("/find")
    public String findId(@RequestParam("id") Long id, ModelMap modelMap) {

        Employee employee = servicesEmployee.findbyId(id);
        List<Employee> employees = new ArrayList<>();
        employees.add(employee);
        modelMap.addAttribute("list", servicesEmployee.showAllEmployee());
        modelMap.addAttribute("listEmployee", employees);
        return "filejsp";
    }


}
