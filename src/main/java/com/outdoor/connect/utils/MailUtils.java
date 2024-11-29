package com.outdoor.connect.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.outdoor.connect.model.Users;

@Component
public class MailUtils {
    private static final Logger logger = LoggerFactory.getLogger(MailUtils.class);

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail(String toEmail, String subject, String message) {
        logger.info("MailComponent | sendMail | START");

        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(toEmail);
            mailMessage.setSubject(subject);
            mailMessage.setText(message);
            mailMessage.setFrom("otdrcnnct@gmail.com");

            javaMailSender.send(mailMessage);
        } catch (MailSendException e) {
            logger.error("MailComponent | SendMail | error: " + e.getMessage());
        }
    }

    public void sendMailUserVerification(Users user) {
        logger.info("MailUtils | sendMailUserVerification | START");

        try {
            logger.info("MailUtils | SendMailUserVerification | user: " + user.toString());
            String message = """
                    Greetings from Outdoor Connect~

                    Hope this message finds you well!

                    Verification code: """ + user.getVerificationCode() + """
                    . This will expire in 10 minutes.


                    This is a email notification, please do not reply!!!
                    """;

            sendMail(user.getEmailAddress(), "Verification Code", message);

            logger.info("MailUtils | SendMailUserVerification | END");
        } catch (Exception ex) {
            logger.error("MailUtils | SendMailUserVerification | error: " + ex.getMessage());
        }
    }
}
