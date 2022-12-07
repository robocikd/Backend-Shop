package pl.robocikd.shop.common.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FakeEmailService implements EmailSender {
    @Override
    public void send(String to, String subject, String msg) {
        log.info("Email has been sent");
        log.info("To " + to);
        log.info("Subject: " + subject);
        log.info("Message: " + msg);
    }
}
