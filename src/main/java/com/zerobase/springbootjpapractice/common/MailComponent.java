package com.zerobase.springbootjpapractice.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Component
@RequiredArgsConstructor
@Slf4j
public class MailComponent {

    private final JavaMailSender mailSender;

    public boolean send(String fromEmail, String fromName,
                        String toEmail, String toName,
                        String title, String content) {
        boolean result = false;

        MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                InternetAddress from = new InternetAddress();
                from.setAddress(fromEmail);
                from.setPersonal(fromName, "UTF-8");

                InternetAddress to = new InternetAddress();
                to.setAddress(toEmail);
                to.setPersonal(toName, "UTF-8");

                messageHelper.setFrom(from);
                messageHelper.setTo(to);
                messageHelper.setSubject(title);
                messageHelper.setText(content, true);
            }
        };
        try {
            mailSender.send(messagePreparator);
            result = true;
        }catch (Exception e) {
            log.info(e.getMessage());
        }
        return result;
    }
}
