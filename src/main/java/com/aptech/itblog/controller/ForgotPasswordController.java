package com.aptech.itblog.controller;

import com.aptech.itblog.collection.User;
import com.aptech.itblog.model.CommonResponseBody;
import com.aptech.itblog.model.ResetPasswordVM;
import com.aptech.itblog.repository.UserRepository;
import com.aptech.itblog.service.EmailService;
import com.aptech.itblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.UUID;

import static com.aptech.itblog.common.CollectionLink.API;
import static com.aptech.itblog.common.CollectionLink.FORGOT_PASSWORD;
import static com.aptech.itblog.common.CollectionLink.FORGOT_PASSWORD_RESET;

@RestController
@RequestMapping(value = API)
public class ForgotPasswordController {

    public static final String APP_URL = "http://localhost:3000";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    // Display forgotPassword page
    @RequestMapping(value = FORGOT_PASSWORD, method = RequestMethod.POST)
    public ResponseEntity<?> sendResetMail(@RequestParam("email") String userEmail) {
        // Lookup user in database by e-mail
        User user = userRepository.findByEmail(userEmail);

        if (user == null) {
            return new ResponseEntity(new CommonResponseBody("UnknownEmail", 404, new LinkedHashMap() {
                {
                    put("message", "We didn't find an account for that e-mail address.");
                }
            }), HttpStatus.NOT_FOUND);
        } else {

            // Generate random 36-character string token for reset password
            user.setResetToken(UUID.randomUUID().toString());

            // Save token to database
            userService.registerUser(user);


            // Email message
            SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
            passwordResetEmail.setFrom("support@demo.com");
            passwordResetEmail.setTo(user.getEmail());
            passwordResetEmail.setSubject("Password Reset Request");
            passwordResetEmail.setText("To reset your password, click the link below:\n"
                    + APP_URL + "/forgot-password/reset?token=" + user.getResetToken());

            emailService.sendEmail(passwordResetEmail);

            // Add success message to view
            return new ResponseEntity(new CommonResponseBody("OK", 200, new LinkedHashMap() {
                {
                    put("message", "A password reset link has been sent to " + userEmail);
                }
            }), HttpStatus.OK);
        }
    }

    // Display form to reset password
    @RequestMapping(value = FORGOT_PASSWORD_RESET, method = RequestMethod.GET)
    public ResponseEntity<?> resetPasswordPage(@RequestParam("token") String token) {

        User user = userRepository.findByResetToken(token);

        if (user == null) { // Token found in DB
            return new ResponseEntity(new CommonResponseBody("UnknownResetToken", 404, new LinkedHashMap() {
                {
                    put("message", "Oops!  This is an invalid password reset link.");
                }
            }), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(new CommonResponseBody("OK", 200, new LinkedHashMap() {
            {
                put("message", "Reset password verified");
            }
        }), HttpStatus.OK);
    }

    // Process reset password form
    @RequestMapping(value = FORGOT_PASSWORD_RESET, method = RequestMethod.POST)
    public ResponseEntity<?> setNewPassword(@RequestParam("token") String token, @RequestParam("password") String password) {

        // Find the user associated with the reset token
        User resetUser = userRepository.findByResetToken(token);

        // This should always be non-null but we check just in case
        if (resetUser != null) {


            // Set the reset token to null so it cannot be used again
            resetUser.setResetToken(null);

            // Save user
            userService.updatePassword(resetUser, password);

            // In order to set a model attribute on a redirect, we must use
            // RedirectAttributes
            return new ResponseEntity(new CommonResponseBody("OK", 200, new LinkedHashMap() {
                {
                    put("message", "You have successfully reset your password.");
                }
            }), HttpStatus.OK);

        } else {
            return new ResponseEntity(new CommonResponseBody("BadRequest", 400, new LinkedHashMap() {
                {
                    put("message", "Oops!  This is an invalid password reset link.");
                }
            }), HttpStatus.BAD_REQUEST);
        }
    }



}
