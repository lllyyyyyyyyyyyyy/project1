package com.vss.lynt.controller;

import com.vss.lynt.file.UserExcelExporter;
import com.vss.lynt.file.UserExcelImport;
import com.vss.lynt.dtos.UserDTO;
import com.vss.lynt.dtos.UserRoleDTO;
import com.vss.lynt.model.User;
import com.vss.lynt.model.UserRole;
import com.vss.lynt.service.ExcelService;
import com.vss.lynt.service.RoleService;
import com.vss.lynt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;

    @Autowired
    ExcelService excelService;

    @GetMapping("/crud-user")
    public String getUser(ModelMap modelMap){
        List<String> roles = new ArrayList<>();
        Collection<? extends GrantedAuthority> authorities =
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();
//        modelMap.addAttribute("authorities", authorities);
        for (GrantedAuthority au : authorities) {
            roles.add(au.getAuthority());
        }
        modelMap.addAttribute("roles", roles);
        modelMap.addAttribute("getAllUser", userService.getAllUser());
        modelMap.addAttribute("getAllUserAndRole", roleService.getAllUserRoleByUserIdAndRoleId());
        return "crud_user";
    }

    @GetMapping("/addUser")
    public String addUser(ModelMap modelMap){
        modelMap.addAttribute("user", new UserDTO());
        return "insertUser";
    }
    @PostMapping("/insert-user")
    public String insert(@ModelAttribute(value = "user") UserDTO userDTO){

        userService.insertUser(userDTO);
        return "redirect:/users/addRole";
    }

    @GetMapping("/addRole")
    public String addRole(ModelMap modelMap){
        modelMap.addAttribute("role", new UserRoleDTO());
        return "insertRole";
    }

    @PostMapping("/insert-role")
    public String insertRole(@ModelAttribute(value = "role") UserRole userRoleDTO, @ModelAttribute(value = "roleId") Long roleId, ModelMap modelMap) {

        userRoleDTO.setRoleId(roleId);
        if (roleService.checkInsert(userRoleDTO.getUserId(), userRoleDTO.getRoleId())) {

            modelMap.addAttribute("text", "tai khoan da ton tai!");
            return "redirect:/users/addUser";

        }
        else {
            roleService.insertRole(userRoleDTO);
            return "redirect:/users/crud-user";
        }
    }

    @PostMapping ("/find")
    public String find(@RequestParam(value = "keyword") String keyword, ModelMap modelMap) {
        List<String> roles = new ArrayList<>();
        Collection<? extends GrantedAuthority> authorities =
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();
//        modelMap.addAttribute("authorities", authorities);
        for (GrantedAuthority au : authorities) {
            roles.add(au.getAuthority());
        }
        modelMap.addAttribute("roles", roles);

        List<UserRole> userRoles = roleService.find(keyword);
        modelMap.addAttribute("keyword", keyword);
        modelMap.addAttribute("findList", userRoles);
        modelMap.addAttribute("getAllUserAndRole", roleService.getAllUserRoleByUserIdAndRoleId());
        modelMap.addAttribute("getAllUser", userService.getAllUser());
        return "crud_user";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable(value = "id") Integer id, ModelMap modelMap){

        UserRole roleupdate = roleService.updateUserRole(id);
        modelMap.addAttribute("updateRole", roleupdate);
        return "update-role";
    }

    @PostMapping("/update-role")
    public String updateRole(@ModelAttribute(value = "updateRole") UserRole userRole, ModelMap modelMap) {

            UserRole userRole1 = roleService.findByUserId(userRole.getUserId());
            roleService.insertRole(userRole1);
            modelMap.addAttribute("userID", userRole1.getUserId());
            return "redirect:/users/update-user";
//            User user = userService.findByUserId(userRole.getUserId()).get();

    }
    @GetMapping("/update-user")
    public String updateUser(@ModelAttribute(value = "userID") Integer userId, ModelMap modelMap){
        User user1 = userService.update(userId);
        modelMap.addAttribute("userUpdate", user1);
        return "update-user";
    }
    @PostMapping("/update-user")
    public String uptdateUser(@ModelAttribute(value = "userUpdate") User user, @RequestParam(value = "enabled") Boolean enabled, ModelMap modelMap){

        userService.insert(user, enabled);
        return "redirect:/users/crud-user";
    }

    @GetMapping("/upload")
    public String getViewUpload() {
        return "upload";
    }

    @PostMapping("/uploadUser")
    public String uploadFile(@RequestParam("file") MultipartFile file, ModelMap modelMap) {

        userService.deleteAll();
        String message = "";
        if (UserExcelImport.hasExcelFormat(file)) {
            try {
                excelService.saveExcelToUser(file);
                message = "Uploaded the file successfully: " + file.getOriginalFilename();
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename();
            }
            modelMap.addAttribute("message", message);

        }
        modelMap.addAttribute("getAllUser", userService.getAllUser());
        modelMap.addAttribute("getAllUserAndRole", roleService.getAllUserRoleByUserIdAndRoleId());
        return "crud_user";
    }

    @GetMapping("/export-excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        List<UserRole> listUsers = roleService.getAllUserRoleByUserIdAndRoleId() ;
        UserExcelExporter excelExporter = new UserExcelExporter(listUsers);
        excelExporter.export(response);
    }

}
