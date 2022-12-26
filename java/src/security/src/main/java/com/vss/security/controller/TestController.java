package com.vss.security.controller;

import com.vss.security.excel.UserExcelExporter;
import com.vss.security.excel.UserExcelImport;
import com.vss.security.message.ResponseMessage;
import com.vss.security.model.LoginRequest;
import com.vss.security.model.User;
import com.vss.security.model.UserDTO;
import com.vss.security.model.UserEnum;
import com.vss.security.repository.UserRepository;
import com.vss.security.service.impliment.ExcelService;
import com.vss.security.service.impliment.LoginRequestService;
import com.vss.security.service.impliment.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static java.awt.SystemColor.text;

@Controller
public class TestController {

    @Autowired
    ExcelService excelService;

    @Autowired
    UserService userService;

//    @Autowired
//    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    LoginRequestService loginRequestService;

    @GetMapping
    public String get(){
        return "index";
    }

    @GetMapping("/register")
    public String showSignUpForm(ModelMap modelMap){
        modelMap.addAttribute("user", new User());
        return "signupForm";
    }

//    @GetMapping("/login")
//    public String loginUser(ModelMap modelMap){
//        return "login";
//    }
//    @PostMapping("/get-login")
//    public String getLogin(){
//        return "redirect:/list-users";
//    }
    @GetMapping("/login")
    public String showLogin(ModelMap modelMap) throws NoSuchAlgorithmException {
        LoginRequest loginRequest = new LoginRequest();
        modelMap.addAttribute("loginRequest", loginRequest);
        return "login";
    }

    @PostMapping("/checklogin")
    public String checkLogin(ModelMap modelMap,@RequestParam(value = "userName") String userName, @RequestParam(value = "password") String password) throws NoSuchAlgorithmException {

//        if(loginRequest.getUserName().equals(userName) && loginRequest.getPassword().equals(password)){
//            return "view-user";
//        }
//
//        else{
//            return "redirect:/login";
//        }

//        List<User> userLíst = userService.getList();
//        modelMap.addAttribute("listUser", userLíst);
        if(loginRequestService.ckeckLogin(userName,password)){
            String text = loginRequestService.checkStatus(userName);
            if(text == "ACTIVE"){
                return "redirect:/list-users";
            }
            else{
                modelMap.addAttribute("text",text);
                return "redirect:/login";
            }

        }

        return "redirect:/login";
    }

    @PostMapping("/process-register")
    public String processRegistation(@ModelAttribute(value = "user") UserDTO user, ModelMap modelMap){
        userService.saveUser(user);
//        userRepository.save(user);
        return "redirect:/login";
    }

    @GetMapping("/list-users")
    public String viewUserList(ModelMap modelMap){
        modelMap.addAttribute("listUser", userService.getList());
        return "users";
    }

    @GetMapping  ("/updateUser/{id}")
    public String update(@PathVariable Long id, ModelMap modelMap){
        User user = userService.updateUser(id);
        modelMap.addAttribute("userUpdate", user);

        modelMap.addAttribute("userName", user.getUserName());
//        List<User> userLíst = userService.getList();
//        modelMap.addAttribute("listUser", userLíst);
//        modelMap.addAttribute("user", new UserDTO());
        return "updateUser";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute(value = "userUpdate") User user, @RequestParam(value = "userEnum") String uenum, ModelMap modelMap){

        User userDTO = new User();
        userDTO = userService.findByUserName(user.getUserName());
        userDTO.setUserEnum(uenum);
        userService.save(userDTO);
        modelMap.addAttribute("listUser", userService.getList());
        return "users";
    }


    //get by id
    //return User(username, password) => html (modelMap)

    //update => POST => @ModelAttribute(value = "user") UserDTO user (new username, new password)
    //find by id =>CurrentUser //tim ra User can sua
    //set currentUser <= UserDTO  //set thong tin sua vao User
    //save currentUser

    @GetMapping("/upload")
    public String getViewUpload() {
        return "upload";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, ModelMap modelMap) {

        String message = "";
        if(UserExcelImport.hasExcelFormat(file)){
            try {
                excelService.saveExcelToUser(file);
                message = "Uploaded the file successfully: " + file.getOriginalFilename();
            }catch (Exception e){
                message = "Could not upload the file: " + file.getOriginalFilename();
            }
            modelMap.addAttribute("message", message);

        }
        modelMap.addAttribute("listUser", userService.getList());
        return "users";

//        String message = "";

//        if (UserExcelImport.hasExcelFormat(file)) {
//            try {
//                excelService.saveExcelToUser(file);
//
//                modelMap.addAttribute("")
////                message = "Uploaded the file successfully: " + file.getOriginalFilename();
//                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
//            } catch (Exception e) {
//                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
//                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
//            }
//        }
//
//        message = "Please upload an excel file!";
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }

    @GetMapping("/export-excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<User> listUsers = userService.getList();

        UserExcelExporter excelExporter = new UserExcelExporter(listUsers);

        excelExporter.export(response);
    }
}
