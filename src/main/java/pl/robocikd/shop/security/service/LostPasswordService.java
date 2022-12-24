package pl.robocikd.shop.security.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.robocikd.shop.common.mail.EmailClientService;
import pl.robocikd.shop.order.repository.ShipmentRepository;
import pl.robocikd.shop.security.model.User;
import pl.robocikd.shop.security.model.dto.ChangePasswordDto;
import pl.robocikd.shop.security.model.dto.EmailObject;
import pl.robocikd.shop.security.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LostPasswordService {

    private final UserRepository userRepository;
    private final EmailClientService emailClientService;
    private final ShipmentRepository shipmentRepository;

    @Value("${app.serviceAddress}")
    private String serviceAddress;

    @Transactional
    public void sendLostPasswordLink(EmailObject email) {
        User user = userRepository.findByUsername(email.getEmail())
                .orElseThrow(() -> new RuntimeException("Taki email nie istnieje"));
        String hash = generateHashForLostPassword(user);
        user.setHash(hash);
        user.setHashDate(LocalDateTime.now());
        emailClientService.getInstance()
                .send(email.getEmail(), "Zresetuj hasło", createMessage(createLink(hash)));
    }

    @Transactional
    public void changePassword(ChangePasswordDto changePasswordDto) {
        if (!Objects.equals(changePasswordDto.getPassword(),
                changePasswordDto.getRepeatPassword())) {
            throw new RuntimeException("Hasła nie są takie same");
        }
        User user = userRepository.findByHash(changePasswordDto.getHash())
                .orElseThrow(() -> new RuntimeException("Nieprawidłowy link"));
        boolean hashNotExpired = user.getHashDate().plusMinutes(10).isAfter(LocalDateTime.now());
        if (hashNotExpired) {
            user.setPassword("{bcrypt}" + new BCryptPasswordEncoder().encode(changePasswordDto.getPassword()));
            user.setHash(null);
            user.setHashDate(null);
        } else {
            throw new RuntimeException("Link stracił ważność");
        }
    }

    private String createLink(String hash) {
        return serviceAddress + "/lostPassword/" + hash;
    }

    private String createMessage(String hashLink) {
        return "Wygenerowaliśmy dla Ciebie link do zmiany hasła" +
                "\n\nKliknij link, żeby zresetować hasło: " +
                "\n" + hashLink +
                "\n\nDziękujemy.";
    }

    private String generateHashForLostPassword(User user) {
        String toHash = user.getId() + user.getUsername() + user.getPassword() +
                LocalDateTime.now();
        return DigestUtils.sha256Hex(toHash);
    }
}
