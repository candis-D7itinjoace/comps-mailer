package com.bayrem.compsmailer.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;



    public void sendHtmlEmail(String recipient, String enterpriseName, byte[] resume) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        // Load HTML email template
        Context context = new Context();
        context.setVariable("enterpriseName", enterpriseName); // Example variable

        String htmlContent = templateEngine.process("emailTemplate", context);

        helper.setTo(recipient);
        helper.setSubject("Application for Junior Software Engineer Position");
        helper.setText(htmlContent, true);
        helper.addAttachment("CV.pdf", new ByteArrayResource(resume));

        // Optionally, add attachments if needed
        // helper.addAttachmendt("attachment.pdf", new ClassPathResource("path/to/attachment.pdf"));

        javaMailSender.send(message);
    }
}

