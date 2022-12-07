package pl.robocikd.shop.common.mail;

public class WevServiceEmailService implements EmailSender {
    @Override
    public void send(String to, String subject, String msg) {
        throw new RuntimeException("Not implemented yet");
    }
}
