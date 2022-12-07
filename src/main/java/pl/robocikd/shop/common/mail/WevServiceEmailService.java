package pl.robocikd.shop.common.mail;

import org.springframework.stereotype.Service;

@Service
public class WevServiceEmailService implements EmailSender {
    @Override
    public void send(String to, String subject, String msg) {
        throw new RuntimeException("Not implemented yet");
    }
}
