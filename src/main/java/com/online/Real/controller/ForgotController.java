package com.online.Real.controller;

import com.online.Real.dao.UserRepository;
import com.online.Real.entities.User;
import com.online.Real.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Random;

@Controller
public class ForgotController {
    Random random = new Random(1000);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private EmailService emailService;
    //forgot password handler
    @RequestMapping("/forgot")
    public String openEMailForm(){
        return "forgot_email_form";
    }
    //otp handler send email
    @PostMapping("/send-otp")
    public String sendOtp(@RequestParam("email") String email, Principal principal, HttpSession session){
        System.out.println("Email " + email);
        //generate random otp

        int otp = random.nextInt(998599);
        System.out.println("OTP : "+otp);
        //now we are executing
        String subject = "OPT verification";
        String message ="<p>" +"Dear Customer,"+"<br>" +
                "\n" +
                "I hope this email finds you well. We have received a request to reset the password associated with your account. To proceed with the password reset process, please use the following One-Time Password (OTP) within the next 59 seconds \n" +
                "\n" +"<h2>"+
                otp +"</h2>"+
                "\n" +
                "Please ensure that you keep this OTP confidential and do not share it with anyone. If you did not initiate this password reset request, please ignore this email or contact our support team immediately.\n" +
                "\n" +"<br>" +
                "Thank you for choosing our service. If you have any further questions or need assistance, feel free to reach out to us at [insert contact information]."+"</p>";
        String to = email;

        boolean flag = this.emailService.sendEmail(subject,message,to);

        if(flag){
            session.setAttribute("myotp" , otp);
            session.setAttribute("email",email);
            return "verify_otp";
        }
        else{
            session.setAttribute("message","Check your Email");
            return "forgot_email_form";
        }

    }

    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam("otp") int otp,HttpSession session){
        int myotp = (int)session.getAttribute("myotp");
        String email = (String) session.getAttribute("email");
        if(myotp==otp){
            //change password form show karo
            User user = this.userRepository.loadUserByUsername(email);
            if(user==null){
                //error message
                session.setAttribute("message","User does not exist");
                return "forgot_email_form";
            }
            else{
                return "change_password_form";
            }

        }
        else{
            session.setAttribute("message","!!! Wrong OTP !!!");
            return "verify_otp";
        }
    }

    @PostMapping("/send-new-password")
    public String newPassword(@RequestParam("newPassword") String password, Principal principal,HttpSession session){
        String email = (String) session.getAttribute("email");
        User currentUser = this.userRepository.loadUserByUsername(email);
        currentUser.setPassword(this.bCryptPasswordEncoder.encode(password));
        this.userRepository.save(currentUser);
        session.setAttribute("message","Password changed");
        return "change_password_form";
    }



}
