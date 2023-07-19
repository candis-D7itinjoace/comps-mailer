package com.bayrem.compsmailer.runners;

import com.bayrem.compsmailer.service.EmailService;
import com.opencsv.CSVReader;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;

@Component
@RequiredArgsConstructor
public class StartUpRunner implements CommandLineRunner {


    private final EmailService emailService;

    @Override
    public void run(String... args) throws Exception {
        ClassPathResource resource = new ClassPathResource("static/data/comps.csv");
        ClassPathResource resource1 = new ClassPathResource("static/CV.pdf");
        try (CSVReader reader = new CSVReader(new InputStreamReader(resource.getInputStream()))) {
            reader.readNext();
                String[] line;
                while ((line = reader.readNext()) != null) {

                    emailService.sendHtmlEmail(line[1], line[0], resource1.getInputStream().readAllBytes());

                    Thread.sleep(10000L);

                }

        }
    }
}
